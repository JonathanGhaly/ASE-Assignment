public class Ride {
    private Area sourceArea, destinationArea;
    private String rideStatus;

    public Ride() {
        sourceArea = new Area();
        destinationArea = new Area();
        rideStatus="Pending";
    }

    public Ride(Area sourceArea, Area destinationArea) {
        this.sourceArea = sourceArea;
        this.destinationArea = destinationArea;
        this.rideStatus="Pending";
    }
}
