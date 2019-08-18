package com.crafty.web.rest;

import java.util.List;

import com.crafty.util.CurrentUser;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crafty.dto.FavoriteResponseDTO;
import com.crafty.dto.FavoriteRequestDTO;
import com.crafty.service.MemberService;

@RestController
@RequestMapping("api/v1")
public class MemberResource {
	
	private final MemberService memberService;
	
	private MemberResource(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping("/favorite-authors")
	public List<FavoriteResponseDTO> getFavoriteAuthors() {
		return memberService.getFavouriteAuthors(CurrentUser.getMemberId());
	}
	
	@PostMapping("/favorite-members")
	public void addFavoriteAuthor(@RequestBody FavoriteRequestDTO favoriteRequestDTO) {
		memberService.addMemberToFavorites(CurrentUser.getMemberId(), favoriteRequestDTO);
	}
	
	@DeleteMapping("/favorite-authors")
	public void deleteFavouriteAuthor(@RequestBody FavoriteRequestDTO favoriteRequestDTO) {
		memberService.removeAuthorFromFavourites(CurrentUser.getMemberId(), favoriteRequestDTO);
	}

	@GetMapping("/favorite-items")
	public List<FavoriteResponseDTO> getFavouriteItems() {
		return memberService.getFavouriteItems(CurrentUser.getMemberId());
	}

	@PostMapping("/favorite-items")
	public void addFavouriteItem(@RequestBody FavoriteRequestDTO favoriteRequestDTO) {
		memberService.addItemToFavourites(CurrentUser.getMemberId(), favoriteRequestDTO);
	}

	@DeleteMapping("/favorite-items")
	public void deleteFavouriteItem(@RequestBody FavoriteRequestDTO favoriteRequestDTO) {
		memberService.removeItemFromFavourites(CurrentUser.getMemberId(), favoriteRequestDTO);
	}

}
