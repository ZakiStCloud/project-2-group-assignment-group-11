public class Driver{

    public static void main(String[] args){
        Warehouse warehouse = new Warehouse();
        UserInterfaceClass warehouseInterface = new UserInterfaceClass(warehouse);
        warehouseInterface.showMainMenu();

    }


}