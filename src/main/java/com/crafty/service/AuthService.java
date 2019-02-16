package com.crafty.service;

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
import com.crafty.entity.User;
import com.crafty.enumeration.UserRole;
import com.crafty.repository.UserRepository;
import com.crafty.security.JwtClaims;
import com.crafty.security.JwtScopeConstants;
import com.crafty.security.JwtTokenUtil;
import com.crafty.security.JwtUser;
import com.crafty.web.exception.ConflictException;
import com.crafty.web.exception.UnauthorizedException;

@Service
public class AuthService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserDetailsService userDetailsService;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;
	
	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			UserDetailsService userDetailsService, AuthenticationManager authenticationManager,
			JwtTokenUtil jwtTokenUtil) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.userDetailsService = userDetailsService;
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
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
	
	public LoginResultDTO login(LoginDTO loginDTO) {
		String email = loginDTO.getEmail();
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UnauthorizedException("Incorrect email or password"));
		this.authenticate(email, loginDTO.getPassword());
		return generateToken(email);
	}
	
	private LoginResultDTO generateToken(String email) throws AuthenticationException {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(email);
//        UserDTO user = authServiceClient.getUserByEmail(email).getData();

        String carrierId = null;
        String employerId;
        String employerGroupId;

        // The member might not be present here. An example might be for
        // the users in the CSR application. In those cases,
        // use the value on the user table to lookup this information.
//        if (member.isPresent()) {

            // TODO: Fix this later.
//            employerGroupId = member.get().getEmployerGroupId();
//
//            // Get the carrier ID and employer ID from the employer group ID.
//            EmployerGroupDTO employerGroupDTO = employerGroupServiceClient.getEmployerGroupById(employerGroupId).getData();
//            employerId = employerGroupDTO.getEmployer().getId();
//            Optional<EmployerDTO> employerDTO = employerServiceClient.getEmployers().getData().stream()
//                .filter(e -> e.getId().equals(employerId))
//                .findFirst();
//
//            if (employerDTO.isPresent()) {
//                carrierId = employerDTO.get().getCarrierId();
//            }
//            carrierId = "BIND_000001";
//            employerId = "BIND_000001";
//            employerGroupId = "BIND_000001";
//        }
//        else {
//            employerGroupId = user.getEmployerGroupId();
//            employerId = user.getEmployerId();
//            carrierId = user.getCarrierId();
//        }
//        
        JwtClaims.Builder jwtClaimsBuilder = new JwtClaims.Builder(jwtUser);
                //.userAssociativeData(JwtAssociativeData.forUser(jwtUser, user.getMemberId(), employerGroupId, employerId, carrierId));
        
        String accessToken = jwtTokenUtil.generateToken(jwtClaimsBuilder.addScope(JwtScopeConstants.ACCESS_TOKEN).build());
        String refreshToken = jwtTokenUtil.generateToken(jwtClaimsBuilder.addScope(JwtScopeConstants.REFRESH_TOKEN).build());
        
        return new LoginResultDTO(accessToken, refreshToken);
    }
	
	public void authenticate(String email, String password) {
		Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (AuthenticationException ex) {
        	throw new UnauthorizedException("Incorrect email or password");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
