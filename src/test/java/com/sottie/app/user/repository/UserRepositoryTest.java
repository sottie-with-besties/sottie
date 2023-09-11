package com.sottie.app.user.repository;

import static com.sottie.app.user.model.UserTest.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.sottie.app.user.model.User;

@DataJpaTest
class UserRepositoryTest {
	@Autowired
	private UserRepository userRepository;

	@Test
	void user_추가() {
		//given
		User user = user();
		userRepository.save(user);
		//when
		Optional<User> result = userRepository.findById(1L);

		//then
		assertThat(result).isNotEqualTo(Optional.empty());
	}

}
