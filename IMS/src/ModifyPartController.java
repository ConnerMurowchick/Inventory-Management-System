import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private RadioButton inHouseRBTN;
    @FXML
    private RadioButton outsourcedRBTN;
    @FXML
    private Label machineIDLabel;
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
    private Button saveButton;

    private Part part;

    /**
     * Creates a new part with the information submitted in the form
     * Deletes the old part
     * Adds the new part to the part list with the add part lesson
     * Lots of input validation
     * @param event the event is clicking the save button
     * @throws IOException
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {

        try {
            InHouse newInHouse = new InHouse(
                    Integer.parseInt(idTextField.getText()),
                    nameTextField.getText(),
                    Double.parseDouble(priceCostTextField.getText()),
                    Integer.parseInt(invTextField.getText()),
                    Integer.parseInt(minTextField.getText()),
                    Integer.parseInt(maxTextField.getText()),
                    Integer.parseInt(machineIDTextField.getText()));

            int min = Integer.parseInt(minTextField.getText());
            int max = Integer.parseInt(maxTextField.getText());
            int inv = Integer.parseInt(invTextField.getText());

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
                    alert.setHeaderText("Min is greater than max");
                    alert.setContentText("Min value must be less than max");
                    alert.showAndWait();
                } else {

                    Inventory.addPart(newInHouse);
                    Inventory.deletePart(part);

                    Stage stage;
                    stage = (Stage) saveButton.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main form.fxml"));
                    Parent root = (Parent) loader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("User Input Error");
            alert.setHeaderText("Please enter valid values in text fields.");
            alert.showAndWait();

        }
    }

    /**
     * Closes the Modify Part form and opens the main form
     * @param event the event is clicking the cancel button
     * @throws IOException
     */
    @FXML
    void onActionDisplayMainForm(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Main form.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Changes the company name text when the outsourced radio button is selected
     * @param event the event is clicking the outsourced radio button
     */
    @FXML
    void onActionDisplayCompanyNameText(ActionEvent event) {
        machineIDLabel.setText("Company Name");
    }

    /**
     * Changes the textfield to Machine ID when the In House radio button is selected
     * @param event the event is selecting the in house radio button
     */
    @FXML
    void onActionDisplayMachineIDText(ActionEvent event) {
        machineIDLabel.setText("Machine ID");
    }


    /**
     * RUNTIME ERROR
     * Was unable to figure out how to check if the machineIDTextField was part of an InHouse or an Outsourced instance
     * After some trial and error found the instanceof keyword witch got me closer, but it still wasn't working.
     * Was struggling to call the getMachineID and getCompanyName methods because they weren't part of the part class
     * Finally, figured out the I could cast the get methods to InHouse and Outsourced respectively, and got it to work.
     *
     * Sets the values of the part to be modified to the selected part
     * @param part1 the selected part
     */
    public void setPart(Part part1) {
        this.part = part1;

            idTextField.setText(Integer.toString(part.getId()));
            nameTextField.setText(part.getName());
            invTextField.setText(Integer.toString(part.getStock()));
            priceCostTextField.setText(Double.toString(part.getPrice()));
            maxTextField.setText(Integer.toString(part.getMax()));
            minTextField.setText(Integer.toString(part.getMin()));
            if(part instanceof InHouse) {
                inHouseRBTN.setSelected(true);
                machineIDTextField.setText(String.valueOf(((InHouse)part).getMachineID()));
            }
            else if (part instanceof Outsourced) {
                outsourcedRBTN.setSelected(true);
                machineIDTextField.setText(String.valueOf(((Outsourced)part).getCompanyName()));
            }
    }

    /**
     * Sets the idTextField to not editable and displays a prompt text for it
     * Creates a toggle group so that only In House or Outsourced can be selected, not both.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
            idTextField.setEditable(false);
            idTextField.setPromptText("Auto-Gen Disabled");

            ToggleGroup group = new ToggleGroup();
            inHouseRBTN.setToggleGroup(group);
            outsourcedRBTN.setToggleGroup(group);

    }
}
