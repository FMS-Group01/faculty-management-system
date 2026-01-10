package com.faculty.view;

import com.faculty.controller.StudentController;
import com.faculty.model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentsPanel extends JPanel {
    
    private JTable studentsTable;
    private DefaultTableModel tableModel;
    private StudentController studentController;
    private final Color PURPLE = new Color(138, 78, 255);
    private final Color LIGHT_GRAY = new Color(220, 220, 220);
    
    public StudentsPanel() {
        studentController = new StudentController();
        
        setLayout(null);
        setBackground(Color.WHITE);
        
        initializeComponents();
        loadStudentsData();
    }
    
    private void initializeComponents() {
        JLabel titleLabel = new JLabel("Students");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(PURPLE);
        titleLabel.setBounds(50, 30, 300, 50);
        add(titleLabel);
        
        JButton btnAddNew = new RoundedButton("Add new", PURPLE, 15);
        btnAddNew.setBounds(50, 100, 150, 45);
        btnAddNew.setForeground(Color.WHITE);
        btnAddNew.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAddNew.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAddNew.addActionListener(e -> addNewStudent());
        add(btnAddNew);
        
        JButton btnEdit = new RoundedButton("Edit", LIGHT_GRAY, 15);
        btnEdit.setBounds(220, 100, 150, 45);
        btnEdit.setForeground(Color.DARK_GRAY);
        btnEdit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEdit.addActionListener(e -> editStudent());
        add(btnEdit);
        
        JButton btnDelete = new RoundedButton("Delete", LIGHT_GRAY, 15);
        btnDelete.setBounds(390, 100, 150, 45);
        btnDelete.setForeground(Color.DARK_GRAY);
        btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete.addActionListener(e -> deleteStudent());
        add(btnDelete);
        
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
        add(scrollPane);
        
        JButton btnSaveChanges = new RoundedButton("Save changes", PURPLE, 15);
        btnSaveChanges.setBounds(250, 550, 300, 50);
        btnSaveChanges.setForeground(Color.WHITE);
        btnSaveChanges.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSaveChanges.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSaveChanges.addActionListener(e -> saveChanges());
        add(btnSaveChanges);
    }
    
    public void loadStudentsData() {
        tableModel.setRowCount(0);
        List<Student> students = studentController.getAllStudents();
        
        if (students != null) {
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
            
            boolean success = studentController.createStudent(studentId, name, email, mobile, username, password);
            
            if (success) {
                loadStudentsData();
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
        Student student = studentController.getStudentById(studentId);
        
        if (student == null) {
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
            String name = txtName.getText().trim();
            String email = txtEmail.getText().trim();
            String mobile = txtMobile.getText().trim();
            
            boolean success = studentController.updateStudent(studentId, name, email, mobile);
            
            if (success) {
                loadStudentsData();
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
        
        boolean success = studentController.deleteStudent(studentId);
        
        if (success) {
            loadStudentsData();
        }
    }
    
    private void saveChanges() {
        loadStudentsData();
        JOptionPane.showMessageDialog(this, "Changes saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
