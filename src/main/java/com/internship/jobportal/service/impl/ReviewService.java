package com.internship.jobportal.service.impl;

import com.internship.jobportal.exception.custom.ResourceNotFoundException;
import com.internship.jobportal.mapper.ReviewMapper;
import com.internship.jobportal.model.entity.Job;
import com.internship.jobportal.model.resource.reviewResource.ReviewResource;
import com.internship.jobportal.repository.JobRepository;
import com.internship.jobportal.repository.ReviewRepository;
import com.internship.jobportal.service.IReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService implements IReviewService {
    private final ReviewRepository reviewsRepository;
    private final ReviewMapper reviewMapper;
    private final JobRepository jobsRepository;

    public ReviewService(ReviewRepository reviewsRepository, ReviewMapper reviewMapper, JobRepository jobsRepository) {
        this.reviewsRepository = reviewsRepository;
        this.reviewMapper = reviewMapper;
        this.jobsRepository = jobsRepository;
    }

    @Override
    public Page<ReviewResource> getAllReviews(String jobCustomId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Optional<Job> jobOptional = jobsRepository.findByCustomId(jobCustomId);
        Job job = jobOptional.orElseThrow(() -> new ResourceNotFoundException("Job not found."));

        Page<ReviewResource> reviews = reviewsRepository.findAllByJob(job, pageable).map(reviewMapper::toResource);
        if (reviews.isEmpty()) {
            throw new ResourceNotFoundException("No reviews found");
        }
        return reviews;
    }

    @Override
    public Page<ReviewResource> getAllReviewsByRating(String jobCustomId, double minRating, double maxRating, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Optional<Job> jobOptional = jobsRepository.findByCustomId(jobCustomId);
        Job job = jobOptional.orElseThrow(() -> new ResourceNotFoundException("Job not found."));

        Page<ReviewResource> reviews = reviewsRepository.findAllByJobAndRatingBetween(job, minRating, maxRating, pageable).map(reviewMapper::toResource);
        if (reviews.isEmpty()) {
            throw new ResourceNotFoundException("No reviews found with rating between " + minRating + " and " + maxRating + ".");
        }
        return reviews;
    }
}
