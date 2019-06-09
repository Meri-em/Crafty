package com.crafty.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ItemDTO extends SimpleItemDTO implements Serializable {

	private static final long serialVersionUID = 4450034229561816590L;
	
	private String description;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm.SSS")
	private LocalDateTime createdAt;
	
	private List<ItemImageDTO> itemImages;
	
	private boolean archived;
	
	public ItemDTO() { }
	
	public ItemDTO(SimpleItemDTO simpleItem) {
		super(simpleItem.getId(), simpleItem.getName(), simpleItem.getPrice(), 
				simpleItem.getAuthor(), simpleItem.getPrimaryImagePath());
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<ItemImageDTO> getItemImages() {
		return itemImages;
	}

	public void setItemImages(List<ItemImageDTO> itemImages) {
		this.itemImages = itemImages;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}
	
}
