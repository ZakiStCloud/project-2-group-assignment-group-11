import java.util.*;

import javax.swing.plaf.TreeUI;

public class UserInterfaceClass {

    private final Warehouse warehouse;
    private final Scanner scanner = new Scanner(System.in);

    public UserInterfaceClass(Warehouse warehouse) { 
        this.warehouse = warehouse;
    }

    public void showMainMenu() {
        while (true) {
            System.out.println("\n=== Warehouse Management System ===");
            System.out.println("1. Manage Clients");
            System.out.println("2. Manage Products");
            System.out.println("3. Process Orders");
            System.out.println("4. Receive Shipment");
            System.out.println("5. Receive Payment");
            System.out.println("6. Queries");
            System.out.println("0. Exit");
            System.out.print("\nEnter choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    clientMenu();
                    break;
                case "2":
                    productMenu();
                    break;
                case "3":
                    processOrder();
                    break;
                case "4":
                    receiveShipment();
                    break;
                case "5":
                    receivePayment();
                    break;
                case "6":
                    runQueries();
                    break;
                case "0":
                    System.out.println("Exiting system...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void clientMenu() {
        boolean stay = true; 
        while (stay) {
            System.out.println("\n=== Client Menu ===");
            System.out.println("1. Add Client");
            System.out.println("2. List Clients");
            System.out.println("3. Freeze/Unfreeze Client");
            System.out.println("4. Add item(s) to Client Wishlist");
            System.out.println("0. Back to Main Menu");
            System.out.print("\nEnter choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addClient();
                    break;
                case "2":
                    listClients();
                    waitForEnter();
                    break;
                case "3":
                    toggleClientFreeze();
                    break;
                case "4":
                    addToClientWishList();
                    break;
                case "0":
                    stay = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }
    private void waitForEnter(){
        System.out.println("\nPress 'Enter' to return...");
        scanner.nextLine();
    }

    private void addToClientWishList(){

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

    private void addClient() {
        System.out.print("Enter full name: ");
        String name = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        warehouse.addClient(name, address);
    }

    private void listClients() {
        System.out.println("\nClientID | Name | Address | Balance");
        for (Client c : warehouse.listClients()) {
            System.out.println(c);
        }
    }

    private void toggleClientFreeze() {
        System.out.print("Enter Client ID: ");
        String id = scanner.nextLine();
        System.out.print("Freeze (f) or Unfreeze (u): ");
        String action = scanner.nextLine().trim().toLowerCase();
        if (action.equals("f")) warehouse.freezeClient(id);
        else if (action.equals("u")) warehouse.unfreezeClient(id);
        else System.out.println("Invalid input.");
    }

    private void productMenu() {
        boolean stay = true;
        while (stay) {
            System.out.println("\n=== Product Menu ===");
            System.out.println("1. Add Product");
            System.out.println("2. List Products");
            System.out.println("3. Set Price");
            System.out.println("0. Back to Main Menu");
            System.out.print("\nEnter choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addProduct();
                    break;
                case "2":
                    listProducts();
                    waitForEnter();
                    break;
                case "3":
                    setPrice();
                    break;
                case "0":
                    stay = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

    private void addProduct() {
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Product Quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Product Price: ");
        double price = Double.parseDouble(scanner.nextLine());
        warehouse.addProduct(name, price, quantity);
    }

    private void listProducts() {
        System.out.println("ProductID | Name | Quantity | Price");
        for (Product p : warehouse.listProducts()) {
            System.out.println(p);
        }
    }

    private void setPrice() {
        System.out.print("Enter product ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter new price: ");
        double price = Double.parseDouble(scanner.nextLine());
        warehouse.setPrice(id, price);
        System.out.println("Price updated.");
    }

    private void processOrder() {
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

    private void receivePayment() {
        System.out.print("Enter client ID: ");
        String clientId = scanner.nextLine().trim();

        System.out.print("Enter payment amount: ");
        double amount;

        try {
            amount = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please enter a numeric value.");
            return;
        }

        boolean success = warehouse.receivePayment(clientId, amount);

        if (success) {
            System.out.println("Payment recorded.");
        } else {
            System.out.println("Payment not recorded");
        }
    }

    private void runQueries() {
        boolean stay = true;
        while (stay) {
            System.out.println("\n=== Queries ===");
            System.out.println("1. View WishList");
            System.out.println("2. View Account Balance");
            System.out.println("3. View Waitlist");
            System.out.println("4. View Inventory");
            System.out.println("5. View all invoices");
            System.out.println("0. Back to Main Menu");
            System.out.print("\nEnter choice: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.print("Enter client ID: ");
                    System.out.println(warehouse.getWishList(scanner.nextLine()));
                    break;
                case "2":
                    System.out.print("Enter client ID: ");
                    System.out.println(warehouse.getClient(scanner.nextLine()).getBalance());
                    break;
                case "3":
                    System.out.print("Enter product ID: ");
                    System.out.println(warehouse.getWaitlist(scanner.nextLine()));
                    break;
                case "4":
                    for (Product p : warehouse.listProducts()) {
                        System.out.println(p);}
                    break;
                case "5":
                    for (Transaction t : warehouse.getTransactions()) {
                        System.out.println(t.getInvoice());
                    }           
                    break;
                case "0":
                    stay = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }
}
