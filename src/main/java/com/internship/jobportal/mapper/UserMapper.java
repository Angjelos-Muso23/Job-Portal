package com.internship.jobportal.mapper;

import com.internship.jobportal.model.entity.User;
import com.internship.jobportal.model.resource.userResource.GetUserResource;
import com.internship.jobportal.model.resource.userResource.UserResource;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserResource userResource) {
        User user = new User();
        return updateEntity(user, userResource);
    }

    public GetUserResource toResource(User user) {
        if (user == null) return null;

        return new GetUserResource(
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }

    public User updateEntity(User user, UserResource userResource) {
        user.setUsername(userResource.username());
        user.setEmail(userResource.email());
        user.setRole(userResource.role());
        return user;
    }
}
