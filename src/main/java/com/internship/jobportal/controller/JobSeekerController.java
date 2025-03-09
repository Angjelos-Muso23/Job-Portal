package com.internship.jobportal.controller;

import com.internship.jobportal.model.enums.StatusEnum;
import com.internship.jobportal.model.resource.applicationResource.ApplicationResource;
import com.internship.jobportal.model.resource.applicationResource.ApplicationUploadResumeResource;
import com.internship.jobportal.service.IJobSeekerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobSeeker")
public class JobSeekerController {
    private final IJobSeekerService jobSeekerService;

    public JobSeekerController(IJobSeekerService jobSeekerService) {
        this.jobSeekerService = jobSeekerService;
    }

    @PostMapping("/apply")
    @PreAuthorize("hasAuthority('ROLE_JOB_SEEKER')")
    public ResponseEntity<String> applyForJob(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody ApplicationResource request) {
        String token = authorizationHeader.replace("Bearer ", "");
        jobSeekerService.createApplication(token, request);
        return ResponseEntity.ok("Applied for a job successfully");
    }

    @PutMapping("/application/resume")
    @PreAuthorize("hasAuthority('ROLE_JOB_SEEKER')")
    public ResponseEntity<String> uploadResume(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody ApplicationUploadResumeResource request) {
        String token = authorizationHeader.replace("Bearer ", "");
        jobSeekerService.uploadResume(token, request);
        return ResponseEntity.ok("Resume added successfully.");
    }

    @GetMapping("/applications")
    @PreAuthorize("hasAuthority('ROLE_JOB_SEEKER')")
    public ResponseEntity<?> getAllApplications(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        String token = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(jobSeekerService.getApplications(token, page, size));
    }

    @GetMapping("/applications/title/{jobTitle}")
    @PreAuthorize("hasAuthority('ROLE_JOB_SEEKER')")
    public ResponseEntity<?> getAllApplicationsByTitle(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String jobTitle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        String token = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(jobSeekerService.getApplicationsByJobTitle(token, jobTitle, page, size));
    }

    @GetMapping("/applications/status/{status}")
    @PreAuthorize("hasAuthority('ROLE_JOB_SEEKER')")
    public ResponseEntity<?> getApplicationsByStatus(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable StatusEnum status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        String token = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(jobSeekerService.getApplicationsByStatus(token, status, page, size));
    }

    @GetMapping("/jobs")
    @PreAuthorize("hasAuthority('ROLE_JOB_SEEKER')")
    public ResponseEntity<?> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(jobSeekerService.getAllJobs(page, size));
    }

    @GetMapping("/jobs/title/{title}")
    @PreAuthorize("hasAuthority('ROLE_JOB_SEEKER')")
    public ResponseEntity<?> getAllJobsByTitle(
            @PathVariable String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(jobSeekerService.getAllJobsByTitle(title, page, size));
    }

    @GetMapping("/jobs/location/{location}")
    @PreAuthorize("hasAuthority('ROLE_JOB_SEEKER')")
    public ResponseEntity<?> getAllJobsByLocation(
            @PathVariable String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(jobSeekerService.getAllJobsByLocation(location, page, size));
    }

    @GetMapping("/jobs/employer/{username}")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    public ResponseEntity<?> getApplicationsForJob(
            @PathVariable String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(jobSeekerService.getAllJobsByEmployer(username, page, size));
    }
}
