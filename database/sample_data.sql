-- Sample Data for Faculty Management System

USE faculty_management_system;

-- Insert Users
INSERT INTO users (username, password, role) VALUES
('admin', 'admin123', 'ADMIN'),
('jdoe', 'pass123', 'STUDENT'),
('asmith', 'pass123', 'STUDENT'),
('mjohnson', 'pass123', 'STUDENT'),
('drbrown', 'prof123', 'LECTURER'),
('drwilson', 'prof123', 'LECTURER'),
('drdavis', 'prof123', 'LECTURER');

-- Insert Departments
INSERT INTO departments (name, hod) VALUES
('Computer Science', 'Dr. Robert Brown'),
('Software Engineering', 'Dr. Emily Wilson'),
('Information Technology', 'Dr. Michael Davis');

-- Insert Degrees
INSERT INTO degrees (name, department_id) VALUES
('BSc Computer Science', 1),
('BSc Software Engineering', 2),
('BSc Information Technology', 3),
('MSc Computer Science', 1),
('MSc Software Engineering', 2);

-- Insert Students
INSERT INTO students (student_id, user_id, name, email, mobile, degree_id) VALUES
('CS2021001', 2, 'John Doe', 'john.doe@student.ac.lk', '0771234567', 1),
('SE2021002', 3, 'Alice Smith', 'alice.smith@student.ac.lk', '0772345678', 2),
('IT2021003', 4, 'Mark Johnson', 'mark.johnson@student.ac.lk', '0773456789', 3);

-- Insert Lecturers
INSERT INTO lecturers (user_id, name, email, mobile, department_id) VALUES
(5, 'Dr. Robert Brown', 'robert.brown@faculty.ac.lk', '0711234567', 1),
(6, 'Dr. Emily Wilson', 'emily.wilson@faculty.ac.lk', '0712345678', 2),
(7, 'Dr. Michael Davis', 'michael.davis@faculty.ac.lk', '0713456789', 3);

-- Insert Courses
INSERT INTO courses (code, title, credits, lecturer_id) VALUES
('CS101', 'Introduction to Programming', 3, 1),
('CS102', 'Data Structures and Algorithms', 4, 1),
('SE101', 'Software Engineering Principles', 3, 2),
('SE102', 'Object-Oriented Programming', 4, 2),
('IT101', 'Database Management Systems', 3, 3),
('IT102', 'Web Development', 3, 3),
('CS201', 'Computer Networks', 3, 1),
('SE201', 'Software Testing', 3, 2);

-- Insert Enrollments
INSERT INTO enrollments (student_id, course_id, grade) VALUES
('CS2021001', 1, 'A'),
('CS2021001', 2, 'A-'),
('CS2021001', 7, 'B+'),
('SE2021002', 3, 'A+'),
('SE2021002', 4, 'A'),
('SE2021002', 8, 'A-'),
('IT2021003', 5, 'B+'),
('IT2021003', 6, 'A');

-- Display summary
SELECT 'Sample data inserted successfully!' AS Status;
SELECT COUNT(*) AS Total_Users FROM users;
SELECT COUNT(*) AS Total_Departments FROM departments;
SELECT COUNT(*) AS Total_Degrees FROM degrees;
SELECT COUNT(*) AS Total_Students FROM students;
SELECT COUNT(*) AS Total_Lecturers FROM lecturers;
SELECT COUNT(*) AS Total_Courses FROM courses;
SELECT COUNT(*) AS Total_Enrollments FROM enrollments;
