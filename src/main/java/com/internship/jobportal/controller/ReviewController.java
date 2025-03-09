package com.internship.jobportal.controller;

import com.internship.jobportal.service.IReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final IReviewService reviewService;

    public ReviewController(IReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/job/{customId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getAllReviews(
            @PathVariable String customId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(reviewService.getAllReviews(customId, page, size));
    }

    @GetMapping("/job/{customId}/from/{minRating}/to/{maxRating}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getAllReviewsByRating(
            @PathVariable String customId,
            @PathVariable double minRating,
            @PathVariable double maxRating,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(reviewService.getAllReviewsByRating(customId, minRating, maxRating, page, size));
    }
}
