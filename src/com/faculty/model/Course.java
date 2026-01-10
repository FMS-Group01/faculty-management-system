package com.faculty.model;

import java.sql.Timestamp;

public class Course {
    private Integer courseId; // Changed to Integer
    private String code;
    private String title;
    private Integer credits;
    private Integer lecturerId;
    private Timestamp createdAt;

    public Course() { }

    public Course(String code, String title, Integer credits, Integer lecturerId) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.lecturerId = lecturerId;
    }

    public Course(Integer courseId, String code, String title, Integer credits, Integer lecturerId, Timestamp createdAt) {
        this.courseId = courseId;
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.lecturerId = lecturerId;
        this.createdAt = createdAt;
    }

    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }

    public Integer getLecturerId() { return lecturerId; }
    public void setLecturerId(Integer lecturerId) { this.lecturerId = lecturerId; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
