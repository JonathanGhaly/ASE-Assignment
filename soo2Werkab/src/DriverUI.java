import java.util.Scanner;

public class DriverUI implements UI {

    DriverOperations driverOperations;
    int choice;
    Scanner in = new Scanner(System.in);
    String source;

    DriverUI(Driver driver) {
        driverOperations = new DriverOperations();
        if (driver.getDriverStatus().equals("idle")) {
            System.out.println("1- Set favorite area\n2- List favorite areas\n3- List favorite rides available\n4- Show my rating\n5- Remove favorite area\n6- Logout");
            choice = in.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("=======================================");
                    System.out.println("Please Enter area to add to favorite");
                    in.skip("\n");
                    source = in.nextLine();
                    Area a = new Area(source);
                    driverOperations.addFavouriteArea(driver, a);
                    System.out.println("=======================================");
                    break;
                case 2:
                    for (Area area : driverOperations.getFavouriteAreas(driver)) {
                        System.out.println(area.areaName);
                    }
                    break;
                case 3:
                    while (choice > - 1) {
                        System.out.println("=======================================");
                        for (Ride ride : driverOperations.listAllRides(driver)) {
                            System.out.println(ride.getRideID() + " from " + ride.getSourceArea().toString() + " to " + ride.getDestinationArea().toString());
                        }
                        System.out.println("Enter 0 to refresh list or Enter ride ID to make offer");
                        int driverOffer = in.nextInt();
                        if (driverOffer > 0) {  //TODO Refactor  Known Bug didnt refresh correctly
                            for (Ride ride : driverOperations.listAllRides(driver)) {
                                if (ride.getRideID() == driverOffer) {
                                    System.out.println("Please enter the price");
                                    Double offerPrice = in.nextDouble();
                                    Offer offer = new Offer(offerPrice, driver, ride.getRideID());
                                    driverOperations.sendOffer(driver, ride, offerPrice);
                                    choice = 0;
                                }
                            }
                        }
                        System.out.println("Enter 0 to refresh list or -1 to exit"); //TODO ADD REFRESH
                        choice = in.nextInt();
                        System.out.println("=======================================");

                    }
                    break;
                case 4:
                    System.out.println("=======================================");
                    System.out.println("Your rate is: " + driverOperations.showRating(driver));
                    System.out.println("=======================================");

                    break;
                case 5:
                    System.out.println("=======================================");
                    System.out.println("Please enter the area name you want to remove");
                    String areaName = in.next();
                    Area area = new Area(areaName);
                    driverOperations.removeFavouriteArea(driver, area);
                    System.out.println("=======================================");
                    break;
                case 6:
                    System.out.println("=======================================");
                    System.out.println("Logging out");
                    System.out.println("=======================================");
                    Main.isLoggedIn = false;
                    return;
                default:
                    System.out.println("Wrong command");
            }
        } else if (driver.getDriverStatus().equals("InRide")) {
            System.out.println("Press 1 to end the ride");
            choice = in.nextInt();
            if (choice == 1) {
                driverOperations.endRide(driver);
            }
        }
    }
}
