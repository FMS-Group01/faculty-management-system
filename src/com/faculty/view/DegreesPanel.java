package com.faculty.view;

import com.faculty.controller.DegreeController;
import com.faculty.model.Degree;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DegreesPanel extends JPanel {

    private JTable degreesTable;
    private DefaultTableModel tableModel;
    private DegreeController degreeController;
    private final Color PURPLE = new Color(138, 78, 255);
    private final Color LIGHT_GRAY = new Color(220, 220, 220);

    public DegreesPanel() {
        degreeController = new DegreeController();

        setLayout(null);
        setBackground(Color.WHITE);

        initializeComponents();
        loadDegreesData();
    }

    private void initializeComponents() {
        JLabel titleLabel = new JLabel("Degrees");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(PURPLE);
        titleLabel.setBounds(50, 30, 300, 50);
        add(titleLabel);

        JButton btnAddNew = new RoundedButton("Add new", PURPLE, 15);
        btnAddNew.setBounds(50, 100, 150, 45);
        btnAddNew.setForeground(Color.WHITE);
        btnAddNew.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAddNew.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAddNew.addActionListener(e -> addNewDegree());
        add(btnAddNew);

        JButton btnEdit = new RoundedButton("Edit", LIGHT_GRAY, 15);
        btnEdit.setBounds(220, 100, 150, 45);
        btnEdit.setForeground(Color.DARK_GRAY);
        btnEdit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEdit.addActionListener(e -> editDegree());
        add(btnEdit);

        JButton btnDelete = new RoundedButton("Delete", LIGHT_GRAY, 15);
        btnDelete.setBounds(390, 100, 150, 45);
        btnDelete.setForeground(Color.DARK_GRAY);
        btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete.addActionListener(e -> deleteDegree());
        add(btnDelete);

        String[] columns = {"Degree ID", "Name", "Department ID"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        degreesTable = new JTable(tableModel);
        degreesTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        degreesTable.setRowHeight(40);
        degreesTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        degreesTable.getTableHeader().setForeground(PURPLE);
        degreesTable.getTableHeader().setBackground(Color.WHITE);
        degreesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        degreesTable.setGridColor(PURPLE);

        JScrollPane scrollPane = new JScrollPane(degreesTable);
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

    public void loadDegreesData() {
        tableModel.setRowCount(0);
        List<Degree> degrees = degreeController.getAllDegrees();

        if (degrees != null) {
            for (Degree degree : degrees) {
                tableModel.addRow(new Object[]{
                    degree.getDegreeId(),
                    degree.getName() != null ? degree.getName() : "N/A",
                    degree.getDepartmentId() != null ? degree.getDepartmentId() : "N/A"
                });
            }
        }
    }

    private void addNewDegree() {
        JTextField txtName = new JTextField();
        JTextField txtDepartmentId = new JTextField();

        Object[] message = {
            "Degree Name:", txtName,
            "Department ID (optional):", txtDepartmentId
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Degree", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String name = txtName.getText().trim();
            String deptIdStr = txtDepartmentId.getText().trim();

            try {
                Integer departmentId = null;
                
                if (!deptIdStr.isEmpty()) {
                    departmentId = Integer.parseInt(deptIdStr);
                }

                boolean success = degreeController.createDegree(name, departmentId);

                if (success) {
                    loadDegreesData();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Department ID must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editDegree() {
        int selectedRow = degreesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a degree to edit!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int degreeId = (int) tableModel.getValueAt(selectedRow, 0);
        Degree degree = degreeController.getDegreeById(degreeId);

        if (degree == null) {
            return;
        }

        JTextField txtName = new JTextField(degree.getName());
        JTextField txtDepartmentId = new JTextField(degree.getDepartmentId() != null ? String.valueOf(degree.getDepartmentId()) : "");

        Object[] message = {
            "Degree Name:", txtName,
            "Department ID (optional):", txtDepartmentId
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Degree", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String name = txtName.getText().trim();
            String deptIdStr = txtDepartmentId.getText().trim();

            try {
                Integer departmentId = null;
                
                if (!deptIdStr.isEmpty()) {
                    departmentId = Integer.parseInt(deptIdStr);
                }

                boolean success = degreeController.updateDegree(degreeId, name, departmentId);

                if (success) {
                    loadDegreesData();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Department ID must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteDegree() {
        int selectedRow = degreesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a degree to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int degreeId = (int) tableModel.getValueAt(selectedRow, 0);

        boolean success = degreeController.deleteDegree(degreeId);

        if (success) {
            loadDegreesData();
        }
    }

    private void saveChanges() {
        loadDegreesData();
        JOptionPane.showMessageDialog(this, "Changes saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
