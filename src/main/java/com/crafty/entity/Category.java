package com.crafty.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;


@Entity
public class Category implements Serializable {

	private static final long serialVersionUID = -2472062921025215658L;
	 
	@Id
    @org.springframework.data.annotation.Id
    @Column(columnDefinition = "VARCHAR(50)", unique = true)
    @NotNull
    private String id;
	
	@Column(columnDefinition = "VARCHAR(50)")
	@NotNull
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "parent")
	private Category parent;

	@Column(name = "`order`")
	private int order;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
}
