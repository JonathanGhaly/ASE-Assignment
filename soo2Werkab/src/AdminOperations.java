import java.util.ArrayList;

public class AdminOperations {
    DataRetriever dataRetriever = DataRetriever.getInstance();

    AdminOperations() {
    }

    public void suspend(Admin admin, String username, int type) {
        if (admin != null)
            dataRetriever.changeAccountState(username, type);
    }

    public void verifyDriver(Admin admin, String username) {
        if (admin != null)
            dataRetriever.verifyDriver(dataRetriever.getID(username));
    }

    public ArrayList<String> getNotVerifiedDrivers(Admin admin) {
        if (admin != null)
            return dataRetriever.getNotVerifiedDriver();
        return null;
    }

    public void manageUser(User user) {

    }

    public void setDiscountToArea(Admin admin, Area area, int discount) {
        dataRetriever.setDiscount(area, discount);
    }

    public ArrayList<Area> listAllAreas(Admin admin) {
        if (admin != null)
            return dataRetriever.getAllAreas();
        return null;
    }

    public String getEventsOfRide(int rideID) {
        ArrayList<Event> events = dataRetriever.getEventsOfRide(rideID);

        StringBuilder result = new StringBuilder();
        for (Event event : events) {
            result.append(event.type).append(" ").append(event.source).append(" ").append(event.info).append(" ").append(event.time);
            result.append("\n");
        }
        return result.toString();
    }
}
