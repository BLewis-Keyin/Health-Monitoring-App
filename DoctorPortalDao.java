import java.util.List;

public class DoctorPortalDao {
    private UserDao userDao;
    private HealthDataDao healthDataDao;

    public DoctorPortalDao() {
        userDao = new UserDao();
        healthDataDao = new HealthDataDao();
    }

    public Doctor getDoctorById(int doctorId) {
        User user = userDao.getUserById(doctorId);
        if (user instanceof Doctor) {
            return (Doctor) user;
        } else {
            return null;
        }
    }

    public List<User> getPatientsByDoctorId(int doctorId) {
        return userDao.getPatientsByDoctorId(doctorId);
    }

    public List<HealthData> getHealthDataByPatientId(int patientId) {
        return healthDataDao.getHealthDataByUserId(patientId);
    }


}

