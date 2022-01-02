package api;

import controller.*;
import model.DataRetriever;
import org.springframework.web.bind.annotation.*;
import view.*;

import javax.xml.crypto.Data;
import java.util.ArrayList;

@RestController
public class RequeestHandler {
    static Admin adm;

    @GetMapping("Admin/Login")
    public Admin adminLogin(@RequestBody Login login){
            adm = login.getAdmin();
        return adm;

    }
    @PostMapping("Admin/Suspend/{username}")
    public void suspendUser(@PathVariable String username,@RequestParam int type){
        AdminOperations adminops = new AdminOperations();
        adminops.suspend(adm,username,type);
    }
    @PostMapping("Admin/verify/{username}")
    public void verifyDriver(@PathVariable String username){
        AdminOperations adminOps = new AdminOperations();
        adminOps.verifyDriver(adm,username);
    }
    @GetMapping("Admin/getNotVerifiedDrivers")
    public ArrayList<String> getNotVerifiedDrivers(){
        AdminOperations adminOps = new AdminOperations();
        adminOps.getNotVerifiedDriver();
    }

}
