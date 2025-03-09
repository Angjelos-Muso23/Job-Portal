package com.internship.jobportal.controller;

import com.internship.jobportal.model.resource.jobResource.JobCreateResource;
import com.internship.jobportal.model.resource.applicationResource.ApplicationUpdateResource;
import com.internship.jobportal.model.resource.reviewResource.ReviewResource;
import com.internship.jobportal.service.IEmployerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employer")
public class EmployerController {

    private final IEmployerService employerService;

    public EmployerController(IEmployerService employerService) {
        this.employerService = employerService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    public ResponseEntity<String> register(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody JobCreateResource request) {
        String token = authorizationHeader.replace("Bearer ", "");
        employerService.createJob(token, request);
        return ResponseEntity.ok("Job created successfully");
    }

    @GetMapping("/jobs")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    public ResponseEntity<?> getAllJobs(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        String token = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(employerService.getAllJobs(token, page, size));
    }

    @GetMapping("/jobs/title/{title}")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    public ResponseEntity<?> getAllJobsByTitle(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        String token = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(employerService.getAllJobsByTitle(token, title, page, size));
    }

    @GetMapping("/jobs/location/{location}")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    public ResponseEntity<?> getAllJobsByLocation(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        String token = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(employerService.getAllJobsByLocation(token, location, page, size));
    }

    @GetMapping("/jobs/applications/{customId}")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    public ResponseEntity<?> getApplicationsForJob(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String customId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        String token = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(employerService.getApplicationsForJob(token, customId, page, size));
    }

    @PutMapping("/jobs/application/update")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    public ResponseEntity<String> updateApplicationStatus(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody ApplicationUpdateResource request) {
        String token = authorizationHeader.replace("Bearer ", "");
        employerService.updateApplicationStatus(token, request);
        return ResponseEntity.ok("Application status updated successfully.");
    }

    @PostMapping("/jobs/review")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    public ResponseEntity<String> createReview(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody ReviewResource request) {
        String token = authorizationHeader.replace("Bearer ", "");
        employerService.createReview(token, request);
        return ResponseEntity.ok("Review created successfully");
    }
}