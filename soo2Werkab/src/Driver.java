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

    Driver(Account account, String nationalID, String drivingLicenseNumber, boolean isVerified, boolean isAccepted, int balance) {
        this.account = account;
        this.nationalID = nationalID;
        this.drivingLicenseNumber = drivingLicenseNumber;
        this.isVerified = isVerified;
        this.isAccepted = isAccepted;
        this.balance = balance;
    }
    public void addFavouriteArea(Area area) {
        area.events.subscribe("addedFavouriteArea",account);
        db.insertDriverFavouriteArea(this, area);
    }

    public ArrayList<Area> getFavouriteAreas() {
        ArrayList<Area> areas;
        areas = db.getCarDriverFavouriteArea(this);
        for (Area area : areas){
            area.events.subscribe("",account); //TODO change eventType
        }
        return areas;
    }

    public void removeFavouriteArea(Area area) { //TODO add volatile options + unsubscribe
        db.removeCarDriverFavouriteArea(this,area);

    }

    public void sendOffer(Ride ride,Integer offer){
        db.makeDriverOffer(this,offer,ride);
        ride.events.subscribe("sent offer",account);
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
