package controller;

import java.util.HashMap;

public class EventManager {
    private HashMap<String, Account> listeners;

    EventManager() {
        this.listeners = new HashMap<>();
    }

    public void subscribe(String eventType,Account listener){
        listeners.put(eventType,listener);
    }
    public void unsubscribe(String eventType,Account listener){
        listeners.remove(eventType,listener);
    }
    public void notify(String eventType,Object data){
        for (Account listener: listeners.values()){
            listener.update();
        }
    }

}

