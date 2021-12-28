import java.util.ArrayList;
import java.util.Scanner;

public class UserUI {
    int choice;
    Scanner in = new Scanner(System.in);
    String username,source,dest;
    UserUI(User user){
        UserOperations userOperations = new UserOperations();
        System.out.println("1- Request ride\n2- Get offers \n3- To Rate driver\n4- Logout");
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
                userOperations.requestRide(user,src,destination);
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
                                System.out.println(RideId++ + " " + offer.toString() + "\n" + offer.getOfferPrice() + "$");
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
            case 2:
                System.out.println("=======================================");
                while (choice > - 1) {
                    ArrayList<Offer> offers = userOperations.getOffers(user);
                    int RideId = 1;
                    for (Offer offer : offers) {
                        System.out.println("===================================================");
                        System.out.println(RideId++ + "- Driver: " + offer.toString() + "\nPrice: " + offer.getOfferPrice() + "$\nRating: "+offer.getAvgRating());
                        System.out.println("===================================================");
                    }
                    System.out.println("Enter 0 to refresh list or -1 to exit");
                    choice = in.nextInt();
                }
                System.out.println("=======================================");
                break;
            case 3:
                System.out.println("=======================================");
                System.out.println("Please Enter driver username");
                String uName = in.next();
                System.out.println("Please enter the number of stars");
                int stars = in.nextInt();
                if(stars > 5 && stars < 1){
                    System.out.println("Rate a number from 1 to 5");
                }else {
                    userOperations.rateDriver(user,uName,stars);
                }
                System.out.println("=======================================");
                break;
            case 4:
                System.out.println("=======================================");
                System.out.println("Logging out");
                System.out.println("=======================================");
                Main.isLoggedIn = false;
                return;

            default:
                System.out.println("Wrong username");
        }
    }
}
