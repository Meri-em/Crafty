package com.crafty.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.crafty.entity.base.BaseEntityId;

@Entity
public class Member extends BaseEntityId {

	private static final long serialVersionUID = 2584772705743123172L;
	
	private String firstName;
	
	private String lastName;	
	
	@ManyToMany
	@JoinTable(name = "favourite", joinColumns = @JoinColumn(name = "member_id"), 
			inverseJoinColumns = @JoinColumn(name = "author_id"))
	private Set<Author> favouriteAuthors;

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

	public Set<Author> getFavouriteAuthors() {
		return favouriteAuthors;
	}

	public void setFavouriteAuthors(Set<Author> favouriteAuthors) {
		this.favouriteAuthors = favouriteAuthors;
	}
}
