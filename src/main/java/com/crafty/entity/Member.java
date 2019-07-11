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
	@JoinTable(name = "favourite_author", joinColumns = @JoinColumn(name = "member_id"),
			inverseJoinColumns = @JoinColumn(name = "author_id"))
	private Set<Author> favouriteAuthors;

	@ManyToMany
	@JoinTable(name = "favourite_item", joinColumns = @JoinColumn(name = "member_id"),
		inverseJoinColumns = @JoinColumn(name = "item_id"))
	private Set<Item> favouriteItems;

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

	public Set<Item> getFavouriteItems() {
		return favouriteItems;
	}

	public void setFavouriteItems(Set<Item> favouriteItems) {
		this.favouriteItems = favouriteItems;
	}
}
