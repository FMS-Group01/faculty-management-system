package com.faculty.controller;

import com.faculty.dao.CourseDAO;
import com.faculty.dao.LecturerDAO;
import com.faculty.model.Course;
import com.faculty.model.Lecturer;

import javax.swing.*;
import java.util.List;

public class CourseController {
    
    private CourseDAO courseDAO;
    private LecturerDAO lecturerDAO;
    
    public CourseController() {
        this.courseDAO = new CourseDAO();
        this.lecturerDAO = new LecturerDAO();
    }
    
    public List<Course> getAllCourses() {
        try {
            return courseDAO.getAllCourses();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading courses: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public boolean createCourse(String code, String title, Integer credits, Integer lecturerId) {
        if (code == null || code.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Course code is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (title == null || title.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Course title is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (credits == null || credits <= 0) {
            JOptionPane.showMessageDialog(null, "Credits must be greater than 0!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validate lecturer exists if provided
        if (lecturerId != null) {
            try {
                Lecturer lecturer = lecturerDAO.getLecturerById(lecturerId);
                if (lecturer == null) {
                    JOptionPane.showMessageDialog(null, "Lecturer ID " + lecturerId + " does not exist! Please create the lecturer first or leave blank.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error validating lecturer: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        try {
            Course course = new Course(code, title, credits, lecturerId);
            int courseId = courseDAO.createCourse(course);
            
            if (courseId > 0) {
                JOptionPane.showMessageDialog(null, "Course added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add course!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error creating course: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean updateCourse(int courseId, String code, String title, Integer credits, Integer lecturerId) {
        // Validate lecturer exists if provided
        if (lecturerId != null) {
            try {
                Lecturer lecturer = lecturerDAO.getLecturerById(lecturerId);
                if (lecturer == null) {
                    JOptionPane.showMessageDialog(null, "Lecturer ID " + lecturerId + " does not exist! Please create the lecturer first or leave blank.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error validating lecturer: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        try {
            Course course = courseDAO.getCourseById(courseId);
            
            if (course == null) {
                JOptionPane.showMessageDialog(null, "Course not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            course.setCode(code);
            course.setTitle(title);
            course.setCredits(credits);
            course.setLecturerId(lecturerId);
            
            boolean success = courseDAO.updateCourse(course);
            
            if (success) {
                JOptionPane.showMessageDialog(null, "Course updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update course!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error updating course: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean deleteCourse(int courseId) {
        int confirm = JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to delete this course?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return false;
        }
        
        try {
            boolean success = courseDAO.deleteCourse(courseId);
            
            if (success) {
                JOptionPane.showMessageDialog(null, "Course deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete course!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error deleting course: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public Course getCourseById(int courseId) {
        try {
            return courseDAO.getCourseById(courseId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error retrieving course: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
