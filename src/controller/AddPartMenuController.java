package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class takes user input values and adds Parts. */
public class AddPartMenuController implements Initializable {

    static int ident = 5;

    @FXML
    private ToggleGroup addPartAcquiredLoc;

    @FXML
    private TextField addPartIdTxt;

    @FXML
    private RadioButton addPartInHouseRBtn;

    @FXML
    private TextField addPartInvTxt;

    @FXML
    private Label addPartMachineLbl;

    @FXML
    private TextField addPartMachineTxt;

    @FXML
    private TextField addPartMaxTxt;

    @FXML
    private TextField addPartMinTxt;

    @FXML
    private TextField addPartNameTxt;

    @FXML
    private RadioButton addPartOutsourcedRBtn;

    @FXML
    private TextField addPartPriceTxt;

    @FXML
    private Button displayMainMenuB;

    @FXML
    private Button savePartB;

    /** This event handler method for the Cancel button applies an Alert pop up window and if the user clicks OK, loads the main menu.
     @param event An event from an action.
     */
    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear any information you entered.  Would you like to continue?");
        Optional<ButtonType> result = alert.showAndWait();                  // optional container we named result contains enumerations for button types.
        if(result.isPresent() && result.get() == ButtonType.OK){            // isPresent returns a boolean if there is something inside the optional container.
                                                                            // therefore its a check on whether someone clicked a button.  result.get() checks to see what type of button is clicked, the ok button or cancel button.
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 400);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();
        }
    }

    /** This event handler method for the In-House radio button is in a toggle group allowing only one radio button to be selected at a time and sets the text for the label to Machine ID.
     @param event An event from an action.
     */
    @FXML
    void onActionInHouseRBtn(ActionEvent event) {
        addPartMachineLbl.setText("Machine ID");
    }

    /** This event handler method for the Outsourced radio button is in a toggle group allowing only one radio button to be selected at a time and sets the text for the label to Company Name.
     @param event An event from an action.
     */
    @FXML
    void onActionOutsourcedRBtn(ActionEvent event) {
        addPartMachineLbl.setText("Company Name");
    }

    /** This event handler method for the Save button saves a Part.
     It has try statements for testing for input errors by the user and catch statements to consequently display an ERROR pop up window with information on correct input values.
     User entered data is checked for math logic and applies an Alert pop up window and if the logic has errors, loads the main menu.  There are checks for blank entries also.
     The text label for the bottom text field changes when a different radio button is selected along with a different parameter when adding the part.
     @param event An event from an action.
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        try {
            int id = ident;
            String name = addPartNameTxt.getText();
            int stock = Integer.parseInt(addPartInvTxt.getText());
            double price = Double.parseDouble(addPartPriceTxt.getText());
            int max = Integer.parseInt(addPartMaxTxt.getText());
            int min = Integer.parseInt(addPartMinTxt.getText());
            boolean isInhouse;
            boolean isOutsourced;

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
                    alert.setContentText("Please enter a valid value for each text field.\nName must be a character.\nInv, Max, and Min must be integers.\nPrice/Cost must be a double.");
                    alert.showAndWait();
                    return;
                }

                if (addPartInHouseRBtn.isSelected()) {
                    isInhouse = true;
                    int machineId = Integer.parseInt(addPartMachineTxt.getText());
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
                } else {
                    addPartOutsourcedRBtn.isSelected();
                    isOutsourced = true;
                    String companyName = addPartMachineTxt.getText();
                    Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                }

                ident++;

                Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 400);
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Incorrect Value Types Entered");
            alert.setContentText("Please enter a valid value for each text field.\nText fields should not be blank.\nInv, Max, Min, and Machine ID must be integers.\nPrice/Cost must be a double.");
            alert.showAndWait();
        }
    }

    /** This initialize method is the first method to load in this class.
     @param url The url.
     @param resourceBundle The resourceBundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
