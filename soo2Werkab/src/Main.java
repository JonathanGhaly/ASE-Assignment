import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static int choice;
    static boolean isLoggedIn = false,isDriver = false;
    static CarDriver driver;
    static Scanner in = new Scanner(System.in);
    static String username,password,email,phoneNo,source,dest;
    public static void main(String[] args) {
        System.out.println("Welcome to soo2Werkab");
        System.out.println("1- Login\n2- Register");
        choice = in.nextInt();
      switch (choice){
          case 1: {
              System.out.println("Please enter username: ");
              username = in.next();
              System.out.println("Please enter the password: ");
              password = in.next();
              Login l = new Login(username, password);
              isDriver = l.isDriver;
              if (isDriver) {
                  driver = l.getCarDriver(username);
              }
              isLoggedIn = true;
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
                      isDriver=true;
                      break;
              }
              isLoggedIn = true;
              password = "";
              email = "";
              phoneNo = "";
              break;
          }
      }while(isLoggedIn){
            if(isDriver){
                System.out.println("1- Set favorite area\n2- List favorite areas");
                choice = in.nextInt();
                switch (choice){
                    case 1:
                        System.out.println("Please Enter area to add to favorite");
                        in.skip("\n");
                        source = in.nextLine();
                        Area a= new Area(source);
                        driver.addFavouriteArea(a);
                        break;
                    case 2:
                        ArrayList<Area> areas = driver.getFavouriteAreas();
                        for(Area area : areas){
                            System.out.println(area.areaName);
                        }
                        break;
                }

            }else{
                System.out.println("Please enter the source: ");
                source = in.next();
                System.out.println("Please enter the destination");
                dest = in.next();
                Area src = new Area(source);
                Area destination = new Area(dest);
                Ride ride = new Ride(src,destination); //TODO change to Car Request
            }
        }
    }
}
