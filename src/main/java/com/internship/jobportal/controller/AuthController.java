package com.internship.jobportal.controller;

import com.internship.jobportal.model.entity.User;
import com.internship.jobportal.model.resource.userResource.AuthenticateResource;
import com.internship.jobportal.model.resource.userResource.UserResource;
import com.internship.jobportal.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final IUserService userService;

    public AuthController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserResource request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody AuthenticateResource request) {
        return ResponseEntity.ok(userService.login(request));
    }
}
