import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * class AddProductController
 */

public class AddProductController implements Initializable {

    public static int productIdCount = 1;
    Part part1;

    @FXML
    private TextField IDTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField maxTextField;
    @FXML
    private TextField invTextField;
    @FXML
    private TextField minTextField;
    @FXML
    private TableView<Part> tableviewTop;
    @FXML
    private TableColumn<Part, Integer> partIDColTop;
    @FXML
    private TableColumn<Part, String> partNameColTop;
    @FXML
    private TableColumn<Part, Integer> invColTop;
    @FXML
    private TableColumn<Part, Double> priceColTop;
    @FXML
    private TextField searchfieldTop;
    @FXML
    private Button addButton;
    @FXML
    private Button removePartButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TableView<Part> tableviewBot;
    @FXML
    private TableColumn<Part, Integer> partIDColBot;
    @FXML
    private TableColumn<Part, String> partNameColBot;
    @FXML
    private TableColumn<Part, Integer> invColBot;
    @FXML
    private TableColumn<Part, Double> priceColBot;

    Product product1 = new Product(0, "", 0 , 0, 0, 0);
    boolean found;

    /**
     * Gets the submitted text of the text-field, searches the list of parts for it, and displays a result
     * Calls searchByPartName and getPartWithID as helper methods
     * @param event the event is the user submitting text to the text-field.
     * @throws IOException
     */
    @FXML
    void topSearchFieldListener(ActionEvent event) throws IOException {

        String q  = searchfieldTop.getText();
        ObservableList<Part> parts = searchByPartName(q);

        if(parts.size() == 0) {
            try {
                int id = Integer.parseInt(q);
                Part part = getPartWithID(id);
                if (part != null) {
                    parts.add(part);
                }
            } catch (NumberFormatException e)
            {
                //ignore
            }
        }
        if (found == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search Error");
            alert.setHeaderText("No Results Found");
            alert.setContentText("Press Enter in the search bar to display all parts.");
            alert.showAndWait();
        }
        tableviewTop.setItems(parts);
        searchfieldTop.setText("");
    }
    /**
     * Helper method for the topSearchFieldListener method.
     * Loops through the existing list of parts and creates a new list that can be searched
     * Checks if the submitted text matches the part name
     * @param partialName the user input of the text field.
     * @return returns the list of parts whose names match the user input.
     */
    private ObservableList<Part> searchByPartName(String partialName) {

        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();
        found = false;
        for(Part part : allParts) {
            if(part.getName().contains(partialName)) {
                found = true;
                namedParts.add(part);
            }

        }
        return namedParts;
    }

    /**
     * The second helper method for the topSearchFieldListener method
     * Loops through the newly created list of parts
     * Checks if the submitted text matches the part ID
     * @param id the user input
     * @return returns the part with the matching ID, or null if none are found
     */
    private Part getPartWithID(int id)
    {
        ObservableList<Part> allParts = Inventory.getAllParts();
        found = false;
        for(Part part : allParts) {
            if(part.getId() == id) {
                found = true;
                return part;
            }
        }
        return null;
    }
    /**
     * Adds a part from the top tableview to the bottom tableview
     * If not null, associates the added part to the product to be added
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAddListener (ActionEvent event) throws IOException {

        part1 = tableviewTop.getSelectionModel().getSelectedItem();

        if (part1 == null) {

        } else {
            product1.addAssociatedPart(part1);
            tableviewBot.setItems(product1.getAllAssociatedParts());

        }
    }
    /**
     * Removes a part from the bottom table view
     * Disassociates the removed part from the product to be added
     */
    @FXML
    void onActionRemoveAssociatedPart (ActionEvent event) throws IOException {

        part1 = tableviewBot.getSelectionModel().getSelectedItem();
        product1.deleteAssociatedPart(part1);

    }

    /**
     * Saves the product to the main form product table, after many input validation tests.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSaveListener (ActionEvent event) throws IOException {

        try {

            product1.setId(Integer.parseInt(IDTextField.getText()));
            product1.setName(nameTextField.getText());
            product1.setPrice(Double.parseDouble(priceTextField.getText()));
            product1.setStock(Integer.parseInt(invTextField.getText()));
            product1.setMin(Integer.parseInt(minTextField.getText()));
            product1.setMax(Integer.parseInt(maxTextField.getText()));

            int inv = Integer.parseInt(invTextField.getText());
            int min = Integer.parseInt(minTextField.getText());
            int max = Integer.parseInt(maxTextField.getText());

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


                if (Integer.parseInt(minTextField.getText()) >= Integer.parseInt(maxTextField.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Input Error");
                    alert.setHeaderText("Min is greater than or equal to max");
                    alert.setContentText("Min value must be less than max");
                    alert.showAndWait();

                } else {


                    Inventory.addProduct(product1);

                    Stage stage;
                    stage = (Stage) saveButton.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main form.fxml"));
                    Parent root = (Parent) loader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("User Input Error");
            alert.setHeaderText("Please enter valid values in text fields.");
            alert.showAndWait();

        }

    }

    /**
     * Cancels the creation of a new product and returns to the main form screen.
     * @param event the event is the cancel button being pressed
     * @throws IOException
     */
    @FXML
    void onActionCancelListener(ActionEvent event) throws IOException {

        Stage stage;
        stage = (Stage) cancelButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader (getClass().getResource("/Main form.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initializes the top and bottom tableviews
     * Sets the top tableview to the list of all parts
     * Initializes the values of the IDTextField
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        IDTextField.setEditable(false);
        IDTextField.setPromptText("Auto-Gen Disabled");

        tableviewTop.setItems(Inventory.getAllParts());

        partIDColTop.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColTop.setCellValueFactory(new PropertyValueFactory<>("name"));
        invColTop.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColTop.setCellValueFactory(new PropertyValueFactory<>("price"));

        IDTextField.setText(String.valueOf(productIdCount));
        productIdCount = productIdCount + 2;

        partIDColBot.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColBot.setCellValueFactory(new PropertyValueFactory<>("name"));
        invColBot.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColBot.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
