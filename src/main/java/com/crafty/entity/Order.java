package com.crafty.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.crafty.entity.base.BaseEntityId;

@Entity
@Table(name="`order`")
public class Order extends BaseEntityId {
	
	private static final long serialVersionUID = -5553205123797954099L;

	@NotNull
	@Column(columnDefinition = "char(36)")
	private String purchaserMemberId;

	@NotNull
	@Column(columnDefinition = "char(36)")
	private String sellerMemberId;
	
	@NotNull
	private Instant createdAt;

	@OneToMany(mappedBy = "order", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	List<OrderItem> items = new ArrayList<>();

	public String getPurchaserMemberId() {
		return purchaserMemberId;
	}

	public void setPurchaserMemberId(String purchaserMemberId) {
		this.purchaserMemberId = purchaserMemberId;
	}

	public String getSellerMemberId() {
		return sellerMemberId;
	}

	public void setSellerMemberId(String sellerMemberId) {
		this.sellerMemberId = sellerMemberId;
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
