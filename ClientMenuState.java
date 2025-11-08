import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class ClientMenuState extends WarehouseState{

  Scanner scanner;
  private static Warehouse warehouse;
  private static ClientMenuState clientMenuState; //For singleton pattern

  //For options in process() and help():

    private static final int showClientDetails= 0; 
    private static final int showProducts = 1;
    private static final int showTransactions= 2; 
    private static final int addToWishList = 3; 
    private static final int displayWishlist = 4; 
    private static final int placeOrder = 5; 
    private static final int logout = 6; 

    public void help() {
        System.out.println("Enter a number between 0 and 6 as explained below:");
        System.out.println(showClientDetails + ""); 
        System.out.println(showProducts + "");
        System.out.println(showTransactions + ""); 
        System.out.println(addToWishList + ""); 
        System.out.println(displayWishlist + ""); 
        System.out.println(placeOrder + ""); 
        System.out.println(logout + ""); 
    }
   


  private ClientMenuState() { //Private constructor
      super();
      warehouse = warehouse.instance(); //If the warehouse doesn't already exist create it.
      this.scanner = new Scanner(System.in);
  }

    public static ClientMenuState instance() { // Instance method
        if (clientMenuState == null) {
            clientMenuState = new ClientMenuState();
        }
        return clientMenuState;
    }


    private void showClientDetails() {
        System.out.println("\nClientID | Name | Address | Balance");
        for (Client c : warehouse.listClients()) {
            System.out.println(c);
        }

    }

    private void showProducts() {
        System.out.println("ProductID | Name | Quantity | Price");
        for (Product p : warehouse.listProducts()) {
            System.out.println(p);
        }
    }
    private void showTransactions(){
         System.out.print("Enter client ID: ");
         System.out.println(warehouse.getWishList(scanner.nextLine()));
    }

    private void addToWishList(){
        System.out.print("Enter Client ID: ");
        String id = scanner.nextLine();
        Boolean x = true;
        while(x){
            System.out.print("Enter Product ID: ");
            String ProdID = scanner.nextLine();
            System.out.print("Enter Product Quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());
            warehouse.addToWishList(id, ProdID, quantity);
            System.out.print("Would you like to add another item? (y/n): ");
            String response = scanner.nextLine();
            if (response.equals("n")) {
                x = false;
            }

        }
    }

    private void displayWishlist(){
         System.out.print("Enter client ID: ");
         System.out.println(warehouse.getWishList(scanner.nextLine()));
    }

    private void placeOrder(){
        String clientId = promptValidClientId();
        System.out.println("Client found.");

    if (promptYesNo("Would you like to order the items on client " + clientId + "'s wishlist? (y/n): ")) {
        warehouse.orderWishlist(clientId);
        System.out.println("Wishlist order placed.");
    }
    if (promptYesNo("Would you like to place another order? (y/n): ")) {
        System.out.print("Enter product ID: ");
        String productId = scanner.nextLine().trim();
        int qty = promptPositiveInt("Enter product quantity: ");
        Map<String, Integer> orderItems = new HashMap<>();
        orderItems.put(productId, qty);

        warehouse.placeOrder(clientId, orderItems);
        System.out.println("Order placed for product " + productId + " x" + qty + ".");
        }else {
            System.out.println("No additional orders placed. Returning to main menu...");
            }

    }
       private String promptValidClientId() {
        while (true) {
            System.out.print("Enter client ID: ");
            String clientId = scanner.nextLine().trim();
            if (clientExists(clientId)) {
                return clientId;
            }
            System.out.println("Client not found. Please try again.");
        }
    }
    // Returns true for 'y' and false for 'n' (keeps asking otherwise).
    private boolean promptYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim().toLowerCase();
            if (s.equals("y")) return true;
            if (s.equals("n")) return false;
            System.out.println("Please enter 'y' or 'n'.");
        }
    }

        // Prompts for a positive integer and validates input.
    private int promptPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            try {
                int v = Integer.parseInt(s);
                if (v > 0) return v;
                System.out.println("Quantity must be a positive whole number.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    private boolean clientExists(String clientId) {
            for (Client c : warehouse.listClients()) {
                if (c.getId().equalsIgnoreCase(clientId)) return true;
            }
            return false;
        }

    private void logout() {
    if ((WarehouseContext.instance()).getLogin() == WarehouseContext.IsManager)
       { //stem.out.println(" going to manager \n ");
         (WarehouseContext.instance()).changeState(-2); //error
        }
    else if (WarehouseContext.instance().getLogin() == WarehouseContext.IsClerk)
       {  //stem.out.println(" going to clerk \n");
        (WarehouseContext.instance()).changeState(-2); //error 
       }
     else if (WarehouseContext.instance().getLogin() == WarehouseContext.IsUser)
       {  //stem.out.println(" going to login \n");
        (WarehouseContext.instance()).changeState(0); // return to login
       }
    else 
       (WarehouseContext.instance()).changeState(-2); // error

    }

    private void process() {
        System.out.println("Type in command for client:");
        help();
        int choice = Integer.parseInt(scanner.nextLine());

        while (true) {
        switch(choice) {
        case showClientDetails: 
            showClientDetails(); 
            break;

        case showProducts: 
            showProducts(); 
            break;

        
        case showTransactions: 
            showTransactions();
            break;


        case addToWishList: 
            addToWishList();
            break;

        
        case displayWishlist: 
            displayWishlist();
            break;


        case placeOrder: 
            placeOrder();
            break;

        case logout: 
            logout(); 
            break;
        }
        logout();
    }
}


    public void run() {
        process();
   }
}
