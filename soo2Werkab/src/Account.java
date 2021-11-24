public class Account {

    private String username;
    private String password;
    private String email;
    private String mobileNumber;
    private boolean isSuspended;

    private boolean validateMobileNumber(String mobileNumber) {
        if (mobileNumber.length() != 11) return false;

        String firstThree = mobileNumber.substring(0, 3);
        if (!firstThree.equals("010") && !firstThree.equals("011") && !firstThree.equals("012")) return false;
        return true;
    }

    public Account(String username, String password, String email, String mobileNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobileNumber = mobileNumber;
        isSuspended = false;
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

        if (validateMobileNumber(mobileNumber))
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
}
