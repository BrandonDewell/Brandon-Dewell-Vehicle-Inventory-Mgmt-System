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
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class imports user input values, takes user data input, and modifies Parts. */
public class ModifyPartMenuController implements Initializable {

    private Part selectedPart;
    private int selectedIndex;
    static int ident = 5;

    @FXML
    private Button displayMainMenuB;

    @FXML
    private ToggleGroup modifyPartAcquiredLoc;

    @FXML
    private TextField modifyPartIdTxt;

    @FXML
    private RadioButton modifyPartInHouseRBtn;

    @FXML
    private TextField modifyPartInvTxt;

    @FXML
    private Label modifyPartMachineLbl;

    @FXML
    private TextField modifyPartMachineTxt;

    @FXML
    private TextField modifyPartMaxTxt;

    @FXML
    private TextField modifyPartMinTxt;

    @FXML
    private TextField modifyPartNameTxt;

    @FXML
    private RadioButton modifyPartOutsourcedRBtn;

    @FXML
    private TextField modifyPartPriceTxt;

    @FXML
    private Button savePartB;

    /** This event handler method for the Cancel button applies an Alert pop up window and if the user clicks OK, loads the main menu.
     @param event An event from an action.
     */
    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear any changes you made.  Would you like to continue?");
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

    /** This event handler method for the In-House radio button is in a toggle group allowing only one radio button to be selected at a time and sets the text for the label to Machine ID.
     @param event An event from an action.
     */
    @FXML
    void onActionInHouseRB(ActionEvent event) {
        modifyPartMachineLbl.setText("Machine ID");
    }

    /** This event handler method for the Outsourced radio button is in a toggle group allowing only one radio button to be selected at a time and sets the text for the label to Company Name.
     @param event An event from an action.
     */
    @FXML
    void onActionOutsourcedRB(ActionEvent event) {
        modifyPartMachineLbl.setText("Company Name");
    }

    /** This event handler method for the Save button saves a Part.
     It has try statements for testing for input errors by the user and catch statements to consequently display an ERROR pop up window with information on correct input values.
     User entered data is checked for math logic and applies an Alert pop up window if the logic has errors.  There are checks for blank entries also.
     The text label for the bottom text field changes when a different radio button is selected along with a different parameter when adding the part.
     The main menu is loaded last.
     @param event An event from an action.
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        try {
            int id = selectedPart.getId();
            String name = modifyPartNameTxt.getText();
            int stock = Integer.parseInt(modifyPartInvTxt.getText());
            double price = Double.parseDouble(modifyPartPriceTxt.getText());
            int max = Integer.parseInt(modifyPartMaxTxt.getText());
            int min = Integer.parseInt(modifyPartMinTxt.getText());

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
                    alert.setContentText("Please enter a valid value for each text field.\nText fields should not be blank.\nName must be a character.\nInv, Max, and Min must be integers.\nPrice/Cost must be a double.");
                    alert.showAndWait();
                    return;
                }

                if (modifyPartInHouseRBtn.isSelected()) {

                    int machineId = Integer.parseInt(modifyPartMachineTxt.getText());
                    InHouse inhouse = new InHouse(id, name, price, stock, min, max, machineId);
                    Inventory.updatePart(selectedIndex, inhouse);
                } else {
                    modifyPartOutsourcedRBtn.isSelected();

                    String companyName = modifyPartMachineTxt.getText();
                    Outsourced outsourced = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.updatePart(selectedIndex, outsourced);
                    // different way to do this but instead of the previous 2 lines above, use this info:  Inventory.deletePart(selectedPart) for the first line;
                    // and this info:  Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName)); for the second line.  this deletes the part and adds it, but it
                    // adds it at the end of the list and its index is the last one in the list.  therefore it shows up last in the list when it reloads in the table.
                }

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
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a valid value for each text field.\nInv, Max, Min, and Machine ID must be integers.\nPrice/Cost must be a double.");
            alert.showAndWait();
        }
    }

    /** This method receives a reference for a part object and sets its values to text for text fields.
     It checks for the instance of an InHouse or Outsourced object.
     @param inPart The part that is sent.
     */
    public void sendPart(Part inPart){
        selectedPart = inPart;

        modifyPartIdTxt.setText(String.valueOf(selectedPart.getId()));  //retrieved id of inPart, converted that id which is an int to a string (via the valueOf method) so we can assign it to a text field.
        modifyPartNameTxt.setText(selectedPart.getName());
        modifyPartInvTxt.setText(String.valueOf(selectedPart.getStock()));
        modifyPartPriceTxt.setText(String.valueOf(selectedPart.getPrice()));
        modifyPartMaxTxt.setText(String.valueOf(selectedPart.getMax()));
        modifyPartMinTxt.setText(String.valueOf(selectedPart.getMin()));

        if(selectedPart instanceof InHouse) {
            modifyPartMachineTxt.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
            modifyPartMachineLbl.setText("Machine ID");
            modifyPartInHouseRBtn.setSelected(true);
        }
        else {
            modifyPartMachineTxt.setText(((Outsourced) selectedPart).getCompanyName());
            modifyPartMachineLbl.setText("Company Name");
            modifyPartOutsourcedRBtn.setSelected(true);
        }
    }

    /** This method receives a reference for an index.
     @param inboundIndex The index that is sent.
     */
    public void sendIndex(int inboundIndex){
        selectedIndex = inboundIndex;
    }

    /** This initialize method is the first method to load in this class.
     @param url The url.
     @param resourceBundle The resourceBundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
