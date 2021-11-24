public class User {
    Account account;
    UserStatus status;
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

    void rateDriver(int stars) {
        currentRequest.rate(stars);
    }

    String getDriverRating() {
        if (currentRequest != null)
            return currentRequest.getRating();
        return "You are not in a ride!";
    }

}
