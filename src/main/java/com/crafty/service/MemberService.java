package com.crafty.service;

import java.util.List;
import java.util.stream.Collectors;

import com.crafty.entity.Item;
import com.crafty.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crafty.dto.FavoriteResponseDTO;
import com.crafty.dto.FavouriteRequestDTO;
import com.crafty.entity.Author;
import com.crafty.entity.Member;
import com.crafty.repository.AuthorRepository;
import com.crafty.repository.MemberRepository;
import com.crafty.util.MapperHelper;
import com.crafty.web.exception.BadRequestException;
import com.crafty.web.exception.NotFoundException;

@Service
@Transactional
public class MemberService {
	
	private final MemberRepository memberRepository;
	private final AuthorRepository authorRepository;
	private final ItemRepository itemRepository;
	private final MapperHelper mapperHelper;
	
	public MemberService(MemberRepository memberRepository, AuthorRepository authorRepository,
						 ItemRepository itemRepository, MapperHelper mapperHelper) {
		this.memberRepository = memberRepository;
		this.authorRepository = authorRepository;
		this.itemRepository = itemRepository;
		this.mapperHelper = mapperHelper;
	}
	
	public List<FavoriteResponseDTO> getFavouriteAuthors(String memberId) {
		Member member = getMemberByIdOrNotFound(memberId);
		return member.getFavouriteAuthors().stream().map(a -> new FavoriteResponseDTO(a.getId(), a.getName()))
			.collect(Collectors.toList());
	}
	
	public void addAuthorToFavourites(String memberId, FavouriteRequestDTO favourite) {
		String authorId = favourite.getId();
		if (authorId == null) {
			throw new BadRequestException("No id provided for author");
		}
		Author author = getAuthorByIdOrNotFound(authorId);
		Member member = getMemberByIdOrNotFound(memberId);
		if (!member.getFavouriteAuthors().contains(author)) {
			member.getFavouriteAuthors().add(author);
			memberRepository.save(member);
		}		
	}
	
	public void removeAuthorFromFavourites(String memberId, FavouriteRequestDTO favourite) {
		String authorId = favourite.getId();
		if (authorId == null) {
			throw new BadRequestException("No id provided for author");
		}
		Author author = getAuthorByIdOrNotFound(authorId);
		Member member = getMemberByIdOrNotFound(memberId);
		if (member.getFavouriteAuthors().contains(author)) {
			member.getFavouriteAuthors().remove(author);
			memberRepository.save(member);
		}		
	}

	public List<FavoriteResponseDTO> getFavouriteItems(String memberId) {
		Member member = getMemberByIdOrNotFound(memberId);
		return member.getFavouriteItems().stream().map(i -> new FavoriteResponseDTO(i.getId(), i.getName()))
			.collect(Collectors.toList());
	}

	public void addItemToFavourites(String memberId, FavouriteRequestDTO favourite) {
		String itemId = favourite.getId();
		if (itemId == null) {
			throw new BadRequestException("No id provider for item");
		}
		Item item = getItemByIdOrNotFound(itemId);
		Member member = getMemberByIdOrNotFound(memberId);
		if (!member.getFavouriteItems().contains(item)) {
			member.getFavouriteItems().add(item);
			memberRepository.save(member);
		}
	}

	public void removeItemFromFavourites(String memberId, FavouriteRequestDTO favourite) {
		String itemId = favourite.getId();
		if (itemId == null) {
			throw new BadRequestException("No id provided for item");
		}
		Item item = getItemByIdOrNotFound(itemId);
		Member member = getMemberByIdOrNotFound(memberId);
		if (member.getFavouriteAuthors().contains(item)) {
			member.getFavouriteAuthors().remove(item);
			memberRepository.save(member);
		}
	}
	
	private Member getMemberByIdOrNotFound(String memberId) {
		return memberRepository.findById(memberId)
				.orElseThrow(() -> new NotFoundException("No member found with id " + memberId));
	}
	
	private Author getAuthorByIdOrNotFound(String authorId) {
		return authorRepository.findById(authorId)
				.orElseThrow(() -> new NotFoundException("No author found with id " + authorId));
	}

	private Item getItemByIdOrNotFound(String authorId) {
		return itemRepository.findById(authorId)
			.orElseThrow(() -> new NotFoundException("No author found with id " + authorId));
	}

}
