package com.crafty.service;

import com.crafty.dto.RegistrationDTO;
import com.crafty.entity.User;
import com.crafty.repository.MemberRepository;
import com.crafty.repository.UserRepository;
import com.crafty.web.exception.BadRequestException;
import com.crafty.web.exception.ConflictException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AuthServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	private AuthService authService;

	@Before
	public void setup() {
		authService = new AuthService(userRepository, memberRepository, passwordEncoder,
			null, null, null,null);
	}

	@Test(expected = BadRequestException.class)
	public void registerEmptyPasswordTest() {
		RegistrationDTO registrationDTO = new RegistrationDTO();
		registrationDTO.setEmail("register_user@email.com");
		authService.register(registrationDTO);
	}

	@Test(expected = BadRequestException.class)
	public void registerEmptyEmailAddressTest() {
		RegistrationDTO registrationDTO = new RegistrationDTO();
		registrationDTO.setPassword("randomPass");
		authService.register(registrationDTO);
	}

	@Test(expected = ConflictException.class)
	public void registerUserExistsTest() {
		RegistrationDTO registrationDTO = new RegistrationDTO();
		registrationDTO.setEmail("karolina@email.com");
		registrationDTO.setPassword("passKarolina");
		User user = new User();
		when(userRepository.findByEmail("karolina@email.com")).thenReturn(Optional.of(user));

		authService.register(registrationDTO);
	}

	@Test
	public void registerUserSuccessfully() {
		RegistrationDTO registrationDTO = new RegistrationDTO();
		registrationDTO.setEmail("karolina@email.com");
		registrationDTO.setPassword("passKarolina");
		User user = new User();
		when(userRepository.findByEmail("karolina@email.com")).thenReturn(Optional.empty());
		when(passwordEncoder.encode("passKarolina"))
			.thenReturn("passKarolinaEncoded");

		authService.register(registrationDTO);

		verify(memberRepository, times(1)).save(any());
	}

}
