package com.faculty.view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnAdmin, btnStudent, btnLecturer, btnLogin;
    private JButton btnSignInTab, btnSignUpTab;
    private JPanel signInPanel, signUpPanel;
    private String selectedRole = "Admin";

    public LoginView() {
        setTitle("Faculty Management System");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createLeftPanel(), BorderLayout.WEST);
        add(createRightPanel(), BorderLayout.CENTER);
    }

    //    left panel
    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 500));
        panel.setBackground(new Color(138, 78, 255));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel iconLabel;
        try {
            java.net.URL iconURL = getClass().getResource("/resources/images/hat.png");
            if (iconURL == null) {
                iconURL = getClass().getClassLoader().getResource("resources/images/hat.png");
            }

            if (iconURL != null) {
                ImageIcon icon = new ImageIcon(iconURL);
                Image scaled = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                iconLabel = new JLabel(new ImageIcon(scaled));
            } else {
                iconLabel = new JLabel("📚");
                iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 60));
                iconLabel.setForeground(Color.WHITE);
            }
        } catch (Exception e) {
            iconLabel = new JLabel("📚");
            iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 60));
            iconLabel.setForeground(Color.WHITE);
        }
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        JLabel title = new JLabel("Faculty Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title2 = new JLabel("System");
        title2.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title2.setForeground(Color.WHITE);
        title2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel faculty = new JLabel("Faculty of Computing & Technology");
        faculty.setFont(new Font("Segoe UI", Font.BOLD, 16));
        faculty.setForeground(Color.WHITE);
        faculty.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Manage your academic journey");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(Color.WHITE);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(60));
        panel.add(iconLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(title);
        panel.add(title2);
        panel.add(Box.createVerticalStrut(100));
        panel.add(faculty);
        panel.add(subtitle);

        return panel;
    }

    //    right panel
    private JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());

        panel.add(createTabButtons(), BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(Color.WHITE);

        signInPanel = createSignInPanel();
        signUpPanel = createSignUpPanel();

        contentPanel.add(signInPanel, "SignIn");
        contentPanel.add(signUpPanel, "SignUp");

        panel.add(contentPanel, BorderLayout.CENTER);

        CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
        
        btnSignInTab.addActionListener(e -> {
            cardLayout.show(contentPanel, "SignIn");
            setActiveTab(btnSignInTab);
        });

        btnSignUpTab.addActionListener(e -> {
            cardLayout.show(contentPanel, "SignUp");
            setActiveTab(btnSignUpTab);
        });

        setActiveTab(btnSignInTab);

        return panel;
    }

    private JPanel createTabButtons() {
        JPanel tabPanel = new JPanel();
        tabPanel.setBackground(Color.WHITE);
        tabPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        tabPanel.setPreferredSize(new Dimension(500, 50));

        btnSignInTab = new JButton("Sign In");
        btnSignInTab.setPreferredSize(new Dimension(150, 50));
        btnSignInTab.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSignInTab.setFocusPainted(false);
        btnSignInTab.setBorderPainted(true);
        btnSignInTab.setContentAreaFilled(true);
        btnSignInTab.setForeground(Color.BLACK);
        btnSignInTab.setBackground(Color.WHITE);
        btnSignInTab.setBorder(null);

        btnSignUpTab = new JButton("Sign Up");
        btnSignUpTab.setPreferredSize(new Dimension(150, 50));
        btnSignUpTab.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSignUpTab.setFocusPainted(false);
        btnSignUpTab.setBorderPainted(true);
        btnSignUpTab.setContentAreaFilled(true);
        btnSignUpTab.setForeground(Color.BLACK);
        btnSignUpTab.setBackground(Color.WHITE);
        btnSignUpTab.setBorder(null);

        tabPanel.add(btnSignInTab);
        tabPanel.add(btnSignUpTab);

        return tabPanel;
    }

    private void setActiveTab(JButton selectedTab) {
        btnSignInTab.setForeground(Color.LIGHT_GRAY);
        btnSignInTab.setBorder(null);
        btnSignUpTab.setForeground(Color.LIGHT_GRAY);
        btnSignUpTab.setBorder(null);

        selectedTab.setForeground(new Color(138, 78, 255));
        selectedTab.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, new Color(138, 78, 255)));
        
        btnSignInTab.repaint();
        btnSignUpTab.repaint();
    }

    // sign in panel
    private JPanel createSignInPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBounds(80, 90, 200, 20);

        txtUsername = new JTextField();
        txtUsername.setBounds(80, 120, 300, 35);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(80, 170, 200, 20);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(80, 200, 300, 35);

        JLabel lblRole = new JLabel("Role");
        lblRole.setBounds(80, 250, 200, 20);

        btnAdmin = createRoleButton("Admin", 80, 280);
        btnStudent = createRoleButton("Student", 190, 280);
        btnLecturer = createRoleButton("Lecturer", 300, 280);

        setActiveRole(btnAdmin);

        btnLogin = new JButton("Sign In");
        btnLogin.setBounds(80, 350, 300, 45);
        btnLogin.setBackground(new Color(138, 78, 255));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);

        panel.add(lblUsername);
        panel.add(txtUsername);
        panel.add(lblPassword);
        panel.add(txtPassword);
        panel.add(lblRole);
        panel.add(btnAdmin);
        panel.add(btnStudent);
        panel.add(btnLecturer);
        panel.add(btnLogin);

        return panel;
    }

    // sign up panel
    private JPanel createSignUpPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        JLabel lblInfo = new JLabel("Create a new account");
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblInfo.setForeground(Color.GRAY);
        lblInfo.setBounds(80, 100, 300, 100);
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(lblInfo);

        return panel;
    }

    /* ================= ROLE BUTTON STYLE ================= */
    private JButton createRoleButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 100, 35);
        btn.setFocusPainted(false);
        btn.setBackground(Color.LIGHT_GRAY);

        btn.addActionListener(e -> {
            selectedRole = text;
            setActiveRole(btn);
        });

        return btn;
    }

    private void setActiveRole(JButton selected) {
        JButton[] buttons = {btnAdmin, btnStudent, btnLecturer};

        for (JButton btn : buttons) {
            btn.setBackground(Color.LIGHT_GRAY);
            btn.setForeground(Color.BLACK);
        }

        selected.setBackground(new Color(138, 78, 255));
        selected.setForeground(Color.WHITE);
    }

    public String getUsername() {
        return txtUsername.getText();
    }

    public String getPassword() {
        return String.valueOf(txtPassword.getPassword());
    }

    public String getRole() {
        return selectedRole;
    }

    public JButton getLoginButton() {
        return btnLogin;
    }
}
