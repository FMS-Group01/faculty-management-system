package com.faculty.view;

import com.faculty.dao.DepartmentDAO;
import com.faculty.model.Department;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DepartmentsPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private DepartmentDAO departmentDAO;

    private final Color PURPLE = new Color(138, 78, 255);
    private final Color LIGHT_GRAY = new Color(220, 220, 220);

    public DepartmentsPanel() {
        departmentDAO = new DepartmentDAO();
        setLayout(null);
        setBackground(Color.WHITE);

        initializeComponents();
        loadDepartmentsData();
    }

    private void initializeComponents() {
        JLabel title = new JLabel("Departments");
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(PURPLE);
        title.setBounds(50, 30, 300, 50);
        add(title);

        JButton btnAdd = new RoundedButton("Add New", PURPLE, 15);
        btnAdd.setBounds(50, 100, 150, 45);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.addActionListener(e -> addDepartment());
        add(btnAdd);

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

        String[] columns = {"Department ID", "Name", "HOD"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(40);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setForeground(PURPLE);
        table.getTableHeader().setBackground(Color.WHITE);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setGridColor(PURPLE);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(50, 170, 700, 350);
        scroll.setBorder(BorderFactory.createLineBorder(PURPLE, 2));
        add(scroll);

        JButton btnSave = new RoundedButton("Save Changes", PURPLE, 15);
        btnSave.setBounds(250, 550, 300, 50);
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.addActionListener(e -> saveChanges());
        add(btnSave);
    }

    public void loadDepartmentsData() {
        tableModel.setRowCount(0);
        List<Department> departments = departmentDAO.getAllDepartments();
        for (Department d : departments) {
            tableModel.addRow(new Object[]{
                    d.getDepartmentId(),
                    d.getName(),
                    d.getHod()
            });
        }
    }

    private void addDepartment() {
        JTextField txtName = new JTextField();
        JTextField txtHod = new JTextField();

        Object[] message = {"Name:", txtName, "HOD:", txtHod};
        int option = JOptionPane.showConfirmDialog(this, message, "Add Department", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            Department dept = new Department(txtName.getText().trim(), txtHod.getText().trim());
            int id = departmentDAO.createDepartment(dept);
            if (id > 0) {
                JOptionPane.showMessageDialog(this, "Department added successfully!");
                loadDepartmentsData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add department!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editDepartment() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a department to edit!");
            return;
        }

        Integer deptId = (Integer) tableModel.getValueAt(row, 0);
        Department dept = departmentDAO.getDepartmentById(deptId);
        if (dept == null) return;

        JTextField txtName = new JTextField(dept.getName());
        JTextField txtHod = new JTextField(dept.getHod());

        Object[] message = {"Name:", txtName, "HOD:", txtHod};
        int option = JOptionPane.showConfirmDialog(this, message, "Edit Department", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            dept.setName(txtName.getText().trim());
            dept.setHod(txtHod.getText().trim());

            if (departmentDAO.updateDepartment(dept)) {
                JOptionPane.showMessageDialog(this, "Department updated successfully!");
                loadDepartmentsData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update department!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteDepartment() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a department to delete!");
            return;
        }

        Integer deptId = (Integer) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this department?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (departmentDAO.deleteDepartment(deptId)) {
                JOptionPane.showMessageDialog(this, "Department deleted successfully!");
                loadDepartmentsData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete department!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveChanges() {
        loadDepartmentsData();
        JOptionPane.showMessageDialog(this, "Changes saved successfully!");
    }
}
