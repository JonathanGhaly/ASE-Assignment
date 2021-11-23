public class Driver {
    Account account;
    String nationalID;
    String drivingLicenseNumber;
    boolean isVerified;
    boolean isAccepted;


    String showRating() {
        return "Your current rating is: " + DataRetriever.getRating(this);
    }
}
