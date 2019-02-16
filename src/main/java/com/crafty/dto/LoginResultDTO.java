package com.crafty.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResultDTO implements Serializable {

    private static final long serialVersionUID = 7936874901150441858L;
    
    private String idToken;
    private String refreshToken;
    
    public LoginResultDTO() { }

    public LoginResultDTO(String idToken, String refreshToken) {
        this.idToken = idToken;
        this.refreshToken = refreshToken;
    }

    @JsonProperty("id_token")
    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    @JsonProperty("refresh_token")
	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
    
}
