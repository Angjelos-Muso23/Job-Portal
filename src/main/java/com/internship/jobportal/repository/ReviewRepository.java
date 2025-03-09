package com.internship.jobportal.repository;

import com.internship.jobportal.model.entity.Job;
import com.internship.jobportal.model.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByJob(Job job, Pageable pageable);

    Page<Review> findAllByJobAndRatingBetween(Job job, double minRating, double maxRating, Pageable pageable);
}
