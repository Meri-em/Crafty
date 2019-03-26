package com.crafty.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import com.crafty.entity.base.BaseEntityId;
import com.crafty.enumeration.UserRole;

@Entity
public class User extends BaseEntityId {
	
	private static final long serialVersionUID = -7122787408462767391L;

	@NotNull
	@Column(unique = true)
	private String email;
	
	@NotNull
	@Column(columnDefinition = "varchar(100)")
	private String password;
	
	@NotNull
	@Column(columnDefinition = "varchar(100)")
	@Enumerated(EnumType.STRING)
	private UserRole role;
	
	@Column(columnDefinition = "char(36)")
	private String memberId;
	
	@Column(columnDefinition = "char(36)")
	private String authorId;
	
	private Instant lastLogoutDate;

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

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public Instant getLastLogoutDate() {
		return lastLogoutDate;
	}

	public void setLastLogoutDate(Instant lastLogoutDate) {
		this.lastLogoutDate = lastLogoutDate;
	}
	
}

