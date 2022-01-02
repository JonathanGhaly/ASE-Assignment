import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

public class Event {
    String type;
    String source, info;
    int rideID;
    String time;

    Event(EventType eventType, String source, String info, int rideID, Date time) {
        this.type = eventType.toString();
        this.source = source;
        this.info = info;
        this.rideID = rideID;
        this.time = time.toString();
    }

    Event(EventType eventType, String source, String info, int rideID, String time) {
        this.type = eventType.toString();
        this.source = source;
        this.info = info;
        this.rideID = rideID;
        this.time = time;
    }

}
