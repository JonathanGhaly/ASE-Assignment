import java.util.ArrayList;

public class User {
    Account account;
    CarRequest currentRequest;

    User(Account account){
        this.account = account;
    }

    public User() {

    }

    void requestRide(Area source, Area destination) {
        if (currentRequest == null)
            currentRequest = new CarRequest(this, source, destination);
    }

    ArrayList<Offer> getOffers(){
        DataRetriever db = DataRetriever.getInstance();
        return db.getDriverOffer(this.currentRequest);
    }

    void acceptOffer(){

    }
    void rejectOffer(){

    }

    // TODO rate driver using driver operations.
    void rateDriver(String driverUserName,Integer stars) {
//        currentRequest.rate(stars);
       DataRetriever db = DataRetriever.getInstance();
       //Driver driver = db.getCarDriver(driverUserName);
       //db.rateDriver(driver,stars);
    }

//
//    String getDriverRating() {
//        if (currentRequest != null)
//            return currentRequest.getRating();
//        return "You are not in a ride!";
//    }


}
