import java.util.Scanner;

public class Driver {
    Account account;
    String nationalID;
    String drivingLicenseNumber;
    boolean isVerified;
    boolean isAccepted;
    int balance;
    Scanner sc;
    DataRetriever d = DataRetriever.getInstance();

    Driver(Account account, String nationalID, String drivingLicenseNumber, boolean isVerified, boolean isAccepted, int balance) {
        this.account = account;
        this.nationalID = nationalID;
        this.drivingLicenseNumber = drivingLicenseNumber;
        this.isVerified = isVerified;
        this.isAccepted = isAccepted;
        this.balance = balance;
    }

    int rate() {
        return d.getRating(d.getID(account.getUsername()));
    }

    void notifyOfRequest(CarRequest request) {
        System.out.println("DO you want to accept the ride with : ");
        System.out.println(request.toString());
        sc = new Scanner(System.in);
        Boolean choice = sc.nextBoolean();
        request.responce(choice);
    }

}
