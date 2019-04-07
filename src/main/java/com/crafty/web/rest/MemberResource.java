package com.crafty.web.rest;

import java.util.List;

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
	
	@GetMapping("/favourites")
	public List<AuthorDTO> getFavouriteAuthors() {
		JwtUser jwtUser = (JwtUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return memberService.getFavouriteAuthors(jwtUser.getMemberId());
	}
	
	@PostMapping("/favourites")
	public void addFavouriteAuthor(@RequestBody FavouriteRequestDTO favouriteRequestDTO) {
		JwtUser jwtUser = (JwtUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		memberService.addAuthorToFavourites(jwtUser.getMemberId(), favouriteRequestDTO);
	}
	
	@DeleteMapping("/favourites")
	public void deleteFavouriteAuthor(@RequestBody FavouriteRequestDTO favouriteRequestDTO) {
		JwtUser jwtUser = (JwtUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		memberService.removeAuthorFromFavourites(jwtUser.getMemberId(), favouriteRequestDTO);
	}

}
