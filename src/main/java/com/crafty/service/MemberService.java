package com.crafty.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crafty.dto.AuthorDTO;
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
	private final MapperHelper mapperHelper;
	
	public MemberService(MemberRepository memberRepository, AuthorRepository authorRepository,
			MapperHelper mapperHelper) {
		this.memberRepository = memberRepository;
		this.authorRepository = authorRepository;
		this.mapperHelper = mapperHelper;
	}
	
	public List<AuthorDTO> getFavouriteAuthors(String memberId) {
		Member member = getMemberByIdOrNotFound(memberId);
		return member.getFavouriteAuthors().stream().map(a -> mapperHelper.toAuthorDTO(a))
			.collect(Collectors.toList());
	}
	
	public void addAuthorToFavourites(String memberId, FavouriteRequestDTO favourite) {
		String authorId = favourite.getAuthorId();
		if (authorId == null) {
			throw new BadRequestException("No authorId provided");
		}
		Author author = getAuthorByIdOrNotFound(authorId);
		Member member = getMemberByIdOrNotFound(memberId);
		if (!member.getFavouriteAuthors().contains(author)) {
			member.getFavouriteAuthors().add(author);
			memberRepository.save(member);
		}		
	}
	
	public void removeAuthorFromFavourites(String memberId, FavouriteRequestDTO favourite) {
		String authorId = favourite.getAuthorId();
		if (authorId == null) {
			throw new BadRequestException("No authorId provided");
		}
		Author author = getAuthorByIdOrNotFound(authorId);
		Member member = getMemberByIdOrNotFound(memberId);
		if (member.getFavouriteAuthors().contains(author)) {
			member.getFavouriteAuthors().remove(author);
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

}
