public class CarRequest {
    User client;
    Driver driver;
    double driverOffer;
    boolean isAccepted;
    int carRequestID;
    Ride ride;
    User user;
    Area source, destination;
    public CarRequest(User user, Area source, Area destination) {
        ride = new Ride(source, destination);
        ride.makeRide(source, destination);
        this.client=user;
        isAccepted=false;
        DataRetriever db = DataRetriever.getInstance();

        db.makeCarRequest(this);
       // source.notify();
    }
    void setDriver(Driver driver){
        this.driver = driver;
        updateRequest();
    }
    void setDriverOffer(Double offer){
        this.driverOffer=offer;
        updateRequest();
    }
    void updateRequest(){
        DataRetriever db = DataRetriever.getInstance();
        db.updateCarRequest(this);
    }


    public void notifyUser(){ //TODO queryDB to notify User for change
        //TODO show driverOffer,Driver

    }

    Double getRating() {
        return 0.0;
    }

//    void rate(int stars){
//
//    }

    void responce( Boolean response){

    }

}
