package com.internship.jobportal.model.resource.reviewResource;

import java.util.Date;

public record ReviewResource(
        double rating,
        String comment,
        String reason,
        Date createdAt,
        String jobCustomId) {
}
