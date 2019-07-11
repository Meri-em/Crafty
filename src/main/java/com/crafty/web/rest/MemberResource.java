package com.crafty.web.rest;

import java.util.List;

import com.crafty.util.CurrentUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crafty.dto.AuthorDTO;
import com.crafty.dto.FavouriteRequestDTO;
import com.crafty.security.JwtUser;
import com.crafty.service.MemberService;

@RestController
@RequestMapping("api/v1/members")
public class MemberResource {
	
	private final MemberService memberService;
	
	private MemberResource(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping("/favourite-authors")
	public List<AuthorDTO> getFavouriteAuthors() {
		return memberService.getFavouriteAuthors(CurrentUser.getMemberId());
	}
	
	@PostMapping("/favourite-authors")
	public void addFavouriteAuthor(@RequestBody FavouriteRequestDTO favouriteRequestDTO) {
		memberService.addAuthorToFavourites(CurrentUser.getMemberId(), favouriteRequestDTO);
	}
	
	@DeleteMapping("/favourite-authors")
	public void deleteFavouriteAuthor(@RequestBody FavouriteRequestDTO favouriteRequestDTO) {
		memberService.removeAuthorFromFavourites(CurrentUser.getMemberId(), favouriteRequestDTO);
	}

	@GetMapping("/favourite-items")
	public List<AuthorDTO> getFavouriteItems() {
		return memberService.getFavouriteAuthors(CurrentUser.getMemberId());
	}

	@PostMapping("/favourite-items")
	public void addFavouriteItem(@RequestBody FavouriteRequestDTO favouriteRequestDTO) {
		memberService.addAuthorToFavourites(CurrentUser.getMemberId(), favouriteRequestDTO);
	}

	@DeleteMapping("/favourite-items")
	public void deleteFavouriteItem(@RequestBody FavouriteRequestDTO favouriteRequestDTO) {
		memberService.removeAuthorFromFavourites(CurrentUser.getMemberId(), favouriteRequestDTO);
	}

}
