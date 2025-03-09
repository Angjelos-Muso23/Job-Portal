package com.internship.jobportal.service;

import com.internship.jobportal.model.enums.StatusEnum;
import com.internship.jobportal.model.resource.applicationResource.ApplicationResource;
import com.internship.jobportal.model.resource.applicationResource.ApplicationUploadResumeResource;
import com.internship.jobportal.model.resource.applicationResource.GetApplicationResource;
import com.internship.jobportal.model.resource.jobResource.JobResource;
import org.springframework.data.domain.Page;

public interface IJobSeekerService {
    void createApplication(String token, ApplicationResource applicationResource);

    void uploadResume(String token, ApplicationUploadResumeResource applicationUploadResumeResource);

    Page<GetApplicationResource> getApplications(String token, int page, int size);

    Page<GetApplicationResource> getApplicationsByJobTitle(String token, String title, int page, int size);

    Page<GetApplicationResource> getApplicationsByStatus(String token, StatusEnum status, int page, int size);

    Page<JobResource> getAllJobs(int page, int size);

    Page<JobResource> getAllJobsByTitle(String title, int page, int size);

    Page<JobResource> getAllJobsByLocation(String location, int page, int size);

    Page<JobResource> getAllJobsByEmployer(String username, int page, int size);
}
