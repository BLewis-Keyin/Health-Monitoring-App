import java.util.List;
public class MedicineReminderManager {
    private MedicineReminderDao reminderDao;

    public MedicineReminderManager(MedicineReminderDao reminderDao) {
        this.reminderDao = reminderDao;
    }

    public void addReminders(List<MedicineReminder> reminders) {
        for (MedicineReminder reminder : reminders) {
            addReminder(reminder);
        }
    }

    public void addReminder(MedicineReminder reminder) {
        reminderDao.addMedicineReminder(reminder);
    }

    public List<MedicineReminder> getRemindersForUser(int userId) {
        return reminderDao.getMedicineRemindersByUserId(userId);
    }

    public List<MedicineReminder> getDueReminders(int userId) {
        return reminderDao.getDueMedicineReminders(userId);
    }
}