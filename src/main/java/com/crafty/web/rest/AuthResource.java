package com.crafty.web.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.crafty.util.CurrentUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crafty.dto.LoginDTO;
import com.crafty.dto.LoginResultDTO;
import com.crafty.dto.RegistrationDTO;
import com.crafty.security.JwtUser;
import com.crafty.service.AuthService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@CrossOrigin
@RequestMapping("api/v1")
public class AuthResource {
	
	private final AuthService authService;
	
	public AuthResource(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping(value="/register", consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	public String register(@RequestBody RegistrationDTO registrationDTO) {
		authService.register(registrationDTO);
		return "Регистрацията е извършена успешно";
	}
	
	@PostMapping(value="/login", consumes = "application/json", produces = "application/json")
	public LoginResultDTO login(@RequestBody LoginDTO loginDTO) {
		return authService.login(loginDTO);
	}
	
	/*
	 * This endpoint can be accessed if the user is logged in the system. It is used to verify that the user is logged in.
	 */
	@GetMapping("/user/info")
	public String getEmail() {
		JwtUser jwtUser = (JwtUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		StringBuilder sb = new StringBuilder();
		sb.append("email: " + jwtUser.getUsername());
		sb.append(", authorities: " + jwtUser.getAuthorities());
		sb.append(", memberId: " + jwtUser.getMemberId());
		return sb.toString();
	}
	
	@PostMapping("/refresh")
    public LoginResultDTO refreshToken(HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
    	LoginResultDTO result = authService.refreshToken(request);
    	return result;
    }
	
	@PostMapping("/logout")
	public boolean logout() {
		return authService.doLogout(CurrentUser.getId());
	}
	

}
