package com.faculty.controller;

import com.faculty.dao.DepartmentDAO;
import com.faculty.model.Department;

import javax.swing.*;
import java.util.List;

public class DepartmentController {
    
    private DepartmentDAO departmentDAO;
    
    public DepartmentController() {
        this.departmentDAO = new DepartmentDAO();
    }
    
    public List<Department> getAllDepartments() {
        try {
            return departmentDAO.getAllDepartments();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading departments: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public boolean createDepartment(String name, String hod) {
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Department name is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            Department department = new Department(name, hod);
            int deptId = departmentDAO.createDepartment(department);
            
            if (deptId > 0) {
                JOptionPane.showMessageDialog(null, "Department added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add department!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error creating department: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean updateDepartment(int departmentId, String name, String hod) {
        try {
            Department department = departmentDAO.getDepartmentById(departmentId);
            
            if (department == null) {
                JOptionPane.showMessageDialog(null, "Department not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            department.setName(name);
            department.setHod(hod);
            
            boolean success = departmentDAO.updateDepartment(department);
            
            if (success) {
                JOptionPane.showMessageDialog(null, "Department updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update department!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error updating department: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean deleteDepartment(int departmentId) {
        int confirm = JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to delete this department?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return false;
        }
        
        try {
            boolean success = departmentDAO.deleteDepartment(departmentId);
            
            if (success) {
                JOptionPane.showMessageDialog(null, "Department deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete department!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error deleting department: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public Department getDepartmentById(int departmentId) {
        try {
            return departmentDAO.getDepartmentById(departmentId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error retrieving department: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
