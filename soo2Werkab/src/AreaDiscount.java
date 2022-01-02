public class AreaDiscount implements Discount {
    DataRetriever db = DataRetriever.getInstance();
    int rideID;

    AreaDiscount(int rideID) {
        this.rideID = rideID;
    }

    @Override
    public double getDiscount(double price) {
        if (db.areaDiscount(rideID)) {
            return price - (price * 0.1);
        }
        return price;
    }
}
