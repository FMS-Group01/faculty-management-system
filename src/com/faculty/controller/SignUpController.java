package com.faculty.controller;

import com.faculty.dao.LecturerDAO;
import com.faculty.dao.StudentDAO;
import com.faculty.dao.UserDAO;
import com.faculty.model.Lecturer;
import com.faculty.model.Student;
import com.faculty.model.User;

import javax.swing.*;

/**
 * SignUpController - Handles sign up business logic
 * Validates input and coordinates between View and DAO layers
 */
public class SignUpController {
    
    private UserDAO userDAO;
    private StudentDAO studentDAO;
    private LecturerDAO lecturerDAO;
    
    public SignUpController() {
        this.userDAO = new UserDAO();
        this.studentDAO = new StudentDAO();
        this.lecturerDAO = new LecturerDAO();
    }
    
    /**
     * Handle user sign up
     * @param username username
     * @param password password
     * @param confirmPassword confirm password
     * @param role user role (ADMIN, STUDENT, LECTURER)
     * @return true if sign up successful, false otherwise
     */
    public boolean signUp(String username, String password, String confirmPassword, String role) {
        // Validate input
        String validationError = validateSignUpInput(username, password, confirmPassword, role);
        if (validationError != null) {
            JOptionPane.showMessageDialog(null, validationError, "Sign Up Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Check if username already exists
        if (userDAO.usernameExists(username)) {
            JOptionPane.showMessageDialog(null, "Username already exists! Please choose another.", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Create user based on role
        try {
            switch (role.toUpperCase()) {
                case "ADMIN":
                    return createAdminUser(username, password);
                    
                case "STUDENT":
                    return createStudentUser(username, password);
                    
                case "LECTURER":
                    return createLecturerUser(username, password);
                    
                default:
                    JOptionPane.showMessageDialog(null, "Invalid role selected!", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                    return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Sign up failed: " + e.getMessage(), "Sign Up Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Validate sign up input
     * @param username username
     * @param password password
     * @param confirmPassword confirm password
     * @param role user role
     * @return error message if validation fails, null if valid
     */
    private String validateSignUpInput(String username, String password, String confirmPassword, String role) {
        // Check for empty fields
        if (username == null || username.trim().isEmpty()) {
            return "Username cannot be empty!";
        }
        
        if (password == null || password.trim().isEmpty()) {
            return "Password cannot be empty!";
        }
        
        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            return "Please confirm your password!";
        }
        
        if (role == null || role.trim().isEmpty()) {
            return "Please select a role!";
        }
        
        // Check username length
        if (username.length() < 3) {
            return "Username must be at least 3 characters long!";
        }
        
        if (username.length() > 50) {
            return "Username cannot exceed 50 characters!";
        }
        
        // Check password length
        if (password.length() < 6) {
            return "Password must be at least 6 characters long!";
        }
        
        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            return "Passwords do not match!";
        }
        
        return null; // All validations passed
    }
    
    /**
     * Create admin user
     * @param username username
     * @param password password
     * @return true if creation successful, false otherwise
     */
    private boolean createAdminUser(String username, String password) {
        // Create user record
        User user = new User(username, password, "ADMIN");
        int userId = userDAO.createUser(user);
        
        if (userId > 0) {
            JOptionPane.showMessageDialog(null, "Admin account created successfully!\nYou can now log in.", "Sign Up Successful", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Failed to create admin account!", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Create student user
     * @param username username
     * @param password password
     * @return true if creation successful, false otherwise
     */
    private boolean createStudentUser(String username, String password) {
        // Create user record
        User user = new User(username, password, "STUDENT");
        int userId = userDAO.createUser(user);
        
        if (userId > 0) {
            // Get student ID from user
            String studentId = JOptionPane.showInputDialog(null, 
                "Enter Student ID (e.g., S001, 2021CS001):", 
                "Student ID Required", 
                JOptionPane.QUESTION_MESSAGE);
            
            // Validate student ID
            if (studentId == null || studentId.trim().isEmpty()) {
                // Rollback user creation
                userDAO.deleteUser(userId);
                JOptionPane.showMessageDialog(null, "Student ID is required! Sign up cancelled.", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // Check if student ID already exists
            if (studentDAO.studentIdExists(studentId)) {
                // Rollback user creation
                userDAO.deleteUser(userId);
                JOptionPane.showMessageDialog(null, "Student ID already exists! Please use another ID.", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // Create student record (name, email, mobile, degree can be updated later by student)
            Student student = new Student(studentId, userId);
            boolean studentCreated = studentDAO.createStudent(student);
            
            if (studentCreated) {
                JOptionPane.showMessageDialog(null, 
                    "Student account created successfully!\nStudent ID: " + studentId + "\nYou can now log in and complete your profile.", 
                    "Sign Up Successful", 
                    JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                // Rollback user creation
                userDAO.deleteUser(userId);
                JOptionPane.showMessageDialog(null, "Failed to create student account!", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Failed to create student account!", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Create lecturer user
     * @param username username
     * @param password password
     * @return true if creation successful, false otherwise
     */
    private boolean createLecturerUser(String username, String password) {
        // Create user record
        User user = new User(username, password, "LECTURER");
        int userId = userDAO.createUser(user);
        
        if (userId > 0) {
            // Get lecturer details from user
            JTextField nameField = new JTextField();
            JTextField emailField = new JTextField();
            
            Object[] message = {
                "Name:", nameField,
                "Email:", emailField
            };
            
            int option = JOptionPane.showConfirmDialog(null, message, "Lecturer Details Required", JOptionPane.OK_CANCEL_OPTION);
            
            if (option == JOptionPane.OK_OPTION) {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                
                // Validate inputs
                if (name.isEmpty() || email.isEmpty()) {
                    // Rollback user creation
                    userDAO.deleteUser(userId);
                    JOptionPane.showMessageDialog(null, "Name and Email are required! Sign up cancelled.", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                
                // Basic email validation
                if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    // Rollback user creation
                    userDAO.deleteUser(userId);
                    JOptionPane.showMessageDialog(null, "Invalid email format! Sign up cancelled.", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                
                // Check if email already exists
                if (lecturerDAO.emailExists(email)) {
                    // Rollback user creation
                    userDAO.deleteUser(userId);
                    JOptionPane.showMessageDialog(null, "Email already exists! Please use another email.", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                
                // Create lecturer record
                Lecturer lecturer = new Lecturer(userId, name, email);
                int lecturerId = lecturerDAO.createLecturer(lecturer);
                
                if (lecturerId > 0) {
                    JOptionPane.showMessageDialog(null, 
                        "Lecturer account created successfully!\nYou can now log in.", 
                        "Sign Up Successful", 
                        JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } else {
                    // Rollback user creation
                    userDAO.deleteUser(userId);
                    JOptionPane.showMessageDialog(null, "Failed to create lecturer account!", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                // User cancelled - rollback user creation
                userDAO.deleteUser(userId);
                JOptionPane.showMessageDialog(null, "Sign up cancelled.", "Sign Up Cancelled", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Failed to create lecturer account!", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
