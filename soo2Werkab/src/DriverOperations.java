import java.util.Scanner;

public class DriverOperations {
    Scanner sc = new Scanner(System.in);

    String showRating(Driver driver) {
        DataRetriever db = DataRetriever.getInstance();
        return "Your current rating is: " + db.getRating(db.getID(driver.account.getUsername()));
    }

    void notifyOfRequest(Driver driver, CarRequest request) {
        System.out.println("DO you want to accept the ride with : ");
        System.out.println(request.toString());
        sc = new Scanner(System.in);
        Boolean choice = sc.nextBoolean();
        request.responce(choice);
    }

}
