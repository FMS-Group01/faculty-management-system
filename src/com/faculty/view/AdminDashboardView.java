package com.faculty.view;

import javax.swing.*;
import java.awt.*;

public class AdminDashboardView extends JFrame {
    
    public AdminDashboardView() {
        setTitle("Faculty Management System - Admin Dashboard");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JLabel welcomeLabel = new JLabel("Welcome to Admin Dashboard", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(138, 78, 255));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        
        panel.add(welcomeLabel, BorderLayout.NORTH);
        
        add(panel);
        setVisible(true);
    }
}
