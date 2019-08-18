package com.crafty.service;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.crafty.web.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.crafty.dto.LoginDTO;
import com.crafty.dto.LoginResultDTO;
import com.crafty.dto.RegistrationDTO;
import com.crafty.entity.Member;
import com.crafty.entity.User;
import com.crafty.enumeration.UserRole;
import com.crafty.repository.MemberRepository;
import com.crafty.repository.UserRepository;
import com.crafty.security.JwtClaims;
import com.crafty.security.JwtScopeConstants;
import com.crafty.security.JwtTokenUtil;
import com.crafty.security.JwtUser;
import com.crafty.security.JwtUserFactory;
import com.crafty.util.SystemClock;
import com.crafty.web.RequestData;
import com.crafty.web.exception.BadRequestException;
import com.crafty.web.exception.ConflictException;
import com.crafty.web.exception.UnauthorizedException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class AuthService {

	private static final Logger log = LoggerFactory.getLogger(AuthService.class);

	private final UserRepository userRepository;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserDetailsService userDetailsService;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;
	private final SystemClock systemClock;

	public AuthService(UserRepository userRepository, MemberRepository memberRepository,
			PasswordEncoder passwordEncoder, UserDetailsService userDetailsService,
			AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, SystemClock systemClock) {
		this.userRepository = userRepository;
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
		this.userDetailsService = userDetailsService;
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		this.systemClock = systemClock;
	}

	public void register(RegistrationDTO registrationDTO) {
		userRepository.findByEmail(registrationDTO.getEmail()).ifPresent(u -> {
			throw new ConflictException("User with provided email already exists");
		});
		String memberId = UUID.randomUUID().toString();
		Member member = new Member();
		member.setId(memberId);
		member.setFirstName(registrationDTO.getFirstName());
		member.setLastName(registrationDTO.getLastName());
		memberRepository.save(member);

		User user = new User();
		user.setEmail(registrationDTO.getEmail().toLowerCase());
		user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
		user.setRole(UserRole.ROLE_MEMBER);
		user.setMemberId(memberId);
		userRepository.save(user);
	}

	public LoginResultDTO login(LoginDTO loginDTO) {
		String email = loginDTO.getEmail();
		userRepository.findByEmail(email).orElseThrow(
			() -> new UnauthorizedException("Грешно потребителско име или парола"));
		this.authenticate(email, loginDTO.getPassword());
		return generateToken(email);
	}

	private LoginResultDTO generateToken(String email) throws AuthenticationException {
		JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(email);
		JwtClaims.Builder jwtClaimsBuilder = new JwtClaims.Builder(jwtUser);

		String accessToken = jwtTokenUtil
				.generateToken(jwtClaimsBuilder.addScope(JwtScopeConstants.ACCESS_TOKEN).build());
		String refreshToken = jwtTokenUtil
				.generateToken(jwtClaimsBuilder.addScope(JwtScopeConstants.REFRESH_TOKEN).build());

		return new LoginResultDTO(accessToken, refreshToken);
	}

	public void authenticate(String email, String password) {
		Authentication authentication;
		try {
			authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (AuthenticationException ex) {
			throw new UnauthorizedException("Грешно потребителско име или парола");
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	public LoginResultDTO refreshToken(HttpServletRequest request)
			throws JsonParseException, JsonMappingException, IOException {
		String tokenHeader = RequestData.AUTHORIZATION;
		String refreshToken = request.getHeader(tokenHeader);

		JwtClaims claims = jwtTokenUtil.parseToken(refreshToken);
		String email = claims.getSubject();

		JwtUser jwtUser = JwtUserFactory.create(claims.getLoggedInUserId(), email, claims.getRoles(),
				claims.getMemberId(), claims.getAuthorId(), 
				claims.getLastLogoutDate() != null ? Instant.parse(claims.getLastLogoutDate()) : null);

		log.info("Checking registration token for {}", jwtUser);

		Optional<User> userOptional = userRepository.findById(claims.getLoggedInUserId());
		if (jwtTokenUtil.validateToken(refreshToken, jwtUser, userOptional.get().getLastLogoutDate())) {
			return generateToken(email);
		}

		throw new BadRequestException("The token is not valid");
	}
	
	public boolean doLogout(String userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			user.setLastLogoutDate(systemClock.instant());
			userRepository.save(user);
			return true;
		} else {
			return false;
		}
	}
}
