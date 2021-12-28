import java.util.ArrayList;

public class UserOperations {
    DataRetriever db = DataRetriever.getInstance();
    CarRequest currentRequest;

    void requestRide(User user, Area source, Area destination) {
        if (currentRequest == null)
            currentRequest = new CarRequest(user, source, destination);
    }
    ArrayList<Offer> getOffers(User user){
        return db.getDriverOffer(user);
    }

    void reviewOffers() {
    }

    void rateDriver(User user,String driverName,int rating) {
        db.rateDriver(user,db.getDriver(driverName), rating);
        }

    Double getDriverRating() {
        if (currentRequest != null)
            return currentRequest.getRating();
        System.out.println("You are not in a ride!");
        return 0.0;
    }


}
