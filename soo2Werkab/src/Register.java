import java.util.Scanner;

public class Register {
    Account acc;
    boolean isDriver = false;
    DataRetriever data = DataRetriever.getInstance();
    public Register(Account account,boolean isDriver){
        acc=account;
        this.isDriver=isDriver;
        if(!isDriver){
            data.UserRegister(account);
        }else{
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter national ID: ");
            String nationalId = in.next();
            System.out.println("Please enter the licence number: ");
            String licenceNo = in.next();
            Driver d = new CarDriver(acc,nationalId,licenceNo);
            data.DriverRegister(d);
        }
    }

}
