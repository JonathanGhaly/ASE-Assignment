public class Ride extends Publisher {
    private Area sourceArea, destinationArea;
    private String rideStatus;
    int rideID;
    int passengersNo;
    boolean aloneStatus;

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
        this.passengersNo = 0;
//        aloneStatus = true;
        //a tiny change
    }

    public Ride(Area sourceArea, Area destinationArea, int passengersNo) {
        this.sourceArea = sourceArea;
        this.destinationArea = destinationArea;
        this.rideStatus = "Pending";
        this.passengersNo = passengersNo;

        //a tiny change
    }

    public Ride(Area sourceArea, Area destinationArea, int passengersNo, String rideStatus, boolean aloneStatus) {
        this.sourceArea = sourceArea;
        this.destinationArea = destinationArea;
        this.rideStatus = rideStatus;
        this.passengersNo = passengersNo;
        this.aloneStatus = aloneStatus;
        //a tiny change
    }

    /**
     * method to change ride status from pending to inride and ended
     *
     * @param status the status of the ride to change to
     */
    public void setRideStatus(String status) {
        this.rideStatus = status;
        db.updateRideStatus(this.rideID, status);
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

    public int getPassengersNo() {
        return passengersNo;
    }

    public boolean getAloneStatus() {
        return aloneStatus;
    }

    /**
     * Make ride and add it to the database
     */
    void makeRide() { //TODO already has attributes why set again
        this.rideStatus = "Pending";
        db.insertRide(this);
    }
}
