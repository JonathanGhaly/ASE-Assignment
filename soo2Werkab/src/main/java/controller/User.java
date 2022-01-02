package controller;

import model.DataRetriever;

public class User {
    Account account;
    DataRetriever db = DataRetriever.getInstance();

    public User(Account account) {
        this.account = account;
    }

    public User() {

    }

    public Account getAccount() {
        return account;
    }

    public String getUserStatus() {
        return db.getUserStatus(this);
    }
}
