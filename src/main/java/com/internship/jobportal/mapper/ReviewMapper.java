package com.internship.jobportal.mapper;

import com.internship.jobportal.model.entity.Review;
import com.internship.jobportal.model.resource.reviewResource.ReviewResource;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public Review toEntity(ReviewResource reviewResource) {
        Review review = new Review();
        return updateEntity(review, reviewResource);
    }

    public ReviewResource toResource(Review review) {
        if (review == null) return null;

        return new ReviewResource(
                review.getRating(),
                review.getComment(),
                review.getReason(),
                review.getCreatedAt(),
                review.getJob().getCustomId()
        );
    }

    public Review updateEntity(Review review, ReviewResource reviewResource) {
        review.setRating(reviewResource.rating());
        review.setComment(reviewResource.comment());
        review.setReason(reviewResource.reason());
        review.setCreatedAt(reviewResource.createdAt());
        return review;
    }
}
