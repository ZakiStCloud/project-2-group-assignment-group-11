import java.util.*;
import java.text.*;
import java.io.*;

public class ManagerState extends WarehouseState {
    // below is only needed to let the switch-case process (work)

    private static final int ADD_PRODUCT = 0;
    private static final int DISPLAY_WAITLIST = 1;
    private static final int RECEIVE_SHIPMENT = 2;
    private static final int BECOME_CLERK = 3;
    private static final int EXIT = 4;

    
    /*Question to ask group: I know the HW says just add product unclear about the qty but i could assume that u can do either 
    just add prod w id or qty and name etc */

    //creates a new product and adds it to the product list stored inside Warehouse.
    public void addProduct(){
        do{
            String name = getToken("Enter Product name to add: ");
            //get token only takes string input so we have to parse the int and double as warehouse in project 1 implements it with int and double
            double price = Double.parseDouble(getToken("Enter price: "));
            int qty = Integer.parseInt(getToken("Enter quantity to add: "));
            //GO BACK: warehouse shouldn't be capital because its an INSTANCE method 
            //and not a static method in project1 Warehouse.java code (backend)
            //which means it must be called on the object and not the class name 
            warehouse.addProduct(name, price, qty); //calls the method on the instance
            System.out.println("Product added");
            
            if (!yesOrNo("Add more product? ")) {
            break;
            }
        } while (true);
    }

    public void DisplayWaitlist(){
        //Display Waitlist from backend with below:
        //Client:
        //ProductId: 
        //Quantity:

        // look at processHolds() or display transactions in Library code
–       //Ask for product ID    
    };

//OR: FROM THE REQUIREMENT BELOW:
//Accept a shipment for a product. Shipment details specify a productid and quantity. 
//Waitlisted listed orders must be filled first, before amount in stock is updated
 
    public void receiveShipment();
    String productId = getToken(prompt: "Enter product ID: ");
    int qty = Integer.parseInt(getToken(prompt: "Enter quantity received: "));

    //Fulfill WaitListorders first
    



    public void BecomeClerk();

    public void logout();

    
    public void process() {
    int command, exitcode = -1;
    help();
    boolean done = false;
    while (!done) {
      switch (getCommand()) {
        case ADD_PRODUCT:        addProduct();
                                 break;
        case DISPLAY_WAITLIST:   displayWaitList();
                                 break;
        case RECEIVE_SHIPMENT:   receiveShipment();
                                 break;
        case BECOME_CLERK:      becomeClerk();
                                break;
        case USERMENU:          if (usermenu())
                                  {exitcode = 1;
                                   done = true;}
                                break;
        case HELP:              help();
                                break;
        case EXIT:              exitcode = 0;
                                done = true; break;
      }
    }
    terminate(exitcode);
  }


  public void run() {
    process();
  }

     public void add(Product p){
        products.add(p);

    }

}
