package controller;

import model.DataRetriever;

public class CarRequest {
    User client;
    Driver driver;
    double driverOffer;
    boolean isAccepted;
    int carRequestID;
    Ride ride;
    User user;
    Area source, destination;
    boolean aloneStatus, carpool;
    DataRetriever db = DataRetriever.getInstance();


    public CarRequest(User user, Area source, Area destination) {
        ride = new Ride(source, destination);
        ride.makeRide();
        this.client = user;
        isAccepted = false;
        aloneStatus = true;
        db.makeCarRequest(this);
        // source.notify();
    }

    public CarRequest(User user, Area source, Area destination, int passengersNo) { //use this
        ride = new Ride(source, destination, passengersNo);
        ride.makeRide();
        this.client = user;
        isAccepted = false;
        aloneStatus = false;
        db.makeCarRequest(this);
        // source.notify();
    }
//    public controller.CarRequest(controller.User user,controller.Area source,controller.Area destination,boolean carpool){
//        this.client= user;
//        isAccepted = false;
//        aloneStatus = false;
//        model.DataRetriever db = model.DataRetriever.getInstance(); //TODO continue
//
//    }


    void setDriver(Driver driver) {
        this.driver = driver;
        updateRequest();
    }

    void setDriverOffer(Double offer) {
        this.driverOffer = offer;
        updateRequest();
    }

    public void setCarRequestID(int carRequestID) {
        this.carRequestID = carRequestID;
    }

    public User getClient() {
        return client;
    }

    public Ride getRide() {
        return ride;
    }

    void updateRequest() {
        DataRetriever db = DataRetriever.getInstance();
        db.updateCarRequest(this);
    }

    int getRideID(){
        return ride.getRideID();
    }


    public void notifyUser() { //TODO queryDB to notify controller.User for change
        //TODO show driverOffer,controller.Driver

    }

    Double getRating() {
        return 0.0;
    }

//    void rate(int stars){
//
//    }

    public Area getDestination() {
        return destination;
    }

    public Area getSource() {
        return source;
    }

    public double getDriverOffer() {
        return driverOffer;
    }

    public int getCarRequestID() {
        return carRequestID;
    }

    public Driver getDriver() {
        return driver;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public boolean getAloneStatus(){return aloneStatus;}

    void responce(Boolean response) {

    }

}
