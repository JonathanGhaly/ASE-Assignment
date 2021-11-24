public class CarRequest {
    User client;
    Driver carDriver;
    double driverOffer;
    boolean isAccepted;
    Ride ride;

    User user;
    Area source, destination;
    CarRequest(User user, Area source, Area destination) { //TODO insert to DB
        ride = new Ride(source, destination);
        this.client=user;
        isAccepted=false;

        source.notify();
    }

    public void notifyUser(){ //TODO queryDB to notify User for change
        //TODO show driverOffer,Driver

    }

    String getRating() {
        return "";
    }

    void rate(int stars){

    }

    void responce( Boolean response){

    }

}
