package com.internship.jobportal.service.impl;

import com.internship.jobportal.exception.custom.ResourceAlreadyExistsException;
import com.internship.jobportal.mapper.UserMapper;
import com.internship.jobportal.model.AuthResponse;
import com.internship.jobportal.model.entity.User;
import com.internship.jobportal.model.resource.userResource.AuthenticateResource;
import com.internship.jobportal.model.resource.userResource.UserResource;
import com.internship.jobportal.repository.UserRepository;
import com.internship.jobportal.security.JwtUtil;
import com.internship.jobportal.service.IUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, @Qualifier("customUserDetailsService") UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public User register(UserResource userResource){
        if (userResource.username() == null || userResource.email() == null || userResource.password() == null) {
            throw new IllegalArgumentException("Credentials must not be null or empty.");
        }
        if (userRepository.findByEmail(userResource.email()).isPresent() ||
                userRepository.findByUsername(userResource.username()).isPresent()) {
            throw new ResourceAlreadyExistsException("User already exists");
        }
        User newUser = userMapper.toEntity(userResource);
        newUser.setPassword(passwordEncoder.encode(userResource.password()));
        return userRepository.save(newUser);
    }

    @Override
    public AuthResponse login(AuthenticateResource authenticateResource){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticateResource.username(), authenticateResource.password())
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticateResource.username());
        String token = jwtUtil.generateToken(userDetails);
        return new AuthResponse(token);
    }
}
