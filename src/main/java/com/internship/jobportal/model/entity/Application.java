package com.internship.jobportal.model.entity;

import com.internship.jobportal.model.enums.StatusEnum;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "resume_url")
    private String resumeUrl;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusEnum status;

    @Column(name = "applied_at")
    private Date appliedAt;

    @ManyToOne
    @JoinColumn(name = "job_seeker_id", nullable = false)
    private User jobSeeker;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Date getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(Date appliedAt) {
        this.appliedAt = appliedAt;
    }

    public User getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(User jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
