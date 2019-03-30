package com.crafty.dto;

import java.io.Serializable;

public class LoginResultDTO implements Serializable {

    private static final long serialVersionUID = 7936874901150441858L;
    
    private String accessToken;
    private String refreshToken;
    
    public LoginResultDTO() { }

    public LoginResultDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
    
}
