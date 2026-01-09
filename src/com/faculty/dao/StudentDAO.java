package com.faculty.dao;

import com.faculty.model.Student;
import com.faculty.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentDAO - Data Access Object for Student operations
 * Handles all database operations related to students
 */
public class StudentDAO {
    
    private Connection connection;
    
    public StudentDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    /**
     * Create a new student in the database
     * @param student Student object to create
     * @return true if creation successful, false otherwise
     */
    public boolean createStudent(Student student) {
        String sql = "INSERT INTO students (student_id, user_id, name, email, mobile, degree_id) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, student.getStudentId());
            pstmt.setInt(2, student.getUserId());
            pstmt.setString(3, student.getName());
            pstmt.setString(4, student.getEmail());
            pstmt.setString(5, student.getMobile());
            
            if (student.getDegreeId() != null) {
                pstmt.setInt(6, student.getDegreeId());
            } else {
                pstmt.setNull(6, Types.INTEGER);
            }
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error creating student: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Get student by student ID
     * @param studentId student ID to search for
     * @return Student object or null if not found
     */
    public Student getStudentById(String studentId) {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractStudentFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting student by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Get student by user ID
     * @param userId user ID to search for
     * @return Student object or null if not found
     */
    public Student getStudentByUserId(int userId) {
        String sql = "SELECT * FROM students WHERE user_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractStudentFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting student by user ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Get all students
     * @return List of all students
     */
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY created_at DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                students.add(extractStudentFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all students: " + e.getMessage());
            e.printStackTrace();
        }
        return students;
    }
    
    /**
     * Update student information
     * @param student Student object with updated information
     * @return true if update successful, false otherwise
     */
    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET name = ?, email = ?, mobile = ?, degree_id = ? WHERE student_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setString(3, student.getMobile());
            
            if (student.getDegreeId() != null) {
                pstmt.setInt(4, student.getDegreeId());
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }
            
            pstmt.setString(5, student.getStudentId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Delete student by student ID
     * @param studentId student ID to delete
     * @return true if deletion successful, false otherwise
     */
    public boolean deleteStudent(String studentId) {
        String sql = "DELETE FROM students WHERE student_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Check if student ID already exists
     * @param studentId student ID to check
     * @return true if exists, false otherwise
     */
    public boolean studentIdExists(String studentId) {
        String sql = "SELECT COUNT(*) FROM students WHERE student_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking student ID existence: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Extract Student object from ResultSet
     * @param rs ResultSet containing student data
     * @return Student object
     * @throws SQLException if error occurs while reading ResultSet
     */
    private Student extractStudentFromResultSet(ResultSet rs) throws SQLException {
        Integer degreeId = rs.getInt("degree_id");
        if (rs.wasNull()) {
            degreeId = null;
        }
        
        return new Student(
            rs.getString("student_id"),
            rs.getInt("user_id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("mobile"),
            degreeId,
            rs.getTimestamp("created_at")
        );
    }
}
