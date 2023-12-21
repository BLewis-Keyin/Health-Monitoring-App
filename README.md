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

Class Diagram:

Associations:

    User-Doctor: Inheritance.
    HealthData-User: Composition.
    MedicineReminder-User: Composition.
    Dao Classes: Used for database operations.
