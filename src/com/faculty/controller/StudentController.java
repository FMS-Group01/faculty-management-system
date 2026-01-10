package com.faculty.controller;

import com.faculty.dao.StudentDAO;
import com.faculty.dao.UserDAO;
import com.faculty.dao.DegreeDAO;
import com.faculty.model.Student;
import com.faculty.model.User;

import javax.swing.*;
import java.util.List;

public class StudentController {
    
    private StudentDAO studentDAO;
    private UserDAO userDAO;
    private DegreeDAO degreeDAO;
    
    public StudentController() {
        this.studentDAO = new StudentDAO();
        this.userDAO = new UserDAO();
        this.degreeDAO = new DegreeDAO();
    }
    
    public List<Student> getAllStudents() {
        try {
            return studentDAO.getAllStudents();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error loading students: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public boolean createStudent(String studentId, String name, String email, String mobile, 
                                  String username, String password, Integer degreeId) {
        String validationError = validateStudentInput(studentId, name, email, mobile, username, password);
        if (validationError != null) {
            JOptionPane.showMessageDialog(null, validationError, "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validate degree exists if provided
        if (degreeId != null) {
            try {
                String degreeName = degreeDAO.getDegreeNameById(degreeId);
                if (degreeName == null) {
                    JOptionPane.showMessageDialog(null, 
                        "Degree ID " + degreeId + " does not exist! Please create the degree first or leave blank.", 
                        "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error validating degree: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        if (studentDAO.studentIdExists(studentId)) {
            JOptionPane.showMessageDialog(null, 
                "Student ID already exists!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (userDAO.usernameExists(username)) {
            JOptionPane.showMessageDialog(null, 
                "Username already exists!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            User user = new User(username, password, "STUDENT");
            int userId = userDAO.createUser(user);
            
            if (userId > 0) {
                Student student = new Student(studentId, userId, name, email, mobile, degreeId);
                boolean success = studentDAO.createStudent(student);
                
                if (success) {
                    JOptionPane.showMessageDialog(null, 
                        "Student added successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } else {
                    userDAO.deleteUser(userId);
                    JOptionPane.showMessageDialog(null, 
                        "Failed to add student!", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error creating student: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean updateStudent(String studentId, String name, String email, String mobile, Integer degreeId) {
        String validationError = validateUpdateInput(name, email, mobile);
        if (validationError != null) {
            JOptionPane.showMessageDialog(null, validationError, "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validate degree exists if provided
        if (degreeId != null) {
            try {
                String degreeName = degreeDAO.getDegreeNameById(degreeId);
                if (degreeName == null) {
                    JOptionPane.showMessageDialog(null, 
                        "Degree ID " + degreeId + " does not exist! Please create the degree first or leave blank.", 
                        "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error validating degree: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        try {
            Student student = studentDAO.getStudentById(studentId);
            
            if (student == null) {
                JOptionPane.showMessageDialog(null, 
                    "Student not found!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            student.setName(name);
            student.setEmail(email);
            student.setMobile(mobile);
            student.setDegreeId(degreeId);
            
            boolean success = studentDAO.updateStudent(student);
            
            if (success) {
                JOptionPane.showMessageDialog(null, 
                    "Student updated successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Failed to update student!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error updating student: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean deleteStudent(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Invalid student ID!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        int confirm = JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to delete this student?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return false;
        }
        
        try {
            boolean success = studentDAO.deleteStudent(studentId);
            
            if (success) {
                JOptionPane.showMessageDialog(null, 
                    "Student deleted successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Failed to delete student!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error deleting student: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public Student getStudentById(String studentId) {
        try {
            return studentDAO.getStudentById(studentId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error retrieving student: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    private String validateStudentInput(String studentId, String name, String email, 
                                        String mobile, String username, String password) {
        if (studentId == null || studentId.trim().isEmpty()) {
            return "Student ID is required!";
        }
        
        if (username == null || username.trim().isEmpty()) {
            return "Username is required!";
        }
        
        if (password == null || password.trim().isEmpty()) {
            return "Password is required!";
        }
        
        if (username.length() < 3) {
            return "Username must be at least 3 characters!";
        }
        
        if (password.length() < 4) {
            return "Password must be at least 4 characters!";
        }
        
        if (email != null && !email.trim().isEmpty() && !isValidEmail(email)) {
            return "Invalid email format!";
        }
        
        if (mobile != null && !mobile.trim().isEmpty() && !isValidMobile(mobile)) {
            return "Invalid mobile number format!";
        }
        
        return null;
    }
    
    private String validateUpdateInput(String name, String email, String mobile) {
        if (email != null && !email.trim().isEmpty() && !isValidEmail(email)) {
            return "Invalid email format!";
        }
        
        if (mobile != null && !mobile.trim().isEmpty() && !isValidMobile(mobile)) {
            return "Invalid mobile number format!";
        }
        
        return null;
    }
    
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    private boolean isValidMobile(String mobile) {
        return mobile.matches("^[0-9]{10,15}$");
    }
}
