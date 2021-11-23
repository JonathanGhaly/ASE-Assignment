public class Driver {
    Account account;
    String nationalID;
    String drivingLicenseNumber;
    boolean isVerified;
    boolean isAccepted;

    public Driver(Account a, String nationalID, String drivingLicenseNumber){
        this.account = a;
        this.nationalID = nationalID;
        this.drivingLicenseNumber = drivingLicenseNumber;
    }
    String showRating() {
        return "Your current rating is: " + DataRetriever.getRating(this);
    }
}
