import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    Account account;
    String nationalID;
    String drivingLicenseNumber;
    boolean isVerified;
    boolean isAccepted;
    int balance;
    Scanner sc;
    DataRetriever db = DataRetriever.getInstance();

    public Driver(Account account, String nationalID, String drivingLicenseNumber, boolean isVerified, boolean isAccepted, int balance) {
        this.account = account;
        this.nationalID = nationalID;
        this.drivingLicenseNumber = drivingLicenseNumber;
        this.isVerified = isVerified;
        this.isAccepted = isAccepted;
        this.balance = balance;
    }
}
