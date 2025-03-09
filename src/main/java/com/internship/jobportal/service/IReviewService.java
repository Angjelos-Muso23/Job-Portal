package com.internship.jobportal.service;

import com.internship.jobportal.model.resource.reviewResource.ReviewResource;
import org.springframework.data.domain.Page;

public interface IReviewService {
    Page<ReviewResource> getAllReviews(String jobCustomId, int page, int size);
    Page<ReviewResource> getAllReviewsByRating(String jobCustomId, double minRating, double maxRating, int page, int size);
}