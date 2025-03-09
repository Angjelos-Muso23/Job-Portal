package com.internship.jobportal.model.resource.jobResource;

import java.util.Date;

public record JobResource(
        String customId,
        String title,
        String description,
        String location,
        Date createdAt,
        String username) {
}
