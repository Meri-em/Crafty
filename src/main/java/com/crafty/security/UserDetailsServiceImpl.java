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
import com.crafty.repository.UserRepository;
import com.crafty.web.exception.NotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private UserRepository userRepository;
	
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	User user = userRepository.findByEmail(username)
    			.orElseThrow(() -> new NotFoundException("No user with provided email found"));
    	
    	Set<String> authorities = new HashSet<>();
    	authorities.add(user.getRole().name());
        return new JwtUser(user.getId(), user.getEmail(),user.getPassword(), mapToGrantedAuthorities(authorities),
        		user.getMemberId(), user.getAuthorId(), user.getLastLogoutDate());
    }
    
    private static Set<GrantedAuthority> mapToGrantedAuthorities(Set<String> authorities) {
        return authorities.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toSet());
    }
}


