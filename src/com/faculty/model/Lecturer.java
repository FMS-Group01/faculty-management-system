package com.faculty.model;

import java.sql.Timestamp;

/**
 * Lecturer Model Class
 * Represents a lecturer in the faculty management system
 */
public class Lecturer {
    private int lecturerId;
    private int userId;
    private String name;
    private String email;
    private String mobile;
    private Integer departmentId;
    private Timestamp createdAt;

    // Constructors
    public Lecturer() {
    }

    public Lecturer(int userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public Lecturer(int userId, String name, String email, String mobile, Integer departmentId) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.departmentId = departmentId;
    }

    public Lecturer(int lecturerId, int userId, String name, String email, String mobile, Integer departmentId, Timestamp createdAt) {
        this.lecturerId = lecturerId;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.departmentId = departmentId;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Lecturer{" +
                "lecturerId=" + lecturerId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", departmentId=" + departmentId +
                ", createdAt=" + createdAt +
                '}';
    }
}
