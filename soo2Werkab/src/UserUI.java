import java.util.ArrayList;
import java.util.Scanner;

public class UserUI implements UI {
    int choice;
    Scanner in = new Scanner(System.in);
    String username, source, dest;
    int passengersNo;
    Discount discount;

    UserUI(User user) {
        UserOperations userOperations = new UserOperations();
        if (user.getUserStatus().equals("idle")) {
            System.out.println("1- Request alone ride \n2- Request share ride\n3- Get offers\n4- Join shared ride \n5- To Rate driver\n6- Logout");
            choice = in.nextInt();
            switch (choice) {
                case 1: {
                    System.out.println("=======================================");
                    System.out.println("Please enter the source: ");
                    source = in.next();
                    System.out.println("Please enter the destination");
                    dest = in.next();
                    Area src = new Area(source);
                    Area destination = new Area(dest);
                    userOperations.requestRide(user, src, destination, 0, true);
                    System.out.println("=======================================");
                    System.out.println("1- Get available offers");
                    choice = in.nextInt();
                    System.out.println("=======================================");
                    switch (choice) {
                        case 1:
                            while (choice > - 1) {
                                ArrayList<Offer> offers = userOperations.getOffers(user);
                                int RideId = 1;
                                for (Offer offer : offers) {
                                    discount = new AreaDiscount(offer.rideID);
                                    double price = offer.getOfferPrice();
                                    price = discount.getDiscount(price);
                                    discount = new BirthdayDiscount(user);
                                    price = discount.getDiscount(price);
                                    discount = new HolidayDiscount();
                                    price = discount.getDiscount(price);
                                    discount = new FirstRideDiscount(user);
                                    price = discount.getDiscount(price);
                                    discount = new PassengerNumDiscount(offer.rideID);
                                    price = discount.getDiscount(price);
                                    System.out.println(RideId++ + " " + price + "\n" + offer.getOfferPrice() + "$");
                                }
                                System.out.println("Enter offer id to accept or 0 to refresh list or -1 to exit");
                                choice = in.nextInt();
                                if (choice > 1) {
                                    userOperations.acceptRide(user, offers.get(choice - 1));
                                    break;
                                }
                            }
                            System.out.println("=======================================");

                            break;
                        default:
                            System.out.println("Wrong command");
                    }
                    break;
                }
                case 2: {
                    System.out.println("=======================================");
                    System.out.println("Please enter the source: ");
                    source = in.next();
                    System.out.println("Please enter the destination");
                    dest = in.next();
                    Area src = new Area(source);
                    Area destination = new Area(dest);
                    System.out.println("Please enter the number of passengers");
                    passengersNo = in.nextInt();
                    if (passengersNo > 3 || passengersNo < 1) {
                        System.out.println("Invalid number of passengers");
                        break;
                    }
                    userOperations.requestRide(user, src, destination, passengersNo, false);
                    System.out.println("=======================================");
                    System.out.println("1- Get available offers");
                    choice = in.nextInt();
                    System.out.println("=======================================");
                    switch (choice) {
                        case 1:
                            while (choice > - 1) {
                                ArrayList<Offer> offers = userOperations.getOffers(user);
                                int RideId = 1;
                                for (Offer offer : offers) {
                                    discount = new AreaDiscount(offer.rideID);
                                    double price = offer.getOfferPrice();
                                    price = discount.getDiscount(price);
                                    discount = new BirthdayDiscount(user);
                                    price = discount.getDiscount(price);
                                    discount = new HolidayDiscount();
                                    price = discount.getDiscount(price);
                                    discount = new FirstRideDiscount(user);
                                    price = discount.getDiscount(price);
                                    discount = new PassengerNumDiscount(offer.rideID);
                                    price = discount.getDiscount(price);
                                    System.out.println(RideId++ + " " + price + "\n" + offer.getOfferPrice() + "$");
                                }
                                System.out.println("Enter 0 to refresh list or -1 to exit");
                                choice = in.nextInt();
                            }
                            System.out.println("=======================================");

                            break;
                        default:
                            System.out.println("Wrong command");
                    }
                    break;
                }
                case 3: {
                    System.out.println("=======================================");
                    while (choice > - 1) {
                        int RideId = 1;
                        ArrayList<Offer> offers = userOperations.getOffers(user);
                        for (Offer offer : offers) {
                            System.out.println("===================================================");
                            discount = new AreaDiscount(offer.rideID);
                            double price = offer.getOfferPrice();
                            price = discount.getDiscount(price);
                            discount = new BirthdayDiscount(user);
                            price = discount.getDiscount(price);
                            discount = new HolidayDiscount();
                            price = discount.getDiscount(price);
                            discount = new FirstRideDiscount(user);
                            price = discount.getDiscount(price);
                            discount = new PassengerNumDiscount(offer.rideID);
                            price = discount.getDiscount(price);
                            System.out.println(RideId++ + "- Driver: " + offer.toString() + "\nPrice: " + price + "$\nRating: " + offer.getAvgRating());
                            System.out.println("===================================================");
                        }
                        System.out.println("Enter offer id to accept or 0 to refresh list or -1 to exit");
                        choice = in.nextInt();
                        if (choice > 0) {
                            userOperations.acceptRide(user, offers.get(choice - 1));
                            break;
                        }
                    }
                    System.out.println("=======================================");
                    break;
                }
                case 4: {
                    System.out.println("=======================================");
                    System.out.println("Please enter the source: ");
                    source = in.next();
                    System.out.println("Please enter the destination");
                    dest = in.next();
                    Area src = new Area(source);
                    Area destination = new Area(dest);
                    src = new Area(source);
                    destination = new Area(dest);
                    while (choice > - 1) {
                        for (Ride ride : userOperations.getCarpoolRides(user, src, destination)) {
                            System.out.println("Ride ID: " + ride.getRideID() + " Source: " + ride.getSourceArea() + " Destination: " + ride.getDestinationArea() + " Passenger Number: " + ride.getPassengersNo());
                        }
                        System.out.println("Enter 0 to refresh list or -1 to exit");
                        choice = in.nextInt();
                    }
                    System.out.println("=======================================");
                    break;
                }
                case 5: {
                    System.out.println("=======================================");
                    System.out.println("Please Enter driver username");
                    String uName = in.next();
                    System.out.println("Please enter the number of stars");
                    int stars = in.nextInt();
                    if (stars > 5 && stars < 1) {
                        System.out.println("Rate a number from 1 to 5");
                    } else {
                        userOperations.rateDriver(user, uName, stars);
                    }
                    System.out.println("=======================================");
                    break;
                }
                case 6:
                    System.out.println("=======================================");
                    System.out.println("Logging out");
                    System.out.println("=======================================");
                    Main.isLoggedIn = false;
                    return;

                default:
                    System.out.println("Wrong username");
            }
        } else if (user.getUserStatus().equals("InRide")) {
            int c = 0;
            while (c++ <= 0) {
                System.out.println("=======================================");
                System.out.println("You are in a ride");
                System.out.println("=======================================");
            }

        } else if (user.getUserStatus().equals("complete")) {
            System.out.println("=======================================");
            System.out.println("Please enter the number of stars");
            int stars = in.nextInt();
            if (stars > 5 && stars < 1) {
                System.out.println("Rate a number from 1 to 5");
            } else {
                userOperations.rateDriver(user, userOperations.getDriverName(user), stars);
            }
            userOperations.setStatus(user, "idle");
            System.out.println("=======================================");
        }
    }
}
