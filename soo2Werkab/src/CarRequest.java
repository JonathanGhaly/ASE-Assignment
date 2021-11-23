public class CarRequest {
    User client;
    Driver carDriver;
    double driverOffer;
    boolean isAccepted;
    Ride ride;

    CarRequest(User user, Area source, Area destination) { //TODO insert to DB
        ride = new Ride(source, destination);
        this.client=user;
        isAccepted=false;

    }

    public void notifyUser(){ //TODO queryDB to notify User for change
        //TODO show driverOffer,Driver

    }
}
