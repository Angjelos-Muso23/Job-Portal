package com.internship.jobportal.controller;

import com.internship.jobportal.model.enums.RoleEnum;
import com.internship.jobportal.service.IAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final IAdminService adminService;

    public AdminController(IAdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(adminService.getAllUsers(page, size));
    }

    @GetMapping("/users/{role}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllUsersByRole(
            @PathVariable RoleEnum role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(adminService.getUsersByRole(role, page, size));
    }

    @DeleteMapping("/delete/id/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @DeleteMapping("/delete/username/{username}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username) {
        adminService.deleteUserByUsername(username);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @DeleteMapping("/review/delete/id/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        adminService.deleteReviews(id);
        return ResponseEntity.ok("Review deleted successfully.");
    }
}

