package com.internship.jobportal.repository;

import com.internship.jobportal.model.entity.Job;
import com.internship.jobportal.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {
    Page<Job> findAll(Pageable pageable);

    Page<Job> findAllByEmployer(User employer, Pageable pageable);

    List<Job> findAllByEmployer(User employer);

    Page<Job> findAllByTitle(String title, Pageable pageable);

    List<Job> findAllByTitle(String title);

    Page<Job> findAllByLocation(String location, Pageable pageable);

    Page<Job> findAllByEmployerAndTitle(User employer, String title, Pageable pageable);

    Page<Job> findAllByEmployerAndLocation(User employer, String location, Pageable pageable);

    Optional<Job> findByCustomId(String customId);
}
