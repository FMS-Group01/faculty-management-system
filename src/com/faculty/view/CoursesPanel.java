package com.faculty.view;

import com.faculty.dao.CourseDAO;
import com.faculty.dao.LecturerDAO;
import com.faculty.model.Course;
import com.faculty.model.Lecturer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CoursesPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private CourseDAO courseDAO;
    private LecturerDAO lecturerDAO;

    private final Color PURPLE = new Color(138, 78, 255);
    private final Color LIGHT_GRAY = new Color(220, 220, 220);

    public CoursesPanel() {
        courseDAO = new CourseDAO();
        lecturerDAO = new LecturerDAO();

        setLayout(null);
        setBackground(Color.WHITE);

        initializeComponents();
        loadCoursesData();
    }

    private void initializeComponents() {
        JLabel titleLabel = new JLabel("Courses");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(PURPLE);
        titleLabel.setBounds(50, 30, 400, 50);
        add(titleLabel);

        JButton btnAdd = new RoundedButton("Add new", PURPLE, 15);
        btnAdd.setBounds(50, 100, 150, 45);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.addActionListener(e -> addCourse());
        add(btnAdd);

        JButton btnEdit = new RoundedButton("Edit", LIGHT_GRAY, 15);
        btnEdit.setBounds(220, 100, 150, 45);
        btnEdit.setForeground(Color.DARK_GRAY);
        btnEdit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEdit.addActionListener(e -> editCourse());
        add(btnEdit);

        JButton btnDelete = new RoundedButton("Delete", LIGHT_GRAY, 15);
        btnDelete.setBounds(390, 100, 150, 45);
        btnDelete.setForeground(Color.DARK_GRAY);
        btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete.addActionListener(e -> deleteCourse());
        add(btnDelete);

        String[] columns = {"Course ID", "Code", "Title", "Credits", "Lecturer"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(40);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setForeground(PURPLE);
        table.getTableHeader().setBackground(Color.WHITE);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setGridColor(PURPLE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 170, 700, 350);
        scrollPane.setBorder(BorderFactory.createLineBorder(PURPLE, 2));
        add(scrollPane);

        JButton btnSave = new RoundedButton("Save changes", PURPLE, 15);
        btnSave.setBounds(250, 550, 300, 50);
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.addActionListener(e -> saveChanges());
        add(btnSave);
    }

    public void loadCoursesData() {
        tableModel.setRowCount(0);
        List<Course> courses = courseDAO.getAllCourses();

        for (Course c : courses) {
            Lecturer lecturer = (c.getLecturerId() != null) ? lecturerDAO.getLecturerById(c.getLecturerId()) : null;
            String lecturerName = (lecturer != null) ? lecturer.getName() : "N/A";

            tableModel.addRow(new Object[]{
                    c.getCourseId(),
                    c.getCode(),
                    c.getTitle(),
                    c.getCredits(),
                    lecturerName
            });
        }
    }

    private void addCourse() {
        JTextField txtCode = new JTextField();
        JTextField txtTitle = new JTextField();
        JTextField txtCredits = new JTextField();
        JComboBox<String> lecturerCombo = new JComboBox<>();

        List<Lecturer> lecturers = lecturerDAO.getAllLecturers();
        lecturerCombo.addItem("N/A");
        for (Lecturer l : lecturers) {
            lecturerCombo.addItem(l.getLecturerId() + " - " + l.getName());
        }

        Object[] message = {
                "Course Code:", txtCode,
                "Title:", txtTitle,
                "Credits:", txtCredits,
                "Lecturer:", lecturerCombo
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Course", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String code = txtCode.getText().trim();
            String title = txtTitle.getText().trim();
            int credits;
            try {
                credits = Integer.parseInt(txtCredits.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Credits must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Integer lecturerId = null;
            if (lecturerCombo.getSelectedIndex() > 0) {
                String selected = (String) lecturerCombo.getSelectedItem();
                lecturerId = Integer.parseInt(selected.split(" - ")[0]);
            }

            Course course = new Course(code, title, credits, lecturerId);
            int id = courseDAO.createCourse(course);
            if (id > 0) {
                JOptionPane.showMessageDialog(this, "Course added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadCoursesData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add course!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editCourse() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a course to edit!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer courseId = (Integer) tableModel.getValueAt(selectedRow, 0);
        Course course = courseDAO.getCourseById(courseId);
        if (course == null) {
            JOptionPane.showMessageDialog(this, "Course not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField txtCode = new JTextField(course.getCode());
        JTextField txtTitle = new JTextField(course.getTitle());
        JTextField txtCredits = new JTextField(String.valueOf(course.getCredits()));
        JComboBox<String> lecturerCombo = new JComboBox<>();

        List<Lecturer> lecturers = lecturerDAO.getAllLecturers();
        lecturerCombo.addItem("N/A");
        int selectedIndex = 0;
        int i = 1;
        for (Lecturer l : lecturers) {
            lecturerCombo.addItem(l.getLecturerId() + " - " + l.getName());
            if (course.getLecturerId() != null && l.getLecturerId().equals(course.getLecturerId())) {
                selectedIndex = i;
            }
            i++;
        }
        lecturerCombo.setSelectedIndex(selectedIndex);

        Object[] message = {
                "Course Code:", txtCode,
                "Title:", txtTitle,
                "Credits:", txtCredits,
                "Lecturer:", lecturerCombo
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Course", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            course.setCode(txtCode.getText().trim());
            course.setTitle(txtTitle.getText().trim());
            try {
                course.setCredits(Integer.parseInt(txtCredits.getText().trim()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Credits must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (lecturerCombo.getSelectedIndex() > 0) {
                String selected = (String) lecturerCombo.getSelectedItem();
                course.setLecturerId(Integer.parseInt(selected.split(" - ")[0]));
            } else {
                course.setLecturerId(null);
            }

            boolean success = courseDAO.updateCourse(course);
            if (success) {
                JOptionPane.showMessageDialog(this, "Course updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadCoursesData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update course!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteCourse() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a course to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer courseId = (Integer) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this course?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = courseDAO.deleteCourse(courseId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Course deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadCoursesData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete course!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveChanges() {
        loadCoursesData();
        JOptionPane.showMessageDialog(this, "Changes saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
