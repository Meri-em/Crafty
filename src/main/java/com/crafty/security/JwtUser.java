package com.crafty.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUser implements UserDetails {
	
	private static final long serialVersionUID = 4973087618924952325L;
	
	private String id;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private final String firstName;
    private final String lastName;
    private final String memberId;
    private final String authorId;
	
	public JwtUser(String id, String username, String password, Collection<? extends GrantedAuthority> authorities, String firstName, String lastName,
			String memberId, String authorId) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.firstName = firstName;
		this.lastName = lastName;
		this.memberId = memberId;
		this.authorId = authorId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMemberId() {
		return memberId;
	}

	public String getAuthorId() {
		return authorId;
	}
	
}

