package com.crafty.entity;

import com.crafty.entity.base.BaseEntityId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
public class Review extends BaseEntityId {

	private static final long serialVersionUID = -4128706747050524950L;

	@JoinColumn(name = "item_id")
	@NotNull
	@Column(columnDefinition = "CHAR(255)")
	private String itemId;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@Column(columnDefinition = "text")
	private String comment;

	private BigDecimal score;

	@NotNull
	private Instant lastUpdated;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public Instant getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Instant lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
}
