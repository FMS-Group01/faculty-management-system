package com.faculty.view;

import javax.swing.*;
import java.awt.*;
import com.faculty.view.StudentsPanel;
import com.faculty.view.LecturersPanel;
import com.faculty.view.CoursesPanel;
import com.faculty.view.DepartmentsPanel;
import com.faculty.view.DegreesPanel;


public class AdminDashboardView extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;

    private JButton btnStudents, btnLecturers, btnCourses, btnDepartments, btnDegrees, btnLogout;

    private StudentsPanel studentsPanel;
    private LecturersPanel lecturersPanel;
    private CoursesPanel coursesPanel;
    private DepartmentsPanel departmentsPanel;
    private DegreesPanel degreesPanel;


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

        // Panels
        studentsPanel = new StudentsPanel();
        lecturersPanel = new LecturersPanel();
        coursesPanel = new CoursesPanel();
        departmentsPanel = new DepartmentsPanel();
        degreesPanel = new DegreesPanel();

        contentPanel.add(studentsPanel, "Students");
        contentPanel.add(lecturersPanel, "Lecturers");
        contentPanel.add(coursesPanel, "Courses");
        contentPanel.add(departmentsPanel, "Departments");
        contentPanel.add(degreesPanel, "Degrees");


        add(contentPanel, BorderLayout.CENTER);

        // Default view
        switchToPanel("Students", btnStudents);

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

        // Buttons
        btnStudents = createNavButton("Students", 45, 150);
        btnStudents.addActionListener(e -> switchToPanel("Students", btnStudents));

        btnLecturers = createNavButton("Lecturers", 45, 220);
        btnLecturers.addActionListener(e -> switchToPanel("Lecturers", btnLecturers));

        btnCourses = createNavButton("Courses", 45, 290);
        btnCourses.addActionListener(e -> switchToPanel("Courses", btnCourses));

        btnDepartments = createNavButton("Departments", 45, 360);
        btnDepartments.addActionListener(e -> switchToPanel("Departments", btnDepartments));

        btnDegrees = createNavButton("Degrees", 45, 430);
        btnDegrees.addActionListener(e -> switchToPanel("Degrees", btnDegrees));

        sidebar.add(btnStudents);
        sidebar.add(btnLecturers);
        sidebar.add(btnCourses);
        sidebar.add(btnDepartments);
        sidebar.add(btnDegrees);

        // Logout button
        btnLogout = new RoundedButton("Logout", Color.WHITE, 25);
        btnLogout.setBounds(120, 580, 150, 60);
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnLogout.setForeground(PURPLE);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.addActionListener(e -> {
            dispose();
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
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        return btn;
    }

    private void switchToPanel(String panelName, JButton activeButton) {
        cardLayout.show(contentPanel, panelName);
        resetAllNavButtons();
        activeButton.setBackground(Color.WHITE);
        activeButton.setForeground(PURPLE);

        // Refresh data for the selected panel
        switch (panelName) {
            case "Students" -> studentsPanel.loadStudentsData();
            case "Lecturers" -> lecturersPanel.loadLecturersData();
            case "Courses" -> coursesPanel.loadCoursesData();
            case "Degrees" -> degreesPanel.loadDegreesData();            case "Departments" -> departmentsPanel.loadDepartmentsData();

        }
    }

    private void resetAllNavButtons() {
        JButton[] buttons = {btnStudents, btnLecturers, btnCourses, btnDepartments, btnDegrees};
        for (JButton btn : buttons) {
            btn.setBackground(PURPLE);
            btn.setForeground(Color.WHITE);
        }
    }
}


