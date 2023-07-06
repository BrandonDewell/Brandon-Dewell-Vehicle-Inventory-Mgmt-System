package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// The Javadocs folder is in a separate zipped file.

/** This class is the main method.
 FUTURE ENHANCEMENT - I would add the capability to drag and drop an item in the Add Product Menu and the Modify Product Menu to move parts in the top table to the bottom table.  This could be done with a mouse click and/or touch on a touchscreen device.
 RUNTIME ERROR - (This comment is also located in the ModifyProductMenuController.java file in the comment above the onActionMPSearchPartsTV1 event handler method.)  I had an issue when I entered neither integers nor string values ($, or + for example) into the search text box in the Modify Product Menu.
 The resulting top parts table did not change as if everything was a valid result.  In the ModifyProductMenuController, I used a try statement around the second part of the method's code that looks for an integer as a fall back to if at first looking for a string did not return any results.
 I used a catch statement to catch the NumberFormatException error and ignore it.  This now gives search results of nothing being shown in the table when searching for an unusual character.
 */
public class Main extends Application {

    /** This method creates a stage to set a scene.
     @param primaryStage The container holding the scenes.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root, 1000, 400));
        primaryStage.show();
    }

    /**This method creates Part and Product objects.  This loads upon execution of the program.
     @param args Creates and assigns values for the main program to execute.
     */
    public static void main(String[] args) {
        InHouse part1 = new InHouse(1, "steering wheel", 199.99, 16, 1, 20, 123);
        Outsourced part2 = new Outsourced(2, "tire", 149.99, 15, 1, 60, "GoodYear");
        InHouse part3 = new InHouse(3, "side mirror", 89.99, 24, 1, 40, 124);
        Outsourced part4 = new Outsourced(4, "leather seat", 799.99, 22, 1, 90, "Ford");

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);

        Product product1 = new Product(1, "Corvette", 79999.99, 8, 1, 10);
        Product product2 = new Product(2, "Mustang", 37999.99, 15, 1, 30);
        Product product3 = new Product(3, "Camry", 45999.99, 24, 1, 40);
        Product product4 = new Product(4, "Outback", 55999.99, 4, 1, 15);

        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part4);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        Inventory.addProduct(product4);

        launch(args);
    }
}
