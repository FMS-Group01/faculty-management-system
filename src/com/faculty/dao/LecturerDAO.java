package com.faculty.dao;

import com.faculty.model.Lecturer;
import com.faculty.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LecturerDAO {
    
    private Connection connection;
    
    public LecturerDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    public int createLecturer(Lecturer lecturer) {
        String sql = "INSERT INTO lecturers (user_id, name, email, mobile, department_id) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, lecturer.getUserId());
            pstmt.setString(2, lecturer.getName());
            pstmt.setString(3, lecturer.getEmail());
            pstmt.setString(4, lecturer.getMobile());
            
            if (lecturer.getDepartmentId() != null) {
                pstmt.setInt(5, lecturer.getDepartmentId());
            } else {
                pstmt.setNull(5, Types.INTEGER);
            }
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating lecturer: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }
    
    public Lecturer getLecturerById(int lecturerId) {
        String sql = "SELECT * FROM lecturers WHERE lecturer_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, lecturerId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractLecturerFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting lecturer by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public Lecturer getLecturerByUserId(int userId) {
        String sql = "SELECT * FROM lecturers WHERE user_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractLecturerFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting lecturer by user ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Lecturer> getAllLecturers() {
        List<Lecturer> lecturers = new ArrayList<>();
        String sql = "SELECT * FROM lecturers ORDER BY created_at DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                lecturers.add(extractLecturerFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all lecturers: " + e.getMessage());
            e.printStackTrace();
        }
        return lecturers;
    }
    
    public boolean updateLecturer(Lecturer lecturer) {
        String sql = "UPDATE lecturers SET name = ?, email = ?, mobile = ?, department_id = ? WHERE lecturer_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, lecturer.getName());
            pstmt.setString(2, lecturer.getEmail());
            pstmt.setString(3, lecturer.getMobile());
            
            if (lecturer.getDepartmentId() != null) {
                pstmt.setInt(4, lecturer.getDepartmentId());
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }
            
            pstmt.setInt(5, lecturer.getLecturerId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating lecturer: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteLecturer(int lecturerId) {
        String sql = "DELETE FROM lecturers WHERE lecturer_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, lecturerId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting lecturer: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM lecturers WHERE email = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking email existence: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    private Lecturer extractLecturerFromResultSet(ResultSet rs) throws SQLException {
        Integer departmentId = rs.getInt("department_id");
        if (rs.wasNull()) {
            departmentId = null;
        }
        
        return new Lecturer(
            rs.getInt("lecturer_id"),
            rs.getInt("user_id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("mobile"),
            departmentId,
            rs.getTimestamp("created_at")
        );
    }
}
