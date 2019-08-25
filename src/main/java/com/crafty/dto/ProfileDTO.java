package com.crafty.dto;

import java.io.Serializable;
import java.util.List;

public class ProfileDTO implements Serializable {

	private static final long serialVersionUID = 8422676958486016579L;

	private String id;
	private String firstName;
	private String lastName;
	private String nickname;
	private String description;
	private String location;
	private List<SimpleItemDTO> items;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public List<SimpleItemDTO> getItems() {
		return items;
	}

	public void setItems(List<SimpleItemDTO> items) {
		this.items = items;
	}
}
