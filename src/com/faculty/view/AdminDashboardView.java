package com.faculty.view;

import javax.swing.*;
import java.awt.*;

public class AdminDashboardView extends JFrame {
    
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private JButton btnStudents, btnLecturers, btnCourses, btnDepartments, btnDegrees, btnLogout;
    private StudentsPanel studentsPanel;
    private LecturersPanel lecturersPanel;
    private final Color PURPLE = new Color(138, 78, 255);
    
    public AdminDashboardView() {
        setTitle("Faculty Management System - Admin Dashboard");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        add(createSidebarPanel(), BorderLayout.WEST);
        
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.WHITE);
        
        studentsPanel = new StudentsPanel();
        lecturersPanel = new LecturersPanel();
        
        contentPanel.add(studentsPanel, "Students");
        contentPanel.add(lecturersPanel, "Lecturers");
        
        add(contentPanel, BorderLayout.CENTER);
        
        setVisible(true);
    }
    
    private JPanel createSidebarPanel() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(390, 700));
        sidebar.setBackground(PURPLE);
        sidebar.setLayout(null);
        
        JLabel welcomeLabel = new JLabel("Welcome, Admin");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(70, 50, 300, 50);
        sidebar.add(welcomeLabel);
        
        btnStudents = createNavButton("Students", 45, 150);
        btnStudents.setBackground(Color.WHITE);
        btnStudents.setForeground(PURPLE);
        btnStudents.addActionListener(e -> switchToPanel("Students", btnStudents));
        
        btnLecturers = createNavButton("Lecturers", 45, 220);
        btnLecturers.addActionListener(e -> switchToPanel("Lecturers", btnLecturers));
        
        btnCourses = createNavButton("Courses", 45, 290);
        btnDepartments = createNavButton("Departments", 45, 360);
        btnDegrees = createNavButton("Degrees", 45, 430);
        
        sidebar.add(btnStudents);
        sidebar.add(btnLecturers);
        sidebar.add(btnCourses);
        sidebar.add(btnDepartments);
        sidebar.add(btnDegrees);
        
        btnLogout = new RoundedButton("Logout", Color.WHITE, 25);
        btnLogout.setBounds(120, 580, 150, 60);
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnLogout.setForeground(PURPLE);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.addActionListener(e -> {
            this.dispose();
            new LoginView().setVisible(true);
        });
        sidebar.add(btnLogout);
        
        return sidebar;
    }
    
    private JButton createNavButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 300, 60);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setBackground(PURPLE);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        return btn;
    }void switchToPanel(String panelName, JButton activeButton) {
        cardLayout.show(contentPanel, panelName);
        
        resetAllNavButtons();
        activeButton.setBackground(Color.WHITE);
        activeButton.setForeground(PURPLE);
        
        if (panelName.equals("Students")) {
            studentsPanel.loadStudentsData();
        }
    }
    
    private void resetAllNavButtons() {
        btnStudents.setBackground(PURPLE);
        btnStudents.setForeground(Color.WHITE);
        btnLecturers.setBackground(PURPLE);
        btnLecturers.setForeground(Color.WHITE);
        btnCourses.setBackground(PURPLE);
        btnCourses.setForeground(Color.WHITE);
        btnDepartments.setBackground(PURPLE);
        btnDepartments.setForeground(Color.WHITE);
        btnDegrees.setBackground(PURPLE);
        btnDegrees.setForeground(Color.WHITE);
    }
}
