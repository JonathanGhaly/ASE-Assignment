public class Ride {
    private Area sourceArea, destinationArea;
    private String rideStatus;
    int rideID;

    DataRetriever db = DataRetriever.getInstance();

    public Ride() {
        sourceArea = new Area();
        destinationArea = new Area();
        rideStatus = "Pending";
    }

    public Ride(Area sourceArea, Area destinationArea) {
        this.sourceArea = sourceArea;
        this.destinationArea = destinationArea;
        this.rideStatus = "Pending";
        db.insertRide(this);
    }

    public void setRideStatus(String status) {
        this.rideStatus = status;
        db.updateRideStatus(this, status);
    }

    public void setRideID(int rideID) {
        this.rideID = rideID;
    }

    public Area getSourceArea() {
        return sourceArea;
    }

    public Area getDestinationArea() {
        return destinationArea;
    }

    public String getRideStatus() {
        return rideStatus;
    }

    public int getRideID() {
        return rideID;
    }
}
