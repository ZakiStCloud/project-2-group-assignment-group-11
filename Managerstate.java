import java.util.*;
import java.text.*;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class ManagerMenuState extends WarehouseState {

  private static Warehouse warehouse;
  Scanner scanner;


  //Singleton pattern:
  private static ManagerMenuState managerMenuState;
  private ManagerMenuState() { //Private constructor
      super();
      warehouse = warehouse.instance(); //If the warehouse doesn't already exist create it.
      this.scanner = new Scanner(System.in);
  }

  public static ManagerMenuState instance() { //Instance method
    if (managerMenuState == null) {
      managerMenuState = new ManagerMenuState();
    }
    return managerMenuState;
  }

  //Options for process and help
    private static final int ADD_PRODUCT = 0;
    private static final int DISPLAY_WAITLIST = 1;
    private static final int RECEIVE_SHIPMENT = 2;
    private static final int BECOME_CLERK = 3;
    private static final int EXIT = 4;
    private static final int HELP = 5;

    public void help() {
      System.out.println("Enter a number between 0 and 5 as explained below:");
      System.out.println(ADD_PRODUCT + " to add new products to the warehouse.");
      System.out.println(DISPLAY_WAITLIST + " to view a product's waitlist.");
      System.out.println(BECOME_CLERK + "to become a clerk.");
      System.out.println(EXIT + " to Exit\n");
      System.out.println(HELP + " for help");
  }

    //Directly from UserInterface.java
   private void addProduct() {
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Product Quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Product Price: ");
        double price = Double.parseDouble(scanner.nextLine());
        warehouse.addProduct(name, price, quantity);
    }

    //Directly from UserInterface.java
    public void displayWaitlist() {
       System.out.print("Enter product ID: ");
       System.out.println(warehouse.getWaitlist(scanner.nextLine())); //getWaitList returns the string of its results.

}
    //Directly from UserInterface.java
    private void receiveShipment() {
        System.out.print("Enter product ID: ");
        String productId = scanner.nextLine().trim();
        System.out.print("Enter quantity received: ");
        int qty = Integer.parseInt(scanner.nextLine().trim());

        boolean success = warehouse.receiveShipment(productId, qty);
            if (success) {
            System.out.println("Shipment recorded.");
            } else {
            System.out.println("Shipment not recorded.");
            waitForEnter();
            }

    }

    private void waitForEnter(){
        System.out.println("\nPress 'Enter' to return...");
        scanner.nextLine();
    }

    //Transition to clerk state
    public boolean becomeClerk() {
      int transitionToClerk = 2;
      WarehouseContext.instance()).changeState(transitionToClerk);
    }

 
    public void logout() {
    int transitionToLogin = 0;
    int transitionErrorState = 4; //Check with group about error state code. 
    //This should be the only check you do.
    //Someone can't login as client or clerk and see the manager menu. 
    if (WarehouseContext.instance().getLogin() == WarehouseContext.IsManager) 
       {  //System.out.println("going to login \n");
        (WarehouseContext.instance()).changeState(transitionToLogin); // to login state.
       }
    else 
       (WarehouseContext.instance()).changeState(transitionErrorState); // go to an error state
  }
    
    public void process() {
    int command;
    help();
    boolean done = false;
    while (!done) {
      switch (getCommand()) { //Only should break out of the switch case loop to change state or exit. 
        case ADD_PRODUCT:        addProduct();
                                 break;
        case DISPLAY_WAITLIST:   displayWaitlist();
                                 break;
        case RECEIVE_SHIPMENT:   receiveShipment();
                                 break;
        case BECOME_CLERK:       becomeClerk();
                                 done = true;
                                 break;
        case HELP:               help();
                                 break;
        case EXIT:               logout();
                                 done = true;
                                 break;
      }
    }
    logout();
  }


  public void run() {
    process();
  }
}
