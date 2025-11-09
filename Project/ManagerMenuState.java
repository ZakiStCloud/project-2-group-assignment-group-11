import java.util.*;
import java.text.*;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;


public class ManagerMenuState extends WarehouseState {

  private static Warehouse warehouse;
  Scanner reader;

  //Singleton pattern:
  private static ManagerMenuState managerMenuState;
  private ManagerMenuState() { //Private constructor
      super();
      warehouse = warehouse.instance(); //If the warehouse doesn't already exist create it.
      this.reader = new Scanner(System.in);
  }

  public String getToken(String prompt){ 
  do {
      try {
        System.out.println(prompt);
        String line = reader.nextLine();
        StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
        if (tokenizer.hasMoreTokens()) {
          return tokenizer.nextToken();
        }
      } catch (Exception e) {
        System.exit(0);
      }
    } while (true);
} 

  public static ManagerMenuState instance() { //Instance method
    if (managerMenuState == null) {
      managerMenuState = new ManagerMenuState();
    }
    return managerMenuState;
  }

  public int getCommand() {
    do {
      try {
        int value = Integer.parseInt(getToken(" "));
        if (value >= 0 && value <= 5) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
  }

  //Options for process and help
    private static final int ADD_PRODUCT = 0;
    private static final int DISPLAY_WAITLIST = 1;
    private static final int RECEIVE_SHIPMENT = 2;
    private static final int BECOME_CLERK = 3;
    private static final int PRINT_INVOICES = 4;
    private static final int EXIT = 5;
    private static final int HELP = 6;

    public void help() {
      System.out.println("\n==================== Manager Menu ====================");
      System.out.println("Enter a number to continue:");
      System.out.println(ADD_PRODUCT + ". Add Product");
      System.out.println(DISPLAY_WAITLIST + ". View Product Waitlist");
      System.out.println(RECEIVE_SHIPMENT + ". Receive Shipment");
      System.out.println(BECOME_CLERK + ". Become Clerk");
      System.out.println(PRINT_INVOICES + ". Show all Customer Invoices");
      System.out.println(EXIT + ". Logout");
      System.out.println(HELP + ". Help");
      System.out.print("Enter choice: ");
  }

    //Directly from UserInterface.java
   private void addProduct() {
        System.out.print("Enter Product Name: ");
        String name = reader.nextLine();
        System.out.print("Enter Product Quantity: ");
        int quantity = Integer.parseInt(reader.nextLine());
        System.out.print("Enter Product Price: ");
        double price = Double.parseDouble(reader.nextLine());
        warehouse.addProduct(name, price, quantity);
    }

    //Directly from UserInterface.java
    public void displayWaitlist() {
       System.out.print("Enter product ID: ");
       System.out.println(warehouse.getWaitlist(reader.nextLine())); //getWaitList returns the string of its results.

}
    //Directly from UserInterface.java
    private void receiveShipment() {
        System.out.print("Enter product ID: ");
        String productId = reader.nextLine().trim();
        System.out.print("Enter quantity received: ");
        int qty = Integer.parseInt(reader.nextLine().trim());

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
        reader.nextLine();
    }

    //Transition to clerk state
    public void becomeClerk() {
      int transitionToClerk = 2;
      WarehouseContext.instance().changeState(transitionToClerk);
    }

 
    public void logout() {
      int transitionToLogin = 0;
      WarehouseContext.instance().changeState(transitionToLogin); // to login state. 
  }
    
    public void process() {
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
        case PRINT_INVOICES: for (Transaction t : warehouse.getTransactions()) {
                        System.out.println(t.getInvoice());}
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
