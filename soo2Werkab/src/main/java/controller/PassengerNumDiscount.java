package controller;

import model.DataRetriever;

public class PassengerNumDiscount implements Discount {
    int rideID;
    DataRetriever db = DataRetriever.getInstance();

    public PassengerNumDiscount(int id) {
        rideID = id;
    }

    @Override
    public double getDiscount(double price) {
        if (db.passengerNumCheck(rideID)) {
            return price - (price * 0.05);
        }
        return price;
    }
}
