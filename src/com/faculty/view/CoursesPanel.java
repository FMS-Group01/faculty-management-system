package com.faculty.view;

import com.faculty.controller.CourseController;
import com.faculty.model.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CoursesPanel extends JPanel {

    private JTable coursesTable;
    private DefaultTableModel tableModel;
    private CourseController courseController;
    private final Color PURPLE = new Color(138, 78, 255);

    public CoursesPanel() {
        courseController = new CourseController();
        setLayout(null);
        setBackground(Color.WHITE);
        initializeComponents();
        loadCoursesData();
    }

    private void initializeComponents() {
        JLabel titleLabel = new JLabel("Courses");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(PURPLE);
        titleLabel.setBounds(50, 30, 300, 50);
        add(titleLabel);

        JButton btnAddNew = new RoundedButton("Add new", PURPLE, 15);
        btnAddNew.setBounds(50, 100, 150, 45);
        btnAddNew.setForeground(Color.WHITE);
        btnAddNew.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAddNew.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAddNew.addActionListener(e -> addNewCourse());
        add(btnAddNew);

        JButton btnEdit = new RoundedButton("Edit", new Color(220, 220, 220), 15);
        btnEdit.setBounds(220, 100, 150, 45);
        btnEdit.setForeground(Color.DARK_GRAY);
        btnEdit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEdit.addActionListener(e -> editCourse());
        add(btnEdit);

        JButton btnDelete = new RoundedButton("Delete", new Color(220, 220, 220), 15);
        btnDelete.setBounds(390, 100, 150, 45);
        btnDelete.setForeground(Color.DARK_GRAY);
        btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete.addActionListener(e -> deleteCourse());
        add(btnDelete);

        String[] columns = {"Course ID", "Code", "Title", "Credits", "Lecturer ID"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        coursesTable = new JTable(tableModel);
        coursesTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        coursesTable.setRowHeight(40);
        coursesTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        coursesTable.getTableHeader().setForeground(PURPLE);
        coursesTable.getTableHeader().setBackground(Color.WHITE);
        coursesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        coursesTable.setGridColor(PURPLE);

        JScrollPane scrollPane = new JScrollPane(coursesTable);
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

    public void loadCoursesData() {
        tableModel.setRowCount(0);
        List<Course> courses = courseController.getAllCourses();

        if (courses != null) {
            for (Course course : courses) {
                tableModel.addRow(new Object[]{
                    course.getCourseId(),
                    course.getCode(),
                    course.getTitle() != null ? course.getTitle() : "N/A",
                    course.getCredits(),
                    course.getLecturerId() != null ? course.getLecturerId() : "N/A"
                });
            }
        }
    }

    private void addNewCourse() {
        JTextField txtCode = new JTextField();
        JTextField txtTitle = new JTextField();
        JTextField txtCredits = new JTextField();
        JTextField txtLecturerId = new JTextField();

        Object[] message = {
            "Course Code:", txtCode,
            "Title:", txtTitle,
            "Credits:", txtCredits,
            "Lecturer ID (optional):", txtLecturerId
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Course", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String code = txtCode.getText().trim();
            String title = txtTitle.getText().trim();
            String creditsStr = txtCredits.getText().trim();
            String lecturerIdStr = txtLecturerId.getText().trim();

            try {
                int credits = Integer.parseInt(creditsStr);
                Integer lecturerId = null;
                
                if (!lecturerIdStr.isEmpty()) {
                    lecturerId = Integer.parseInt(lecturerIdStr);
                }

                boolean success = courseController.createCourse(code, title, credits, lecturerId);

                if (success) {
                    loadCoursesData();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Credits and Lecturer ID must be numbers!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editCourse() {
        int selectedRow = coursesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a course to edit!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int courseId = (int) tableModel.getValueAt(selectedRow, 0);
        Course course = courseController.getCourseById(courseId);

        if (course == null) {
            return;
        }

        JTextField txtCode = new JTextField(course.getCode());
        JTextField txtTitle = new JTextField(course.getTitle());
        JTextField txtCredits = new JTextField(String.valueOf(course.getCredits()));
        JTextField txtLecturerId = new JTextField(course.getLecturerId() != null ? String.valueOf(course.getLecturerId()) : "");

        Object[] message = {
            "Course Code:", txtCode,
            "Title:", txtTitle,
            "Credits:", txtCredits,
            "Lecturer ID (optional):", txtLecturerId
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Course", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String code = txtCode.getText().trim();
            String title = txtTitle.getText().trim();
            String creditsStr = txtCredits.getText().trim();
            String lecturerIdStr = txtLecturerId.getText().trim();

            try {
                int credits = Integer.parseInt(creditsStr);
                Integer lecturerId = null;
                
                if (!lecturerIdStr.isEmpty()) {
                    lecturerId = Integer.parseInt(lecturerIdStr);
                }

                boolean success = courseController.updateCourse(courseId, code, title, credits, lecturerId);

                if (success) {
                    loadCoursesData();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Credits and Lecturer ID must be numbers!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteCourse() {
        int selectedRow = coursesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a course to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int courseId = (int) tableModel.getValueAt(selectedRow, 0);

        boolean success = courseController.deleteCourse(courseId);

        if (success) {
            loadCoursesData();
        }
    }

    private void saveChanges() {
        loadCoursesData();
        JOptionPane.showMessageDialog(this, "Changes saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
