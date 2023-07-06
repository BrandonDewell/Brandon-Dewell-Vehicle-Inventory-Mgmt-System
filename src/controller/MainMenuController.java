package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/** This class creates an app that displays lists of parts and products. */
public class MainMenuController implements Initializable {

    public static int tempIndex = 0;

    @FXML
    private Button deletePartB;

    @FXML
    private Button deleteProductB;

    @FXML
    private Button displayAddPartMenuB;

    @FXML
    private Button displayAddProductMenuB;

    @FXML
    private Button displayModifyPartMenuB;

    @FXML
    private Button displayModifyProductMenuB;

    @FXML
    private Button exitB;

    @FXML
    private TableColumn<Part, Integer> mainPartsInvLevelCol;

    @FXML
    private TableColumn<Part, Integer> mainPartsPartIdCol;

    @FXML
    private TableColumn<Part, String> mainPartsPartNameCol;

    @FXML
    private TableColumn<Part, Double> mainPartsPriceCol;

    @FXML
    private TextField mainPartsSearchTxt;

    @FXML
    private TableView<Part> mainMenuPartsTableView;

    @FXML
    private TableColumn<Product, Integer> mainProductsInvLevelCol;

    @FXML
    private TableColumn<Product, Double> mainProductsPriceCol;

    @FXML
    private TableColumn<Product, Integer> mainProductsProductIdCol;

    @FXML
    private TableColumn<Product, String> mainProductsProductNameCol;

    @FXML
    private TextField mainProductsSearchTxt;

    @FXML
    private TableView<Product> mainMenuProductsTableView;

    /** This event handler method deletes a part from the parts list.
     It checks to ensure a part is selected before proceeding with the deletion process and if not displays an ERROR pop up window.
     It then displays a CONFIRMATION pop up window to ensure the user wants to delete the part.
     If the OK button is clicked, the deletePart method is called to remove the part from an observable list.
     @param event An event from an action.
     */
    @FXML
    void onActionDeletePart(ActionEvent event) {
        Part temp = mainMenuPartsTableView.getSelectionModel().getSelectedItem();
        if (temp == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No selection was made");
            alert.setContentText("Please select a part from the left table to delete.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm you would like to delete the selected part by clicking the OK button.");
            alert.setTitle("Deleting a Part");
            alert.setHeaderText("Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(mainMenuPartsTableView.getSelectionModel().getSelectedItem());
            }
        }
    }

    /** This event handler method deletes a product from the products list.
     It checks to ensure a product is selected before proceeding with the deletion process and if not displays an ERROR pop up window.
     It then displays a CONFIRMATION pop up window to ensure the user wants to delete the product.
     If the OK button is clicked, the deleteProduct method is called to remove the part from an observable list.
     @param event An event from an action.
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        Product temp = mainMenuProductsTableView.getSelectionModel().getSelectedItem();
        if (temp == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No selection was made");
            alert.setContentText("Please select a product from the table on the right to delete.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm you would like to delete the selected product by clicking the OK button.");
            alert.setTitle("Deleting a Product");
            alert.setHeaderText("Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct(mainMenuProductsTableView.getSelectionModel().getSelectedItem());
            }
        }
    }

    /** This event handler method for the Add button below the parts table loads the Add Part menu.
     @param event An event from an action.
     */
    @FXML
    void onActionDisplayAddPartMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPartMenu.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /** This event handler method for the Add button below the products table loads the Add Product menu.
     @param event An event from an action.
     */
    @FXML
    void onActionDisplayAddProductMenu(ActionEvent event) throws IOException {
        // you can initialize the second menu controller in two ways:
        // 1. is to call the static method() FXMLLoader.load such as:  Parent root = FXMLLoader.load(getClass().getResource("/view/AddProductMenu.fxml"));
        // 2. is to create a FXMLLoader object such as:  FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddProductMenu.fxml"));
        // 2 continued.  and then on the next line call the load() method on the loader to load that object such as:  Parent root = loader.load();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddProductMenu.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /** This event handler method for the Modify button below the parts table loads the Modify Part menu.
     It checks to ensure a part is selected and if not displays an ERROR pop up window instructing the user to make a selection.
     It then sends data to the next screen.
     @param event An event from an action.
     */
    @FXML
    void onActionDisplayModifyPartMenu(ActionEvent event) throws IOException {
        Part temp = mainMenuPartsTableView.getSelectionModel().getSelectedItem();  // 1. get the selected item and assign it to a temporary variable.

        if (temp == null) {        // 2. check if temp is null, then if null pop up an error message box telling the user to make sure to select an item.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No selection was made");
            alert.setContentText("Please select a part from the left table to modify.");
            alert.showAndWait();

        }
        else {                   // 3. else do the rest including sending data and loading the modify part stage.
            FXMLLoader loader = new FXMLLoader();                                           // 4. created the FXMLLoader object.
            loader.setLocation(getClass().getResource("/view/ModifyPartMenu.fxml"));  // 5. let that loader object know which view to use.
            loader.load();                                                                  // 6. load the object.

            ModifyPartMenuController MPMController = loader.getController();  // 7. call the loader object's getController() method using the loader reference variable MPMController.
            int tempIndex = Inventory.getAllParts().indexOf(temp);  // 8. use indexOf() to get the index of the selected item in the allParts observableList and assign it to tempIndex.

            MPMController.sendPart(temp);        // 9. move the selected object to the controller
            MPMController.sendIndex(tempIndex);  // and also move the index of the selected object.

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();                      // 10. then set the stage and scene
            Parent root = loader.getRoot();
            stage.setTitle("Modify Part");
            Scene scene = new Scene(root, 600, 600);
            stage.setScene(scene);
            stage.show();
        }
    }

    /** This event handler method for the Modify button below the products table loads the Modify Products menu.
     It checks to ensure a product is selected and if not displays an ERROR pop up window instructing the user to make a selection.
     It then sends data to the next screen.
     @param event An event from an action.
     */
    @FXML
    void onActionDisplayModifyProductMenu(ActionEvent event) throws IOException {
        Product temp = mainMenuProductsTableView.getSelectionModel().getSelectedItem();  // 1. get the selected item and assign it to a temporary variable.

        if (temp == null) {        // 2. check if temp is null, then if null pop up an error message box telling the user to make sure to select an item.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No selection was made");
            alert.setContentText("Please select a product from the table on the right to modify.");
            alert.showAndWait();
        }
        else {                   // 3. else do the rest including sending data and loading the modify product stage.
            FXMLLoader loader = new FXMLLoader();                                           // 4. created the FXMLLoader object.
            loader.setLocation(getClass().getResource("/view/ModifyProductMenu.fxml"));  // 5. let that loader object know which view to use.
            loader.load();                                                                  // 6. load the object.

            ModifyProductMenuController MPrMController = loader.getController();  // 7. call the loader object's getController() method using the loader reference variable MPrMController.
            int tempIndex = Inventory.getAllProducts().indexOf(temp);  // 8. use indexOf() to get the index of the selected item in the allProducts observableList and assign it to tempIndex.

            MPrMController.sendProduct(temp);        // 9. move the selected object to the controller
            MPrMController.sendIndex(tempIndex);  // and also move the index of the selected object to the controller.

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();                      // 10. then set the stage and scene
            Parent root = loader.getRoot();
            stage.setTitle("Modify Product");
            Scene scene = new Scene(root, 1000, 600);
            stage.setScene(scene);
            stage.show();
        }
    }

    /** This event handler method for the Exit button closes the program.
     @param event An event from an action.
     */
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    /** This event handler method for the search parts text box above the parts table searches for Strings or ints within the observable list of part objects.
     It searches a String first, and if the resulting list has no entries, it then searches an int.  It uses try/catch statements to ignore number format exception errors.
     It adds the found item/s to an observable list and displays that list in the table.  It reset the search text box to no entry.
     @param event An event from an action.
     */
    @FXML
    void onActionSearchParts(ActionEvent event) {
        String userquery = mainPartsSearchTxt.getText();

        ObservableList<Part> parts =  Inventory.lookupPart(userquery);  //first use the lookupPart method using a string parameter

        if(parts.size() == 0){                                          //if the parts observableList has no entries after looking through a possible string query by the user, then there is nothing in the list right now.
            try{
                int id = Integer.parseInt(userquery);                    //so assign the id with an integer that was converted by the string userquery using the parseInt() method.
                Part p = Inventory.lookupPart(id);                      //second pass in that newly assigned int id's value as an argument using the lookupPart method and assign that returned value to a temporary variable p.
                if(p != null)
                    parts.add(p);
            }
            catch (NumberFormatException e){
                //catch it and ignore it
            }
        }

        mainMenuPartsTableView.setItems(parts);
        mainPartsSearchTxt.setText("");                         //resets the text field with no entry for a new search.  Pressing enter again without anything in the search text field returns and displays the complete list.
    }

    /** This event handler method for the search products text box above the products table searches for Strings or ints within the observable list of product objects.
     It searches a String first, and if the resulting list has no entries, it then searches an int.  It uses try/catch statements to ignore number format exception errors.
     It adds the found item/s to an observable list and displays that list in the table.  It reset the search text box to no entry.
     @param event An event from an action.
     */
    @FXML
    void onActionSearchProducts(ActionEvent event) {
        String userquery = mainProductsSearchTxt.getText();

        ObservableList<Product> products = Inventory.lookupProduct(userquery);

        if(products.size() == 0){                                  //if the parts observableList has no entries after looking through a possible string query by the user, then there is nothing in the list right now.
            try{
                int id = Integer.parseInt(userquery);               //so assign the id with an integer that was converted by the string userquery.
                Product p = Inventory.lookupProduct(id);
                if(p != null)
                    products.add(p);
            }
            catch (NumberFormatException e){
                //catch it and ignore it
            }
        }

        mainMenuProductsTableView.setItems(products);
        mainProductsSearchTxt.setText("");
    }

    /** This initialize method is the first method to load in this class.
     It sets the Parts and Products tables with data from observable lists.
     @param url The url.
     @param resourceBundle The resourceBundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainMenuPartsTableView.setItems(Inventory.getAllParts());
        mainPartsPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainPartsPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainPartsInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        mainMenuProductsTableView.setItems(Inventory.getAllProducts());
        mainProductsProductIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainProductsProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainProductsInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainProductsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}