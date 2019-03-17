package com.crafty.web.rest;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crafty.dto.LoginDTO;
import com.crafty.dto.LoginResultDTO;
import com.crafty.dto.RegistrationDTO;
import com.crafty.service.AuthService;

@RestController
@RequestMapping("api/v1")
public class AuthResource {
	
	private final AuthService authService;
	
	public AuthResource(AuthService authService) {
		this.authService = authService;
	}
	
	@GetMapping("/welcome")
    public String getWelcomeMessage() {
    	return "Welcome!";
    }
	
	@PostMapping(value="/register", consumes = "application/json", produces = "application/json")
	public String register(@RequestBody @Valid RegistrationDTO registrationDTO) {
		authService.register(registrationDTO);
		return "You were successfully registered";
	}
	
	@PostMapping(value="/login", consumes = "application/json", produces = "application/json")
	public LoginResultDTO login(@RequestBody @Valid LoginDTO loginDTO) {
		return authService.login(loginDTO);
	}

}
