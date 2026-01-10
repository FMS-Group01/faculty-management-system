package com.faculty.model;

import java.sql.Timestamp;

public class Department {
    private Integer departmentId;
    private String name;
    private String hod;
    private Timestamp createdAt;

    public Department() { }

    public Department(String name, String hod) {
        this.name = name;
        this.hod = hod;
    }

    public Department(Integer departmentId, String name, String hod, Timestamp createdAt) {
        this.departmentId = departmentId;
        this.name = name;
        this.hod = hod;
        this.createdAt = createdAt;
    }

    public Integer getDepartmentId() { return departmentId; }
    public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getHod() { return hod; }
    public void setHod(String hod) { this.hod = hod; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
