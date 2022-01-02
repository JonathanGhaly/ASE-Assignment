package controller;

import java.util.Date;

public class Event {
    String type;
    String source, info;
    int rideID;
    String time;

    public Event(EventType eventType, String source, String info, int rideID, Date time) {
        this.type = eventType.toString();
        this.source = source;
        this.info = info;
        this.rideID = rideID;
        this.time = time.toString();
    }

    public Event(String eventType, String source, String info, int rideID, String time) {
        this.type = eventType;
        this.source = source;
        this.info = info;
        this.rideID = rideID;
        this.time = time;
    }
    public String getType(){return type;}
    public String getSource(){return source;}
    public String getInfo(){return info;}
    public String getTime(){return time;}
    public int getRideID(){return rideID;}
}
