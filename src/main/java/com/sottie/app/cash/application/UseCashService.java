package com.sottie.app.cash.application;

import com.sottie.app.cash.error.CashErrorCode;
import com.sottie.app.cash.model.Cash;
import com.sottie.app.cash.model.CashLog;
import com.sottie.app.cash.model.CashStatusCategory;
import com.sottie.app.cash.model.CashTypeCategory;
import com.sottie.app.cash.model.dto.CashLogDto;
import com.sottie.app.cash.model.record.CashRequest;
import com.sottie.app.cash.repository.CashLogRepository;
import com.sottie.app.cash.repository.CashRepository;
import com.sottie.app.gathering.error.GatheringErrorCode;
import com.sottie.app.user.model.User;
import com.sottie.app.user.repository.UserRepository;
import com.sottie.errors.CommonException;
import com.sottie.utils.SottieUserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UseCashService {

	private final CashRepository cashRepository;

	private final CashLogRepository cashLogRepository;
	private final UserRepository userRepository;

	public CashLogDto useCash(CashRequest cashRequest) {

		Long userId = SottieUserUtils.getUserIdLong();

		Optional<User> optUser = userRepository.findById(userId);

		Cash cash = cashRepository.findByUserId(userId);

		CashLog cashLog;

		if (optUser.isPresent()) {

			Integer totalAmount = cash.getAmount();
			Integer chargeAmount = cashRequest.amount();

			Integer calculatedTotalAmount = subtractChargeAmountFromTotalAmount(cash, totalAmount, chargeAmount);

			Cash savedCash = cashRepository.save(cash.setCalculatedAmount(calculatedTotalAmount));

			cashLog = new CashLog(savedCash.getId(), CashStatusCategory.COMPLETED, CashTypeCategory.USE);

			cashLogRepository.save(cashLog);

		} else {
			cashLog = new CashLog(cash.getId(), CashStatusCategory.FAILED, CashTypeCategory.USE);

			cashLogRepository.save(cashLog);

			log.error(GatheringErrorCode.NOT_HOST_USER.getMessage());
			throw CommonException.builder(GatheringErrorCode.NOT_HOST_USER).build();
		}

		return CashLogDto.from(cashLog);
	}

	private Integer subtractChargeAmountFromTotalAmount(Cash cash, Integer totalAmount, Integer chargeAmount) {

		if (totalAmount < chargeAmount) {
			CashLog cashLog = new CashLog(cash.getId(), CashStatusCategory.FAILED, CashTypeCategory.USE);

			cashLogRepository.save(cashLog);

			log.error(CashErrorCode.INSUFFICIENT_TOTAL_CASH.getMessage());
			throw CommonException.builder(CashErrorCode.INSUFFICIENT_TOTAL_CASH).build();
		}

		return totalAmount - chargeAmount;
	}

}
