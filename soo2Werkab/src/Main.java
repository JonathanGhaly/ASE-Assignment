import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static int choice;
    static boolean isLoggedIn = false, isDriver = false, isAdmin = false;
    static CarDriver driver = null;
    static User user = null;
    static Admin admin = null;
    static Scanner in = new Scanner(System.in);
    static String username, password, email, phoneNo, source, dest;
    static double offer=0;
    public static void main(String[] args) {
        System.out.println("Welcome to soo2Werkab");
        System.out.println("1- Login\n2- Register");
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
                    driver = l.getCarDriver();
                } else {
                    user = l.getUser();
                }
                isLoggedIn = l.getIsLoggedin();
                break;
            }
            case 2: {
                System.out.println("Please enter username: ");
                username = in.next();
                System.out.println("Please enter the password: ");
                password = in.next();
                System.out.println("Please enter Email (Leave blank if there is no email): ");
                in.skip("\n");
                email = in.nextLine();
                System.out.println("Please enter the phone number: ");
                phoneNo = in.next();
                Account acc = new Account(username, password, email, phoneNo);
                System.out.println("1- To register as user\n2- To register as driver");
                choice = in.nextInt();
                switch (choice) {
                    case 1:
                        Register r = new Register(acc, false);
                        break;
                    case 2:
                        r = new Register(acc, true);
                        isDriver = true;
                        break;
                    default:
                        System.out.println("Wrong command");
                }
                isLoggedIn = true;
                password = "";
                email = "";
                phoneNo = "";
                break;
            }
        }
        while (isLoggedIn) {
            if (isAdmin) {
                System.out.println("1- Verify Driver\n2- Suspend user\n3- Unsuspend user");
                int choice = in.nextInt();
                switch (choice) {
                    case 1:
                    System.out.println("Enter driver's username");
                    username = in.next();
                    admin.verifyDriver(username);
                        break;
                    case 2:
                    System.out.println("Enter username");
                    username = in.next();
                    admin.suspend(username, 1);
                        break;
                    case 3:
                    System.out.println("Enter username");
                    admin.suspend(username, 0);
                    break;
                    case -1:
                        return;
                    default:
                        System.out.println("Wrong command");
                }
            } else if (isDriver) {
                System.out.println("1- Set favorite area\n2- List favorite areas\n3- List favorite rides available\n4- Show my rating");

                choice = in.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Please Enter area to add to favorite");
                        in.skip("\n");
                        source = in.nextLine();
                        Area a = new Area(source);
                        driver.addFavouriteArea(a);
                        break;
                    case 2:
                        ArrayList<Area> areas = driver.getFavouriteAreas();
                        for (Area area : areas) {
                            System.out.println(area.areaName);
                        }
                        break;
                    case 3:
                        while (choice >-1) {
                            ArrayList<Ride> rides = driver.listAllRides();
                            for(Ride ride : rides){
                                System.out.println(ride.getRideID() +" from "+ride.getSourceArea().toString()+" to "+ride.getDestinationArea().toString());
                            }
                            System.out.println("Enter 0 to refresh list or Enter ride ID to make offer");
                            int driverOffer = in.nextInt();
                            if(driverOffer > 0){
                                for(Ride ride : rides){
                                    if(ride.getRideID() == driverOffer){
                                        System.out.println("Please enter the price");
                                        Integer offerPrice = in.nextInt();
                                        Offer offer = new Offer(offerPrice,driver);
                                        driver.sendOffer(ride,offerPrice);
                                    }
                                }
                            }
                            System.out.println("Enter 0 to refresh list or -1 to exit");
                            choice = in.nextInt();
                        }
                        break;
                    case 4:
                        System.out.println("Your rate is: " + driver.rate());
                        break;
                    case -1:
                        return;
                    default:
                        System.out.println("Wrong command");
                }

            } else {
                System.out.println("1- To request ride\n2- To Rate driver");
                choice = in.nextInt();
                switch (choice){
                    case 1:{
                        System.out.println("Please enter the source: ");
                        source = in.next();
                        System.out.println("Please enter the destination");
                        dest = in.next();
                        Area src = new Area(source);
                        Area destination = new Area(dest);
                        user.requestRide(src,destination);
                        System.out.println("1- Get available offers");
                        choice = in.nextInt();
                        switch (choice){
                            case 1:
                                while (choice > -1){
                                    ArrayList<Offer> offers = user.getOffers();
                                    int RideId = 0;
                                    for(Offer offer:offers){
                                        System.out.println(RideId++ +" "+offer.toString()+"\n"+offer.getOfferPrice()+"$");
                                    }
                                    System.out.println("Enter 0 to refresh list or -1 to exit");
                                    choice = in.nextInt();
                                }
                                break;
                            default:
                                System.out.println("Wrong command");
                        }
                    }
                    case 2:
                        System.out.println("Please Enter driver username");
                        String uName = in.next();
                        System.out.println("Please enter the number of stars");
                        int stars = in.nextInt();
                        user.rateDriver(uName,stars);
                        break;
                    case -1:
                        return;

                    default:
                        System.out.println("Wrong username");
                }

            }
        }
    }
}
