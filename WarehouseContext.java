import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.io.*;


public class WarehouseContext{
  private static int currentState;
  private static Warehouse warehouse;
  private int currentUser;
  private String userID;
  private static WarehouseContext context;
  public static final int IsUser = 1;
  public static final int IsClerk = 2;
  public static final int IsManager = 3;
  private static WarehouseState[] states;
  private int[][] nextState;


private WarehouseContext{

  states = new WarehouseState[4];
  states[3] = ManagerState.instance();
  states[2] = Clerkstate.instance();
  states[1] = Userstate.instance(); 
  states[0] = Loginstate.instance();

  currentState = 0;
  nextState = new int[4][4];
  nextState[0][0] = -1;nextState[0][1] = 1;nextState[0][2] = 2;nextState[0][2] = 3;
  nextState[1][0] = 0;nextState[1][1] = 1;nextState[1][2] = -2;nextState[1][3] = -2;
  nextState[2][0] = 0;nextState[2][1] = 1;nextState[2][2] = 2;nextState[2][3] = -2;
  nextState[3][0] = 0;nextState[3][1] = 1;nextState[3][2] = 2;nextState[3][3] = 3;
}
  
//Prompts the user and retrieves their input
public String getToken(String prompt){ 
  do {
      try {
        System.out.println(prompt);
        String line = reader.readLine();
        StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
        if (tokenizer.hasMoreTokens()) {
          return tokenizer.nextToken();
        }
      } catch (IOException ioe) {
        System.exit(0);
      }
    } while (true);
} 
  
//Prompts the user with yes or no and returns bool
private boolean yesOrNo(String prompt){
   String more = getToken(prompt + " (Y|y)[es] or anything else for no");
    if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
      return false;
    }
    return true;
}


public void setLogin(int code){
  currentUser = code;
}

public void setUser(String uID){
  userID = uID;
}

public int getLogin(){
  return currentUser;
}

public String getUser(){
  return userID;
}

//Initiates the change of state based on current and ddesired state
 public void changeState(int transition)
  {
    //System.out.println("current state " + currentState + " \n \n ");
    currentState = nextState[currentState][transition];
    if (currentState == -2) 
      {System.out.println("Error has occurred"); terminate();}
    if (currentState == -1) 
      { //System.out.println("current state " + currentState + " \n \n ");
      terminate();}
    states[currentState].run();
  }

//Exits the program
private void terminate(){
  System.exit(0);
} 

//Runs the state
 public static WarehouseContext instance() {
    if (context == null) {
       System.out.println("calling constructor");
      context = new WarehouseContext();
    }
    return context;
  }

  public void process(){
    states[currentState].run();
  }
  
  public static void main (String[] args){
    WarehouseContext.instance().process(); 
    states[currentState].run();
  }


}
