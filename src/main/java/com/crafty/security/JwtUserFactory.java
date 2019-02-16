package com.crafty.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;


@Component
public final class JwtUserFactory {

    private JwtUserFactory() { }

    public static JwtUser create(String id, String email, Set<String> authorities, String firstName, String lastName) {
        Set<GrantedAuthority> grantedAuthorities = mapToGrantedAuthorities(authorities);
        
        return new JwtUser(id, email, null, grantedAuthorities, firstName, lastName);
    }
    
    private static Set<GrantedAuthority> mapToGrantedAuthorities(Set<String> authorities) {
        return authorities.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toSet());
    }
}

