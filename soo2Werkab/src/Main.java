import java.util.Scanner;

public class Main {
    static int choice;
    static boolean isLoggedIn = false,isDriver = false;
    static Scanner in = new Scanner(System.in);
    static String username,password,email,phoneNo;
    public static void main(String[] args) {
        System.out.println("Welcome to soo2Werkab");
        System.out.println("1- Login\n2- Register");
        choice = in.nextInt();
      switch (choice){
          case 1:
              System.out.println("Please enter username: ");
              username=in.next();
              System.out.println("Please enter the password: ");
              password=in.next();
              Login l = new Login(username,password);
              isDriver = l.isDriver;
              isLoggedIn=true;
              break;
          case 2:
              System.out.println("Please enter username: ");
              username=in.next();
              System.out.println("Please enter the password: ");
              password=in.next();
              System.out.println("Please enter Email (Leave blank if there is no email): ");
              in.skip("\n");
              email= in.nextLine();
              System.out.println("Please enter the phone number: ");
              phoneNo=in.next();
              Account acc = new Account(username,password,email,phoneNo);
              System.out.println("1- To register as user\n2- To register as driver");
              choice = in.nextInt();
              switch (choice){
                  case 1:
                      Register r = new Register(acc,false);
                      break;
                  case 2:
                       r = new Register(acc,true);
                       break;
              }
              isLoggedIn=true;
              password="";
              email="";
              phoneNo="";
              break;
      }while(isLoggedIn){
            if(isDriver){
                System.out.println("I am driver");
                break;
            }else{
                System.out.println("I amnot a driver");
                break;
            }
        }
    }
}
