import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class DriverOperations {
    Scanner sc = new Scanner(System.in);
    DataRetriever db = DataRetriever.getInstance();
    Logger logger = Logger.getInstance();

    ArrayList<Integer> showRating(Driver driver) {
        return db.getRating(db.getID(driver.account.getUsername()));
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

    public void sendOffer(Driver driver, Ride ride, Double offer) {
        db.makeDriverOffer(driver, offer, ride);
        logger.log(new Event(EventType.CaptainSetPrice, driver.account.getUsername(), String.valueOf(offer), ride.rideID, Calendar.getInstance().getTime()));
    }


    public ArrayList<Ride> listAllRides(Driver driver) {
        ArrayList<Ride> rides = new ArrayList<>();
        for (Area area : getFavouriteAreas(driver)) {
            rides.addAll(db.getRidesFromArea(driver));
        }
        return rides;
    }

    public void endRide(Driver driver) {
        db.endRide(driver);
    }

    public void arrivedToUserLocation(Driver driver){
        db.logArrival(driver.account.getUsername());
    }

//    int rate(Driver driver) {
//        return db.getRating(db.getID(driver.account.getUsername()));
//    }
}
