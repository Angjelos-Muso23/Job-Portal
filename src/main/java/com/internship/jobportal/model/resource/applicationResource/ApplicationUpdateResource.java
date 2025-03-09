package com.internship.jobportal.model.resource.applicationResource;

import com.internship.jobportal.model.enums.StatusEnum;

public record ApplicationUpdateResource(
        String jobCustomId,
        String username,
        StatusEnum status) {
}
