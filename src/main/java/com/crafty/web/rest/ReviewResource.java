package com.crafty.web.rest;

import com.crafty.dto.FavoriteRequestDTO;
import com.crafty.dto.FavoriteResponseDTO;
import com.crafty.dto.ReviewDTO;
import com.crafty.service.ReviewService;
import com.crafty.util.CurrentUser;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/reviews")
public class ReviewResource {

	private final ReviewService reviewService;

	public ReviewResource(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@GetMapping("/{itemId}")
	public List<ReviewDTO> getReviews(@PathVariable String itemId) {
		return reviewService.getReviewsByItemId(itemId);
	}

	@PostMapping("")
	public void addReview(@RequestBody ReviewDTO reviewDTO) {
		reviewService.addReview(CurrentUser.getMemberId(), reviewDTO);
	}

	@DeleteMapping("/{itemId}")
	public void deleteReview(@PathVariable String itemId) {
		reviewService.deleteReview(CurrentUser.getMemberId(), itemId);
	}
}
