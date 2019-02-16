package com.crafty.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginDTO implements Serializable {
	
	private static final long serialVersionUID = -4544985601844817991L;

	@NotBlank
    @Email
    private String email;
	
    @NotBlank
    private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
