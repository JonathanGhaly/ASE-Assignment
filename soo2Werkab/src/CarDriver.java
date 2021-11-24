import java.util.ArrayList;

public class CarDriver extends Driver {


    DataRetriever db = DataRetriever.getInstance();
    CarDriver(){

    }
    CarDriver(Account a, String nationalID, String drivingLicenseNumber) {
        this.account = a;
        this.nationalID = nationalID;
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    public ArrayList<Ride> listAllRides() { //TODO query rides on favourite area for Driver
        ArrayList<Ride> rides = new ArrayList<>();

        return rides;
    }

    public void addFavouriteArea(Area area) {
        db.insertCarDriverFavouriteArea(this, area);
    }

    public ArrayList<Area> getFavouriteAreas() { //TODO query favourite areas according to driver
        ArrayList<Area> areas = new ArrayList<>();
        areas = db.getCarDriverFavouriteArea(this);
        return areas;
    }

    public void removeFavouriteArea(Area area) { //TODO remove favourite area

    }
}