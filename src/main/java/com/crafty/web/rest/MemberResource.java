package com.crafty.web.rest;

import java.util.List;

import com.crafty.dto.ProfileDTO;
import com.crafty.util.CurrentUser;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/profile")
	public ProfileDTO getProfile() {
		return memberService.getProfileInformation(CurrentUser.getMemberId());
	}

	@PostMapping("/profile")
	public void updateProfile(@RequestBody ProfileDTO profileDTO) {
		memberService.updateProfile(profileDTO, CurrentUser.getMemberId());
	}

	@GetMapping("/profile/{memberId}")
	public ProfileDTO getMemberProfile(@PathVariable String memberId) {
		return memberService.getProfileInformation(memberId);
	}
	
	@GetMapping("/favorite-members")
	public List<FavoriteResponseDTO> getFavoriteMembers() {
		return memberService.getFavouriteMembers(CurrentUser.getMemberId());
	}
	
	@PostMapping("/favorite-members")
	public void addFavoriteMember(@RequestBody FavoriteRequestDTO favoriteRequestDTO) {
		memberService.addMemberToFavorites(CurrentUser.getMemberId(), favoriteRequestDTO);
	}
	
	@DeleteMapping("/favorite-members")
	public void deleteFavouriteMember(@RequestBody FavoriteRequestDTO favoriteRequestDTO) {
		memberService.removeMemberFromFavourites(CurrentUser.getMemberId(), favoriteRequestDTO);
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
