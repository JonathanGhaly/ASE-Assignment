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

    void reviewOffers(){
        DataRetriever db = DataRetriever.getInstance();
        db.getDriverOffer(this.currentRequest);

    }

//    void rateDriver(int stars) {
//        currentRequest.rate(stars);
//    }
//
//    String getDriverRating() {
//        if (currentRequest != null)
//            return currentRequest.getRating();
//        return "You are not in a ride!";
//    }


}
