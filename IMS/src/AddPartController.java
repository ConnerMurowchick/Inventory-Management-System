import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * class AddPartController
 */
public class AddPartController  {

    Stage stage;
    Parent scene;
    public static int idCount = 0;

    @FXML
    private ToggleGroup rbButtons;
    @FXML
    private RadioButton inHouseRadioButton;
    @FXML
    private RadioButton outsourcedRadioButton;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField invTextField;
    @FXML
    private TextField priceCostTextField;
    @FXML
    private TextField maxTextField;
    @FXML
    private TextField machineIDTextField;
    @FXML
    private TextField minTextField;
    @FXML
    private TextField companyNameTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label machineIDLabel;


    /**
     * Gets the inputted data from the text-fields of the Add Part form.
     * Creates an In House object if the inHouseRadioButton is selected, creates an Outsourced Object if the
     * outsourcedRadioButton is selected.
     * If a part is successfully created, adds it to the part tableview and redirects the user to the main form
     * Displays an error message if neither radio button is selected and
     * redirects the user back to the main form.
     * @param event the event is the action of clicking on the save button.
     * @throws IOException
     */
    @FXML
    void onActionSavePartListener (ActionEvent event) throws IOException {
// NumberFormatException

        try {

            String name = nameTextField.getText();
            int inv = Integer.parseInt(invTextField.getText());
            double price = Double.parseDouble(priceCostTextField.getText());
            int max = Integer.parseInt(maxTextField.getText());
            int min = Integer.parseInt(minTextField.getText());
            int machineID = Integer.parseInt(machineIDTextField.getText());
            String companyName = machineIDTextField.getText();

            if (inv < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText("Inventory is less than minimum value");
                alert.setContentText("Inventory must be between min and max");
                alert.showAndWait();
            } else if (inv > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText("Inventory is greater than maximum value");
                alert.setContentText("Inventory must be between min and max");
                alert.showAndWait();
            } else {

                if (min >= max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Input Error");
                    alert.setHeaderText("Min is greater than or equal to max");
                    alert.setContentText("Min value must be less than max");
                    alert.showAndWait();

                } else {


                    if (inHouseRadioButton.isSelected()) {
                        idCount = idCount + 2;
                        Inventory.addPart(new InHouse(idCount, name, price, inv, min, max, machineID));
                    } else if (outsourcedRadioButton.isSelected()) {
                        idCount = idCount + 2;
                        Inventory.addPart(new Outsourced(idCount, name, price, inv, min, max, companyName));
                    } else if ((!(outsourcedRadioButton.isSelected())) && !(inHouseRadioButton.isSelected())) {

                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Input Error");
                        alert.setHeaderText("Neither In House nor Outsourced selected");
                        alert.setContentText("No part created. Returning to main form.");
                        alert.showAndWait();
                    }
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/Main form.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();

                }
            }
        }
        catch(NumberFormatException e){

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("User Input Error");
                alert.setHeaderText("Please enter valid values in text fields.");
                alert.showAndWait();
            }
        }



    /**
     * Returns the user to the main form from the Add Part Form
     * @param event the event is the action of clicking the cancel button
     * @throws IOException
     */
    @FXML
    void onActionDisplayMainForm(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Main form.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Changes the bottom text field label from Machine ID to Company Name.
     * @param event the event is the user selecting the outsourced radio button.
     */
    @FXML
    void onActionDisplayCompanyNameText(ActionEvent event)
    {
        machineIDLabel.setText("Company Name");
    }

    /**
     * Changes the bottom text field label to Machine ID.
     * @param event the event is the user selecting the In House radio button.
     */
    @FXML
    void onActionDisplayMachineIDText(ActionEvent event)
    {
        machineIDLabel.setText("Machine ID");
    }
}
