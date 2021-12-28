import java.util.Scanner;

public class AdminUI {
    int choice;
    Scanner in = new Scanner(System.in);
    String username;
    AdminUI(Admin admin){
        System.out.println("1- List not verified drivers\n2- Verify Driver\n3- Suspend user\n4- Unsuspend user\n5- logout");
        int choice = in.nextInt();
        AdminOperations op = new AdminOperations();
        switch (choice) {
            case 1:
                System.out.println("=======================================");
                if(op.getNotVerifiedDrivers(admin).size()>0){
                    int ind = 1;
                    for(String driver : op.getNotVerifiedDrivers(admin)){
                        System.out.println(ind++ +" "+ driver);
                    }
                }else{
                    System.out.println("There is no drivers");
                }
                System.out.println("=======================================");
                break;
            case 2:
                System.out.println("=======================================");

                System.out.println("Enter driver's username");
                username = in.next();
                op.verifyDriver(admin,username);
                System.out.println("Driver: "+ username + " is verified successfully");
                System.out.println("=======================================");
                break;
            case 3:
                System.out.println("=======================================");
                System.out.println("Enter username");
                username = in.next();
                op.suspend(admin,username, 1);
                System.out.println("User: "+ username + " is suspended successfully");
                System.out.println("=======================================");
                break;
            case 4:
                System.out.println("=======================================");
                System.out.println("Enter username");
                username = in.next();
                op.suspend(admin,username, 0);
                System.out.println("User: "+ username + " is unsuspended successfully");
                System.out.println("=======================================");

                break;
            case 5:
                System.out.println("=======================================");
                System.out.println("Logging out");
                System.out.println("=======================================");
                Main.isLoggedIn=false;
                return;
            default:
                System.out.println("=======================================");
                System.out.println("Wrong command");
                System.out.println("=======================================");
        }
    }
}
