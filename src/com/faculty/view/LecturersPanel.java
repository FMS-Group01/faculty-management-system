package com.faculty.view;

import com.faculty.controller.LecturerController;
import com.faculty.model.Lecturer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LecturersPanel extends JPanel {

    private JTable lecturersTable;
    private DefaultTableModel tableModel;
    private LecturerController lecturerController;
    private final Color PURPLE = new Color(138, 78, 255);
    private final Color LIGHT_GRAY = new Color(220, 220, 220);

    public LecturersPanel() {
        lecturerController = new LecturerController();
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

        String[] columns = {"Lecturer ID", "Name", "Email", "Mobile", "Department ID"};
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
        List<Lecturer> lecturers = lecturerController.getAllLecturers();

        if (lecturers != null) {
            for (Lecturer lecturer : lecturers) {
                tableModel.addRow(new Object[]{
                    lecturer.getLecturerId(),
                    lecturer.getName() != null ? lecturer.getName() : "N/A",
                    lecturer.getEmail() != null ? lecturer.getEmail() : "N/A",
                    lecturer.getMobile() != null ? lecturer.getMobile() : "N/A",
                    lecturer.getDepartmentId() != null ? lecturer.getDepartmentId() : "N/A"
                });
            }
        }
    }

    private void addNewLecturer() {
        JTextField txtName = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtMobile = new JTextField();
        JTextField txtUsername = new JTextField();
        JPasswordField txtPassword = new JPasswordField();
        JTextField txtDepartmentId = new JTextField();

        Object[] message = {
            "Name:", txtName,
            "Email:", txtEmail,
            "Mobile:", txtMobile,
            "Username:", txtUsername,
            "Password:", txtPassword,
            "Department ID:", txtDepartmentId
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Lecturer", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String name = txtName.getText().trim();
            String email = txtEmail.getText().trim();
            String mobile = txtMobile.getText().trim();
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();
            String deptIdStr = txtDepartmentId.getText().trim();

            try {
                int departmentId = Integer.parseInt(deptIdStr);
                boolean success = lecturerController.createLecturer(name, email, mobile, username, password, departmentId);

                if (success) {
                    loadLecturersData();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Department ID must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editLecturer() {
        int selectedRow = lecturersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a lecturer to edit!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int lecturerId = (int) tableModel.getValueAt(selectedRow, 0);
        Lecturer lecturer = lecturerController.getLecturerById(lecturerId);

        if (lecturer == null) {
            return;
        }

        JTextField txtName = new JTextField(lecturer.getName());
        JTextField txtEmail = new JTextField(lecturer.getEmail());
        JTextField txtMobile = new JTextField(lecturer.getMobile());
        JTextField txtDepartmentId = new JTextField(String.valueOf(lecturer.getDepartmentId()));

        Object[] message = {
            "Name:", txtName,
            "Email:", txtEmail,
            "Mobile:", txtMobile,
            "Department ID:", txtDepartmentId
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Lecturer", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String name = txtName.getText().trim();
            String email = txtEmail.getText().trim();
            String mobile = txtMobile.getText().trim();
            String deptIdStr = txtDepartmentId.getText().trim();

            try {
                int departmentId = Integer.parseInt(deptIdStr);
                boolean success = lecturerController.updateLecturer(lecturerId, name, email, mobile, departmentId);

                if (success) {
                    loadLecturersData();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Department ID must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteLecturer() {
        int selectedRow = lecturersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a lecturer to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int lecturerId = (int) tableModel.getValueAt(selectedRow, 0);

        boolean success = lecturerController.deleteLecturer(lecturerId);

        if (success) {
            loadLecturersData();
        }
    }

    private void saveChanges() {
        loadLecturersData();
        JOptionPane.showMessageDialog(this, "Changes saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
