# Faculty Management System

A desktop-based Faculty Management System built with Java Swing following the MVC design pattern. This application manages students, lecturers, courses, departments, and degrees within the Computing and Technology Faculty.

## 📋 Features

### Authentication
- ✅ Role-based login (Admin, Student, Lecturer)
- ✅ User registration with role selection
- ✅ Secure password validation

### Student Module
- View and edit profile (name, ID, degree, email, mobile)
- View enrolled courses with grades
- View timetable

### Admin Module (Full CRUD)
- Manage Students (Add, View, Edit, Delete)
- Manage Lecturers (Add, View, Edit, Delete)
- Manage Courses (Add, View, Edit, Delete, Assign Lecturers)
- Manage Departments (Add, View, Edit, Delete)
- Manage Degrees (Add, View, Edit, Delete)

### Lecturer Module
- View and edit profile
- View assigned courses
- Manage course materials

## 🏗️ Architecture

This project follows the **MVC (Model-View-Controller)** design pattern:

```
com.faculty/
├── model/          # Entity classes (User, Student, Lecturer, etc.)
├── view/           # Swing GUI components
├── controller/     # Business logic handlers
├── dao/            # Database operations (CRUD)
└── util/           # Utility classes (DatabaseConnection)
```

## 🚀 Getting Started

### Prerequisites
- Java JDK 8 or higher
- MySQL Server 8.0+
- MySQL Connector/J (JDBC Driver)
- IDE (IntelliJ IDEA, Eclipse, or NetBeans)

### Database Setup

1. **Create Database:**
   ```sql
   CREATE DATABASE faculty_management_db;
   ```

2. **Run Schema:**
   - Open MySQL Workbench
   - Execute `database/schema.sql`
   
3. **Load Sample Data (Optional):**
   - Execute `database/sample_data.sql`

For detailed instructions, see [database/DATABASE_SETUP.md](database/DATABASE_SETUP.md)

### Configure Database Connection

Update credentials in `src/com/faculty/util/DatabaseConnection.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/faculty_management_db";
private static final String USERNAME = "root";
private static final String PASSWORD = "your_password";
```

### Add MySQL Connector/J

**Option 1: Manual**
1. Download MySQL Connector/J from [MySQL Downloads](https://dev.mysql.com/downloads/connector/j/)
2. Add the JAR file to your project's classpath

**Option 2: Maven**
Add to `pom.xml`:
```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

### Running the Application

1. **Compile:**
   ```bash
   javac -d bin src/com/faculty/**/*.java
   ```

2. **Run:**
   ```bash
   java -cp bin com.faculty.main.App
   ```

Or run directly from your IDE.

## 📦 Project Structure

```
faculty-management-system/
├── src/com/faculty/
│   ├── model/
│   │   ├── User.java
│   │   ├── Student.java
│   │   ├── Lecturer.java
│   │   ├── Course.java
│   │   ├── Department.java
│   │   ├── Degree.java
│   │   └── Enrollment.java
│   ├── view/
│   │   ├── LoginView.java
│   │   ├── HomeView.java
│   │   ├── StudentDashboardView.java
│   │   ├── AdminDashboardView.java
│   │   ├── RoundedButton.java
│   │   └── RoundedBorder.java
│   ├── controller/
│   │   ├── SignUpController.java
│   │   ├── LoginController.java
│   │   ├── StudentController.java
│   │   └── AdminController.java
│   ├── dao/
│   │   ├── UserDAO.java
│   │   ├── StudentDAO.java
│   │   ├── LecturerDAO.java
│   │   ├── CourseDAO.java
│   │   ├── DepartmentDAO.java
│   │   └── DegreeDAO.java
│   ├── util/
│   │   └── DatabaseConnection.java
│   └── main/
│       └── App.java
├── database/
│   ├── schema.sql
│   ├── sample_data.sql
│   └── DATABASE_SETUP.md
├── docs/
├── demo/
├── .gitignore
└── README.md
```

## 🔐 Sample Login Credentials

After loading sample data:

| Role     | Username  | Password    |
|----------|-----------|-------------|
| Admin    | admin     | admin123    |
| Student  | student1  | student123  |
| Lecturer | lecturer1 | lecturer123 |

## 📊 Database Schema

### Tables
- **users** - Authentication and role management
- **departments** - Department information
- **degrees** - Degree programs linked to departments
- **students** - Student details and profile
- **lecturers** - Lecturer details and department
- **courses** - Course information and assigned lecturers
- **enrollments** - Student-course relationships with grades

### Key Relationships
- Users → Students/Lecturers (1:1)
- Departments → Degrees (1:N)
- Degrees → Students (1:N)
- Lecturers → Courses (1:N)
- Students ↔ Courses (N:N via Enrollments)

## 🛠️ Technology Stack

- **Language:** Java 8+
- **GUI Framework:** Java Swing
- **Database:** MySQL 8.0+
- **JDBC Driver:** MySQL Connector/J
- **Design Pattern:** MVC (Model-View-Controller)

## 👥 Team

**GitHub Organization:** [FMS-Group01](https://github.com/FMS-Group01)  
**Repository:** [faculty-management-system](https://github.com/FMS-Group01/faculty-management-system)

## 📝 Development Notes

### Implemented Features ✅
- Database schema with all required tables
- User authentication system
- Sign up functionality for all roles (Admin, Student, Lecturer)
- Input validation and error handling
- Database connection management
- Model classes for all entities
- DAO layer with CRUD operations

### Pending Features 🚧
- Login functionality
- Student dashboard and profile management
- Admin CRUD operations for all entities
- Lecturer dashboard and course management
- Timetable view
- Course enrollment management

## 🤝 Contributing

This is a group assignment project. Individual contributions are tracked through Git commit history.

## 📄 License

This project is developed as part of CSCI 21052/ETEC 21062 coursework.

## 📞 Support

For issues or questions, please contact your team members or instructor.

---

**Assignment Deadline:** January 10, 2026  
**Course:** CSCI 21052/ETEC 21062  
**Institution:** Faculty of Computing & Technology
