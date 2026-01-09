package com.faculty.controller;

import com.faculty.dao.UserDAO;
import com.faculty.model.User;
import javax.swing.*;

public class LoginController {
    
    private UserDAO userDAO;

    public LoginController() {
        this.userDAO = new UserDAO();
    }
    
    public boolean login(String username, String password, String role) {
        String validationError = validateLoginInput(username, password, role);
        if (validationError != null) {
            JOptionPane.showMessageDialog(null, validationError, "Login Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        User user = userDAO.authenticateUser(username, password);
        
        if (user == null) {
            JOptionPane.showMessageDialog(null, "Invalid username or password!", "Login Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!user.getRole().equalsIgnoreCase(role)) {
            JOptionPane.showMessageDialog(null, "Invalid role selected!\nYou are registered as: " + user.getRole(), "Login Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        JOptionPane.showMessageDialog(null, "Login successful!\nWelcome, " + username + "!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
    
    private String validateLoginInput(String username, String password, String role) {
        if (username == null || username.trim().isEmpty()) {
            return "Username cannot be empty!";
        }
        
        if (password == null || password.trim().isEmpty()) {
            return "Password cannot be empty!";
        }
        
        if (role == null || role.trim().isEmpty()) {
            return "Please select a role!";
        }
        
        return null;
    }
}
