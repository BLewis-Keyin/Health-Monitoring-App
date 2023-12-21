import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class MedicineReminderDao {

    public void addMedicineReminder(MedicineReminder reminder) {
        String query = "INSERT INTO medicine_reminders (medicine_name, dosage, schedule, start_date, end_date, user_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getCon();
                PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, reminder.getMedicineName());
            statement.setString(2, reminder.getDosage());
            statement.setTimestamp(3, reminder.getSchedule());
            statement.setTimestamp(4, reminder.getStartDate());
            statement.setTimestamp(5, reminder.getEndDate());
            statement.setInt(6, reminder.getUserId());

            int rowsAffected = statement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MedicineReminder getMedicineReminderByUserId(int userId) {
        String query = "SELECT * FROM medicine_reminders WHERE user_id = ?";
        try (Connection con = DatabaseConnection.getCon();
                PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String medicineName = rs.getString("medicine_name");
                String dosage = rs.getString("dosage");
                Timestamp schedule = rs.getTimestamp("schedule");
                Timestamp startDate = rs.getTimestamp("start_date");
                Timestamp endDate = rs.getTimestamp("end_date");

                return new MedicineReminder(id, medicineName, dosage, schedule, startDate, endDate, userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }



    public List<MedicineReminder> getDueMedicineReminders(int userId) {
        List<MedicineReminder> dueReminders = new ArrayList<>();
        String query = "SELECT * FROM medicine_reminders WHERE user_id = ? AND schedule <= NOW()";
        try (Connection con = DatabaseConnection.getCon();
                PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String medicineName = rs.getString("medicine_name");
                String dosage = rs.getString("dosage");
                Timestamp schedule = rs.getTimestamp("schedule");
                Timestamp startDate = rs.getTimestamp("start_date");
                Timestamp endDate = rs.getTimestamp("end_date");

                MedicineReminder reminder = new MedicineReminder(id, medicineName, dosage, schedule, startDate, endDate,
                        userId);
                dueReminders.add(reminder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dueReminders;
    }

    public List<MedicineReminder> getMedicineRemindersByUserId(int userId) {
        List<MedicineReminder> reminders = new ArrayList<>();
        String query = "SELECT * FROM medicine_reminders WHERE user_id = ?";
        try (Connection con = DatabaseConnection.getCon();
                PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String medicineName = rs.getString("medicine_name");
                String dosage = rs.getString("dosage");
                Timestamp schedule = rs.getTimestamp("schedule");
                Timestamp startDate = rs.getTimestamp("start_date");
                Timestamp endDate = rs.getTimestamp("end_date");
                MedicineReminder reminder = new MedicineReminder(id, medicineName, dosage, schedule, startDate, endDate,
                        userId);
                reminders.add(reminder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reminders;
    }
}