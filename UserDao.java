import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class UserDao {
    public static boolean createUser(User user) {
        boolean success = false;

        if (userExistsEmail(user.getEmail())) {
            System.out.println("User with the same email already exists.");
            return success;
        }
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        String query;
        try {
            Connection con = DatabaseConnection.getCon();
            if (user.isDoctor()) {
                query = "INSERT INTO doctors (name, license_number, specialization) VALUES (?, ?, ?)";
                try (PreparedStatement statement = con.prepareStatement(query)) {
                    statement.setString(1, user.getName());
                    statement.setString(2, ((Doctor) user).getLicenseNumber());
                    statement.setString(3, ((Doctor) user).getSpecialization());
                    int updatedRows = statement.executeUpdate();
                    if (updatedRows != 0) {
                        success = true;
                    }
                }
            }
            query = "INSERT INTO users (name, email, password, is_doctor, assigned_doctor) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setString(1, user.getName());
                statement.setString(2, user.getEmail());
                statement.setString(3, hashedPassword);
                statement.setBoolean(4, user.isDoctor());
                Integer assignedDoctor = user.getAssignedDoctor();
                if (assignedDoctor != null) {
                    statement.setInt(5, assignedDoctor);
                } else {
                    statement.setNull(5, Types.INTEGER);
                }
                int updatedRows = statement.executeUpdate();
                if (updatedRows != 0) {
                    success = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }


    public static boolean userExistsEmail(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (Connection con = DatabaseConnection.getCon();
                PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean loginUser(String email, String password) {
        String query = "SELECT password FROM users WHERE email = ?";
        boolean loginSuccess = false;
        try (Connection con = DatabaseConnection.getCon();
                PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            String hashedPassword = null;
            while (rs.next()) {
                hashedPassword = rs.getString("password");
            }
            if (hashedPassword != null && BCrypt.checkpw(password, hashedPassword)) {
                loginSuccess = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loginSuccess;
    }

    public User getUserById(int id) {
        int user_id = 0;
        String name = null;
        String email = null;
        String password = null;
        boolean is_doctor = false;
        int assigned_doctor = 0;
        String query = "SELECT * FROM users WHERE id = ?";
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user_id = rs.getInt("id");
                name = rs.getString("name");
                email = rs.getString("email");
                password = rs.getString("password");
                is_doctor = rs.getBoolean("is_doctor");
                assigned_doctor = rs.getInt("assigned_doctor");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new User(user_id, name, email, password, is_doctor, assigned_doctor);
    }

    public static User getUserByEmail(String email) { 
        int id = 0;
        String name = null;
        String user_email = null;
        String password = null;
        boolean is_doctor = false;
        int assigned_doctor = 0;
        String query = "SELECT * FROM users WHERE email = ?";
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
                name = rs.getString("name");
                user_email = rs.getString("email");
                password = rs.getString("password");
                is_doctor = rs.getBoolean("is_doctor");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new User(id, name, user_email, password, is_doctor, assigned_doctor);
    }

    public boolean updateUser(User user) {
        boolean bool = false;
        String query = "UPDATE users " +
                "SET name = ?, email = ?, password = ?, is_doctor = ? " +
                "WHERE id = ?";
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashedPassword);
            statement.setBoolean(4, user.isDoctor());
            statement.setInt(5, user.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows != 0) {
                bool = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }

    public boolean deleteUser(int id) { 
        boolean bool = false;
        String query = "DELETE FROM users WHERE id = ?";

        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated != 0) {
                bool = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }

    public static boolean verifyPassword(String email, String password) {
        boolean bool = false;
        String query = "SELECT password FROM users WHERE email = ?";
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            String hashedPassword = null;
            while (rs.next()) {
                hashedPassword = rs.getString("password");
            }
            if (BCrypt.checkpw(password, hashedPassword)) {
                bool = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }

    public Doctor
    getDoctorById(int doctorId) {
        Doctor doctor = null;
        String query = "SELECT * FROM doctors WHERE id = ?";
        try (Connection con = DatabaseConnection.getCon();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, doctorId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String licenseNumber = rs.getString("license_number");
                String specialization = rs.getString("specialization");
                doctor = new Doctor(id, name, "", "", true, 0, licenseNumber, specialization );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctor;
    }

    public List<User> getPatientsByDoctorId(int doctorId) {
        List<User> patients = new ArrayList<>();
        String query = "SELECT * FROM users WHERE assigned_doctor = ?";

        try (Connection con = DatabaseConnection.getCon();
                PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, doctorId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                boolean isDoctor = rs.getBoolean("is_doctor");
                int assignedDoctorId = rs.getInt("assigned_doctor");
                User patient = new User(userId, name, email, "", isDoctor, assignedDoctorId);
                patients.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patients;
    }
    public static boolean addHealthData(int userId, HealthData healthData) {
        boolean success = false;
        String query = "INSERT INTO health_data (user_id, weight, height, steps, heart_rate, date) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getCon();
                PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setDouble(2, healthData.getWeight());
            statement.setDouble(3, healthData.getHeight());
            statement.setInt(4, healthData.getSteps());
            statement.setInt(5, healthData.getHeartRate());
            statement.setString(6, healthData.getDate());
            int updatedRows = statement.executeUpdate();
            if (updatedRows != 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
}