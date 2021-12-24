public class UserOperations {

    CarRequest currentRequest;

    void requestRide(User user, Area source, Area destination) {
        if (currentRequest == null)
            currentRequest = new CarRequest(user, source, destination);
    }

    void reviewOffers() {
    }

    void rateDriver(int stars) {
        currentRequest.rate(stars);
    }

    Double getDriverRating() {
        if (currentRequest != null)
            return currentRequest.getRating();
        System.out.println("You are not in a ride!");
        return 0.0;
    }


}
