# BL_s3JavaFinalSprint

The Health Monitoring App tracks health data, offers recommendations, and manages medicine reminders for users, including patients and doctors.

Classes:

    User:
        Basic user info.
        Subclass: "Doctor" with medical details.

    HealthData:
        Records health metrics.
        Linked to a user.

    MedicineReminder:
        Manages medicine alerts.
        Includes medicine details and schedule.

    UserDao:
        Handles user data operations.

    HealthDataDao:
        Manages health data operations.

    MedicineReminderDao:
        Controls medicine reminder operations.

    RecommendationSystem:
        Generates health recommendations.

    HealthMonitoringApp:
        Main app class.
        Manages interactions and developer functions.

How to Start/Access:

    Run Application:
        Execute HealthMonitoringApp.
        GUI or CLI options.

    Developer Functions:
        Manually add users, health data, reminders, and generate recommendations.

    Login:
        Simulate user logins with provided functions.

Associations:

    User-Doctor: Inheritance.
    HealthData-User: Composition.
    MedicineReminder-User: Composition.
    Dao Classes: Used for database operations.

Database:

    Database Type:
        Supports relational databases (e.g., MySQL, PostgreSQL).

    Configuration:
        Configure database connection in DatabaseConnection.java.

    Schema:
        Refer to database schema creation scripts.


Compiler Time Dependencies:

    MindRot BCrypt Library:
        Required for password hashing.
        Include in lib/ or manage via a build tool.

System Requirements:

    Java Runtime Environment (JRE):
        Ensure Java 8 or later is installed.

    Database:
        Set up a relational database (e.g., MySQL, PostgreSQL).

 Database Configuration:

    Database Connection:
        Open DatabaseConnection.java.

    Edit Configuration:
        Modify database URL, username, and password.

Features

    Registration:
        Access the application and register a new user.

    Login:
        Log in with the registered user credentials.

    Health Data Entry:
        Add health data for the logged-in user.

    Medicine Reminders:
        Optionally, set up medicine reminders.

GUI Mode:

    If using GUI, ensure a graphical environment is available.

Developer Functions (Optional):

    Developer functions can be accessed based on requirements.

