package com.faculty.view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.sql.*;
import java.sql.Types;
import com.faculty.util.DatabaseConnection;

public class StudentDashboardView extends JFrame {

    public StudentDashboardView(String username) {
        setTitle("Faculty Management System - Student Dashboard");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(138, 78, 255));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        sidebar.add(Box.createVerticalStrut(20));

        JPanel userIconPanel = new JPanel();
        userIconPanel.setBackground(new Color(138, 78, 255));
        userIconPanel.setLayout(new BoxLayout(userIconPanel, BoxLayout.Y_AXIS));
        userIconPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

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
        userIconPanel.add(Box.createVerticalStrut(20));

        JLabel welcomeLabel = new JLabel("Welcome, " + username);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        userIconPanel.add(welcomeLabel);

        sidebar.add(userIconPanel);
        sidebar.add(Box.createVerticalStrut(40));

        JPanel contentArea = new JPanel();
        contentArea.setBackground(Color.WHITE);
        CardLayout cardLayout = new CardLayout();
        contentArea.setLayout(cardLayout);

        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new Color(138, 78, 255));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(0, 12, 8, 12));
        menuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension buttonSize = new Dimension(270, 48);
        int buttonMaxHeight = 48;

        JButton profileBtn = new JButton();
        profileBtn.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 4));
        profileBtn.setBackground(Color.WHITE);
        profileBtn.setPreferredSize(buttonSize);
        profileBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonMaxHeight));
        profileBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileBtn.setFocusPainted(false);
        profileBtn.setBorderPainted(false);
        profileBtn.setContentAreaFilled(true);
        profileBtn.setOpaque(true);

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

        menuPanel.add(profileBtn);
        menuPanel.add(Box.createVerticalStrut(30));

        JButton timetableBtn = new JButton();
        timetableBtn.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 4));
        timetableBtn.setBackground(Color.WHITE);
        timetableBtn.setPreferredSize(buttonSize);
        timetableBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonMaxHeight));
        timetableBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        timetableBtn.setFocusPainted(false);
        timetableBtn.setBorderPainted(false);
        timetableBtn.setContentAreaFilled(true);
        timetableBtn.setOpaque(true);

        JLabel timetableLabel = new JLabel("Time table");
        timetableLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timetableLabel.setForeground(new Color(138, 78, 255));

        timetableBtn.add(timetableLabel);
        timetableBtn.addActionListener(e -> cardLayout.show(contentArea, "TIMETABLE"));

        menuPanel.add(timetableBtn);
        menuPanel.add(Box.createVerticalStrut(30));

        JButton coursesBtn = new JButton();
        coursesBtn.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 4));
        coursesBtn.setBackground(Color.WHITE);
        coursesBtn.setPreferredSize(buttonSize);
        coursesBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonMaxHeight));
        coursesBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        coursesBtn.setFocusPainted(false);
        coursesBtn.setBorderPainted(false);
        coursesBtn.setContentAreaFilled(true);
        coursesBtn.setOpaque(true);

        JLabel coursesLabel = new JLabel("Courses Enrolled");
        coursesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        coursesLabel.setForeground(new Color(138, 78, 255));

        coursesBtn.add(coursesLabel);
        coursesBtn.addActionListener(e -> cardLayout.show(contentArea, "COURSES"));

        menuPanel.add(coursesBtn);

        sidebar.add(menuPanel);
        sidebar.add(Box.createVerticalGlue());

        // Create logout button at the bottom
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(Color.WHITE);
        logoutButton.setForeground(new Color(138, 78, 255));
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setPreferredSize(new Dimension(75, 48));
        logoutButton.setMaximumSize(new Dimension(75, 48));
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(true);
        logoutButton.setBorder(BorderFactory.createLineBorder(new Color(138, 78, 255), 2));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                LoginView loginView = new LoginView();
                loginView.setVisible(true);
            });
        });

        sidebar.add(logoutButton);
        sidebar.add(Box.createVerticalStrut(20));

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

        JPanel profileDetailsPanel = createProfileDetailsPanel(username, fullName, studentId, degree, email, mobile);
        contentArea.add(profileDetailsPanel, "PROFILE");

        JPanel timetablePanel = new JPanel(new BorderLayout());
        timetablePanel.setBackground(Color.WHITE);

        JLabel timetableHeading = new JLabel("Time table", SwingConstants.CENTER);
        timetableHeading.setFont(new Font("Arial", Font.BOLD, 36));
        timetableHeading.setForeground(new Color(138, 78, 255));
        timetableHeading.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        timetablePanel.add(timetableHeading, BorderLayout.NORTH);

        String[] columnNames = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        Object[][] data = {
                {"08:00", "OOP", "OOP", "OOP", "OOP", "OOP"},
                {"10:00", "OOP", "OOP", "OOP", "OOP", "OOP"},
                {"Interval", "", "", "", "", ""},
                {"01:00", "SE", "OOP", "SE", "SE", "SE"},
                {"03:00", "SE", "OOP", "SE", "SE", "SE"}
        };

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setFont(new Font("Arial", Font.BOLD, 20));
        table.setForeground(new Color(138, 78, 255));
        table.setBackground(Color.WHITE);
        table.setRowHeight(80);
        table.setGridColor(new Color(138, 78, 255));
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(2, 2));

        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 22));
        table.getTableHeader().setForeground(new Color(138, 78, 255));
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 60));

        // Custom renderer with merged cells for Interval row
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.CENTER);
                setVerticalAlignment(JLabel.CENTER);

                if (row == 2) {
                    setBackground(new Color(138, 78, 255));
                    setForeground(Color.WHITE);
                    setFont(new Font("Arial", Font.BOLD, 24));
                    // Only show text in first column for merged appearance
                    if (column == 0) {
                        setText("Interval");
                    } else {
                        setText("");
                    }
                } else {
                    setBackground(Color.WHITE);
                    setForeground(new Color(138, 78, 255));
                    setFont(new Font("Arial", Font.BOLD, 20));
                }

                setOpaque(true);
                return this;
            }
        });

        // Custom UI to paint merged cell appearance
        table.setUI(new javax.swing.plaf.basic.BasicTableUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                super.paint(g, c);

                // Draw a visual merge effect for row 2 (Interval row)
                Rectangle cellRect = table.getCellRect(2, 0, false);
                if (cellRect != null) {
                    int rowHeight = table.getRowHeight(2);
                    int tableWidth = 0;
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        tableWidth += table.getColumnModel().getColumn(i).getWidth();
                    }

                    // Fill the entire row with purple
                    g.setColor(new Color(138, 78, 255));
                    g.fillRect(cellRect.x, cellRect.y, tableWidth, rowHeight);

                    // Draw "Interval" text centered
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Arial", Font.BOLD, 24));
                    FontMetrics fm = g.getFontMetrics();
                    String text = "Interval";
                    int textWidth = fm.stringWidth(text);
                    int textX = (tableWidth - textWidth) / 2;
                    int textY = cellRect.y + (rowHeight - fm.getHeight()) / 2 + fm.getAscent();
                    g.drawString(text, textX, textY);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(138, 78, 255), 2));

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(scrollPane);

        timetablePanel.add(centerPanel, BorderLayout.CENTER);
        contentArea.add(timetablePanel, "TIMETABLE");

        JPanel coursesPanel = new JPanel(new BorderLayout());
        coursesPanel.setBackground(Color.WHITE);
        JLabel coursesHeading = new JLabel("Courses Enrolled", SwingConstants.CENTER);
        coursesHeading.setFont(new Font("Arial", Font.BOLD, 36));
        coursesHeading.setForeground(new Color(138, 78, 255));
        coursesHeading.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        coursesPanel.add(coursesHeading, BorderLayout.NORTH);

        // Create courses table
        String[] coursesColumnNames = {"Course code", "Course name", "Credits", "Grade"};
        Object[][] coursesData = {
                {"ETEC 21062", "OOP", "2", "A+"},
                {"ETEC 21052", "OOP", "2", "B"},
                {"ETEC 21042", "OOP", "2", "A"},
                {"ETEC 21032", "OOP", "2", "D"},
                {"ETEC 21022", "OOP", "2", "C"},
                {"ETEC 21012", "OOP", "2", "B"}
        };

        JTable coursesTable = new JTable(coursesData, coursesColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        coursesTable.setFont(new Font("Arial", Font.BOLD, 20));
        coursesTable.setForeground(new Color(138, 78, 255));
        coursesTable.setBackground(Color.WHITE);
        coursesTable.setRowHeight(80);
        coursesTable.setGridColor(new Color(138, 78, 255));
        coursesTable.setShowGrid(true);
        coursesTable.setIntercellSpacing(new Dimension(2, 2));

        coursesTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 22));
        coursesTable.getTableHeader().setForeground(new Color(138, 78, 255));
        coursesTable.getTableHeader().setBackground(Color.WHITE);
        coursesTable.getTableHeader().setPreferredSize(new Dimension(0, 60));

        // Make the first column (Course code) wider
        coursesTable.getColumnModel().getColumn(0).setPreferredWidth(250);

        coursesTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.CENTER);
                setVerticalAlignment(JLabel.CENTER);
                setBackground(Color.WHITE);
                setForeground(new Color(138, 78, 255));
                setFont(new Font("Arial", Font.BOLD, 20));
                setOpaque(true);
                return this;
            }
        });

        JScrollPane coursesScrollPane = new JScrollPane(coursesTable);
        coursesScrollPane.setBorder(BorderFactory.createLineBorder(new Color(138, 78, 255), 2));

        JPanel coursesCenterPanel = new JPanel(new GridBagLayout());
        coursesCenterPanel.setBackground(Color.WHITE);
        coursesCenterPanel.add(coursesScrollPane);

        coursesPanel.add(coursesCenterPanel, BorderLayout.CENTER);
        contentArea.add(coursesPanel, "COURSES");

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
                BorderFactory.createLineBorder(new Color(138, 78, 255), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(fullNameField, gbc);

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
                BorderFactory.createLineBorder(new Color(138, 78, 255), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        studentIdField.setEditable(studentId == null || studentId.isEmpty());
        formPanel.add(studentIdField, gbc);

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
                BorderFactory.createLineBorder(new Color(138, 78, 255), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(degreeField, gbc);

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
                BorderFactory.createLineBorder(new Color(138, 78, 255), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(emailField, gbc);

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
                BorderFactory.createLineBorder(new Color(138, 78, 255), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(mobileField, gbc);

        formContainer.add(formPanel);
        formContainer.add(Box.createVerticalStrut(30));

        JButton saveButton = new JButton("Save changes");
        saveButton.setFont(new Font("Arial", Font.BOLD, 20));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBackground(new Color(138, 78, 255));
        saveButton.setPreferredSize(new Dimension(300, 55));
        saveButton.setMaximumSize(new Dimension(300, 55));
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setFocusPainted(false);
        saveButton.setBorderPainted(false);
        saveButton.setContentAreaFilled(true);
        saveButton.setOpaque(true);

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