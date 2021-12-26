import java.util.ArrayList;
import java.util.Scanner;

public class DriverOperations {
    Scanner sc = new Scanner(System.in);
    DataRetriever db = DataRetriever.getInstance();

    String showRating(Driver driver) {
        return "Your current rating is: " + db.getRating(db.getID(driver.account.getUsername()));
    }

    void notifyOfRequest(Driver driver, CarRequest request) {
        System.out.println("DO you want to accept the ride with : ");
        System.out.println(request.toString());
        sc = new Scanner(System.in);
        Boolean choice = sc.nextBoolean();
        request.responce(choice);
    }

    void addFavouriteArea(Driver driver, Area area) {
        db.insertDriverFavoriteArea(driver, area);
    }

    public ArrayList<Area> getFavouriteAreas(Driver driver) {
        return db.getCarDriverFavouriteArea(driver);
    }

    public void removeFavouriteArea(Driver driver, Area area) {
        db.removeCarDriverFavouriteArea(driver, area);
    }

    public void sendOffer(Driver driver, Ride ride, Integer offer) {
        db.makeDriverOffer(driver, offer, ride);
    }

    public ArrayList<Ride> listAllRides(Driver driver) {
        ArrayList<Ride> rides = new ArrayList<>();
        for (Area area : getFavouriteAreas(driver)) {
            rides.addAll(db.getRidesFromArea(driver, area));
        }
        return rides;
    }
//    int rate(Driver driver) {
//        return db.getRating(db.getID(driver.account.getUsername()));
//    }
}
