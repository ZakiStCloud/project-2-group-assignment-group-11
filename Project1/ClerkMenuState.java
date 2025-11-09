import java.util.Scanner;

public class ClerkMenuState {
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

                case 4:     // Show clients with balance
                    showClientsWithBalance();
                    break;

                case 5:     // Record payment
                    processPayment();
                    break;

                case 6:     // Become client
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
        System.out.println("4. Show Clients with Outstanding Balance");
        System.out.println("5. Record Payment from Client");
        System.out.println("6. Become Client");
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

        String id = warehouse.addClient(name, address);

        System.out.println("Client added. New client ID: " + id);
    }

    // ===================================================
    // 2) Show products (with available quantity and price)
    // ===================================================
    private void showProducts() {
        System.out.println("\n--- Product List (qty & price) ---");
        warehouse.displayAllProducts();
    }

    // ===================
    // 3) Show all clients
    // ===================
    private void showClients() {
        System.out.println("\n--- All Clients ---");
        warehouse.displayAllClients();
    }

    // ============================================
    // 4) Show all clients with outstanding balance
    // ============================================
    private void showClientsWithBalance() {
        System.out.println("\n--- Clients with Outstanding Balance ---");
        warehouse.displayClientsWithBalance();
    }

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
        boolean ok = warehouse.recordPayment(clientId, amount);

        if (ok) {
            System.out.println("Payment recorded.");
        } else {
            System.out.println("Payment failed. Check client ID or amount.");
        }
    }

    // ============================================================================
    // 6) Become a client (ask for clientID, validate, switch to ClientMenuState)
    // ============================================================================
    private void becomeClient() {
        System.out.println("\n--- Become Client ---");
        System.out.print("Enter Client ID: ");
        String clientId = in.nextLine().trim();

        Client c = warehouse.findClientById(clientId);

        if (c == null) {
            System.out.println("Invalid Client ID. Cannot switch to client menu.");
            return;
        }

        UserInterfaceClass ui = UserInterfaceClass.instance();

        ui.setCurrentClient(c);

        ui.changeState(UserInterfaceClass.TO_CLIENT);

        System.out.println("Now acting as client: " + clientId);
    }

    // ======================
    // 7) Logout (Exit event)
    // ======================
    private void logout() {
        System.out.println("\nLogging out of Clerk menu...");

        UserInterfaceClass ui = UserInterfaceClass.instance(); 
        ui.changeState(UserInterfaceClass.EXIT);
    }
}
