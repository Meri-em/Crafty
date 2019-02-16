package com.crafty.web;

import com.crafty.security.JwtClaims;

public class RequestData {

    public static final String CORRELATION_ID = "CorrelationId";
    public static final String AUTHORIZATION = "Authorization";
//    public static final String SYSTEM_AUTHORIZATION = "System-Authorization";
    public static final String SWAGGER_AUTHORIZATION = "api_key";   // swagger adds the JWT token to an api_key header instead of Authorization

    private static final ThreadLocal<String> correlationId = new ThreadLocal<>();
        
    private static final ThreadLocal<String> authorizationToken = new ThreadLocal<>();
    private static final ThreadLocal<String> systemAuthorizationToken = new ThreadLocal<>();

    private static final ThreadLocal<JwtClaims> claims = new ThreadLocal<>();
    
    public static String getCorrelationId() {
        return correlationId.get();
    }

    public static void setCorrelationId(String id) {
        correlationId.set(id);
    }

    public static String getAuthorizationToken() {
        return authorizationToken.get();
    }

    public static void setAuthorizationToken(String token) {
        authorizationToken.set(token);
    }

    public static String getSystemAuthorizationToken() {
        return systemAuthorizationToken.get();
    }

    public static void setSystemAuthorizationToken(String token) {
        systemAuthorizationToken.set(token);
    }

    public static JwtClaims getClaims() {
        return claims.get();
    }
    
    public static void setClaims(JwtClaims value) {
        claims.set(value);
    }
}

