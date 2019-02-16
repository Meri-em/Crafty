package com.crafty.security;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crafty.ApplicationProperties;
import com.crafty.util.SystemClock;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {
	
	private static final long serialVersionUID = 4204871577202248135L;
	private final String secret;
    private final Long accessTokenExpiration;
    private final Long refreshTokenExpiration;
    private final SystemClock systemClock;

    @Autowired
    public JwtTokenUtil(ApplicationProperties applicationProperties, SystemClock systemClock) {
        this.secret = Base64.getEncoder().encodeToString(applicationProperties.getProperty("jwt.secret").getBytes());
        this.accessTokenExpiration = Long.parseLong(applicationProperties.getProperty("jwt.accessToken.expiration"));
        this.refreshTokenExpiration = Long.parseLong(applicationProperties.getProperty("jwt.refreshToken.expiration"));
        this.systemClock = systemClock;
    }
    
    public JwtClaims parseToken(String token) {
        return JwtClaims.fromToken(token, (t, k) -> getClaimStringValue(t, k), (t, k) -> getClaimRawValue(t, k));
    }
    
    public Instant getExpirationInstant(String token) {
        return getRawClaims(token).getExpiration().toInstant();
    }
    
    public Boolean validateToken(String token, JwtUser jwtUser) {
        return (jwtUser.getUsername() != null && !isTokenExpired(token));
    }

    public String generateToken(JwtClaims jwtClaims) {
    	String scopeType = jwtClaims.getScope();
    	Long expiration;
    	if (scopeType.equals(JwtScopeConstants.ACCESS_TOKEN)){
    		expiration = accessTokenExpiration;
    	} else if (scopeType.equals(JwtScopeConstants.REFRESH_TOKEN)) {
    		expiration = refreshTokenExpiration;
    	} else {
    		throw new IllegalArgumentException("Incorrect JWT scope");
    	}
        return Jwts.builder()
            .setClaims(jwtClaims.toMap())
            .setExpiration(Date.from(generateExpirationInstant(expiration)))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }
    
    private String getClaimStringValue(String token, String key) {
        return (String) getRawClaims(token).get(key);
    }
    
    private Object getClaimRawValue(String token, String key) {
        return getRawClaims(token).get(key);
    }
    
    private Claims getRawClaims(String token) {
        return Jwts.parser()
            .setSigningKey(this.secret)
            .parseClaimsJws(token)
            .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return getExpirationInstant(token).isBefore(this.systemClock.instant());
    }

    private Instant generateExpirationInstant(Long expiration) {
        return systemClock.instant().plus(expiration, ChronoUnit.SECONDS);
    }

}
