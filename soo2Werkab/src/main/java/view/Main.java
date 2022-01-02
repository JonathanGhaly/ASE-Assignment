package view;

import java.util.Scanner;

import controller.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    static int choice;
    static boolean isLoggedIn = false, isDriver = false, isAdmin = false;
    static Driver driver = null;
    static User user = null;
    static Admin admin = null;
    static Scanner in = new Scanner(System.in);
    static String username, password, email, phoneNo, birthDayString;
    static UI ui;

    public static void main(String[] args) {
        System.out.println("Welcome to soo2Werkab");
        System.out.println("1- controller.Login\n2- controller.Register");
        choice = in.nextInt();
        switch (choice) {
            case 1: {
                System.out.println("Please enter username: ");
                username = in.next();
                System.out.println("Please enter the password: ");
                password = in.next();
                Login l = new Login(username, password);
                isDriver = l.isDriver();
                isAdmin = l.isAdmin;
                if (isAdmin) {
                    admin = l.getAdmin();
                } else if (isDriver) {
                    driver = l.getDriver();
                } else {
                    user = l.getUser();
                }
                isLoggedIn = l.getIsLoggedin();
                break;
            }
            case 2: {
                Register r = new Register();
                System.out.println("Please enter username: ");
                username = in.next();
                System.out.println("Please enter the password: ");
                password = in.next();
                System.out.println("Please enter Email (Leave blank if there is no email): ");
                in.skip("\n");
                email = in.nextLine();
                System.out.println("Please enter the phone number: ");
                phoneNo = in.next();
                if (! r.validateMobileNumber(phoneNo)) {
                    System.out.println("===========================================");
                    System.out.println("Wrong phone number format please try again");
                    System.out.println("===========================================");
                    break;
                }
                System.out.println("Please enter Your BirthDate Day/Month/Year: ");
                birthDayString = in.next();
//                Date dt = new Date();
//                try {
//                    SimpleDateFormat BirthDay = new SimpleDateFormat("dd/MM/YYYY", Locale.ENGLISH);
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//                    dt = BirthDay.parse(birthDayString);
//
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
                Account acc = new Account(username, password, email, phoneNo, birthDayString);
                System.out.println("1- To register as user\n2- To register as driver");
                choice = in.nextInt();
                switch (choice) {
                    case 1:
                        r = new Register(acc, false);
                        isDriver = false;
                        break;
                    case 2:
                        r = new Register(acc, true);
                        isDriver = true;
                        break;
                    default:
                        System.out.println("Wrong command");
                }
                Login l = new Login(username, password);
                isDriver = l.isDriver();
                isAdmin = l.isAdmin;
                isLoggedIn = l.getIsLoggedin();
                password = "";
                email = "";
                phoneNo = "";
                break;
            }
        }
        while (isLoggedIn) {
            if (isAdmin) {
                ui = new AdminUI(admin);
            } else if (isDriver) {
                ui = new DriverUI(driver);
            } else {
                ui = new UserUI(user);
            }
        }
    }
}
