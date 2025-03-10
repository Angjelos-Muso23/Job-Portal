package com.internship.jobportal.service.impl;

import com.internship.jobportal.exception.custom.ResourceNotFoundException;
import com.internship.jobportal.mapper.UserMapper;
import com.internship.jobportal.model.entity.Review;
import com.internship.jobportal.model.entity.User;
import com.internship.jobportal.model.enums.RoleEnum;
import com.internship.jobportal.model.resource.userResource.GetUserResource;
import com.internship.jobportal.repository.ReviewRepository;
import com.internship.jobportal.repository.UserRepository;
import com.internship.jobportal.service.IAdminService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService implements IAdminService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ReviewRepository reviewsRepository;

    public AdminService(UserRepository userRepository, UserMapper userMapper, ReviewRepository reviewsRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.reviewsRepository = reviewsRepository;
    }

    @Override
    public Page<GetUserResource> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<GetUserResource> users = userRepository.findAll(pageable).map(userMapper::toResource);

        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found");
        }
        return users;
    }

    @Override
    public Page<GetUserResource> getUsersByRole(RoleEnum role, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<GetUserResource> users = userRepository.findByRole(role, pageable).map(userMapper::toResource);

        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found for role:" + role.name());
        }
        return users;
    }

    @Override
    public void deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        if (user.get().getRole() == RoleEnum.ADMIN) {
            throw new IllegalArgumentException("You can not delete an admin.");
        }
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public void deleteUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        if (user.get().getRole() == RoleEnum.ADMIN) {
            throw new IllegalArgumentException("You can not delete an admin.");
        }
        userRepository.deleteByUsername(username);
    }

    @Override
    public void deleteReviews(Long reviewId) {
        Optional<Review> review = reviewsRepository.findById(reviewId);
        if (review.isEmpty()) {
            throw new ResourceNotFoundException("Review not found.");
        }
        reviewsRepository.deleteById(reviewId);
    }
}
