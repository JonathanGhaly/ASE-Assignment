import java.util.ArrayList;

public class CarDriver extends Driver {

    public CarDriver(Account a, String nationalID, String drivingLicenseNumber) {
        super(a, nationalID, drivingLicenseNumber);
    }

    public ArrayList<Ride> listAllRides() { //TODO query rides on favourite area for Driver
        ArrayList<Ride> rides = new ArrayList<>();

        return rides;
    }

    public void addFavouriteArea(Area area) { //TODO add area to DB

    }

    public ArrayList<Area> getFavouriteAreas() { //TODO query favourite areas according to driver
        ArrayList<Area> areas = new ArrayList<>();

        return areas;
    }

    public void removeFavouriteArea(Area area) { //TODO remove favourite area

    }
}
