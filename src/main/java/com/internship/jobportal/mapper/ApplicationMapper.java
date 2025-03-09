package com.internship.jobportal.mapper;

import com.internship.jobportal.exception.custom.ResourceNotFoundException;
import com.internship.jobportal.model.entity.Application;
import com.internship.jobportal.model.entity.Job;
import com.internship.jobportal.model.resource.applicationResource.ApplicationResource;
import com.internship.jobportal.model.resource.applicationResource.GetApplicationResource;
import com.internship.jobportal.repository.JobRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ApplicationMapper {
    private final JobRepository jobsRepository;

    public ApplicationMapper(JobRepository jobsRepository) {
        this.jobsRepository = jobsRepository;
    }

    public Application toEntity(ApplicationResource applicationResource) {
        Application application = new Application();
        return updateEntity(application, applicationResource);
    }

    public GetApplicationResource toResource(Application application) {
        if (application == null) return null;

        return new GetApplicationResource(
                application.getResumeUrl(),
                application.getStatus(),
                application.getAppliedAt(),
                application.getJobSeeker().getUsername(),
                application.getJob().getCustomId()
        );
    }

    public Application updateEntity(Application application, ApplicationResource applicationResource) {
        application.setResumeUrl(applicationResource.resumeUrl());
        application.setAppliedAt(applicationResource.appliedAt());
        Optional<Job> jobOptional = jobsRepository.findByCustomId(applicationResource.jobCustomId());
        jobOptional.ifPresentOrElse(
                application::setJob,
                () -> { throw new ResourceNotFoundException("Job with id " + applicationResource.jobCustomId() + " not found."); }
        );
        return application;
    }
}
