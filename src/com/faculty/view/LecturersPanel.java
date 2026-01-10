package com.faculty.view;

import com.faculty.dao.LecturerDAO;
import com.faculty.dao.UserDAO;
import com.faculty.model.Lecturer;
import com.faculty.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LecturersPanel extends JPanel {

    private JTable lecturersTable;
    private DefaultTableModel tableModel;
    private LecturerDAO lecturerDAO;
    private UserDAO userDAO;
    private final Color PURPLE = new Color(138, 78, 255);
    private final Color LIGHT_GRAY = new Color(220, 220, 220);

    public LecturersPanel() {
        lecturerDAO = new LecturerDAO();
        userDAO = new UserDAO();

        setLayout(null);
        setBackground(Color.WHITE);

        initializeComponents();
        loadLecturersData();
    }

    private void initializeComponents() {
        JLabel titleLabel = new JLabel("Lecturers");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(PURPLE);
        titleLabel.setBounds(50, 30, 300, 50);
        add(titleLabel);

        JButton btnAddNew = new RoundedButton("Add new", PURPLE, 15);
        btnAddNew.setBounds(50, 100, 150, 45);
        btnAddNew.setForeground(Color.WHITE);
        btnAddNew.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAddNew.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAddNew.addActionListener(e -> addNewLecturer());
        add(btnAddNew);

        JButton btnEdit = new RoundedButton("Edit", LIGHT_GRAY, 15);
        btnEdit.setBounds(220, 100, 150, 45);
        btnEdit.setForeground(Color.DARK_GRAY);
        btnEdit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEdit.addActionListener(e -> editLecturer());
        add(btnEdit);

        JButton btnDelete = new RoundedButton("Delete", LIGHT_GRAY, 15);
        btnDelete.setBounds(390, 100, 150, 45);
        btnDelete.setForeground(Color.DARK_GRAY);
        btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete.addActionListener(e -> deleteLecturer());
        add(btnDelete);

        String[] columns = {"Full Name", "Lecturer ID", "Email", "Mobile", "Department ID"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        lecturersTable = new JTable(tableModel);
        lecturersTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lecturersTable.setRowHeight(40);
        lecturersTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        lecturersTable.getTableHeader().setForeground(PURPLE);
        lecturersTable.getTableHeader().setBackground(Color.WHITE);
        lecturersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lecturersTable.setGridColor(PURPLE);

        JScrollPane scrollPane = new JScrollPane(lecturersTable);
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

    public void loadLecturersData() {
        tableModel.setRowCount(0);
        List<Lecturer> lecturers = lecturerDAO.getAllLecturers();

        for (Lecturer lecturer : lecturers) {
            String deptId = lecturer.getDepartmentId() != null ? String.valueOf(lecturer.getDepartmentId()) : "N/A";
            tableModel.addRow(new Object[]{
                    lecturer.getName() != null ? lecturer.getName() : "N/A",
                    lecturer.getLecturerId(),
                    lecturer.getEmail() != null ? lecturer.getEmail() : "N/A",
                    lecturer.getMobile() != null ? lecturer.getMobile() : "N/A",
                    deptId
            });
        }
    }

    private void addNewLecturer() {
        JTextField txtName = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtMobile = new JTextField();
        JTextField txtUsername = new JTextField();
        JPasswordField txtPassword = new JPasswordField();

        Object[] message = {
                "Full Name:", txtName,
                "Email:", txtEmail,
                "Mobile:", txtMobile,
                "Username:", txtUsername,
                "Password:", txtPassword
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Lecturer", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String name = txtName.getText().trim();
            String email = txtEmail.getText().trim();
            String mobile = txtMobile.getText().trim();
            String username = txtUsername.getText().trim();
            String password = String.valueOf(txtPassword.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and Password are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (lecturerDAO.emailExists(email)) {
                JOptionPane.showMessageDialog(this, "Email already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User user = new User(username, password, "LECTURER");
            int userId = userDAO.createUser(user);

            if (userId > 0) {
                Lecturer lecturer = new Lecturer(userId, name, email, mobile, null);
                int lecturerId = lecturerDAO.createLecturer(lecturer);

                if (lecturerId > 0) {
                    JOptionPane.showMessageDialog(this, "Lecturer added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadLecturersData();
                } else {
                    userDAO.deleteUser(userId);
                    JOptionPane.showMessageDialog(this, "Failed to add lecturer!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void editLecturer() {
        int selectedRow = lecturersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a lecturer to edit!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int lecturerId = (int) tableModel.getValueAt(selectedRow, 1);
        Lecturer lecturer = lecturerDAO.getLecturerById(lecturerId);

        if (lecturer == null) {
            JOptionPane.showMessageDialog(this, "Lecturer not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField txtName = new JTextField(lecturer.getName());
        JTextField txtEmail = new JTextField(lecturer.getEmail());
        JTextField txtMobile = new JTextField(lecturer.getMobile());

        Object[] message = {
                "Full Name:", txtName,
                "Email:", txtEmail,
                "Mobile:", txtMobile
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Lecturer", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            lecturer.setName(txtName.getText().trim());
            lecturer.setEmail(txtEmail.getText().trim());
            lecturer.setMobile(txtMobile.getText().trim());

            boolean success = lecturerDAO.updateLecturer(lecturer);

            if (success) {
                JOptionPane.showMessageDialog(this, "Lecturer updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadLecturersData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update lecturer!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteLecturer() {
        int selectedRow = lecturersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a lecturer to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int lecturerId = (int) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this lecturer?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = lecturerDAO.deleteLecturer(lecturerId);

            if (success) {
                JOptionPane.showMessageDialog(this, "Lecturer deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadLecturersData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete lecturer!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveChanges() {
        loadLecturersData();
        JOptionPane.showMessageDialog(this, "Changes saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
