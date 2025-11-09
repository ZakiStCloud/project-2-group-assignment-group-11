import java.util.Scanner;

public class LoginMenuState extends WarehouseState{

  Scanner scanner;
  private static Warehouse warehouse;
  private static LoginMenuState loginMenuState; //For singleton pattern


  private LoginMenuState() { //Private constructor
      super();
      warehouse = warehouse.instance(); //If the warehouse doesn't already exist create it.
      this.scanner = new Scanner(System.in);
  }

  public static LoginMenuState instance() { // Instance method
        if (loginMenuState == null) {
            loginMenuState = new LoginMenuState();
        }
        return loginMenuState;
    }

  public void accessManagerMenu() {
    (WarehouseContext.instance()).setLogin(WarehouseContext.IsManager);
    (WarehouseContext.instance()).changeState(3);

  }

  public void accessClerkMenu() {
    (WarehouseContext.instance()).setLogin(WarehouseContext.IsClerk);
    (WarehouseContext.instance()).changeState(2);


  }

  public void accessClientMenu() {
    (WarehouseContext.instance()).setLogin(WarehouseContext.IsClient);
    (WarehouseContext.instance()).changeState(1);


  }

   public void run() {

    while(true){
        System.out.println("\n==================== Login Menu ====================");
        System.out.println("Enter a number to login:");
        System.out.println("1. Login as Client");
        System.out.println("2. Login as Clerk");
        System.out.println("3. Login as Manager");
        System.out.println("4. to Exit");
        System.out.print("Enter choice: ");

        int choice = Integer.parseInt(scanner.nextLine());

        switch(choice) {
            case 1:
                accessClientMenu();
                break;
            case 2:
                accessClerkMenu();
                break;
            case 3:
                accessManagerMenu();
                break;
            case 4:
                WarehouseContext.instance().terminate();
                break;
        }
    }

   }


}