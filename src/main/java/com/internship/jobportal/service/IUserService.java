package com.internship.jobportal.service;

import com.internship.jobportal.model.AuthResponse;
import com.internship.jobportal.model.entity.User;
import com.internship.jobportal.model.resource.userResource.AuthenticateResource;
import com.internship.jobportal.model.resource.userResource.UserResource;

public interface IUserService {
    User register(UserResource userResource);
    AuthResponse login(AuthenticateResource authenticateResource);
}