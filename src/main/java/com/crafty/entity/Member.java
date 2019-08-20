package com.crafty.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.crafty.entity.base.BaseEntityId;

@Entity
public class Member extends BaseEntityId {

	private static final long serialVersionUID = 2584772705743123172L;
	
	private String firstName;
	
	private String lastName;

	@Column(columnDefinition = "varchar(100)")
	private String nickname;

	@Column(columnDefinition = "text")
	private String description;

	private String location;
	
	@ManyToMany
	@JoinTable(name = "favorite_member", joinColumns = @JoinColumn(name = "member_id"),
			inverseJoinColumns = @JoinColumn(name = "favorite_member_id"))
	private Set<Member> favoriteMembers;

	@ManyToMany
	@JoinTable(name = "favorite_item", joinColumns = @JoinColumn(name = "member_id"),
		inverseJoinColumns = @JoinColumn(name = "item_id"))
	private Set<Item> favoriteItems;

	@OneToMany(mappedBy = "member")
	private List<Item> items;

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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Set<Member> getFavoriteMembers() {
		return favoriteMembers;
	}

	public void setFavoriteMembers(Set<Member> favoriteMembers) {
		this.favoriteMembers = favoriteMembers;
	}

	public Set<Item> getFavoriteItems() {
		return favoriteItems;
	}

	public void setFavoriteItems(Set<Item> favoriteItems) {
		this.favoriteItems = favoriteItems;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
}
