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
    public void addFavouriteArea(Area area) {
        db.insertDriverFavouriteArea(this, area);
    }

    public ArrayList<Area> getFavouriteAreas() {
        ArrayList<Area> areas;
        areas = db.getCarDriverFavouriteArea(this);
        return areas;
    }

    public void removeFavouriteArea(Area area) {
        db.removeCarDriverFavouriteArea(this,area);
    }

    public void sendOffer(Ride ride,Integer offer){
        db.makeDriverOffer(this,offer,ride);
    }

    int rate() {
        return db.getRating(db.getID(account.getUsername()));
    }

//    void notifyOfRequest(CarRequest request) {
//        System.out.println("DO you want to accept the ride with : ");
//        System.out.println(request.toString());
//        sc = new Scanner(System.in);
//        Boolean choice = sc.nextBoolean();
//        request.responce(choice);
//
//    }




}
