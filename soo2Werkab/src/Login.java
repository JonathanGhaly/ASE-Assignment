public class Login {
    DataRetriever d = DataRetriever.getInstance();
    String username;
    String password;
    Boolean isDriver = false, isLoggedin = false, isAdmin = false;

    CarDriver c;
    User u;
    Admin admin;

    Login(String username, String password) {
        this.username = username;
        this.password = password;
        this.isLoggedin = d.Login(this);
        if (isLoggedin && isDriver && ! d.isVerified(getCarDriver(username))) {
            isLoggedin = false;
            System.out.println("Driver is not verified yet");
        } else if (isLoggedin) {
            System.out.println("Logged in successfully");
            if (isAdmin) admin = getAdmin();
            else if (isDriver) c = getCarDriver(username);
            else u = getUser(username);
        } else {
            System.out.println("Wrong username or password");
        }
    }

    CarDriver getCarDriver(String username) {
        return d.getCarDriver(username);
    }

    User getUser(String username) {
        return d.getUser(username);
    }

    User getUser() {
        return this.u;
    }

    CarDriver getCarDriver() {
        return this.c;
    }

    Boolean isDriver() {
        return this.isDriver;
    }
    void setIsDriver(boolean isDriver){
        this.isDriver = isDriver;
    }
    Boolean getIsLoggedin() {
        return this.isLoggedin;
    }

    Admin getAdmin() {
        return d.getAdmin(this.username);
    }

}
