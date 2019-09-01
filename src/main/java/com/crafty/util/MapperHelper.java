package com.crafty.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.crafty.dto.*;
import com.crafty.entity.*;
import org.springframework.stereotype.Component;

@Component
public class MapperHelper {
	
	private static final String IMAGES_DIRECTORY = "/images";
	
	public MemberDTO toMemberDTO(Member member) {
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setId(member.getId());
		memberDTO.setName(member.getNickname());
		return memberDTO;
	}
	
	public ItemImageDTO toItemImageDTO(ItemImage itemImage) {
		ItemImageDTO itemImageDTO = new ItemImageDTO();
		itemImageDTO.setId(itemImage.getId());
		itemImageDTO.setPath(getImagePath(itemImage));
		return itemImageDTO;
	}

	public SimpleItemDTO toSimpleItemDTO(Item item) {
		MemberDTO author = toMemberDTO(item.getMember());
		String primaryImagePath = null;
		
		if (!item.getItemImages().isEmpty()) {
			Comparator<ItemImage> itemImageComparator = Comparator.comparing(ItemImage::getOrder);
			ItemImage itemImage = item.getItemImages().stream().sorted(itemImageComparator).findFirst().get();
			primaryImagePath = getImagePath(itemImage);
		}
		SimpleItemDTO simpleItem = new SimpleItemDTO(item.getId(), item.getName(),item.getPrice(),
				author, primaryImagePath, item.isArchived());
		return simpleItem;
	}
	
	public ItemDTO toItemDTO(Item item) {
		ItemDTO itemDTO = new ItemDTO(toSimpleItemDTO(item));
		itemDTO.setDescription(item.getDescription());
		itemDTO.setCreatedAt(LocalDateTime.ofInstant(item.getCreatedAt(), ZoneOffset.UTC));
		
		Comparator<ItemImage> itemImageComparator = Comparator.comparing(ItemImage::getOrder);
		List<ItemImageDTO> itemImages = item.getItemImages().stream().sorted(itemImageComparator)
				.map(it -> toItemImageDTO(it)).collect(Collectors.toList());
		itemDTO.setImages(itemImages);
		itemDTO.setArchived(item.isArchived());
		return itemDTO;
	}
	
	public CartItemDTO toCartItemDTO(CartItem cartItem) {
		CartItemDTO cartItemDTO = new CartItemDTO();
		cartItemDTO.setItem(this.toSimpleItemDTO(cartItem.getItem()));
		cartItemDTO.setQuantity(cartItem.getQuantity());
		return cartItemDTO;
	}
	
	public OrderDTO toOrderDTO(Order order, BigDecimal total, Member member) {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setCreatedAt(LocalDateTime.ofInstant(order.getCreatedAt(), ZoneOffset.UTC));
		orderDTO.setTotal(total);
		orderDTO.setMember(toMemberDTO(member));
		List<OrderItemDTO> orderItemDTOs = order.getItems().stream()
			.map(i -> this.toOrderItemDTO(i))
			.collect(Collectors.toList());
		orderDTO.setItems(orderItemDTOs);
		return orderDTO;
	}
	
	private String getImagePath(ItemImage itemImage) {
		return String.format("%s/%s/%s", IMAGES_DIRECTORY, itemImage.getItem().getId(), itemImage.getName());
	}

	private OrderItemDTO toOrderItemDTO(OrderItem orderItem) {
		OrderItemDTO orderItemDTO = new OrderItemDTO();
		orderItemDTO.setItem(toSimpleItemDTO(orderItem.getItem()));
		orderItemDTO.setPaid(orderItem.getPaidPerItem());
		orderItemDTO.setQuantity(orderItem.getQuantity());
		return orderItemDTO;
	}

	public ReviewDTO toReviewDTO(Review review) {
		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setItemId(review.getItemId());
		reviewDTO.setMember(toMemberDTO(review.getMember()));
		reviewDTO.setComment(review.getComment());
		reviewDTO.setScore(review.getScore());
		reviewDTO.setLastUpdated(LocalDateTime.ofInstant(review.getLastUpdated(),
			ZoneOffset.UTC).toLocalDate());
		return reviewDTO;
	}

	public ProfileDTO toProfileDTO(Member member) {
		ProfileDTO profileDTO = new ProfileDTO();
		profileDTO.setId(member.getId());
		profileDTO.setFirstName(member.getFirstName());
		profileDTO.setLastName(member.getLastName());
		profileDTO.setNickname(member.getNickname());
		profileDTO.setDescription(member.getDescription());
		profileDTO.setLocation(member.getLocation());
		List<SimpleItemDTO> itemDTOs = new ArrayList<>();
		for (Item item : member.getItems()) {
			itemDTOs.add(toSimpleItemDTO(item));
		}
		profileDTO.setItems(itemDTOs);
		return profileDTO;
	}
}
