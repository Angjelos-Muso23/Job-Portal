package com.internship.jobportal.model.resource.applicationResource;

import com.internship.jobportal.model.enums.StatusEnum;

import java.util.Date;

public record GetApplicationResource(
        String resumeUrl,
        StatusEnum status,
        Date appliedAt,
        String jobSeekerUsername,
        String jobCustomId) {
}
