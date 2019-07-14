package com.crafty.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.crafty.entity.base.BaseEntityId;

@Entity
@Table(name="`order`")
public class Order extends BaseEntityId {
	
	private static final long serialVersionUID = -5553205123797954099L;

	@JoinColumn(name = "member_id")
	@NotNull
	@Column(columnDefinition = "CHAR(255)")
	private String memberId;
	
	@NotNull
	private Instant createdAt;

	@OneToMany(mappedBy = "order", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	List<OrderItem> items;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
}
