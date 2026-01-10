package com.faculty.model;

import java.sql.Timestamp;

public class Lecturer {
    private Integer lecturerId; // Changed from int → Integer
    private Integer userId;
    private String name;
    private String email;
    private String mobile;
    private Integer departmentId;
    private Timestamp createdAt;

    public Lecturer() { }

    public Lecturer(Integer userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public Lecturer(Integer userId, String name, String email, String mobile, Integer departmentId) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.departmentId = departmentId;
    }

    public Lecturer(Integer lecturerId, Integer userId, String name, String email, String mobile, Integer departmentId, Timestamp createdAt) {
        this.lecturerId = lecturerId;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.departmentId = departmentId;
        this.createdAt = createdAt;
    }

    public Integer getLecturerId() { return lecturerId; }
    public void setLecturerId(Integer lecturerId) { this.lecturerId = lecturerId; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public Integer getDepartmentId() { return departmentId; }
    public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return name; // optional: for combobox display
    }
}
