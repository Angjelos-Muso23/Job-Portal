package com.internship.jobportal.service.impl;

import com.internship.jobportal.exception.custom.ResourceNotFoundException;
import com.internship.jobportal.exception.custom.UnauthorizedAccessException;
import com.internship.jobportal.mapper.ApplicationMapper;
import com.internship.jobportal.mapper.JobMapper;
import com.internship.jobportal.mapper.ReviewMapper;
import com.internship.jobportal.model.entity.Application;
import com.internship.jobportal.model.entity.Job;
import com.internship.jobportal.model.entity.Review;
import com.internship.jobportal.model.entity.User;
import com.internship.jobportal.model.resource.applicationResource.ApplicationUpdateResource;
import com.internship.jobportal.model.resource.jobResource.JobCreateResource;
import com.internship.jobportal.model.resource.reviewResource.ReviewResource;
import com.internship.jobportal.model.resource.applicationResource.GetApplicationResource;
import com.internship.jobportal.model.resource.jobResource.JobResource;
import com.internship.jobportal.repository.ApplicationRepository;
import com.internship.jobportal.repository.JobRepository;
import com.internship.jobportal.repository.ReviewRepository;
import com.internship.jobportal.repository.UserRepository;
import com.internship.jobportal.security.JwtUtil;
import com.internship.jobportal.service.IEmployerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployerService implements IEmployerService {
    private final JobRepository jobsRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationsRepository;
    private final ReviewRepository reviewsRepository;
    private final JobMapper jobsMapper;
    private final ApplicationMapper applicationMapper;
    private final ReviewMapper reviewMapper;
    private final JwtUtil jwtUtil;

    public EmployerService(
            JobRepository jobsRepository,
            UserRepository userRepository,
            ApplicationRepository applicationsRepository,
            ReviewRepository reviewsRepository,
            JobMapper jobsMapper,
            ApplicationMapper applicationMapper,
            ReviewMapper reviewMapper,
            JwtUtil jwtUtil
            ) {
        this.jobsRepository = jobsRepository;
        this.userRepository = userRepository;
        this.applicationsRepository = applicationsRepository;
        this.reviewsRepository = reviewsRepository;
        this.jobsMapper = jobsMapper;
        this.applicationMapper = applicationMapper;
        this.reviewMapper = reviewMapper;
        this.jwtUtil = jwtUtil;

    }

    @Override
    public void createJob(String token, JobCreateResource jobsResource){
        if (jobsResource.customId() == null || jobsResource.title() == null || jobsResource.description() == null) {
            throw new IllegalArgumentException("Custom ID, title and description must not be null or empty.");
        }

        Optional<Job> existingJob = jobsRepository.findByCustomId(jobsResource.customId());
        if (existingJob.isPresent()) {
            throw new IllegalArgumentException("A job with this customId already exists.");
        }
        User employer = getUserFromToken(token);
        Job job = jobsMapper.toEntity(jobsResource);
        job.setEmployer(employer);
        jobsRepository.save(job);
    }

    @Override
    public Page<JobResource> getAllJobs(String token, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        User employer = getUserFromToken(token);

        Page<JobResource> jobs = jobsRepository.findAllByEmployer(employer, pageable).map(jobsMapper::toResource);
        if (jobs.isEmpty()) {
            throw new ResourceNotFoundException("No jobs found");
        }
        return jobs;
    }

    @Override
    public Page<JobResource> getAllJobsByTitle(String token, String title, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        User employer = getUserFromToken(token);

        Page<JobResource> jobs = jobsRepository.findAllByEmployerAndTitle(employer, title, pageable).map(jobsMapper::toResource);
        if (jobs.isEmpty()) {
            throw new ResourceNotFoundException("No jobs found with title " + title + ".");
        }
        return jobs;
    }

    @Override
    public Page<JobResource> getAllJobsByLocation(String token, String location, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        User employer = getUserFromToken(token);

        Page<JobResource> jobs = jobsRepository.findAllByEmployerAndLocation(employer, location, pageable).map(jobsMapper::toResource);
        if (jobs.isEmpty()) {
            throw new ResourceNotFoundException("No jobs found in location " + location + ".");
        }
        return jobs;
    }

    @Override
    public Page<GetApplicationResource> getApplicationsForJob(String token, String jobCustomId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Job job = validateJob(token, jobCustomId, "You can only view applications for jobs you have created.");
        return  applicationsRepository.findAllByJob(job, pageable).map(applicationMapper::toResource);
    }

    @Override
    public void updateApplicationStatus(String token, ApplicationUpdateResource applicationUpdateResource){
        Job job = validateJob(token, applicationUpdateResource.jobCustomId(), "You can only update applications for jobs you have created.");

        Optional<User> seekerOptional = userRepository.findByUsername(applicationUpdateResource.username());
        User seeker = seekerOptional.orElseThrow(() -> new ResourceNotFoundException("User not found."));

        Optional<Application> applicationOptional = applicationsRepository.findByJobSeeker(seeker);
        Application application = applicationOptional.orElseThrow(() -> new ResourceNotFoundException("Application not found."));

        application.setStatus(applicationUpdateResource.status());
        applicationsRepository.save(application);
    }

    @Override
    public void createReview(String token, ReviewResource reviewResource) {
        if (reviewResource.rating() < 0 || reviewResource.rating() > 5 || reviewResource.reason() == null) {
            throw new IllegalArgumentException("Rating should be within range and reason must not be null or empty.");
        }

        Job jobToReview = validateJob(token, reviewResource.jobCustomId(), "You can only review jobs you have created.");

        Review review = reviewMapper.toEntity(reviewResource);
        review.setJob(jobToReview);

        reviewsRepository.save(review);
    }

    private User getUserFromToken(String token){
        String username = jwtUtil.extractUsername(token);
        Optional<User> employer = userRepository.findByUsername(username);
        return employer.orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    private Job validateJob(String token, String jobCustomId, String message){
        User employer = getUserFromToken(token);
        List<Job> employerJobs = jobsRepository.findAllByEmployer(employer);

        return employerJobs.stream()
                .filter(job -> job.getCustomId().equals(jobCustomId))
                .findFirst()
                .orElseThrow(() -> new UnauthorizedAccessException(message));
    }
}
