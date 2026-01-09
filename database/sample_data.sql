USE faculty_management_system;

INSERT INTO users (username, password, role) VALUES ('admin', 'admin123', 'ADMIN');

INSERT INTO departments (name, hod) VALUES
('Computer Science', 'Dr. John Smith'),
('Information Technology', 'Dr. Sarah Johnson'),
('Software Engineering', 'Dr. Michael Brown');

INSERT INTO degrees (name, department_id) VALUES
('BSc Computer Science', 1),
('BSc Information Technology', 2),
('BSc Software Engineering', 3),
('MSc Computer Science', 1);
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
