package com.crafty.web.rest;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crafty.dto.RegistrationDTO;
import com.crafty.service.AuthService;

@RestController
@RequestMapping("api/v1")
public class AuthResource {
	
	private final AuthService authService;

	public AuthResource(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping(value="/register", consumes = "application/json", produces = "application/json")
	public String register(@RequestBody @Valid RegistrationDTO registrationDTO) {
		authService.register(registrationDTO);
		return "You were successfully registered";
	}
	
	@GetMapping("/hello")
    public ResponseEntity<String> hello(String name) {
        return new ResponseEntity<>("Hello "+ name, HttpStatus.OK);
    }
    
    @GetMapping("/welcome")
    public String getWelcomeMessage() {
    	return "Welcome";
    }

}
