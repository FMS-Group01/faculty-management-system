package com.faculty.model;

public class Degree {
    private Integer degreeId;
    private String name;
    private Integer departmentId;

    public Degree() {
    }

    public Degree(String name, Integer departmentId) {
        this.name = name;
        this.departmentId = departmentId;
    }

    public Degree(Integer degreeId, String name, Integer departmentId) {
        this.degreeId = degreeId;
        this.name = name;
        this.departmentId = departmentId;
    }

    public Integer getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(Integer degreeId) {
        this.degreeId = degreeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
}
