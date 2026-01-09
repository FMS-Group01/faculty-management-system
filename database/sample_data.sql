-- Sample Data for Faculty Management System
-- Run this after executing schema.sql

-- Insert sample departments
INSERT INTO departments (name, hod) VALUES
('Computer Science', 'Dr. John Smith'),
('Information Technology', 'Dr. Sarah Johnson'),
('Software Engineering', 'Dr. Michael Brown');

-- Insert sample degrees
INSERT INTO degrees (name, department_id) VALUES
('BSc Computer Science', 1),
('BSc Information Technology', 2),
('BSc Software Engineering', 3),
('MSc Computer Science', 1);

-- Insert sample admin user (username: admin, password: admin123)
INSERT INTO users (username, password, role) VALUES
('admin', 'admin123', 'ADMIN');

-- Insert sample student users (username: student1, password: student123)
INSERT INTO users (username, password, role) VALUES
('student1', 'student123', 'STUDENT'),
('student2', 'student123', 'STUDENT'),
('student3', 'student123', 'STUDENT');

-- Insert sample students
INSERT INTO students (student_id, user_id, name, email, mobile, degree_id) VALUES
('2021CS001', 2, 'Alice Johnson', 'alice.johnson@university.edu', '0771234567', 1),
('2021IT002', 3, 'Bob Williams', 'bob.williams@university.edu', '0772345678', 2),
('2021SE003', 4, 'Charlie Davis', 'charlie.davis@university.edu', '0773456789', 3);

-- Insert sample lecturer users (username: lecturer1, password: lecturer123)
INSERT INTO users (username, password, role) VALUES
('lecturer1', 'lecturer123', 'LECTURER'),
('lecturer2', 'lecturer123', 'LECTURER'),
('lecturer3', 'lecturer123', 'LECTURER');

-- Insert sample lecturers
INSERT INTO lecturers (user_id, name, email, mobile, department_id) VALUES
(5, 'Dr. Emily Martinez', 'emily.martinez@university.edu', '0774567890', 1),
(6, 'Prof. David Anderson', 'david.anderson@university.edu', '0775678901', 2),
(7, 'Dr. Lisa Taylor', 'lisa.taylor@university.edu', '0776789012', 3);

-- Insert sample courses
INSERT INTO courses (code, title, credits, lecturer_id) VALUES
('CS101', 'Introduction to Programming', 3, 1),
('CS102', 'Data Structures and Algorithms', 4, 1),
('IT201', 'Database Management Systems', 3, 2),
('IT202', 'Web Development', 3, 2),
('SE301', 'Software Engineering Principles', 4, 3),
('SE302', 'Agile Development', 3, 3);

-- Insert sample enrollments
INSERT INTO enrollments (student_id, course_id, grade) VALUES
('2021CS001', 1, 'A'),
('2021CS001', 2, 'B+'),
('2021CS001', 3, 'A-'),
('2021IT002', 3, 'A'),
('2021IT002', 4, 'B'),
('2021SE003', 5, 'A-'),
('2021SE003', 6, 'B+');
