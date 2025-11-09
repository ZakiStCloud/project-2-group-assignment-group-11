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
                case 4:     // Show clients
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


    private int getCommand() {
        System.out.println("\n==================== Clerk Menu ====================");
        System.out.println("0. Logout");
        System.out.println("1. Add Client");
        System.out.println("2. Show Products (qty & price)");
        System.out.println("3. Show Clients");
        System.out.println("4. Show Clients With Outstanding balance");
        System.out.println("5. Record Payment from Client");
        System.out.println("6. Become Client");
        System.out.print("Enter choice: ");

        try {
            return Integer.parseInt(in.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void addClient() {
        System.out.println("\n--- Add Client ---");
        System.out.print("Client name: ");
        String name = in.nextLine().trim();

        System.out.print("Client address: ");
        String address = in.nextLine().trim();

        warehouse.addClient(name, address);
    }


    private void showProducts() {
        System.out.println("\n--- Product List (qty & price) ---");
        for (Product p : warehouse.listProducts()) {
                        System.out.println(p);}
    }

    private void showClients() {
        System.out.println("\n--- All Clients ---");
        for (Client c : warehouse.listClients()) {
            System.out.println(c);
        }
    }


    private void showClientsWithBalance() {
        System.out.println("\n--- All Clients ---");
        for (Client c : warehouse.listClients()) {
            if (c.getBalance() > 0){
            System.out.println(c);
            }
        }
    }


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

        boolean ok = warehouse.receivePayment(clientId, amount);

        if (ok) {
            System.out.println("Payment recorded.");
        } else {
            System.out.println("Payment failed. Check client ID or amount.");
        }
    }


     public void becomeClient() {
    int transitionToClient = 1;    
    
    WarehouseContext.instance().changeState(transitionToClient );
  }

 
    public void logout() {
    int transitionToLogin = 0;

        (WarehouseContext.instance()).changeState(transitionToLogin); // to login state.
       }
    
  }