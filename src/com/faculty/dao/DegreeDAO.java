package com.faculty.dao;

import com.faculty.model.Degree;
import com.faculty.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DegreeDAO {
    
    public DegreeDAO() {
    }

    public int createDegree(Degree degree) {
        String query = "INSERT INTO degrees (name, department_id) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, degree.getName());
            if (degree.getDepartmentId() != null) {
                pstmt.setInt(2, degree.getDepartmentId());
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
            return 0;
            
        } catch (SQLException e) {
            System.err.println("Error creating degree: " + e.getMessage());
            return 0;
        }
    }
    
    public List<Degree> getAllDegrees() {
        List<Degree> degrees = new ArrayList<>();
        String query = "SELECT * FROM degrees ORDER BY degree_id";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Degree degree = new Degree();
                degree.setDegreeId(rs.getInt("degree_id"));
                degree.setName(rs.getString("name"));
                
                int deptId = rs.getInt("department_id");
                degree.setDepartmentId(rs.wasNull() ? null : deptId);
                
                degrees.add(degree);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all degrees: " + e.getMessage());
        }
        
        return degrees;
    }

    public Degree getDegreeById(int degreeId) {
        String query = "SELECT * FROM degrees WHERE degree_id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, degreeId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Degree degree = new Degree();
                degree.setDegreeId(rs.getInt("degree_id"));
                degree.setName(rs.getString("name"));
                
                int deptId = rs.getInt("department_id");
                degree.setDepartmentId(rs.wasNull() ? null : deptId);
                
                return degree;
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting degree by ID: " + e.getMessage());
        }
        
        return null;
    }

    public boolean updateDegree(Degree degree) {
        String query = "UPDATE degrees SET name = ?, department_id = ? WHERE degree_id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, degree.getName());
            if (degree.getDepartmentId() != null) {
                pstmt.setInt(2, degree.getDepartmentId());
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }
            pstmt.setInt(3, degree.getDegreeId());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating degree: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteDegree(int degreeId) {
        String query = "DELETE FROM degrees WHERE degree_id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, degreeId);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting degree: " + e.getMessage());
            return false;
        }
    }

    public Integer getDegreeIdByName(String degreeName) {
        if (degreeName == null || degreeName.trim().isEmpty()) {
            return null;
        }
        
        String query = "SELECT degree_id FROM degrees WHERE TRIM(LOWER(name)) = TRIM(LOWER(?))";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, degreeName.trim());
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("degree_id");
            }
            return null;
            
        } catch (SQLException e) {
            System.err.println("Error getting degree ID: " + e.getMessage());
            return null;
        }
    }

    public String getDegreeNameById(Integer degreeId) {
        if (degreeId == null) {
            return null;
        }
        
        String query = "SELECT name FROM degrees WHERE degree_id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, degreeId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("name");
            }
            return null;
            
        } catch (SQLException e) {
            System.err.println("Error getting degree name: " + e.getMessage());
            return null;
        }
    }
}
