package com.faculty.controller;

import com.faculty.dao.UserDAO;
import com.faculty.model.User;
import javax.swing.*;

public class SignUpController {
    
    private UserDAO userDAO;
    
    public SignUpController() {
        this.userDAO = new UserDAO();
    }
    
    public boolean signUp(String username, String password, String confirmPassword, String role) {
        String validationError = validateSignUpInput(username, password, confirmPassword, role);
        if (validationError != null) {
            JOptionPane.showMessageDialog(null, validationError, "Sign Up Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (userDAO.usernameExists(username)) {
            JOptionPane.showMessageDialog(null, "Username already exists! Please choose another.", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            User user = new User(username, password, role.toUpperCase());
            int userId = userDAO.createUser(user);
            
            if (userId > 0) {
                JOptionPane.showMessageDialog(null, 
                    "Account created successfully!\nYou can now log in.", 
                    "Sign Up Successful", 
                    JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Failed to create account!", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Sign up failed: " + e.getMessage(), "Sign Up Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
    
    private String validateSignUpInput(String username, String password, String confirmPassword, String role) {
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
        
        if (username.length() < 3) {
            return "Username must be at least 3 characters long!";
        }
        
        if (username.length() > 50) {
            return "Username cannot exceed 50 characters!";
        }
        
        if (password.length() < 4) {
            return "Password must be at least 4 characters long!";
        }
        
        if (!password.equals(confirmPassword)) {
            return "Passwords do not match!";
        }
        
        return null;
    }
}
