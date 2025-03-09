package com.internship.jobportal.model.resource.userResource;

import com.internship.jobportal.model.enums.RoleEnum;

public record UserResource(
        String username,
        String email,
        String password,
        RoleEnum role) {
}
