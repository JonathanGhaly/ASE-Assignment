package api;

import java.util.ArrayList;
import controller.Admin;
import controller.Login;
import controller.*;
import controller.UserOperations;
import org.springframework.web.bind.annotation.*;
public class UserRequestHandler{
    UserOperations userOps = new UserOperations();

    static User user;
    @GetMapping("User/Login")
    public User userLogin(@RequestBody Login login){
        user = login.getUser();
        return user;
    }

    @GetMapping("User/requestRide/{source}/{destination}/{passengersNo}/{aloneStatus}")
    public void requestRide(@PathVariable("source") String source,@PathVariable("destination") String destination,@PathVariable("passengersNo") String passengersNo,@PathVariable("aloneStatus") boolean aloneStatus){
        Area sources = new Area(source);
        Area destinations = new Area(destination);
        int no = Integer.parseInt(passengersNo);
        UserOperations userOps = new UserOperations();
        userOps.requestRide(user,sources,destinations,no,aloneStatus);
    }

    @GetMapping("User/getOffers")
    public ArrayList<Offer> getOffers(){
        UserOperations userOps = new UserOperations();
        return userOps.getOffers(user);
    }
    @GetMapping("User/rateDriver/{driverName}/{rating}")
    public void rateDriver(@PathVariable("driverName") String driverName,@PathVariable("rating") String rating){
        UserOperations userOps = new UserOperations();
        int rate = Integer.parseInt(rating);
        userOps.rateDriver(user,driverName,rate);
    }
    @GetMapping("User/getCarpoolRides/{source}/{destination}")
    public ArrayList<Ride> getCarpoolRides(@PathVariable("source") String source,@PathVariable("destination") String destination){
        UserOperations userOps = new UserOperations();
        Area src = new Area(source);
        Area dest = new Area(destination);
        return userOps.getCarpoolRides(user,src,dest);
    }
    @GetMapping("User/joinRide/{RideID}")
    public void joinRide(@PathVariable String rideID){
        UserOperations userOps = new UserOperations();
        userOps.joinRide(user,Integer.parseInt(rideID));
    }
    @GetMapping("User/acceptRide")
    public void acceptRide(@PathVariable Offer offer){
        UserOperations userOps = new UserOperations();
        userOps.acceptRide(user,offer);
    }
    @PostMapping("User/getDriverName")
    public String getDriverName(){
        UserOperations userOps = new UserOperations();
        return userOps.getDriverName(user);
    }
    @GetMapping("User/setStauts/{status}")
    public void setStatus(@PathVariable String status){
        UserOperations userOps = new UserOperations();
        userOps.setStatus(user,status);
    }


}
