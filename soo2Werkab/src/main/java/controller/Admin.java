package controller;

import model.DataRetriever;

public class Admin {


    DataRetriever dataRetriever = DataRetriever.getInstance();
    Account account;

    public Admin(Account account) {
        this.account = account;
    }

}
