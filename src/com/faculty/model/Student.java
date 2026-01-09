package com.faculty.model;

import java.sql.Timestamp;

/**
 * Student Model Class
 * Represents a student in the faculty management system
 */
public class Student {
    private String studentId;
    private int userId;
    private String name;
    private String email;
    private String mobile;
    private Integer degreeId;
    private Timestamp createdAt;

    // Constructors
    public Student() {
    }

    public Student(String studentId, int userId) {
        this.studentId = studentId;
        this.userId = userId;
    }

    public Student(String studentId, int userId, String name, String email, String mobile, Integer degreeId) {
        this.studentId = studentId;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.degreeId = degreeId;
    }

    public Student(String studentId, int userId, String name, String email, String mobile, Integer degreeId, Timestamp createdAt) {
        this.studentId = studentId;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.degreeId = degreeId;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public Integer getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(Integer degreeId) {
        this.degreeId = degreeId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", degreeId=" + degreeId +
                ", createdAt=" + createdAt +
                '}';
    }
}
