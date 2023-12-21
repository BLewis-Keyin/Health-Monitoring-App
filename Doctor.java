public class Doctor extends User {
    private String LicenseNumber;
    private String specialization;

    public Doctor(int id, String name, String email, String password, boolean isDoctor, Integer assignedDoctor, String LicenseNumber, String specialization) {
        super(id, name, email, password, isDoctor, assignedDoctor);
        this.LicenseNumber = LicenseNumber;
        this.specialization = specialization;
    }
    public String getLicenseNumber() {
        return LicenseNumber;
    }
    public void setLicenseNumber(String LicenseNumber) {
        this.LicenseNumber = LicenseNumber;
    }
    public String getSpecialization() {
        return specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}