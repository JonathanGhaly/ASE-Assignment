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
        ArrayList<Ride> rides=new ArrayList<>();
        ArrayList<Area> areas = getFavouriteAreas();
        for(Area area : areas){
            rides.addAll(db.getRidesFromArea(area));
        }

        return rides;
    }

    public void addFavouriteArea(Area area) {
        db.insertCarDriverFavouriteArea(this, area);
    }

    public ArrayList<Area> getFavouriteAreas() {
        ArrayList<Area> areas;
        areas = db.getCarDriverFavouriteArea(this);
        return areas;
    }

    public void removeFavouriteArea(Area area) { //TODO remove favourite area

    }
}