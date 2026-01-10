package com.faculty.dao;

import com.faculty.model.Course;
import com.faculty.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    private Connection connection;

    public CourseDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public int createCourse(Course course) {
        String sql = "INSERT INTO courses (code, title, credits, lecturer_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, course.getCode());
            pstmt.setString(2, course.getTitle());
            pstmt.setInt(3, course.getCredits());

            if (course.getLecturerId() != null) {
                pstmt.setInt(4, course.getLecturerId());
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet keys = pstmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating course: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    public Course getCourseById(Integer courseId) {
        String sql = "SELECT * FROM courses WHERE course_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, courseId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractCourse(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses ORDER BY created_at DESC";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                courses.add(extractCourse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public boolean updateCourse(Course course) {
        String sql = "UPDATE courses SET code = ?, title = ?, credits = ?, lecturer_id = ? WHERE course_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, course.getCode());
            pstmt.setString(2, course.getTitle());
            pstmt.setInt(3, course.getCredits());

            if (course.getLecturerId() != null) {
                pstmt.setInt(4, course.getLecturerId());
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }

            pstmt.setInt(5, course.getCourseId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCourse(Integer courseId) {
        String sql = "DELETE FROM courses WHERE course_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, courseId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Course extractCourse(ResultSet rs) throws SQLException {
        Integer lecturerId = rs.getInt("lecturer_id");
        if (rs.wasNull()) lecturerId = null;

        return new Course(
                rs.getInt("course_id"),
                rs.getString("code"),
                rs.getString("title"),
                rs.getInt("credits"),
                lecturerId,
                rs.getTimestamp("created_at")
        );
    }
}

