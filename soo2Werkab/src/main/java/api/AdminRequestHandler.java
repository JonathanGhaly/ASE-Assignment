package api;

import controller.*;
import model.DataRetriever;
import org.springframework.web.bind.annotation.*;
import view.*;

import javax.xml.crypto.Data;
import java.util.ArrayList;

@RestController
public class AdminRequestHandler {
    static Admin adm;

    @GetMapping("Admin/Login")
    public Admin adminLogin(@RequestBody Login login){
            adm = login.getAdmin();
        return adm;

    }
    @GetMapping("Admin/Suspend/{username}/{type}")
    public void suspendUser(@PathVariable("username") String username,@PathVariable("type") String type){
        AdminOperations adminops = new AdminOperations();
        adminops.suspend(adm,username,Integer.parseInt(type));
    }
    @GetMapping("Admin/verify/{username}")
    public void verifyDriver(@PathVariable String username){
        AdminOperations adminOps = new AdminOperations();
        adminOps.verifyDriver(adm,username);

    }
    @GetMapping("Admin/getNotVerifiedDrivers")
    public ArrayList<String> getNotVerifiedDrivers(){
        AdminOperations adminOps = new AdminOperations();
        ArrayList<String> op = new ArrayList<>();
        op = adminOps.getNotVerifiedDrivers(adm);
        return op;
    }
    @GetMapping("Admin/setDiscountToArea/{area}/{discount}")
    public void setDiscountToArea(@PathVariable String Area,@PathVariable String discount){
        AdminOperations adminOps = new AdminOperations();
        Area area = new Area(Area);
        adminOps.setDiscountToArea(adm,area,Integer.parseInt(discount));
    }
    @GetMapping("Admin/listAllAreas")
    public ArrayList<Area> listAllAreas(){
        AdminOperations adminOps = new AdminOperations();
        return adminOps.listAllAreas(adm);
    }
    @GetMapping("Admin/getEventsOfRide/{rideID}")
    public String getEventsOfRide(@PathVariable String rideID){
        AdminOperations adminOps = new AdminOperations();
       return adminOps.getEventsOfRide(Integer.parseInt(rideID));

    }
}
