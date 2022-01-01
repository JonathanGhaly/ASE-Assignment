public class CarRequest {
    User client;
    Driver driver;
    double driverOffer;
    boolean isAccepted;
    int carRequestID;
    Ride ride;
    User user;
    Area source, destination;
    boolean aloneStatus,carpool;

    public CarRequest(User user, Area source, Area destination) {
        ride = new Ride(source, destination);
        ride.makeRide();
        this.client=user;
        isAccepted=false;
        aloneStatus = true;
        DataRetriever db = DataRetriever.getInstance();
        //db.makeCarRequest(this);
        // source.notify();
    }
    public CarRequest(User user, Area source, Area destination,int passengersNo) { //use this
        ride = new Ride(source, destination,passengersNo);
        ride.makeRide();
        this.client=user;
        isAccepted=false;
        aloneStatus = true;
        DataRetriever db = DataRetriever.getInstance();
        db.makeCarRequest(this);
       // source.notify();
    }
//    public CarRequest(User user,Area source,Area destination,boolean carpool){
//        this.client= user;
//        isAccepted = false;
//        aloneStatus = false;
//        DataRetriever db = DataRetriever.getInstance(); //TODO continue
//
//    }


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
