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

    void rateDriver(Integer stars) {
//        currentRequest.rate(stars);
       DataRetriever db = DataRetriever.getInstance();
       db.rateDriver(currentRequest.carDriver,stars);
    }

//
//    String getDriverRating() {
//        if (currentRequest != null)
//            return currentRequest.getRating();
//        return "You are not in a ride!";
//    }


}
