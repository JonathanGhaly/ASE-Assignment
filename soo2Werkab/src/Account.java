import java.util.Date;


public class Account extends EventListener {

    private String username;
    private String password;
    private String email;
    private String mobileNumber;
    //    private Date birthDate;
    String birthDate;
    private boolean isSuspended;
    private DataRetriever db;

    public Account(String username, String password, String email, String mobileNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobileNumber = mobileNumber;
        isSuspended = false;
        db = DataRetriever.getInstance();
    }

    public Account(String username, String password, String email, String mobileNumber, String birthDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.birthDate = birthDate;
        isSuspended = false;
        db = DataRetriever.getInstance();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Boolean getStatus() {
        return isSuspended;
    }

    @Override
    public void update() {

    }

    public String getBirthDate() {
        return birthDate;
    }
}
