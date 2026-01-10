package com.faculty.controller;

import com.faculty.dao.LecturerDAO;
import com.faculty.dao.UserDAO;
import com.faculty.model.Lecturer;
import com.faculty.model.User;

import javax.swing.*;
import java.util.List;

public class LecturerController {
    
    private LecturerDAO lecturerDAO;
    private UserDAO userDAO;
    
    public LecturerController() {
        this.lecturerDAO = new LecturerDAO();
        this.userDAO = new UserDAO();
    }
    
    public List<Lecturer> getAllLecturers() {
        try {
            return lecturerDAO.getAllLecturers();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error loading lecturers: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public boolean createLecturer(String name, String email, String mobile, 
                                   String username, String password, Integer departmentId) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username and password are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (userDAO.usernameExists(username)) {
            JOptionPane.showMessageDialog(null, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            User user = new User(username, password, "LECTURER");
            int userId = userDAO.createUser(user);
            
            if (userId > 0) {
                Lecturer lecturer = new Lecturer(userId, name, email, mobile, departmentId);
                int lecturerId = lecturerDAO.createLecturer(lecturer);
                
                if (lecturerId > 0) {
                    JOptionPane.showMessageDialog(null, "Lecturer added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } else {
                    userDAO.deleteUser(userId);
                    JOptionPane.showMessageDialog(null, "Failed to add lecturer!", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error creating lecturer: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean updateLecturer(int lecturerId, String name, String email, String mobile, Integer departmentId) {
        try {
            Lecturer lecturer = lecturerDAO.getLecturerById(lecturerId);
            
            if (lecturer == null) {
                JOptionPane.showMessageDialog(null, "Lecturer not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            lecturer.setName(name);
            lecturer.setEmail(email);
            lecturer.setMobile(mobile);
            lecturer.setDepartmentId(departmentId);
            
            boolean success = lecturerDAO.updateLecturer(lecturer);
            
            if (success) {
                JOptionPane.showMessageDialog(null, "Lecturer updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update lecturer!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error updating lecturer: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean deleteLecturer(int lecturerId) {
        int confirm = JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to delete this lecturer?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return false;
        }
        
        try {
            boolean success = lecturerDAO.deleteLecturer(lecturerId);
            
            if (success) {
                JOptionPane.showMessageDialog(null, "Lecturer deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete lecturer!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error deleting lecturer: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public Lecturer getLecturerById(int lecturerId) {
        try {
            return lecturerDAO.getLecturerById(lecturerId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error retrieving lecturer: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
