# Quick Setup Guide - Faculty Management System

## 🚀 Quick Start (5 Minutes)

### Step 1: Setup Database (2 minutes)

1. Open MySQL Workbench
2. Connect to your MySQL server
3. Run these commands in SQL Editor:

```sql
-- Create database
CREATE DATABASE faculty_management_db;
USE faculty_management_db;
```

4. Open and execute `database/schema.sql`
5. (Optional) Open and execute `database/sample_data.sql` for test data

### Step 2: Configure Database Connection (1 minute)

Open `src/com/faculty/util/DatabaseConnection.java` and update:

```java
private static final String URL = "jdbc:mysql://localhost:3306/faculty_management_db";
private static final String USERNAME = "root";
private static final String PASSWORD = "your_mysql_password";
```

### Step 3: Add MySQL JDBC Driver (1 minute)

**If using IDE (IntelliJ/Eclipse):**
1. Download MySQL Connector/J from: https://dev.mysql.com/downloads/connector/j/
2. Add the JAR file to your project libraries
3. In IntelliJ: File → Project Structure → Libraries → + → Java → Select the JAR
4. In Eclipse: Right-click project → Build Path → Add External Archives → Select the JAR

### Step 4: Run the Application (1 minute)

1. Open `src/com/faculty/main/App.java` in your IDE
2. Click Run
3. The login window should appear

## ✅ Testing Sign Up Functionality

### Test Case 1: Admin Sign Up
1. Click "Sign Up" tab
2. Enter:
   - Username: `testadmin`
   - Password: `admin123`
   - Confirm Password: `admin123`
   - Role: Select "Admin"
3. Click "Sign Up"
4. ✅ Success message should appear

### Test Case 2: Student Sign Up
1. Click "Sign Up" tab
2. Enter:
   - Username: `teststudent`
   - Password: `student123`
   - Confirm Password: `student123`
   - Role: Select "Student"
3. Click "Sign Up"
4. Enter Student ID: `2024CS999`
5. ✅ Success message should appear

### Test Case 3: Lecturer Sign Up
1. Click "Sign Up" tab
2. Enter:
   - Username: `testlecturer`
   - Password: `lecturer123`
   - Confirm Password: `lecturer123`
   - Role: Select "Lecturer"
3. Click "Sign Up"
4. Enter:
   - Name: `Dr. Test Lecturer`
   - Email: `test.lecturer@university.edu`
5. ✅ Success message should appear

## 🔍 Verify in Database

After signing up, verify in MySQL Workbench:

```sql
-- Check users table
SELECT * FROM users WHERE username LIKE 'test%';

-- Check students table (for student sign ups)
SELECT * FROM students WHERE student_id = '2024CS999';

-- Check lecturers table (for lecturer sign ups)
SELECT * FROM lecturers WHERE email = 'test.lecturer@university.edu';
```

## 🐛 Common Issues & Solutions

### Issue 1: "MySQL JDBC Driver not found"
**Solution:** 
- Download MySQL Connector/J
- Add to project classpath/libraries
- Restart IDE

### Issue 2: "Failed to establish database connection"
**Solution:**
- Verify MySQL server is running
- Check username/password in DatabaseConnection.java
- Ensure database `faculty_management_db` exists
- Check MySQL port (default: 3306)

### Issue 3: "Username already exists"
**Solution:**
- This is correct behavior! Try a different username
- Or delete the user from database:
  ```sql
  DELETE FROM users WHERE username = 'testuser';
  ```

### Issue 4: Compilation errors
**Solution:**
- Ensure all Java files are in correct packages
- Rebuild project: Build → Rebuild Project
- Check JDK version (Java 8+)

## 📝 What's Implemented

✅ **Complete Database Schema**
- All 7 tables with relationships
- Indexes for performance
- Sample data for testing

✅ **Sign Up Functionality**
- Admin sign up
- Student sign up (with student ID)
- Lecturer sign up (with name and email)
- Password validation (min 6 characters)
- Username validation (min 3 characters)
- Password confirmation check
- Duplicate username check
- Duplicate student ID check
- Duplicate email check (for lecturers)

✅ **Model Layer (POJOs)**
- User.java
- Student.java
- Lecturer.java

✅ **DAO Layer (Database Operations)**
- UserDAO.java (CRUD operations)
- StudentDAO.java (CRUD operations)
- LecturerDAO.java (CRUD operations)

✅ **Controller Layer**
- SignUpController.java (business logic)

✅ **View Layer**
- LoginView.java (with Sign Up UI)
- Connected to controller

## 🚧 Next Steps

To complete the assignment, you need to implement:
1. Login functionality (LoginController)
2. Student Dashboard
3. Admin Dashboard with CRUD operations
4. Lecturer Dashboard
5. Additional DAO classes (Course, Department, Degree, Enrollment)
6. Additional Model classes (Course, Department, Degree, Enrollment)

## 📞 Need Help?

Check these files:
- Database setup: `database/DATABASE_SETUP.md`
- Full documentation: `README.md`
- Sample data: `database/sample_data.sql`

---

**Ready to test?** Follow the steps above and start with Test Case 1! 🎉
