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

public class ModifyProductController implements Initializable {

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
    private TableView<Part> topTableView;
    @FXML
    private TableColumn<Part, Integer> topPartIdCol;
    @FXML
    private TableColumn<Part, String> topPartNameCol;
    @FXML
    private TableColumn<Part, Integer> topInvCol;
    @FXML
    private TableColumn<Part, Double> topPriceCol;
    @FXML
    private TextField topSearchField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TableView<Part> botTableView;
    @FXML
    private TableColumn<Part, Integer> botPartIdCol;
    @FXML
    private TableColumn<Part, String> botPartNameCol;
    @FXML
    private TableColumn<Part, Integer> botInvCol;
    @FXML
    private TableColumn<Part, Double> botPriceCol;

    Part part1;
    boolean found;
    private Product productOld = new Product(0, "", 0, 0, 0, 0);
    private int productID;

    /**
     * Gets the submitted text of the text-field, searches the list of parts for it, and displays a result
     * Calls searchByPartName and getPartWithID as helper methods
     * @param event the event is the user submitting text to the text-field.
     * @throws IOException
     */
    @FXML
    void topSearchFieldListener(ActionEvent event) throws IOException {

        String q  = topSearchField.getText();
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
        topTableView.setItems(parts);
        topSearchField.setText("");
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
     * Adds a part from the top tableview to the bottom table view
     * adds this part to the associated parts list of the product
     * @param event the event is clicking the add button
     * @throws IOException
     */
    @FXML
    void onActionAddListener (ActionEvent event) throws IOException {

        part1 = topTableView.getSelectionModel().getSelectedItem();
        productOld.addAssociatedPart(part1);
        botTableView.setItems(productOld.getAllAssociatedParts());

    }

    /**
     * removes a part from the bottom tableview
     * removes this part from the associated parts list of the product
     * @param event clicking the remove associated part button
     * @throws IOException
     */
    @FXML
    void onActionRemoveAssociatedPart (ActionEvent event) throws IOException {

        part1 = botTableView.getSelectionModel().getSelectedItem();
        productOld.deleteAssociatedPart(part1);
    }

    /**
     *  Sets the values of the product being modified to the selected product.
     *  Updates the product in the inventory list with the modified values
     * @param event clicking the save button
     * @throws IOException
     */
    @FXML
    void onActionSaveListener(ActionEvent event) throws IOException {

        try {
            productOld.setId(Integer.parseInt(IDTextField.getText()));
            productOld.setName(nameTextField.getText());
            productOld.setPrice(Double.parseDouble(priceTextField.getText()));
            productOld.setStock(Integer.parseInt(invTextField.getText()));
            productOld.setMin(Integer.parseInt(minTextField.getText()));
            productOld.setMax(Integer.parseInt(maxTextField.getText()));


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

                    Inventory.updateProduct(productID, productOld);

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
     * Closes the window and returns the user to the main form.
     * @param event clicking the cancel button
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
     * Sets the values of the product to the selected product.
     * @param product3 the selected product
     */
    public void setProduct(Product product3) {

        productOld = product3;

            productID = Inventory.getAllProducts().indexOf(productOld);
            IDTextField.setText(String.valueOf(productOld.getId()));
            nameTextField.setText(productOld.getName());
            invTextField.setText(String.valueOf(productOld.getStock()));
            priceTextField.setText(String.valueOf(productOld.getPrice()));
            maxTextField.setText(String.valueOf(productOld.getMax()));
            minTextField.setText(String.valueOf(productOld.getMin()));

        botTableView.setItems(productOld.getAllAssociatedParts());

    }

    /**
     * Initializes the columns of the top and bottom tables.
     * Sets the items in the top table to be equal to the list of all parts
     * Sets properties of the IDTextField.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        IDTextField.setEditable(false);
        IDTextField.setPromptText("Auto-Gen Disabled");

        topTableView.setItems(Inventory.getAllParts());

        topPartIdCol.setCellValueFactory((new PropertyValueFactory<>("id")));
        topPartNameCol.setCellValueFactory(((new PropertyValueFactory<>("name"))));
        topInvCol.setCellValueFactory((new PropertyValueFactory<>("stock")));
        topPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        botPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        botPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        botInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        botPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

}
