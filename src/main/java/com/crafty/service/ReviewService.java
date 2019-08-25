package com.crafty.service;


import com.crafty.dto.ReviewDTO;
import com.crafty.entity.Member;
import com.crafty.entity.Review;
import com.crafty.repository.MemberRepository;
import com.crafty.repository.ReviewRepository;
import com.crafty.util.MapperHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewService {

	private ReviewRepository reviewRepository;
	private MemberService memberService;
	private MapperHelper mapperHelper;

	public ReviewService(ReviewRepository reviewRepository,
						 MemberService memberService,
						 MapperHelper mapperHelper) {
		this.reviewRepository = reviewRepository;
		this.memberService = memberService;
		this.mapperHelper = mapperHelper;
	}

	public List<ReviewDTO> getReviewsByItemId(String itemId) {
		return reviewRepository.findByItemIdOrderByLastUpdatedDesc(itemId).stream()
			.map(r -> mapperHelper.toReviewDTO(r))
			.collect(Collectors.toList());
	}

	public void addReview(String memberId, ReviewDTO reviewDTO) {
		Optional<Review> reviewOptional = reviewRepository.findByMemberIdAndItemId(memberId,
			reviewDTO.getItemId());
		Review review;
		if (reviewOptional.isPresent()) {
			review = reviewOptional.get();
			if (review.getComment().equals(reviewDTO.getItemId()) ||
				review.getScore().equals(reviewDTO.getScore())) {
				return;
			}
		} else {
			review = new Review();
			review.setItemId(reviewDTO.getItemId());
			review.setMember(memberService.getMemberByIdOrNotFound(memberId));
		}
		review.setComment(reviewDTO.getComment());
		review.setScore(reviewDTO.getScore());
		review.setLastUpdated(Instant.now());
		reviewRepository.save(review);
	}

	public void deleteReview(String memberId, String itemId) {
		reviewRepository.deleteByMemberIdAndItemId(memberId, itemId);
	}
}
