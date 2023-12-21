
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import javax.swing.SwingUtilities;
import java.sql.Timestamp;
import java.util.Scanner;
import java.util.InputMismatchException;


public class HealthMonitoringApp {
    static Scanner scanner = new Scanner(System.in);
    public UserDao getUserDao() {
        return userDao;
    }

    public HealthDataDao getHealthDataDao() {
        return HealthDataDao;
    }
    
    public MedicineReminderDao MedicineReminderDao() {
        return medicineReminderDao;
    }
    public static UserDao userDao = new UserDao();
    public static HealthDataDao HealthDataDao = new HealthDataDao();
    public static MedicineReminderDao medicineReminderDao = new MedicineReminderDao();

    /**
     * Test the following functionalities within the Main Application
     *  1. Register a new user
     *  2. Log in the user
     *  3. Add health data
     *  4. Generate recommendations
     *  5. Add a medicine reminder
     *  6. Get reminders for a specific user
     *  7. Get due reminders for a specific user
     *  8. test doctor portal
     */
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        HealthMonitoringApp healthApp = new HealthMonitoringApp();
        

        //  // --- BATCH TESTS (Uncomment if you want to run them without GUI)

        //healthApp.testUserRegistration(userDao);
        //healthApp.testLoginUser();
        //healthApp.testDoctorPortal();
        //healthApp.testAddHealthData(userDao, HealthDataDao);
        //healthApp.testGenerateRecommendations(userDao, HealthDataDao);
        //healthApp.testMedicineReminders(new MedicineReminderDao(), new MedicineReminderManager(new MedicineReminderDao()));
        //healthApp.testGetRemindersForUser();


        SwingUtilities.invokeLater(() -> {
            GUI GUI = new GUI();
            GUI.initialize();
            GUI.showMainFrame();
        });
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (scanner != null) {
                scanner.close();;
            }
        }));
    }
    

    // TESTS ---------------------------------------------

    // Test user registration
   public static void testUserRegistration(UserDao userDao) {
        System.out.println("----------------------------\nTesting user registration...");
        System.out.println("----------------------------");
        List<User> userList = new ArrayList<>();
        User user1 = new User(5, "Ainee Malik", "qmalik@gmail.com", "guggu", false, 1);
        User user2 = new User(6, "Frank Milicek", "fmilicek@gmail.com", "12345", false, 1);
        if (!userDao.userExistsEmail(user1.getEmail())) {
            userList.add(user1);
        } else {
            System.out.println("User with email " + user1.getEmail() + " already exists");
        }

        if (!userDao.userExistsEmail(user2.getEmail())) {
            userList.add(user2);
        } else {
            System.out.println("User with email " + user2.getEmail() + " already exists");
        }
        for (User user : userList) {
            userDao.createUser(user);
        }
        System.out.println("User registration test complete\n----------------------------");
    }


    // Test doctor portal
    public static void testDoctorPortal() {
        System.out.println("Testing doctor portal...");
        System.out.println("----------------------------");
        int doctorId = 1;
        UserDao userDao = new UserDao();
        Doctor doctor = userDao.getDoctorById(doctorId);
        if (doctor != null) {
            System.out.println("Doctor:");
            System.out.println("ID: " + doctor.getId());
            System.out.println("Name: " + doctor.getName());
            System.out.println("License Number: " + doctor.getLicenseNumber());
            System.out.println("Specialization: " + doctor.getSpecialization());
        } else {
            System.out.println("No doctors found");
        }
        HealthDataDao healthDataDao = new HealthDataDao();
        List<User> patients = userDao.getPatientsByDoctorId(doctorId);
        if (!patients.isEmpty()) {
            System.out.println("   Patients:");
            for (User patient : patients) {
                System.out.print("    ID: " + patient.getId() + ", Name: " + patient.getName());
                List<HealthData> healthDataList = healthDataDao.getHealthDataByUserId(patient.getId());
                if (!healthDataList.isEmpty()) {
                    System.out.print(", Health data:");
                    for (HealthData healthData : healthDataList) {
                        System.out.print(" Date: " + healthData.getDate() + ", Weight: " + healthData.getWeight()
                                + ", Heart Rate: " + healthData.getHeartRate());
                    System.out.println(); 
                    }
                } else {
                    System.out.print(", No health data found");
                }
            }
        } else {
            System.out.println("No patients found.");
        }
        System.out.println("Doctor portal test complete\n----------------------------");
    }


    // Test login
    public static void testLoginUser() {
        System.out.println("Testing login...");
        System.out.println("----------------------------");
        String user1Email = "qmalik@gmail.com";
        String user1Password = "guggu";
        String user2Email = "fmilicek@gmail.com";
        String user2Password = "4321";

        //user1 Should be able to login
        System.out.println("Testing " + user1Email + " with password: " + user1Password);
        boolean loginSuccess1 = loginUser(user1Email, user1Password);
        if (loginSuccess1) {
            System.out.println("Login successful");

        } else {
            System.out.println("Incorrect email or password. Please try again.\n");
        }
        //user 2 should not be able to login
        System.out.println("Testing " + user2Email + " with password: " + user2Password);
        boolean loginSuccess2 = loginUser(user2Email, user2Password);
        if (loginSuccess2) {
            System.out.println("Login successful");
        } else {
            System.out.println("Incorrect email or password. Please try again.");

        }

        System.out.println("Login test complete\n----------------------------");
    }


    // Test adding health data
    public static void testAddHealthData(UserDao userDao, HealthDataDao healthDataDao) {
        int userId = 2;

        System.out.println("Testing adding health data...");
        System.out.println("----------------------------");

        HealthData healthData = new HealthData();
        healthData.setUserId(userId);
        healthData.setWeight(70.5); 
        healthData.setHeight(175.0); 
        healthData.setSteps(10000);
        healthData.setHeartRate(75); 
        healthData.setDate("2023-12-15");
        boolean success = healthDataDao.addHealthData(userId, healthData);

        if (success) {
            System.out.println("Health data added successfully");
        } else {
            System.out.println("Failed to add health data");
        }

        System.out.println("Adding health data test complete\n----------------------------");
    }


    // Test generate recommendations
    public static void testGenerateRecommendations(UserDao userDao, HealthDataDao healthDataDao) {
        int userId = 2; // Change to generate reccomendations for a different user

        System.out.println("Testing generate recommendations...");
        System.out.println("----------------------------");

            HealthData healthData = new HealthData();
            healthData.setUserId(userId);
            healthData.setHeartRate(70);
            healthData.setSteps(8000);
            RecommendationSystem recommendationSystem = new RecommendationSystem();
            List<String> recommendations = recommendationSystem.generateRecommendations(healthData);
        System.out.println("Generating reccomendations for user " + userId + " with heart rate " + healthData.getHeartRate() + " and steps " + healthData.getSteps());
        System.out.println("Generated Recommendations:");
        for (String recommendation : recommendations) {
            System.out.println(recommendation);
        }
             recommendationSystem.storeRecommendations(userId, recommendations);
        System.out.println("Generate recommendations test complete\n----------------------------");
    }


    // Test adding a medicine reminder
    public static void testMedicineReminders(MedicineReminderDao reminderDao, MedicineReminderManager reminderManager) {
        int userId = 1; // Change to add to different user

        System.out.println("Testing medicine reminders...");
        System.out.println("----------------------------");

        MedicineReminder latestReminder = reminderDao.getMedicineReminderByUserId(userId);

        System.out.println("Adding Reminder for user " + userId + " with medicine name " + latestReminder.getMedicineName() + " - " + latestReminder.getDosage() + " - " + latestReminder.getStartDate() + " - " + latestReminder.getEndDate());
        MedicineReminder newReminder = new MedicineReminder(0, "Ibuprofen", "200mg",
                Timestamp.valueOf("2023-12-21 10:30:00"),
                Timestamp.valueOf("2023-12-21 00:00:00"), Timestamp.valueOf("2023-12-28 00:00:00"), userId);
        reminderManager.addReminder(newReminder);


        List<MedicineReminder> userReminders = reminderManager.getRemindersForUser(userId);
        System.out.println("Reminders for user " + userId + ":");
        for (MedicineReminder userReminder : userReminders) {
            System.out.println(userReminder.getId() + ": " + userReminder.getMedicineName() + " - " + userReminder.getDosage() + " - " + userReminder.getStartDate() + " - " + userReminder.getEndDate());
        }

        System.out.println("Medicine reminders test complete\n----------------------------");
    }
    

    // Test get reminders for user
    public static void testGetRemindersForUser() {
        int userId; // User is prompted to enter User ID

        System.out.println("Testing getRemindersForUser...");
        System.out.println("----------------------------");

        System.out.print("Enter User ID: ");
        try {
            userId = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error: Please enter a valid integer for User ID.");
            return;
        } finally {
        }
        System.out.println("You entered User ID: " + userId);

        MedicineReminderManager reminderManager = new MedicineReminderManager(new MedicineReminderDao());

        List<MedicineReminder> userReminders = reminderManager.getRemindersForUser(userId);
        System.out.println("Fetching reminders for User ID " + userId);

        if (userReminders == null) {
            System.out.println("Error: Unable to fetch reminders for User ID " + userId);
        } else {
            if (userReminders.isEmpty()) {
                System.out.println("No reminders found for User ID " + userId);
            } else {
                System.out.println("Reminders for User ID " + userId + ":");
                for (MedicineReminder reminder : userReminders) {
                    System.out.println(
                            "  Medicine: " + reminder.getMedicineName() + ", Schedule: " + reminder.getSchedule());
                }
            }
        }
        System.out.println("Get reminders for user test complete\n----------------------------");
    }
    
    public static void testGetDueRemindersForUser(MedicineReminderManager reminderManager) {
        System.out.println("Testing getDueRemindersForUser...");
        System.out.println("----------------------------");

        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();

        List<MedicineReminder> dueReminders = reminderManager.getDueReminders(userId);

        if (dueReminders == null) {
            System.out.println("Error: Unable to fetch due reminders for User ID " + userId);
        } else {
            if (dueReminders.isEmpty()) {
                System.out.println("No due reminders found for User ID " + userId);
            } else {
                System.out.println("Due reminders for User ID " + userId + ":");
                for (MedicineReminder reminder : dueReminders) {
                    System.out.println(
                            "  Medicine: " + reminder.getMedicineName() + ", Schedule: " + reminder.getSchedule());
                }
            }
        }

        System.out.println("Get due reminders for user test complete\n----------------------------");
    }

    // Developer functions----------------------------------------

// Add user manually
public static void manuallyAddUser() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter UserName: ");
    String name = scanner.nextLine();
    System.out.print("Enter UserEmail: ");
    String email = scanner.nextLine();
    System.out.print("Enter UserPassword: ");
    String password = scanner.nextLine();
    System.out.print("doctor? (yes/no): ");
    boolean isDoctor = scanner.nextLine().equalsIgnoreCase("yes");
    Integer assignedDoctor = null;
    if (isDoctor) {
        System.out.print("Enter Medical ID: ");
        String medicalId = scanner.nextLine();
        System.out.print("Enter Specialization: ");
        String specialization = scanner.nextLine();
        User user = new User(0, name, email, password, true, assignedDoctor);
        Doctor doctor = new Doctor(0, name, email, password, true, assignedDoctor, medicalId, specialization);
        userDao.createUser(doctor);
        userDao.createUser(user);
        System.out.println("User added successfully.");
    } else {
        System.out.print("Enter User  Assigned Doctor ID:");
        String assignedDoctorId = scanner.nextLine();
        if (!assignedDoctorId.isEmpty()) {
            assignedDoctor = Integer.parseInt(assignedDoctorId);
        }
        User user = new User(0, name, email, password, false, assignedDoctor);
        userDao.createUser(user);
        System.out.println("User added successfully.");
    }
}

// Add health data manually for a patient
public static void manuallyAddHealthData() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter Patient ID: ");
    int patientId = scanner.nextInt();
    scanner.nextLine();
    System.out.print("Enter Weight: ");
    double weight = scanner.nextDouble();
    System.out.print("Enter Height: ");
    double height = scanner.nextDouble();
    System.out.print("Enter Steps: ");
    int steps = scanner.nextInt();
    System.out.print("Enter Heart Rate: ");
    int heartRate = scanner.nextInt();
    scanner.nextLine();
    System.out.print("Enter Date (YYYY-MM-DD): ");
    String date = scanner.nextLine();
    HealthData healthData = new HealthData();
    healthData.setUserId(patientId);
    healthData.setWeight(weight);
    healthData.setHeight(height);
    healthData.setSteps(steps);
    healthData.setHeartRate(heartRate);
    healthData.setDate(date);
    boolean success = HealthDataDao.addHealthData(patientId, healthData);
    if (success) {
        System.out.println("Health data added successfully");
    } else {
        System.out.println("Failed to add health data");
    }
}

// Add medicine reminder manually
public static void manuallyAddMedicineReminder() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter User ID: ");
    int userId = scanner.nextInt();
    scanner.nextLine();
    System.out.print("Enter Medicine Name: ");
    String medicineName = scanner.nextLine();
    System.out.print("Enter Dosage: ");
    String dosage = scanner.nextLine();
    System.out.print("Enter Schedule (YYYY-MM-DD HH:mm:ss): ");
    Timestamp schedule = Timestamp.valueOf(scanner.nextLine());
    System.out.print("Enter Start Date (YYYY-MM-DD HH:mm:ss): ");
    Timestamp startDate = Timestamp.valueOf(scanner.nextLine());
    System.out.print("Enter End Date (YYYY-MM-DD HH:mm:ss): ");
    Timestamp endDate = Timestamp.valueOf(scanner.nextLine());
    MedicineReminder reminder = new MedicineReminder(0, medicineName, dosage, schedule, startDate, endDate, userId);
    medicineReminderDao.addMedicineReminder(reminder);
    System.out.println("Medicine reminder added successfully");
}

// Add recommendation manually for a user
public static void generateAndAddRecommendation() {
    System.out.println("Enter user ID: ");
    int userId = scanner.nextInt();
    RecommendationSystem recommendationSystem = new RecommendationSystem();
    HealthDataDao healthDataDao = new HealthDataDao();
    HealthData latestHealthData = healthDataDao.getLatestHealthDataByUserId(userId);
    if (latestHealthData != null) {
        List<String> recommendations = recommendationSystem.generateRecommendations(latestHealthData);
        recommendationSystem.storeRecommendations(userId, recommendations);
        System.out.println("Recommendation generated and added successfully.");
    } else {
        System.out.println("Failed to retrieve health data for the user.");
    }
}

// Login user
public void loginAsUser() {
    System.out.print("Enter Email: ");
    String email = scanner.nextLine();
    System.out.print("Enter Password: ");
    String password = scanner.nextLine();

    boolean loginSuccess = loginUser(email, password);

    if (loginSuccess) {
        System.out.println("Login successful");
    } else {
        System.out.println("Incorrect email or password. Please try again.");
    }
}


// Helper methods--------------------------------------------
public static boolean loginUser(String email, String password) {

    UserDao userDao = new UserDao();
    User user = userDao.getUserByEmail(email);

    if (user != null) {
        String hashedPassword = user.getPassword();
        if (hashedPassword != null && password != null) {
            if (BCrypt.checkpw(password, hashedPassword)) {
                return true;
            }
        } else {
            System.out.println("Password is null");
        }
    } else {
        System.out.println("Email is null");
    }

    return false;
}
    
}