

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private boolean isDoctor;
    private Integer assignedDoctor;

    public User(int id, String name, String email, String password, boolean isDoctor, Integer assignedDoctor) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isDoctor = isDoctor;
        this.assignedDoctor = assignedDoctor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDoctor() {
        return isDoctor;
    }

    public void setDoctor(boolean doctor) {
        isDoctor = doctor;
    }

    public Integer getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setAssignedDoctor(Integer assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }


   
}
