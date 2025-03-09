package com.internship.jobportal.model.resource.applicationResource;

import java.util.Date;

public record ApplicationResource(
        String resumeUrl,
        Date appliedAt,
        String jobCustomId) {
}
