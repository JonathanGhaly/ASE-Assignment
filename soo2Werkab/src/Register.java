import java.util.Scanner;

public class Register {
    Account acc;
    boolean isDriver = false;
    DataRetriever data = DataRetriever.getInstance();

    public Register() {
    }

    public Register(Account account, boolean isDriver) {
        acc = account;
        this.isDriver = isDriver;
        if (! isDriver) {
            data.UserRegister(account);
        } else {
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter national ID: ");
            String nationalId = in.next();
            System.out.println("Please enter the licence number: ");
            String licenceNo = in.next();
            Driver d = new Driver(acc, nationalId, licenceNo, false, false, 0);
            data.DriverRegister(d);
        }
    }

    /**
     * This method is used to check if the phone number is valid in Egypt
     *
     * @param mobileNumber the phone number to check
     * @return false if number is not valid true if valid
     */

    public boolean validateMobileNumber(String mobileNumber) {
        if (mobileNumber.length() != 11) return false;

        String firstThree = mobileNumber.substring(0, 3);
        if (! firstThree.equals("010") && ! firstThree.equals("011") && ! firstThree.equals("012") && ! firstThree.equals("015"))
            return false;
        return true;
    }

}
