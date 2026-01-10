package com.faculty.view;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import com.faculty.util.DatabaseConnection;

public class StudentDashboardView extends JFrame {

    public StudentDashboardView(String username) {
        setTitle("Faculty Management System - Student Dashboard");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Main layout
        setLayout(new BorderLayout());

        // 1. Sidebar Panel (Purple Region)
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(138, 78, 255));
        sidebar.setLayout(new BorderLayout());

        // User icon panel (centered at top)
        JPanel userIconPanel = new JPanel();
        userIconPanel.setBackground(new Color(138, 78, 255));
        userIconPanel.setLayout(new BoxLayout(userIconPanel, BoxLayout.Y_AXIS));

        // Create custom user icon
        JPanel userIcon = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillOval(32, 10, 36, 36);
                g2d.fillRoundRect(15, 48, 70, 45, 50, 50);
            }
        };
        userIcon.setPreferredSize(new Dimension(100, 100));
        userIcon.setMaximumSize(new Dimension(100, 100));
        userIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        userIcon.setBackground(new Color(138, 78, 255));

        userIconPanel.add(userIcon);

        // Welcome text
        JLabel welcomeLabel = new JLabel("Welcome, " + username);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        userIconPanel.add(Box.createVerticalStrut(10));
        userIconPanel.add(welcomeLabel);

        sidebar.add(userIconPanel, BorderLayout.NORTH);

        // 2. Content Panel (White Region) - CardLayout for switching
        JPanel contentArea = new JPanel();
        contentArea.setBackground(Color.WHITE);
        CardLayout cardLayout = new CardLayout();
        contentArea.setLayout(cardLayout);

        // Menu buttons panel - REDUCED SPACING
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new Color(138, 78, 255));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        // Helper sizes
        Dimension buttonSize = new Dimension(270, 48);
        int buttonMaxHeight = 48;

        // Profile Details button
        JButton profileBtn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        profileBtn.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 4));
        profileBtn.setBackground(Color.WHITE);
        profileBtn.setPreferredSize(buttonSize);
        profileBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonMaxHeight));
        profileBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileBtn.setFocusPainted(false);
        profileBtn.setBorderPainted(false);
        profileBtn.setContentAreaFilled(false);

        JPanel btnIcon = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(138, 78, 255));
                g2d.fillOval(6, 2, 18, 18);
                g2d.fillRoundRect(3, 20, 28, 14, 20, 20);
            }
        };
        btnIcon.setPreferredSize(new Dimension(35, 36));
        btnIcon.setBackground(Color.WHITE);

        JLabel profileLabel = new JLabel("Profile Details");
        profileLabel.setFont(new Font("Arial", Font.BOLD, 16));
        profileLabel.setForeground(new Color(138, 78, 255));

        profileBtn.add(btnIcon);
        profileBtn.add(profileLabel);
        profileBtn.addActionListener(e -> cardLayout.show(contentArea, "PROFILE"));

        JPanel buttonWrapper = new JPanel();
        buttonWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonWrapper.setBackground(new Color(138, 78, 255));
        buttonWrapper.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
        buttonWrapper.add(profileBtn);

        menuPanel.add(buttonWrapper);
        menuPanel.add(Box.createVerticalStrut(2));

        // Time table button
        JButton timetableBtn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        timetableBtn.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 4));
        timetableBtn.setBackground(Color.WHITE);
        timetableBtn.setPreferredSize(buttonSize);
        timetableBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonMaxHeight));
        timetableBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        timetableBtn.setFocusPainted(false);
        timetableBtn.setBorderPainted(false);
        timetableBtn.setContentAreaFilled(false);

        JLabel calendarIcon = new JLabel("\u2637");
        calendarIcon.setFont(new Font("Arial", Font.PLAIN, 26));
        calendarIcon.setForeground(new Color(138, 78, 255));
        JLabel timetableLabel = new JLabel("Time table");
        timetableLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timetableLabel.setForeground(new Color(138, 78, 255));

        timetableBtn.add(calendarIcon);
        timetableBtn.add(timetableLabel);
        timetableBtn.addActionListener(e -> cardLayout.show(contentArea, "TIMETABLE"));

        JPanel timetableWrapper = new JPanel();
        timetableWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        timetableWrapper.setBackground(new Color(138, 78, 255));
        timetableWrapper.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
        timetableWrapper.add(timetableBtn);

        menuPanel.add(timetableWrapper);
        menuPanel.add(Box.createVerticalStrut(2));

        // Courses Enrolled button
        JButton coursesBtn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        coursesBtn.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 4));
        coursesBtn.setBackground(Color.WHITE);
        coursesBtn.setPreferredSize(buttonSize);
        coursesBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonMaxHeight));
        coursesBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        coursesBtn.setFocusPainted(false);
        coursesBtn.setBorderPainted(false);
        coursesBtn.setContentAreaFilled(false);

        JLabel bookIcon = new JLabel("\u2398");
        bookIcon.setFont(new Font("Arial", Font.PLAIN, 26));
        bookIcon.setForeground(new Color(138, 78, 255));
        JLabel coursesLabel = new JLabel("Courses Enrolled");
        coursesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        coursesLabel.setForeground(new Color(138, 78, 255));

        coursesBtn.add(bookIcon);
        coursesBtn.add(coursesLabel);
        coursesBtn.addActionListener(e -> cardLayout.show(contentArea, "COURSES"));

        JPanel coursesWrapper = new JPanel();
        coursesWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        coursesWrapper.setBackground(new Color(138, 78, 255));
        coursesWrapper.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
        coursesWrapper.add(coursesBtn);

        menuPanel.add(coursesWrapper);
        menuPanel.add(Box.createVerticalStrut(2));

        // Logout button (4th button)
        JButton logoutBtn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        logoutBtn.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 4));
        logoutBtn.setBackground(Color.WHITE);
        logoutBtn.setPreferredSize(buttonSize);
        logoutBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonMaxHeight));
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setContentAreaFilled(false);

        JLabel logoutIcon = new JLabel("\u2325");
        logoutIcon.setFont(new Font("Arial", Font.PLAIN, 26));
        logoutIcon.setForeground(new Color(138, 78, 255));
        JLabel logoutLabel = new JLabel("Logout");
        logoutLabel.setFont(new Font("Arial", Font.BOLD, 16));
        logoutLabel.setForeground(new Color(138, 78, 255));

        logoutBtn.add(logoutIcon);
        logoutBtn.add(logoutLabel);
        logoutBtn.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new LoginView());
        });

        JPanel logoutWrapper = new JPanel();
        logoutWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        logoutWrapper.setBackground(new Color(138, 78, 255));
        logoutWrapper.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
        logoutWrapper.add(logoutBtn);

        menuPanel.add(logoutWrapper);

        sidebar.add(menuPanel, BorderLayout.CENTER);

        // Fetch student data from database
        String fullName = "";
        String studentId = "";
        String degree = "";
        String email = "";
        String mobile = "";

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            String query = "SELECT s.student_id, s.name, s.email, s.mobile, d.name as degree_name " +
                    "FROM students s " +
                    "LEFT JOIN degrees d ON s.degree_id = d.degree_id " +
                    "LEFT JOIN users u ON s.user_id = u.user_id " +
                    "WHERE u.username = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                studentId = rs.getString("student_id") != null ? rs.getString("student_id") : "";
                fullName = rs.getString("name") != null ? rs.getString("name") : "";
                email = rs.getString("email") != null ? rs.getString("email") : "";
                mobile = rs.getString("mobile") != null ? rs.getString("mobile") : "";
                degree = rs.getString("degree_name") != null ? rs.getString("degree_name") : "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Profile Details Panel
        JPanel profileDetailsPanel = createProfileDetailsPanel(username, fullName, studentId, degree, email, mobile);
        contentArea.add(profileDetailsPanel, "PROFILE");

        // Time table Panel
        JPanel timetablePanel = new JPanel(new BorderLayout());
        timetablePanel.setBackground(Color.WHITE);
        JLabel timetableHeading = new JLabel("Time table", SwingConstants.CENTER);
        timetableHeading.setFont(new Font("Arial", Font.BOLD, 36));
        timetableHeading.setForeground(new Color(138, 78, 255));
        timetableHeading.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0));
        timetablePanel.add(timetableHeading, BorderLayout.NORTH);
        contentArea.add(timetablePanel, "TIMETABLE");

        // Courses Enrolled Panel
        JPanel coursesPanel = new JPanel(new BorderLayout());
        coursesPanel.setBackground(Color.WHITE);
        JLabel coursesHeading = new JLabel("Courses Enrolled", SwingConstants.CENTER);
        coursesHeading.setFont(new Font("Arial", Font.BOLD, 36));
        coursesHeading.setForeground(new Color(138, 78, 255));
        coursesHeading.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0));
        coursesPanel.add(coursesHeading, BorderLayout.NORTH);
        contentArea.add(coursesPanel, "COURSES");

        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, contentArea);
        splitPane.setResizeWeight(0.35);
        splitPane.setDividerSize(0);
        splitPane.setEnabled(false);

        add(splitPane, BorderLayout.CENTER);
        setVisible(true);

        splitPane.setDividerLocation((int)(getWidth() * 0.35));
    }

    private JPanel createProfileDetailsPanel(String username, String fullName, String studentId, String degree, String email, String mobile) {
        JPanel formContainer = new JPanel();
        formContainer.setBackground(Color.WHITE);
        formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
        formContainer.setBorder(BorderFactory.createEmptyBorder(50, 0, 40, 0));

        JLabel profileDetailsHeading = new JLabel("Profile Details");
        profileDetailsHeading.setFont(new Font("Arial", Font.BOLD, 36));
        profileDetailsHeading.setForeground(new Color(138, 78, 255));
        profileDetailsHeading.setAlignmentX(Component.CENTER_ALIGNMENT);
        formContainer.add(profileDetailsHeading);
        formContainer.add(Box.createVerticalStrut(5));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Full Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 15, 10);
        JLabel fullNameLabel = new JLabel("Full Name");
        fullNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        fullNameLabel.setForeground(new Color(138, 78, 255));
        formPanel.add(fullNameLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JTextField fullNameField = new JTextField(fullName, 40);
        fullNameField.setFont(new Font("Arial", Font.PLAIN, 16));
        fullNameField.setForeground(new Color(138, 78, 255));
        fullNameField.setPreferredSize(new Dimension(500, 50));
        fullNameField.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(new Color(138, 78, 255), 2, 15),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(fullNameField, gbc);

        // Student ID
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(15, 10, 15, 10);
        JLabel studentIdLabel = new JLabel("Student ID");
        studentIdLabel.setFont(new Font("Arial", Font.BOLD, 18));
        studentIdLabel.setForeground(new Color(138, 78, 255));
        formPanel.add(studentIdLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JTextField studentIdField = new JTextField(studentId, 40);
        studentIdField.setFont(new Font("Arial", Font.PLAIN, 16));
        studentIdField.setForeground(new Color(138, 78, 255));
        studentIdField.setPreferredSize(new Dimension(500, 50));
        studentIdField.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(new Color(138, 78, 255), 2, 15),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        studentIdField.setEditable(studentId == null || studentId.isEmpty());
        formPanel.add(studentIdField, gbc);

        // Degree
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel degreeLabel = new JLabel("Degree");
        degreeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        degreeLabel.setForeground(new Color(138, 78, 255));
        formPanel.add(degreeLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JTextField degreeField = new JTextField(degree, 40);
        degreeField.setFont(new Font("Arial", Font.PLAIN, 16));
        degreeField.setForeground(new Color(138, 78, 255));
        degreeField.setPreferredSize(new Dimension(500, 50));
        degreeField.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(new Color(138, 78, 255), 2, 15),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(degreeField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 18));
        emailLabel.setForeground(new Color(138, 78, 255));
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JTextField emailField = new JTextField(email, 40);
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        emailField.setForeground(new Color(138, 78, 255));
        emailField.setPreferredSize(new Dimension(500, 50));
        emailField.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(new Color(138, 78, 255), 2, 15),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(emailField, gbc);

        // Mobile Number
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel mobileLabel = new JLabel("Mobile Number");
        mobileLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mobileLabel.setForeground(new Color(138, 78, 255));
        formPanel.add(mobileLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JTextField mobileField = new JTextField(mobile, 40);
        mobileField.setFont(new Font("Arial", Font.PLAIN, 16));
        mobileField.setForeground(new Color(138, 78, 255));
        mobileField.setPreferredSize(new Dimension(500, 50));
        mobileField.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(new Color(138, 78, 255), 2, 15),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(mobileField, gbc);

        formContainer.add(formPanel);
        formContainer.add(Box.createVerticalStrut(30));

        // Save button
        JButton saveButton = new JButton("Save changes") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }
        };
        saveButton.setFont(new Font("Arial", Font.BOLD, 20));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBackground(new Color(138, 78, 255));
        saveButton.setPreferredSize(new Dimension(300, 55));
        saveButton.setMaximumSize(new Dimension(300, 55));
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setFocusPainted(false);
        saveButton.setBorderPainted(false);
        saveButton.setContentAreaFilled(false);

        saveButton.addActionListener(e -> {
            try {
                Connection conn = DatabaseConnection.getInstance().getConnection();

                String userIdQuery = "SELECT user_id FROM users WHERE username = ?";
                PreparedStatement userIdStmt = conn.prepareStatement(userIdQuery);
                userIdStmt.setString(1, username);
                ResultSet userIdRs = userIdStmt.executeQuery();

                if (!userIdRs.next()) {
                    JOptionPane.showMessageDialog(this, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int userId = userIdRs.getInt("user_id");

                String degreeQuery = "SELECT degree_id FROM degrees WHERE TRIM(LOWER(name)) = TRIM(LOWER(?))";
                PreparedStatement degreeStmt = conn.prepareStatement(degreeQuery);
                String degreeText = degreeField.getText().trim();
                degreeStmt.setString(1, degreeText);
                ResultSet degreeRs = degreeStmt.executeQuery();

                Integer degreeId = null;
                if (degreeRs.next()) {
                    degreeId = degreeRs.getInt("degree_id");
                } else if (!degreeText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Degree '" + degreeText + "' not found in database.", "Warning", JOptionPane.WARNING_MESSAGE);
                }

                String checkQuery = "SELECT student_id FROM students WHERE user_id = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
                checkStmt.setInt(1, userId);
                ResultSet checkRs = checkStmt.executeQuery();

                int rowsAffected = 0;

                if (checkRs.next()) {
                    String updateQuery = "UPDATE students SET student_id = ?, name = ?, email = ?, mobile = ?, degree_id = ? WHERE user_id = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                    updateStmt.setString(1, studentIdField.getText().trim());
                    updateStmt.setString(2, fullNameField.getText().trim());
                    updateStmt.setString(3, emailField.getText().trim());
                    updateStmt.setString(4, mobileField.getText().trim());
                    if (degreeId != null) {
                        updateStmt.setInt(5, degreeId);
                    } else {
                        updateStmt.setNull(5, Types.INTEGER);
                    }
                    updateStmt.setInt(6, userId);
                    rowsAffected = updateStmt.executeUpdate();
                } else {
                    String insertQuery = "INSERT INTO students (student_id, user_id, name, email, mobile, degree_id) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                    insertStmt.setString(1, studentIdField.getText().trim());
                    insertStmt.setInt(2, userId);
                    insertStmt.setString(3, fullNameField.getText().trim());
                    insertStmt.setString(4, emailField.getText().trim());
                    insertStmt.setString(5, mobileField.getText().trim());
                    if (degreeId != null) {
                        insertStmt.setInt(6, degreeId);
                    } else {
                        insertStmt.setNull(6, Types.INTEGER);
                    }
                    rowsAffected = insertStmt.executeUpdate();
                }

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Changes saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    studentIdField.setEditable(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to save changes.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        formContainer.add(saveButton);

        JPanel profileDetailsPanel = new JPanel(new BorderLayout());
        profileDetailsPanel.setBackground(Color.WHITE);
        profileDetailsPanel.add(formContainer, BorderLayout.CENTER);

        return profileDetailsPanel;
    }
}