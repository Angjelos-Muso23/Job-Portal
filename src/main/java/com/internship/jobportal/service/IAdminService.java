package com.internship.jobportal.service;

import com.internship.jobportal.model.enums.RoleEnum;
import com.internship.jobportal.model.resource.userResource.GetUserResource;
import org.springframework.data.domain.Page;

public interface IAdminService {
    Page<GetUserResource> getAllUsers(int page, int size);

    Page<GetUserResource> getUsersByRole(RoleEnum role, int page, int size);

    void deleteUser(Long userId);

    void deleteUserByUsername(String username);

    void deleteReviews(Long reviewId);
}

