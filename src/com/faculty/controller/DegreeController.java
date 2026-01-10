package com.faculty.controller;

import com.faculty.dao.DegreeDAO;
import com.faculty.dao.DepartmentDAO;
import com.faculty.model.Degree;
import com.faculty.model.Department;

import javax.swing.*;
import java.util.List;

public class DegreeController {
    
    private DegreeDAO degreeDAO;
    private DepartmentDAO departmentDAO;
    
    public DegreeController() {
        this.degreeDAO = new DegreeDAO();
        this.departmentDAO = new DepartmentDAO();
    }
    
    public List<Degree> getAllDegrees() {
        try {
            return degreeDAO.getAllDegrees();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading degrees: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public boolean createDegree(String name, Integer departmentId) {
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Degree name is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validate department exists if provided
        if (departmentId != null) {
            try {
                Department department = departmentDAO.getDepartmentById(departmentId);
                if (department == null) {
                    JOptionPane.showMessageDialog(null, "Department ID " + departmentId + " does not exist! Please create the department first or leave blank.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error validating department: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        try {
            Degree degree = new Degree(name, departmentId);
            int degreeId = degreeDAO.createDegree(degree);
            
            if (degreeId > 0) {
                JOptionPane.showMessageDialog(null, "Degree added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add degree!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error creating degree: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean updateDegree(int degreeId, String name, Integer departmentId) {
        // Validate department exists if provided
        if (departmentId != null) {
            try {
                Department department = departmentDAO.getDepartmentById(departmentId);
                if (department == null) {
                    JOptionPane.showMessageDialog(null, "Department ID " + departmentId + " does not exist! Please create the department first or leave blank.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error validating department: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        try {
            Degree degree = degreeDAO.getDegreeById(degreeId);
            
            if (degree == null) {
                JOptionPane.showMessageDialog(null, "Degree not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            degree.setName(name);
            degree.setDepartmentId(departmentId);
            
            boolean success = degreeDAO.updateDegree(degree);
            
            if (success) {
                JOptionPane.showMessageDialog(null, "Degree updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update degree!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error updating degree: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean deleteDegree(int degreeId) {
        int confirm = JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to delete this degree?", 
            "Confirm Deletion", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return false;
        }
        
        try {
            boolean success = degreeDAO.deleteDegree(degreeId);
            
            if (success) {
                JOptionPane.showMessageDialog(null, "Degree deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete degree!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error deleting degree: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public Degree getDegreeById(int degreeId) {
        try {
            return degreeDAO.getDegreeById(degreeId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading degree: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
