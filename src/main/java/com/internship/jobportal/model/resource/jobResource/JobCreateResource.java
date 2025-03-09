package com.internship.jobportal.model.resource.jobResource;

import java.util.Date;

public record JobCreateResource(
        String customId,
        String title,
        String description,
        String location,
        Date createdAt) {
}
