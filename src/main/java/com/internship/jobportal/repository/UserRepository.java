package com.internship.jobportal.repository;

import com.internship.jobportal.model.entity.User;
import com.internship.jobportal.model.enums.RoleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAll(Pageable pageable);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Page<User> findByRole(RoleEnum role, Pageable pageable);

    void deleteByUsername(String username);
}
