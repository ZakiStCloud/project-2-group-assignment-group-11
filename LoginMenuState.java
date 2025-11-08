import java.util.Scanner;

public class LoginMenuState extends WarehouseState{

  Scanner scanner;
  private static Warehouse warehouse;
  private static LoginMenuState loginMenuState; //Singleton pattern


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

   public void run() {
    
   }


}
