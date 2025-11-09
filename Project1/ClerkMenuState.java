import java.util.Scanner;

public class ClerkMenuState extends WarehouseState {
    private static ClerkMenuState instance;

    public static ClerkMenuState instance() {
        if (instance == null) {
            instance = new ClerkMenuState();
        }
        return instance;
    }

    private final Warehouse warehouse;
    private final Scanner in;

    private ClerkMenuState() {
        warehouse = Warehouse.instance();
        in = new Scanner(System.in);
    }

    // ==============================
    // Public entry point 
    // ==============================
    public void run() {
        boolean done = false;

        while (!done) {
            int command = getCommand();

            switch (command) {
                case 0:     // Logout
                    logout();
                    done = true;
                    break;

                case 1:     // Add client
                    addClient();
                    break;

                case 2:     // Show products
                    showProducts();
                    break;

                case 3:     // Show clients
                    showClients();
                    break;

                case 4:     // Record payment
                    processPayment();
                    break;

                case 5:     // Become client
                    becomeClient();
                    done = true;   //Leaving ClerkMenu state
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        }
    }
    public void process() {
        run();
    }

    // =======================
    // Menu & command input 
    // =======================
    private int getCommand() {
        System.out.println("\n==================== Clerk Menu ====================");
        System.out.println("0. Logout");
        System.out.println("1. Add Client");
        System.out.println("2. Show Products (qty & price)");
        System.out.println("3. Show Clients");
        System.out.println("4. Record Payment from Client");
        System.out.println("5. Become Client");
        System.out.print("Enter choice: ");

        try {
            return Integer.parseInt(in.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // =============
    // 1) Add client
    // =============
    private void addClient() {
        System.out.println("\n--- Add Client ---");
        System.out.print("Client name: ");
        String name = in.nextLine().trim();

        System.out.print("Client address: ");
        String address = in.nextLine().trim();

        warehouse.addClient(name, address);
    }

    // ===================================================
    // 2) Show products (with available quantity and price)
    // ===================================================
    private void showProducts() {
        System.out.println("\n--- Product List (qty & price) ---");
        for (Product p : warehouse.listProducts()) {
                        System.out.println(p);}
    }

    // ===================
    // 3) Show all clients
    // ===================
    private void showClients() {
        System.out.println("\n--- All Clients ---");
        for (Client c : warehouse.listClients()) {
            System.out.println(c);
        }
    }

    // ============================================
    // 4) Show all clients with outstanding balance
    // ============================================

    // ===============================
    // 5) Record payment from a client
    // ===============================
    private void processPayment() {
        System.out.println("\n--- Record Client Payment ---");

        System.out.print("Client ID: ");
        String clientId = in.nextLine().trim();

        System.out.print("Payment amount: ");
        double amount;
        try {
            amount = Double.parseDouble(in.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Payment cancelled.");
            return;
        }

        // Match to your Warehouse method:
        // Example: boolean ok = warehouse.recordPayment(clientId, amount);
        boolean ok = warehouse.receivePayment(clientId, amount);

        if (ok) {
            System.out.println("Payment recorded.");
        } else {
            System.out.println("Payment failed. Check client ID or amount.");
        }
    }

    // ============================================================================
    // 6) Become a client (ask for clientID, validate, switch to ClientMenuState)
    // ============================================================================
     public void becomeClient() {
    int transitionToClient = 1;    
    
    WarehouseContext.instance().changeState(transitionToClient );
  }

    // ======================
    // 7) Logout (Exit event)
    // ======================
    public void logout() {
    int transitionToLogin = 0;
    int transitionErrorState = -2; //error
    
    
    if (WarehouseContext.instance().getLogin() == WarehouseContext.IsClerk) 
       {  //System.out.println("going to Manager \n");
        (WarehouseContext.instance()).changeState(transitionToLogin); // to login state.
       }
    //Error state if the user state isn't clerk somehow
    else 
       (WarehouseContext.instance()).changeState(transitionErrorState); // go to an error state
  }
}
