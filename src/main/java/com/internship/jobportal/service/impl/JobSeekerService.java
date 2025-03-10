package com.internship.jobportal.service.impl;

import com.internship.jobportal.exception.custom.ResourceAlreadyExistsException;
import com.internship.jobportal.exception.custom.ResourceNotFoundException;
import com.internship.jobportal.mapper.ApplicationMapper;
import com.internship.jobportal.mapper.JobMapper;
import com.internship.jobportal.model.entity.Application;
import com.internship.jobportal.model.entity.Job;
import com.internship.jobportal.model.entity.User;
import com.internship.jobportal.model.enums.StatusEnum;
import com.internship.jobportal.model.resource.applicationResource.ApplicationResource;
import com.internship.jobportal.model.resource.applicationResource.ApplicationUploadResumeResource;
import com.internship.jobportal.model.resource.applicationResource.GetApplicationResource;
import com.internship.jobportal.model.resource.jobResource.JobResource;
import com.internship.jobportal.repository.ApplicationRepository;
import com.internship.jobportal.repository.JobRepository;
import com.internship.jobportal.repository.UserRepository;
import com.internship.jobportal.security.JwtUtil;
import com.internship.jobportal.service.IJobSeekerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class JobSeekerService implements IJobSeekerService {
    private final ApplicationRepository applicationsRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ApplicationMapper applicationMapper;
    private final JobRepository jobsRepository;
    private final JobMapper jobsMapper;

    public JobSeekerService(ApplicationRepository applicationsRepository, JwtUtil jwtUtil, UserRepository userRepository, ApplicationMapper applicationMapper, JobRepository jobsRepository, JobMapper jobsMapper) {
        this.applicationsRepository = applicationsRepository;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.applicationMapper = applicationMapper;
        this.jobsRepository = jobsRepository;
        this.jobsMapper = jobsMapper;
    }

    @Override
    public void createApplication( String token, ApplicationResource applicationResource) {
        Optional<Job> jobOptional = jobsRepository.findByCustomId(applicationResource.jobCustomId());
        Job job = jobOptional.orElseThrow(() -> new ResourceNotFoundException("Job with id " + applicationResource.jobCustomId() + " not found."));
        Application application = applicationMapper.toEntity(applicationResource);
        User user = getUserFromToken(token);
        if (applicationsRepository.existsByJobAndJobSeeker(job, user)){
            throw new ResourceAlreadyExistsException("You have already applied for this job.");
        }
        application.setAppliedAt(new Date());
        application.setJobSeeker(user);
        application.setStatus(StatusEnum.PENDING);
        applicationsRepository.save(application);
    }

    @Override
    public void uploadResume(String token, ApplicationUploadResumeResource applicationUploadResumeResource){
        User seeker = getUserFromToken(token);
        Optional<Job> jobOptional = jobsRepository.findByCustomId(applicationUploadResumeResource.jobCustomId());
        Job job = jobOptional.orElseThrow(() -> new ResourceNotFoundException("Job not found."));

        Optional<Application> applicationOptional = applicationsRepository.findByJobSeekerAndJob(seeker, job);
        Application application = applicationOptional.orElseThrow(() -> new ResourceNotFoundException("Application not found."));
        application.setResumeUrl(applicationUploadResumeResource.resumeUrl());
        applicationsRepository.save(application);
    }

    @Override
    public Page<GetApplicationResource> getApplications(String token, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        User seeker = getUserFromToken(token);

        Page<GetApplicationResource> applications = applicationsRepository.findAllByJobSeeker(seeker, pageable).map(applicationMapper::toResource);
        if (applications.isEmpty()) {
            throw new ResourceNotFoundException("No applications found");
        }
        return applications;
    }

    @Override
    public Page<GetApplicationResource> getApplicationsByJobTitle(String token, String title, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        User seeker = getUserFromToken(token);

        List<Job> jobsWithTitle = jobsRepository.findAllByTitle(title);

        Page<GetApplicationResource> applications = applicationsRepository.findAllByJobSeekerAndJobIn(seeker, jobsWithTitle, pageable)
                .map(applicationMapper::toResource);
        if (applications.isEmpty()) {
            throw new ResourceNotFoundException("No applications found");
        }
        return applications;
    }

    @Override
    public Page<GetApplicationResource> getApplicationsByStatus(String token, StatusEnum status, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        User seeker = getUserFromToken(token);

        Page<GetApplicationResource> applications = applicationsRepository.findAllByJobSeekerAndStatus(seeker, status, pageable)
                .map(applicationMapper::toResource);
        if (applications.isEmpty()) {
            throw new ResourceNotFoundException("No applications found");
        }
        return applications;
    }

    @Override
    public Page<JobResource> getAllJobs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<JobResource> jobs = jobsRepository.findAll(pageable).map(jobsMapper::toResource);
        if (jobs.isEmpty()) {
            throw new ResourceNotFoundException("No jobs found");
        }
        return jobs;
    }

    @Override
    public Page<JobResource> getAllJobsByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<JobResource> jobs = jobsRepository.findAllByTitle(title, pageable).map(jobsMapper::toResource);
        if (jobs.isEmpty()) {
            throw new ResourceNotFoundException("No jobs found with title " + title + ".");
        }
        return jobs;
    }

    @Override
    public Page<JobResource> getAllJobsByLocation(String location, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<JobResource> jobs = jobsRepository.findAllByLocation(location, pageable).map(jobsMapper::toResource);
        if (jobs.isEmpty()) {
            throw new ResourceNotFoundException("No jobs found in location " + location + ".");
        }
        return jobs;
    }

    @Override
    public Page<JobResource> getAllJobsByEmployer(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        User employer = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found."));
        Page<JobResource> jobs = jobsRepository.findAllByEmployer(employer, pageable).map(jobsMapper::toResource);
        if (jobs.isEmpty()) {
            throw new RuntimeException("No jobs found by employer " + username + ".");
        }
        return jobs;
    }

    private User getUserFromToken(String token){
        String username = jwtUtil.extractUsername(token);
        Optional<User> employer = userRepository.findByUsername(username);
        return employer.orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }
}
