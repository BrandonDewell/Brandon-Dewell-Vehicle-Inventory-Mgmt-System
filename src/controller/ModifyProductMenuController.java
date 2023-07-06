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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class takes user input values, imports data, and modifies Products. */
public class ModifyProductMenuController implements Initializable {

    private Product pr;
    private Product selectedProduct;
    private int selectedIndex;
    private ObservableList<Part> mPrMaddPartToAssocPartsTable = FXCollections.observableArrayList();

    @FXML
    private TextField mPrMProductIdTxtFld;

    @FXML
    private TextField mPrMProductInvTxtFld;

    @FXML
    private TextField mPrMProductMaxTxtFld;

    @FXML
    private Button mPrMAddButton;

    @FXML
    private Button mPrMCancelButton;

    @FXML
    private Button mPrMSaveButton;

    @FXML
    private TextField mPrMProductMinTxtFld;

    @FXML
    private TextField mPrMProductNameTxtFld;

    @FXML
    private TextField mPrMProductPriceTxtFld;

    @FXML
    private Button mPrMRemoveAssocPartButton;

    @FXML
    private TextField mPrMProductSearchTxtFld;

    @FXML
    private TableColumn<Part, Integer> mPrMProductTV1InvLevelCol;

    @FXML
    private TableColumn<Part, Integer> mPrMProductTV1PartIdCol;

    @FXML
    private TableColumn<Part, String> mPrMProductTV1PartNameCol;

    @FXML
    private TableColumn<Part, Double> mPrMProductTV1PriceCol;

    @FXML
    private TableColumn<Part, Integer> mPrMProductTV2InvLevelCol;

    @FXML
    private TableColumn<Part, Integer> mPrMProductTV2PartIdCol;

    @FXML
    private TableColumn<Part, String> mPrMProductTV2PartNameCol;

    @FXML
    private TableColumn<Part, Double> mPrMProductTV2PriceCol;

    @FXML
    private TableView<Part> mPrMProductTableView1;

    @FXML
    private TableView<Part> mPrMProductTableView2;

    /** This event handler for the Cancel button applies an Alert pop up window and if the user clicks OK, loads the main menu.
     @param event An event from an action.
     */
    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear any changes you made.  Would you like to continue?");
        alert.setHeaderText("Cancel modification?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 400);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();
        }
    }

    /** This event handler method for the Add button below the top Parts table adds the selected part in the top table into the observable list of the bottom table.
     It checks if a selection was made and pops up an ERROR window if not.
     It adds the selected part to the associatedParts observable list displayed in the bottom table.
     @param event An event from an action.
     */
    @FXML
    void onActionMPAddPartToAssocParts(ActionEvent event) {
        Part temp = mPrMProductTableView1.getSelectionModel().getSelectedItem();
        if (temp == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No selection was made");
            alert.setContentText("Please select a part from the top table to associate with the product.");
            alert.showAndWait();
        }
        else {
            pr.addAssociatedPart(temp);
        }
    }

    /** This event handler method for the Remove button removes the selected part from the bottom table.
     It checks if a selection was made and pops up an ERROR window if not.
     It then displays a CONFIRMATION pop up window to ensure the user wants to remove the part.
     If the OK button is clicked, the selected part is removed the part from the bottom table.
     @param event An event from an action.
     */
    @FXML
    void onActionMPRemoveAssocPartFromProduct(ActionEvent event) {
        System.out.println("remove button clicked in modify product menu");

        Part temp = mPrMProductTableView2.getSelectionModel().getSelectedItem();
        if (temp == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part from the bottom table to remove from your modified product.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm you would like to remove the selected associated part from this product by clicking the OK button.");
            alert.setTitle("Removing an Associated Part from a Product");
            alert.setHeaderText("Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                pr.getAllAssociatedParts().remove(temp);
            }
        }
    }

    /** This event handler method for the search text box searches the top table of parts for a string or int.
     It uses try/catch statements to ignore number format exception errors.
     It displays found parts in the top table and resets the search text box.
     RUNTIME ERROR - (This comment is also located in the main.java file in the model package in the comment for the Main class method.)  I had an issue when I entered neither integers nor string values ($, or &, or + for example) into the search text box.
     The resulting top parts table did not change as if everything was a valid result.  I used a try statement around the second part of the method's code that looks for an integer as a fall back to if at first looking for a string did not return any results.
     I used a catch statement to catch the NumberFormatException error and ignore it.  This now gives search results of nothing being shown in the table when searching for an unusual character.
     @param event An event from an action.
     */
    @FXML
    void onActionMPSearchPartsTV1(ActionEvent event) {
        String userquery = mPrMProductSearchTxtFld.getText();

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
        mPrMProductTableView1.setItems(parts);
        mPrMProductSearchTxtFld.setText("");                         //resets the text field with no entry for a new search.  Pressing enter again without anything in the search text field returns and displays the complete list.
    }

    /** This event handler method for the Save button saves a Product and its associated parts.
     It uses try/catch statements to display an ERROR pop up window giving instructions for correct values and types.  It has a math logic check.
     There are checks for blank entries also.  The updateProduct method is called and the Main Menu is loaded.
     @param event An event from an action.
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) {
        try {
            pr.setId(selectedProduct.getId());  // 2/17 10:24am commented out because changing the stock to outside the bounds of the max or min, when saving doesn't error.  Also pulling back up the data by clicking modify on the same product doesnt pull up the newly entered data.
            String name = mPrMProductNameTxtFld.getText();
            int stock = Integer.parseInt(mPrMProductInvTxtFld.getText());
            double price = Double.parseDouble(mPrMProductPriceTxtFld.getText());
            int max = Integer.parseInt(mPrMProductMaxTxtFld.getText());
            int min = Integer.parseInt(mPrMProductMinTxtFld.getText());

            if(stock > max || stock < min){
                Alert alert = new Alert(Alert.AlertType.WARNING);  //change to error?  does error behave differently than warning?
                alert.setTitle("Warning");
                alert.setHeaderText("Inventory value must be within allowed range.");
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

                pr.setStock(stock);
                pr.setPrice(price);
                pr.setName(name);
                pr.setMin(min);
                pr.setMax(max);

                Inventory.updateProduct(Integer.parseInt(mPrMProductIdTxtFld.getText()), pr);

                Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 400);
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (NumberFormatException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a valid value for each text field.\nText fields should not be blank.\nInv, Max, Min, and Machine ID must be integers.\nPrice/Cost must be a double.");
            alert.showAndWait();
        }
    }

    /** This method receives a reference for a product object and sets its values to text for text fields.
     It sets the associatedParts observable list to the bottom table.
     @param inProduct The product that is sent.
     */
    public void sendProduct(Product inProduct){
        selectedProduct = inProduct;

        mPrMProductIdTxtFld.setText(String.valueOf(selectedProduct.getId()));
        mPrMProductNameTxtFld.setText(selectedProduct.getName());
        mPrMProductInvTxtFld.setText(String.valueOf(selectedProduct.getStock()));
        mPrMProductPriceTxtFld.setText(String.valueOf(selectedProduct.getPrice()));
        mPrMProductMaxTxtFld.setText(String.valueOf(selectedProduct.getMax()));
        mPrMProductMinTxtFld.setText(String.valueOf(selectedProduct.getMin()));

        pr.getAllAssociatedParts().addAll(selectedProduct.getAllAssociatedParts());
    }

    /** This method receives a reference for an index.
     @param inboundIndex The index that is sent.
     */
    public void sendIndex(int inboundIndex){
        selectedIndex = inboundIndex;
    }

    /** This initialize method is the first method to load in this class.
     It displays the allParts observable list in the top table and the associatedParts observable list in the bottom table.
     @param url The url.
     @param resourceBundle The resourceBundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mPrMProductTableView1.setItems(Inventory.getAllParts());
        mPrMProductTV1PartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mPrMProductTV1PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mPrMProductTV1InvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mPrMProductTV1PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        pr = new Product(0, "temp", 0.0, 0,0,0);

        mPrMProductTableView2.setItems(pr.getAllAssociatedParts());
        mPrMProductTV2PartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mPrMProductTV2PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mPrMProductTV2InvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mPrMProductTV2PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
