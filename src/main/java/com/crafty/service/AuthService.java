package com.crafty.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.crafty.dto.RegistrationDTO;
import com.crafty.entity.User;
import com.crafty.enumeration.UserRole;
import com.crafty.repository.UserRepository;
import com.crafty.web.exception.ConflictException;
import com.crafty.web.exception.NotFoundException;

@Service
public class AuthService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public void register(RegistrationDTO registrationDTO) {
		userRepository.findByEmail(registrationDTO.getEmail())
			.ifPresent(u -> {
				throw new ConflictException("User with provided email already exists");
			});
		User user = new User();
		user.setEmail(registrationDTO.getEmail().toLowerCase());
		user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
		user.setRole(UserRole.MEMBER);
		userRepository.save(user);
	}
	
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email)
			.orElseThrow(() -> new NotFoundException("No user with provided email found"));
	}
}
