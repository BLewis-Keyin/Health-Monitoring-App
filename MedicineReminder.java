import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MedicineReminder {
    private int id;
    private String medicineName;
    private String dosage;
    private Timestamp schedule;  
    private Timestamp startDate;  
    private Timestamp endDate;
    private int userId;

    public MedicineReminder(int id, String medicineName, String dosage, Timestamp schedule, Timestamp startDate, Timestamp endDate, int userId) {
        this.id = id;
        this.userId = userId;
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.schedule = schedule;
        this.startDate = startDate;
        this.endDate = endDate;
    }

   
    // private Timestamp convertToTimestamp(String dateString) {
    //     try {
    //         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //         Date parsedDate = dateFormat.parse(dateString);
    //         return new Timestamp(parsedDate.getTime());
    //     } catch (ParseException e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public Timestamp getSchedule() {
        return schedule;
    }

    public void setSchedule(Timestamp schedule) {
        this.schedule = schedule;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }
}