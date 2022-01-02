package api;

import controller.*;
import model.DataRetriever;
import org.springframework.web.bind.annotation.*;
import view.Main;

import java.util.ArrayList;

@RestController
public class DriverRequestHandler {
    Driver driver;
    DriverOperations op = new DriverOperations();
    @GetMapping("Driver/Login")
    public Driver adminLogin(@RequestBody Login login){
        driver = login.getDriver();
        return driver;
    }
    @GetMapping("Driver/AddArea/{area}")
    public void addArea(@PathVariable("area") String area){
        Area a = new Area(area);
        op.addFavouriteArea(driver, a);
    }
    @GetMapping("Driver/getArea")
    public ArrayList<Area> getFavouriteAreas(){
        ArrayList<Area> a = new ArrayList<>();
        for (Area area : op.getFavouriteAreas(driver)) {
            a.add(area);
            System.out.println(area.areaName);
        }
        return a;
    }
    @GetMapping("Driver/getRide")
    public ArrayList<Ride> getRides(){
        ArrayList<Ride> a = new ArrayList<>();
        for (Ride ride : op.listAllRides(driver)) {
            a.add(ride);
            System.out.println(ride.getRideID() + " from " + ride.getSourceArea().toString() + " to " + ride.getDestinationArea().toString());
        }
        return a;
    }
    @GetMapping("Driver/makeOffer/{rideID}/{offer}")
    public void makeOffer(@PathVariable("rideID") int rideID,@PathVariable("offer") double offerPrice){
        Offer offer = new Offer(offerPrice, driver,rideID);
        ArrayList<Ride> rides = op.listAllRides(driver);
        op.sendOffer(driver,rides.get(rideID),offerPrice);
    }
    @GetMapping("Driver/getRating")
    public ArrayList<Integer> getRatings(){
        return op.showRating(driver);
    }
    @GetMapping("Driver/logout")
    public void logout(){
        driver = null;
    }
    @PostMapping("Driver/endRide")
    public void endRide(){
        op.endRide(driver);
    }
}
