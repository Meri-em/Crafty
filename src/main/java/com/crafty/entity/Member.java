package com.crafty.entity;

import javax.persistence.Entity;

import com.crafty.entity.base.BaseEntityId;

@Entity
public class Member extends BaseEntityId {

	private static final long serialVersionUID = 2584772705743123172L;
	
	private String firstName;
	
	private String lastName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
