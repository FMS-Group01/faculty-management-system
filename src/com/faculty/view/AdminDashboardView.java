package com.faculty.view;

import com.faculty.dao.StudentDAO;
import com.faculty.dao.UserDAO;
import com.faculty.model.Student;
import com.faculty.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminDashboardView extends JFrame {
    
    private JPanel contentPanel;
    private JButton btnStudents, btnLecturers, btnCourses, btnDepartments, btnDegrees, btnLogout;
    private JTable studentsTable;
    private DefaultTableModel tableModel;
    private StudentDAO studentDAO;
    private UserDAO userDAO;
    private final Color PURPLE = new Color(138, 78, 255);
    private final Color LIGHT_GRAY = new Color(220, 220, 220);
    
    public AdminDashboardView() {
        setTitle("Faculty Management System - Admin Dashboard");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        studentDAO = new StudentDAO();
        userDAO = new UserDAO();
        
        add(createSidebarPanel(), BorderLayout.WEST);
        
        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.add(createStudentsPanel(), "Students");
        
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
        btnStudents.addActionListener(e -> showStudentsPanel());
        
        btnLecturers = createNavButton("Lecturers", 45, 220);
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
    }
    
    private JPanel createStudentsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("Students");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(PURPLE);
        titleLabel.setBounds(50, 30, 300, 50);
        panel.add(titleLabel);
        
        JButton btnAddNew = new RoundedButton("Add new", PURPLE, 15);
        btnAddNew.setBounds(50, 100, 150, 45);
        btnAddNew.setForeground(Color.WHITE);
        btnAddNew.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAddNew.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAddNew.addActionListener(e -> addNewStudent());
        panel.add(btnAddNew);
        
        JButton btnEdit = new RoundedButton("Edit", LIGHT_GRAY, 15);
        btnEdit.setBounds(220, 100, 150, 45);
        btnEdit.setForeground(Color.DARK_GRAY);
        btnEdit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEdit.addActionListener(e -> editStudent());
        panel.add(btnEdit);
        
        JButton btnDelete = new RoundedButton("Delete", LIGHT_GRAY, 15);
        btnDelete.setBounds(390, 100, 150, 45);
        btnDelete.setForeground(Color.DARK_GRAY);
        btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete.addActionListener(e -> deleteStudent());
        panel.add(btnDelete);
        
        String[] columns = {"Full Name", "Student ID", "Degree", "Email", "Mobile Number"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        studentsTable = new JTable(tableModel);
        studentsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        studentsTable.setRowHeight(40);
        studentsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        studentsTable.getTableHeader().setForeground(PURPLE);
        studentsTable.getTableHeader().setBackground(Color.WHITE);
        studentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentsTable.setGridColor(PURPLE);
        
        JScrollPane scrollPane = new JScrollPane(studentsTable);
        scrollPane.setBounds(50, 170, 700, 350);
        scrollPane.setBorder(BorderFactory.createLineBorder(PURPLE, 2));
        panel.add(scrollPane);
        
        JButton btnSaveChanges = new RoundedButton("Save changes", PURPLE, 15);
        btnSaveChanges.setBounds(250, 550, 300, 50);
        btnSaveChanges.setForeground(Color.WHITE);
        btnSaveChanges.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSaveChanges.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSaveChanges.addActionListener(e -> saveChanges());
        panel.add(btnSaveChanges);
        
        loadStudentsData();
        
        return panel;
    }
    
    private void showStudentsPanel() {
        loadStudentsData();
    }
    
    private void loadStudentsData() {
        tableModel.setRowCount(0);
        List<Student> students = studentDAO.getAllStudents();
        
        for (Student student : students) {
            String degreeName = student.getDegreeId() != null ? "Degree #" + student.getDegreeId() : "N/A";
            tableModel.addRow(new Object[]{
                student.getName() != null ? student.getName() : "N/A",
                student.getStudentId(),
                degreeName,
                student.getEmail() != null ? student.getEmail() : "N/A",
                student.getMobile() != null ? student.getMobile() : "N/A"
            });
        }
    }
    
    private void addNewStudent() {
        JTextField txtStudentId = new JTextField();
        JTextField txtName = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtMobile = new JTextField();
        JTextField txtUsername = new JTextField();
        JPasswordField txtPassword = new JPasswordField();
        
        Object[] message = {
            "Student ID:", txtStudentId,
            "Full Name:", txtName,
            "Email:", txtEmail,
            "Mobile:", txtMobile,
            "Username:", txtUsername,
            "Password:", txtPassword
        };
        
        int option = JOptionPane.showConfirmDialog(this, message, "Add New Student", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            String studentId = txtStudentId.getText().trim();
            String name = txtName.getText().trim();
            String email = txtEmail.getText().trim();
            String mobile = txtMobile.getText().trim();
            String username = txtUsername.getText().trim();
            String password = String.valueOf(txtPassword.getPassword());
            
            if (studentId.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Student ID, Username and Password are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (studentDAO.studentIdExists(studentId)) {
                JOptionPane.showMessageDialog(this, "Student ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            User user = new User(username, password, "STUDENT");
            int userId = userDAO.createUser(user);
            
            if (userId > 0) {
                Student student = new Student(studentId, userId, name, email, mobile, null);
                boolean success = studentDAO.createStudent(student);
                
                if (success) {
                    JOptionPane.showMessageDialog(this, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadStudentsData();
                } else {
                    userDAO.deleteUser(userId);
                    JOptionPane.showMessageDialog(this, "Failed to add student!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void editStudent() {
        int selectedRow = studentsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to edit!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String studentId = (String) tableModel.getValueAt(selectedRow, 1);
        Student student = studentDAO.getStudentById(studentId);
        
        if (student == null) {
            JOptionPane.showMessageDialog(this, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JTextField txtName = new JTextField(student.getName());
        JTextField txtEmail = new JTextField(student.getEmail());
        JTextField txtMobile = new JTextField(student.getMobile());
        
        Object[] message = {
            "Full Name:", txtName,
            "Email:", txtEmail,
            "Mobile:", txtMobile
        };
        
        int option = JOptionPane.showConfirmDialog(this, message, "Edit Student", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            student.setName(txtName.getText().trim());
            student.setEmail(txtEmail.getText().trim());
            student.setMobile(txtMobile.getText().trim());
            
            boolean success = studentDAO.updateStudent(student);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Student updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadStudentsData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update student!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteStudent() {
        int selectedRow = studentsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String studentId = (String) tableModel.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this student?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = studentDAO.deleteStudent(studentId);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Student deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadStudentsData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete student!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void saveChanges() {
        loadStudentsData();
        JOptionPane.showMessageDialog(this, "Changes saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
