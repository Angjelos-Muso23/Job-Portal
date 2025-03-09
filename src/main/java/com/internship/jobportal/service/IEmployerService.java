package com.internship.jobportal.service;

import com.internship.jobportal.model.resource.applicationResource.ApplicationUpdateResource;
import com.internship.jobportal.model.resource.jobResource.JobCreateResource;
import com.internship.jobportal.model.resource.reviewResource.ReviewResource;
import com.internship.jobportal.model.resource.applicationResource.GetApplicationResource;
import com.internship.jobportal.model.resource.jobResource.JobResource;
import org.springframework.data.domain.Page;

public interface IEmployerService {
    void createJob(String token, JobCreateResource jobsResource);

    Page<JobResource> getAllJobs(String token, int page, int size);

    Page<JobResource> getAllJobsByTitle(String token, String title, int page, int size);

    Page<JobResource> getAllJobsByLocation(String token, String location, int page, int size);

    Page<GetApplicationResource> getApplicationsForJob(String token, String jobCustomId, int page, int size);

    void updateApplicationStatus(String token, ApplicationUpdateResource applicationUpdateResource);

    void createReview(String token, ReviewResource reviewResource);
}
