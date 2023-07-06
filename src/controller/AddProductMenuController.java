package controller;

import javafx.collections.FXCollections;
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
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class takes user input values and adds Products. */
public class AddProductMenuController implements Initializable {

    static int ident = 5;
    private Product selectedProduct;
    private ObservableList<Part> aPrMaddPartToAssocPartsTableOL = FXCollections.observableArrayList();
    Product pr;

    @FXML
    private TextField addProductIdTxt;

    @FXML
    private TextField addProductInvTxt;

    @FXML
    private TextField addProductMaxTxt;

    @FXML
    private Button addProductMenuAddB;

    @FXML
    private Button addProductMenuCancelB;

    @FXML
    private Button addProductMenuRemoveAssocPartB;

    @FXML
    private Button addProductMenuSaveB;

    @FXML
    private TextField addProductMinTxt;

    @FXML
    private TextField addProductNameTxt;

    @FXML
    private TextField addProductPriceTxt;

    @FXML
    private TextField addProductSearchTxt;

    @FXML
    private TableColumn<Part, Integer> addProductTV1InvLevelCol;

    @FXML
    private TableColumn<Part, Integer> addProductTV1PartIdCol;

    @FXML
    private TableColumn<Part, String> addProductTV1PartNameCol;

    @FXML
    private TableColumn<Part, Double> addProductTV1PriceCol;

    @FXML
    private TableColumn<Part, Integer> addProductTV2InvLevelCol;

    @FXML
    private TableColumn<Part, Integer> addProductTV2PartIdCol;

    @FXML
    private TableColumn<Part, String> addProductTV2PartNameCol;

    @FXML
    private TableColumn<Part, Double> addProductTV2PriceCol;

    @FXML
    private TableView<Part> addProductTableView1;

    @FXML
    private TableView<Part> addProductTableView2;

    /** This event handler method for the Add button below the top Parts table adds the selected part in the top table into the observable list of the bottom table.
     It checks if a selection was made and pops up an ERROR window if not.
     It adds the selected part to the associatedParts observable list displayed in the bottom table.
     @param event An event from an action.
     */
    @FXML
    void onActionAddPartToAssocParts(ActionEvent event) {
        Part temp = addProductTableView1.getSelectionModel().getSelectedItem();
        if (temp == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No selection was made");
            alert.setContentText("Please select a part from the top table to associate with your created product.");
            alert.showAndWait();
        }
        else {
            pr.addAssociatedPart(temp);
        }
    }

    /** This event handler for the Cancel button applies an Alert pop up window and if the user clicks OK, loads the main menu.
     @param event An event from an action.
     */
    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear any information you entered.  Would you like to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 400);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();
        }
    }

    /** This event handler method for the Remove button removes the selected part from the bottom table.
     It checks if a selection was made and pops up an ERROR window if not.
     It then displays a CONFIRMATION pop up window to ensure the user wants to remove the part.
     If the OK button is clicked, the selected part is removed the part from the bottom table.
     @param event An event from an action.
     */
    @FXML
    void onActionRemoveAssocPartFromProduct(ActionEvent event) {
        Part temp = addProductTableView2.getSelectionModel().getSelectedItem();
        if (temp == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part from the bottom table to remove from your created product.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm you would like to remove the selected associated part from this product by clicking the OK button.");
            alert.setTitle("Removing an Associated Part from a Product");
            alert.setHeaderText("Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                pr.deleteAssociatedPart(temp);

            }
        }
    }

    /** This event handler method for the Save button saves a Product and its associated parts.
     It uses try/catch statements to display an ERROR pop up window giving instructions for correct values and types.  It has a math logic check.
     There are checks for blank entries also.  A new product has all the associated parts added to it and the product is added to the allProducts observable list.
     The main menu is loaded next.
     @param event An event from an action.
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
        try {
            int id = ident;
            String name = addProductNameTxt.getText();
            int stock = Integer.parseInt(addProductInvTxt.getText());
            double price = Double.parseDouble(addProductPriceTxt.getText());
            int max = Integer.parseInt(addProductMaxTxt.getText());
            int min = Integer.parseInt(addProductMinTxt.getText());

            if(stock > max || stock < min){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Inventory value must be within allowed range");
                alert.setContentText("Please enter a valid value for Inv, Max, and Min.\nMin value must be less than Max value.\nInv value must be less than or equal to Max value.\nInv value must be greater than or equal to Min value.");
                alert.showAndWait();
                return;
            }
            else
            {
                if(name.isBlank()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Fields must not be left blank.");
                    alert.setContentText("Please enter a valid value for each text field.\nName must be a character.\nInv, Max, and Min must be integers.\nPrice must be a double.");
                    alert.showAndWait();
                    return;
                }

                Product product = new Product(id, name, price, stock, min, max);
                product.getAllAssociatedParts().addAll(pr.getAllAssociatedParts());
                Inventory.addProduct(product);
                ident++;

                Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 400);
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a valid value for each text field.\nText fields should not be blank.\nInv, Max, and Min must be integers.\nPrice must be a double.");
            alert.showAndWait();
        }
    }

    /** This event handler method for the search text box searches the top table of parts for a string or int.
     It uses try/catch statements to ignore number format exception errors.
     It displays found parts in the top table.
     @param event An event from an action.
     */
    @FXML
    void onActionSearchPartsTV1(ActionEvent event) {
        String userquery = addProductSearchTxt.getText();

        ObservableList<Part> parts = Inventory.lookupPart(userquery);

        if(parts.size() == 0){                                  //if the parts observableList has no entries after looking through a possible string query by the user, then there is nothing in the list right now.
            try{
                int id = Integer.parseInt(userquery);               //so assign the id with an integer that was converted by the string userquery.
                Part p = Inventory.lookupPart(id);
                if(p != null)
                    parts.add(p);
            }
            catch (NumberFormatException e){
                //catch it and ignore it
            }
        }
        addProductTableView1.setItems(parts);
        addProductSearchTxt.setText("");                         //resets the text field with no entry for a new search.  Pressing enter again without anything in the search text field returns and displays the complete list.
    }

    /** This initialize method is the first method to load in this class.
     It displays the allParts observable list in the top table and the associatedParts observable list in the bottom table.
     @param url The url.
     @param resourceBundle The resourceBundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductTableView1.setItems(Inventory.getAllParts());
        addProductTV1PartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductTV1PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductTV1InvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductTV1PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        pr = new Product(0, "temp", 0.0, 0,0,0);

        addProductTableView2.setItems(pr.getAllAssociatedParts());  // for bottom parts tableview in add product menu
        addProductTV2PartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductTV2PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductTV2InvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductTV2PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
