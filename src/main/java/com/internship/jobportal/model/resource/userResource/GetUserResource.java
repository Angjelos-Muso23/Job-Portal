package com.internship.jobportal.model.resource.userResource;

import com.internship.jobportal.model.enums.RoleEnum;

public record GetUserResource(String username, String email, RoleEnum role) {
}
