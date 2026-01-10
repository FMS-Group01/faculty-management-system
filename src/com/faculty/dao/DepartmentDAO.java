package com.faculty.dao;

import com.faculty.model.Department;
import com.faculty.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {

    private Connection connection;

    public DepartmentDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public int createDepartment(Department dept) {
        String sql = "INSERT INTO departments (name, hod) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, dept.getName());
            pstmt.setString(2, dept.getHod());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet keys = pstmt.getGeneratedKeys()) {
                    if (keys.next()) return keys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Department getDepartmentById(Integer id) {
        String sql = "SELECT * FROM departments WHERE department_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return extractDepartment(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Department> getAllDepartments() {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT * FROM departments ORDER BY created_at DESC";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(extractDepartment(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean updateDepartment(Department dept) {
        String sql = "UPDATE departments SET name = ?, hod = ? WHERE department_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, dept.getName());
            pstmt.setString(2, dept.getHod());
            pstmt.setInt(3, dept.getDepartmentId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean deleteDepartment(Integer id) {
        String sql = "DELETE FROM departments WHERE department_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private Department extractDepartment(ResultSet rs) throws SQLException {
        return new Department(
                rs.getInt("department_id"),
                rs.getString("name"),
                rs.getString("hod"),
                rs.getTimestamp("created_at")
        );
    }
}
