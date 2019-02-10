package com.crafty.security;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.crafty.entity.User;
import com.crafty.service.AuthService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private AuthService authService;
	
	public UserDetailsServiceImpl(AuthService authService) {
		this.authService = authService;
	}
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	User user = authService.getUserByEmail(username);
    	
    	Set<String> authorities = new HashSet<>();
    	authorities.add(user.getRole().name());
        return new JwtUser(user.getEmail(),user.getPassword(), mapToGrantedAuthorities(authorities));
    }
    
    private static Set<GrantedAuthority> mapToGrantedAuthorities(Set<String> authorities) {
        return authorities.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toSet());
    }
}


