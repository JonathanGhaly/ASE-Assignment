package controller;

public class Area extends Publisher {
    public String areaName;
    Driver[] drivers;

    Area() {
        areaName = "";
    }

    public Area(String areaName) {
        this.areaName = areaName;
    }

    Area(String areaName, Integer discount) {
        this.areaName = areaName;
    }

    public Area(String areaName, Driver[] drivers) { // used in DB
        this.areaName = areaName;
        this.drivers = new Driver[drivers.length];
        for (int i = 0; i < drivers.length; i++)
            this.drivers[i] = drivers[i];
    }

//    void notifyDrivers(controller.CarRequest request) {
//        for (int i = 0; i < drivers.length; i++)
//            drivers[i].notifyOfRequest(request);
//    }

    void notifyDrivers(CarRequest request) {
        events.notify("New controller.Ride", request);
    }

    @Override
    public String toString() {
        return areaName;
    }


}
