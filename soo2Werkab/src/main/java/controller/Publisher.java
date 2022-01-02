package controller;

public class Publisher {
    public  EventManager events;

    public Publisher(){
        events = new EventManager();
    }

    public void notifyListeners(String eventType,Object data){ //TODO ADD PARAMETERS or override in areas and ride
        events.notify(eventType,data);
    }
}
