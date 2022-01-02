package view;

import controller.Admin;
import controller.AdminOperations;
import controller.Area;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminUI implements UI {
    int choice;
    Scanner in = new Scanner(System.in);
    String username;

    public AdminUI(Admin admin) {
        System.out.println("1- List not verified drivers\n2- Verify controller.Driver\n3- Suspend user\n4- Unsuspend user\n5- Set discount to area\n6- View events in ride\n7-Logout");
        int choice = in.nextInt();
        AdminOperations op = new AdminOperations();
        switch (choice) {
            case 1:
                System.out.println("=======================================");
                if (op.getNotVerifiedDrivers(admin).size() > 0) {
                    int ind = 1;
                    for (String driver : op.getNotVerifiedDrivers(admin)) {
                        System.out.println(ind++ + " " + driver);
                    }
                } else {
                    System.out.println("There is no drivers");
                }
                System.out.println("=======================================");
                break;
            case 2:
                System.out.println("=======================================");

                System.out.println("Enter driver's username");
                username = in.next();
                op.verifyDriver(admin, username);
                System.out.println("controller.Driver: " + username + " is verified successfully");
                System.out.println("=======================================");
                break;
            case 3:
                System.out.println("=======================================");
                System.out.println("Enter username");
                username = in.next();
                op.suspend(admin, username, 1);
                System.out.println("controller.User: " + username + " is suspended successfully");
                System.out.println("=======================================");
                break;
            case 4:
                System.out.println("=======================================");
                System.out.println("Enter username");
                username = in.next();
                op.suspend(admin, username, 0);
                System.out.println("controller.User: " + username + " is unsuspended successfully");
                System.out.println("=======================================");

                break;
            case 5:
                System.out.println("=======================================");
                System.out.println("Select an area");
                ArrayList<Area> areasList = op.listAllAreas(admin);
                int ind = 0;
                for (Area area : areasList) {
                    System.out.println(++ ind + " " + area.areaName);
                }
                System.out.println(++ ind + " -" + "Exit-");
                int areaChoice = in.nextInt();
                while (areaChoice < 1 || areaChoice > ind) {
                    System.out.println("Invalid input, enter your choice again");
                    areaChoice = in.nextInt();
                }
                if (areaChoice != ind) {
                    op.setDiscountToArea(admin, areasList.get(areaChoice - 1), 10);
                }
                break;
            case 6:
                System.out.println("=======================================");
                System.out.println("Enter the ride ID");
                int rideID = in .nextInt();
                String eventString = op.getEventsOfRide(rideID);
                System.out.println(eventString);
                System.out.println("=======================================");
            case 7:
                System.out.println("=======================================");
                System.out.println("Logging out");
                System.out.println("=======================================");
                Main.isLoggedIn = false;
                return;
            default:
                System.out.println("=======================================");
                System.out.println("Wrong command");
                System.out.println("=======================================");
        }
    }
}
