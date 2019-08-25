package com.crafty.service;

import java.util.List;
import java.util.stream.Collectors;

import com.crafty.dto.ProfileDTO;
import com.crafty.entity.Item;
import com.crafty.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crafty.dto.FavoriteResponseDTO;
import com.crafty.dto.FavoriteRequestDTO;
import com.crafty.entity.Member;
import com.crafty.repository.MemberRepository;
import com.crafty.util.MapperHelper;
import com.crafty.web.exception.BadRequestException;
import com.crafty.web.exception.NotFoundException;

@Service
@Transactional
public class MemberService {
	
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;
	private final MapperHelper mapperHelper;
	
	public MemberService(MemberRepository memberRepository,
						 ItemRepository itemRepository, MapperHelper mapperHelper) {
		this.memberRepository = memberRepository;
		this.itemRepository = itemRepository;
		this.mapperHelper = mapperHelper;
	}

	public ProfileDTO getProfileInformation(String memberId) {
		Member member = getMemberByIdOrNotFound(memberId);
		return mapperHelper.toProfileDTO(member);
	}
	
	public List<FavoriteResponseDTO> getFavouriteMembers(String memberId) {
		Member member = getMemberByIdOrNotFound(memberId);
		return member.getFavoriteMembers().stream().map(a -> new FavoriteResponseDTO(a.getId(), a.getNickname()))
			.collect(Collectors.toList());
	}
	
	public void addMemberToFavorites(String memberId, FavoriteRequestDTO favourite) {
		String favoriteMemberId = favourite.getId();
		if (favoriteMemberId == null) {
			throw new BadRequestException("No id provided for favorite member");
		}
		Member favoriteMember = getMemberByIdOrNotFound(favoriteMemberId);
		Member member = getMemberByIdOrNotFound(memberId);
		if (!member.getFavoriteMembers().contains(favoriteMember)) {
			member.getFavoriteMembers().add(favoriteMember);
			memberRepository.save(member);
		}		
	}
	
	public void removeMemberFromFavourites(String memberId, FavoriteRequestDTO favourite) {
		String favoriteMemberId = favourite.getId();
		if (favoriteMemberId == null) {
			throw new BadRequestException("No id provided for favorite member");
		}
		Member favoriteMember = getMemberByIdOrNotFound(favoriteMemberId);
		Member member = getMemberByIdOrNotFound(memberId);
		if (member.getFavoriteMembers().contains(favoriteMember)) {
			member.getFavoriteMembers().remove(favoriteMember);
			memberRepository.save(member);
		}		
	}

	public List<FavoriteResponseDTO> getFavouriteItems(String memberId) {
		Member member = getMemberByIdOrNotFound(memberId);
		return member.getFavoriteItems().stream().map(i -> new FavoriteResponseDTO(i.getId(), i.getName()))
			.collect(Collectors.toList());
	}

	public void addItemToFavourites(String memberId, FavoriteRequestDTO favorite) {
		String itemId = favorite.getId();
		if (itemId == null) {
			throw new BadRequestException("No id provider for item");
		}
		Item item = getItemByIdOrNotFound(itemId);
		Member member = getMemberByIdOrNotFound(memberId);
		if (!member.getFavoriteItems().contains(item)) {
			member.getFavoriteItems().add(item);
			memberRepository.save(member);
		}
	}

	public void removeItemFromFavourites(String memberId, FavoriteRequestDTO favorite) {
		String itemId = favorite.getId();
		if (itemId == null) {
			throw new BadRequestException("No id provided for item");
		}
		Item item = getItemByIdOrNotFound(itemId);
		Member member = getMemberByIdOrNotFound(memberId);
		if (member.getFavoriteItems().contains(item)) {
			member.getFavoriteItems().remove(item);
			memberRepository.save(member);
		}
	}
	
	public Member getMemberByIdOrNotFound(String memberId) {
		return memberRepository.findById(memberId)
				.orElseThrow(() -> new NotFoundException("No member found with id " + memberId));
	}

	private Item getItemByIdOrNotFound(String itemId) {
		return itemRepository.findById(itemId)
			.orElseThrow(() -> new NotFoundException("No item found with id " + itemId));
	}

}
