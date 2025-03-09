package com.internship.jobportal.mapper;

import com.internship.jobportal.model.entity.Job;
import com.internship.jobportal.model.resource.jobResource.JobCreateResource;
import com.internship.jobportal.model.resource.jobResource.JobResource;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {
    public Job toEntity(JobCreateResource jobsResource) {
        Job job = new Job();
        return updateEntity(job, jobsResource);
    }

    public JobResource toResource(Job job) {
        if (job == null) return null;

        return new JobResource(
                job.getCustomId(),
                job.getTitle(),
                job.getDescription(),
                job.getLocation(),
                job.getCreatedAt(),
                job.getEmployer().getUsername()
        );
    }

    public Job updateEntity(Job job, JobCreateResource jobsResource) {
        job.setCustomId(jobsResource.customId());
        job.setTitle(jobsResource.title());
        job.setDescription(jobsResource.description());
        job.setLocation(jobsResource.location());
        job.setCreatedAt(jobsResource.createdAt());
        return job;
    }
}
