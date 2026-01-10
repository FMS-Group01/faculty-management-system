package com.faculty.view;

import com.faculty.controller.DepartmentController;
import com.faculty.model.Department;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DepartmentsPanel extends JPanel {

    private JTable departmentsTable;
    private DefaultTableModel tableModel;
    private DepartmentController departmentController;
    private final Color PURPLE = new Color(138, 78, 255);
    private final Color LIGHT_GRAY = new Color(220, 220, 220);

    public DepartmentsPanel() {
        departmentController = new DepartmentController();

        setLayout(null);
        setBackground(Color.WHITE);

        initializeComponents();
        loadDepartmentsData();
    }

    private void initializeComponents() {
        JLabel titleLabel = new JLabel("Departments");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(PURPLE);
        titleLabel.setBounds(50, 30, 300, 50);
        add(titleLabel);

        JButton btnAddNew = new RoundedButton("Add new", PURPLE, 15);
        btnAddNew.setBounds(50, 100, 150, 45);
        btnAddNew.setForeground(Color.WHITE);
        btnAddNew.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAddNew.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAddNew.addActionListener(e -> addNewDepartment());
        add(btnAddNew);

        JButton btnEdit = new RoundedButton("Edit", LIGHT_GRAY, 15);
        btnEdit.setBounds(220, 100, 150, 45);
        btnEdit.setForeground(Color.DARK_GRAY);
        btnEdit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEdit.addActionListener(e -> editDepartment());
        add(btnEdit);

        JButton btnDelete = new RoundedButton("Delete", LIGHT_GRAY, 15);
        btnDelete.setBounds(390, 100, 150, 45);
        btnDelete.setForeground(Color.DARK_GRAY);
        btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete.addActionListener(e -> deleteDepartment());
        add(btnDelete);

        String[] columns = {"Department ID", "Name", "Head of Department"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        departmentsTable = new JTable(tableModel);
        departmentsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        departmentsTable.setRowHeight(40);
        departmentsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        departmentsTable.getTableHeader().setForeground(PURPLE);
        departmentsTable.getTableHeader().setBackground(Color.WHITE);
        departmentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        departmentsTable.setGridColor(PURPLE);

        JScrollPane scrollPane = new JScrollPane(departmentsTable);
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

    public void loadDepartmentsData() {
        tableModel.setRowCount(0);
        List<Department> departments = departmentController.getAllDepartments();

        if (departments != null) {
            for (Department department : departments) {
                tableModel.addRow(new Object[]{
                    department.getDepartmentId(),
                    department.getName() != null ? department.getName() : "N/A",
                    department.getHod() != null ? department.getHod() : "N/A"
                });
            }
        }
    }

    private void addNewDepartment() {
        JTextField txtName = new JTextField();
        JTextField txtHod = new JTextField();

        Object[] message = {
            "Department Name:", txtName,
            "Head of Department:", txtHod
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Department", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String name = txtName.getText().trim();
            String hod = txtHod.getText().trim();

            boolean success = departmentController.createDepartment(name, hod);

            if (success) {
                loadDepartmentsData();
            }
        }
    }

    private void editDepartment() {
        int selectedRow = departmentsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a department to edit!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int departmentId = (int) tableModel.getValueAt(selectedRow, 0);
        Department department = departmentController.getDepartmentById(departmentId);

        if (department == null) {
            return;
        }

        JTextField txtName = new JTextField(department.getName());
        JTextField txtHod = new JTextField(department.getHod());

        Object[] message = {
            "Department Name:", txtName,
            "Head of Department:", txtHod
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Department", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String name = txtName.getText().trim();
            String hod = txtHod.getText().trim();

            boolean success = departmentController.updateDepartment(departmentId, name, hod);

            if (success) {
                loadDepartmentsData();
            }
        }
    }

    private void deleteDepartment() {
        int selectedRow = departmentsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a department to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int departmentId = (int) tableModel.getValueAt(selectedRow, 0);

        boolean success = departmentController.deleteDepartment(departmentId);

        if (success) {
            loadDepartmentsData();
        }
    }

    private void saveChanges() {
        loadDepartmentsData();
        JOptionPane.showMessageDialog(this, "Changes saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
