public class CarRequest {
    User client;
    Driver carDriver;
    double driverOffer;
    boolean isAccepted;
    int carRequestID;
    Ride ride;

    User user;
    Area source, destination;
    CarRequest(User user, Area source, Area destination) {
        ride = new Ride(source, destination);
        this.client=user;
        isAccepted=false;
        DataRetriever db = DataRetriever.getInstance();
        db.makeCarRequest(this);
       // source.notify();
    }

    void setCarDriver(CarDriver carDriver){
        this.carDriver = carDriver;
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
