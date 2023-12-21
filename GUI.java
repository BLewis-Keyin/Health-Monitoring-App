import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
public class GUI {

    private JFrame mainFrame;
    private JComboBox<String> testDropdown;
    private JButton runTestButton;
    private JButton addUserManuallyButton;
    private JButton addHealthDataManuallyButton;
    private JButton addMedicineManuallyButton;
    private JButton addReccomendationButton;
    private JButton loginAsUserButton;

    private HealthMonitoringApp healthMonitoringApp; 

    public void initialize() {
        mainFrame = new JFrame("Health Monitoring GUI");
        mainFrame.setSize(400, 200);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        healthMonitoringApp = new HealthMonitoringApp();

        // Test options
        String[] testOptions = { "Register a new user", "Log in the user", "Add health data",
                "Generate recommendations",
                "Add a medicine reminder", "Get reminders for a specific user", "Get due reminders for a specific user",
                "Test doctor portal" };

        // Dropdown menu
        testDropdown = new JComboBox<>(testOptions);
        mainFrame.add(testDropdown);

        // Run Test button
        runTestButton = new JButton("Run Test");
        runTestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runTest();
            }
        });
        mainFrame.add(runTestButton);

        // Add User
        addUserManuallyButton = new JButton("Add User");
        addUserManuallyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                healthMonitoringApp.manuallyAddUser();
            }
        });
        mainFrame.add(addUserManuallyButton);

        addHealthDataManuallyButton = new JButton("Add Health Data");
        addHealthDataManuallyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                healthMonitoringApp.manuallyAddHealthData();
            }
        });
        mainFrame.add(addHealthDataManuallyButton);

        addMedicineManuallyButton = new JButton("Add Medicine Reminder");
        addMedicineManuallyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                healthMonitoringApp.manuallyAddMedicineReminder();
            }
        });
        mainFrame.add(addMedicineManuallyButton);
                
        addReccomendationButton = new JButton("Add Health Recommendation");
        addReccomendationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                healthMonitoringApp.generateAndAddRecommendation();
            }
        });
        mainFrame.add(addReccomendationButton);
                
        loginAsUserButton = new JButton("Login as User");
        loginAsUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                healthMonitoringApp.loginAsUser();
            }
        });
        mainFrame.add(loginAsUserButton);
    }
    
    


    public void showMainFrame() {
        mainFrame.setVisible(true);
    }

    private void runTest() {
    String selectedTest = (String) testDropdown.getSelectedItem();

    switch (selectedTest) {
        case "Register a new user":
            healthMonitoringApp.testUserRegistration(new UserDao());
            break;
        case "Log in the user":
            healthMonitoringApp.testLoginUser();
            break;
        case "Add health data":
            healthMonitoringApp.testAddHealthData(new UserDao(), new HealthDataDao());
            break;
        case "Generate recommendations":
            healthMonitoringApp.testGenerateRecommendations(new UserDao(), new HealthDataDao());
            break;
        case "Test doctor portal":
            healthMonitoringApp.testDoctorPortal();  
            break;
        case "Add a medicine reminder":
            healthMonitoringApp.testMedicineReminders(new MedicineReminderDao(),
                    new MedicineReminderManager(new MedicineReminderDao()));
            break;
        case "Get reminders for a specific user":
            healthMonitoringApp.testGetRemindersForUser();
            break;
        case "Get due reminders for a specific user":
            healthMonitoringApp.testGetDueRemindersForUser((new MedicineReminderManager(new MedicineReminderDao())));
            break;
        default:
            System.out.println("No method defined for the selected test.");
            break;
    }
}

// ...

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            gui.initialize();
            gui.showMainFrame();
        });
    }
}