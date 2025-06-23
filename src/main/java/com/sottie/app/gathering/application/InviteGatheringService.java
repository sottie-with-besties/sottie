package com.sottie.app.gathering.application;

import com.sottie.app.friend.application.GetFriendService;
import com.sottie.app.friend.model.FriendProfile;
import com.sottie.app.gathering.error.GatheringErrorCode;
import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GatheringInvitation;
import com.sottie.app.gathering.model.GenderCategory;
import com.sottie.app.gathering.model.InvitationStatusCategory;
import com.sottie.app.gathering.model.dto.GatheringInvitationDto;
import com.sottie.app.gathering.model.record.InviteGatheringRequest;
import com.sottie.app.gathering.model.record.JoinGatheringRequest;
import com.sottie.app.gathering.model.record.ReactInviteGatheringRequest;
import com.sottie.app.gathering.repository.GatheringInvitationRepository;
import com.sottie.app.gathering.repository.GatheringRepository;
import com.sottie.app.user.model.Gender;
import com.sottie.app.user.model.User;
import com.sottie.app.user.repository.UserRepository;
import com.sottie.errors.CommonException;
import com.sottie.utils.SottieUserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class InviteGatheringService {
	private final UserRepository userRepository;
	private final GatheringRepository gatheringRepository;
	private final JoinGatheringService joinGatheringService;
	private final GetFriendService getFriendService;
	private final GatheringInvitationRepository gatheringInvitationRepository;

	public GatheringInvitationDto inviteGathering(InviteGatheringRequest inviteGatheringRequest) {

		Long userId = SottieUserUtils.getUserIdLong();

		Optional<User> optUser = userRepository.findById(userId);

		if (optUser.isPresent()) {
			Optional<Gathering> optGathering = gatheringRepository.findById(inviteGatheringRequest.gatheringId());

			if (optGathering.isPresent()) {
				Gathering gathering = optGathering.get();
				User user = optUser.get();

				Optional<User> optFriendUser = userRepository.findById(inviteGatheringRequest.friendUserId());

				if (optFriendUser.isPresent()) {

					List<FriendProfile> friendProfiles = getFriendService.getFriendProfileList();

					List<Long> friendUserIds = friendProfiles.stream().map(FriendProfile::userId).toList();

					boolean isFriend = friendUserIds.contains(optFriendUser.get().getId());

					if (isFriend) {
						GatheringInvitation gatheringInvitation = GatheringInvitation.createGatheringInvitation(user.getId(), gathering.getId(), optFriendUser.get().getId());
						gatheringInvitationRepository.save(gatheringInvitation);

						return GatheringInvitationDto.from(gatheringInvitation);

					} else {
						throw CommonException.builder(GatheringErrorCode.CANNOT_FIND_FRIEND_USER).build();
					}

				} else {
					throw CommonException.builder(GatheringErrorCode.CANNOT_FIND_FRIEND_USER).build();
				}

			} else {
				throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();
			}

		} else {
			throw CommonException.builder(GatheringErrorCode.NOT_HOST_USER).build();
		}
	}

	public GatheringInvitationDto reactInviteGathering(ReactInviteGatheringRequest reactInviteGatheringRequest) {
		Long userId = SottieUserUtils.getUserIdLong();

		Optional<User> optUser = userRepository.findById(userId);

		if (optUser.isPresent()) {
			Optional<Gathering> optGathering = gatheringRepository.findById(reactInviteGatheringRequest.gatheringId());

			if (optGathering.isPresent()) {
				Gathering gathering = optGathering.get();
				User user = optUser.get();

				Optional<User> optFriendUser = userRepository.findById(reactInviteGatheringRequest.friendUserId());

				if (optFriendUser.isPresent()) {

					List<FriendProfile> friendProfiles = getFriendService.getFriendProfileList();

					List<Long> friendUserIds = friendProfiles.stream().map(FriendProfile::userId).toList();

					boolean isFriend = friendUserIds.contains(optFriendUser.get().getId());

					if (isFriend) {
						Optional<GatheringInvitation> optGatheringInvitation = gatheringInvitationRepository.findByUserIdAndGatheringIdAndFriendUserId(user.getId(), gathering.getId(), optFriendUser.get().getId());

						if (optGatheringInvitation.isPresent()) {
							// 프론트에서 받아온 초대 응답 값 set (APPROVED or REFUSED)
							GatheringInvitation gatheringInvitation = optGatheringInvitation.get().reactGatheringInvitation(reactInviteGatheringRequest.invitationStatus());

							if (gatheringInvitation.getInvitationStatus().equals(InvitationStatusCategory.APPROVED)) {
								if (!isNoMoreRoomGathering(gathering, user)) {
									joinGatheringService.joinGathering(JoinGatheringRequest.builder().userId(user.getId())
											.gatheringId(reactInviteGatheringRequest.gatheringId()).build());
								} else {
									// 초대 받은 유저가 APPROVED 했어도 인원이 찼으면 WAITING 으로 status 변경해서 return
									gatheringInvitation.reactGatheringInvitation(InvitationStatusCategory.WAITING);
								}
							}
							gatheringInvitationRepository.save(gatheringInvitation);
							return GatheringInvitationDto.from(gatheringInvitation);

						} else {
							throw CommonException.builder(GatheringErrorCode.CANNOT_FIND_GATHERING_INVITATION).build();
						}

					} else {
						throw CommonException.builder(GatheringErrorCode.CANNOT_FIND_FRIEND_USER).build();
					}

				} else {
					throw CommonException.builder(GatheringErrorCode.CANNOT_FIND_FRIEND_USER).build();
				}

			} else {
				throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();
			}

		} else {
			throw CommonException.builder(GatheringErrorCode.NOT_HOST_USER).build();
		}
	}

	/**
	 * 초대 응답 값이 APPROVED 일 때, 이미 채팅방 활성화 된 방인지 or 남/녀 인원 빈자리 있는지 확인 필요
	 */
	private Boolean isNoMoreRoomGathering(Gathering gathering, User user) {

		GenderCategory genderCategory = gathering.getGenderRestriction();
		Gender gender = user.getGender();

		if (gathering.isNoMoreRoom()) {
			return true;
		}

		switch (genderCategory) {
			case NONE:
				return gathering.isNoMoreRoom();
			case MIX:
				if (gender.equals(Gender.MALE)) {
					return gathering.isNoRoomForMale();
				} else {
					return gathering.isNoRoomForFemale();
				}
			case FEMALE:
				if (gender.equals(Gender.MALE)) {
					return true;
				} else {
					return gathering.isNoRoomForFemale();
				}
			case MALE:
				if (gender.equals(Gender.FEMALE)) {
					return true;
				} else {
					return gathering.isNoRoomForMale();
				}
			default:
				return true;
		}
	}

}
