package controller;

import model.DataRetriever;

public class Login {
    DataRetriever d = DataRetriever.getInstance();
    String username;
    String password;
    Boolean isDriver = false;
    Boolean isLoggedin = false;
    public Boolean isAdmin = false;
    Driver c;
    User u;
    Admin admin;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
        this.isLoggedin = d.Login(this);
        if (isLoggedin && isDriver && ! d.isVerified(getDriver(username))) {
            isLoggedin = false;
            System.out.println("controller.Driver is not verified yet");
        } else if (isLoggedin) {
            System.out.println("Logged in successfully");
            if (isAdmin) admin = getAdmin();
            else if (isDriver) c = getDriver(username);
            else u = getUser(username);
        } else {
            System.out.println("Wrong username or password");
        }
    }

    private Driver getDriver(String username) {
        return d.getDriver(username);
    }
    User getUser(String username) {
        return d.getUser(username);
    }
    public User getUser() {
        return this.u;
    }
    public Driver getDriver() {
        return this.c;
    }
    public Boolean isDriver() {
        return this.isDriver;
    }
    public void setIsDriver(boolean isDriver){
        this.isDriver = isDriver;
    }
    public void setIsAdmin(boolean isAdmin){this.isAdmin=isAdmin;}
    public Boolean getIsLoggedin() {
        return this.isLoggedin;
    }
    public Admin getAdmin() {
        return d.getAdmin(this.username);
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
