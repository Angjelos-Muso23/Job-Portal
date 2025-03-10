package com.internship.jobportal.repository;

import com.internship.jobportal.model.entity.Application;
import com.internship.jobportal.model.entity.Job;
import com.internship.jobportal.model.entity.User;
import com.internship.jobportal.model.enums.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Page<Application> findAllByJob(Job job, Pageable pageable);

    Optional<Application> findByJobSeeker(User jobSeeker);

    Page<Application> findAllByJobSeeker(User jobSeeker, Pageable pageable);

    Page<Application> findAllByJobSeekerAndJobIn(User jobSeeker, List<Job> jobs, Pageable pageable);

    Page<Application> findAllByJobSeekerAndStatus(User jobSeeker, StatusEnum status, Pageable pageable);

    Optional<Application> findByJobSeekerAndJob(User jobSeeker, Job job);

    boolean existsByJobAndJobSeeker(Job job, User jobSeeker);
}
