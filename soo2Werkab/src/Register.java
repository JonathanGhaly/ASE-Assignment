import java.util.Scanner;

public class Register {
    Account acc;
    boolean isDriver = false;
    DataRetriever data = new DataRetriever();
    public Register(Account account,boolean isDriver){
        acc=account;
        this.isDriver=isDriver;
        if(!isDriver){
            data.UserRegister(account);
        }else{
            Scanner in = new Scanner(System.in);
            String nationalId = in.nextLine();
            String licenceNo = in.nextLine();
            Driver d = new Driver(acc,nationalId,licenceNo);
            data.DriverRegister(d);
        }
    }

}
