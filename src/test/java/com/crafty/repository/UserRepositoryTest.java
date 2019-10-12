package com.crafty.repository;

import com.crafty.entity.Member;
import com.crafty.entity.User;
import com.crafty.enumeration.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

	private static final String EMAIL_ADDRESS = "new-user@email.com";
	private static final String USER_ID = "userId";

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void test() {
		Member member = new Member();
		member.setId("memberId");
		memberRepository.save(member);
		User user = new User();
		user.setId("userId");
		user.setMemberId("memberId");
		user.setEmail(EMAIL_ADDRESS);
		user.setPassword("encodedPass");
		user.setRole(UserRole.ROLE_MEMBER);
		userRepository.save(user);

		Optional<User> userOptional = userRepository.findByEmail(EMAIL_ADDRESS);
		Optional<User> anotherUserOptional = userRepository.findByEmail("another-user@email.com");

		assertTrue(userOptional.isPresent());
		assertEquals(USER_ID, userOptional.get().getId());
		assertFalse(anotherUserOptional.isPresent());
	}
}
