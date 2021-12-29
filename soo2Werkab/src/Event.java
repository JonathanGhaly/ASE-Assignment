import java.util.Calendar;
import java.util.SimpleTimeZone;

public class Event {
    String type;
    int rideID;
    String time;

    Event(EventType eventType, int rideID, Calendar time){
        this.type = eventType.toString();
        this.rideID = rideID;
        this.time = time.toString();
    }
    Event(EventType eventType, int rideID, String time){
        this.type = eventType.toString();
        this.rideID = rideID;
        this.time = time;
    }

}
