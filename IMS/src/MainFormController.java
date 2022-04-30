import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * class MainFormController
 */
public class MainFormController implements Initializable {

    Stage stage;
    Parent scene;
    boolean found;
    Part part;
    Product product;

    @FXML
    private Button partModifyButton;
    @FXML
    private Button productModifyButton;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> partIDColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> partPriceCostColumn;
    @FXML
    private TextField partSearchField;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, Integer> productIDColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productInventoryLevelColumn;
    @FXML
    private TableColumn<Product, Double> productPriceCostColumn;
    @FXML
    private TextField productSearchField;

    /**
     * Exits the application
     * @param event the event is the clicking of the exit button
     */
    @FXML
    void mainFormExitButtonListener(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Opens the add part form.
     * @param event clicking the add part button
     * @throws IOException
     */
    @FXML
    void partAddButtonListener(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/AddPartForm.fxml"));
        stage.setTitle("Add Part Form");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Gets the selected item and opens the modify part form
     * Calls the setPart method of the modify part class
     * Checks if a part is selected, throws an dialogue box error if not.
     * @param event the event is clicking the modify part button
     * @throws IOException
     */
    @FXML
    void partModifyButtonListener(ActionEvent event) throws IOException {

        try {
            Stage stage;
            stage = (Stage) partModifyButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyPartForm.fxml"));
            Parent root = loader.load();

            ModifyPartController controller = loader.getController();
            part = partsTable.getSelectionModel().getSelectedItem();
            controller.setPart(part);

            Scene scene = new Scene(root);
            stage.setTitle("Modify Part Form");
            stage.setScene(scene);
            stage.show();

        } catch (NullPointerException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No part selected.");
            alert.setHeaderText("A part must be selected before it can be modified.");
            alert.showAndWait();

        }
    }

    /**
     * Opens the Add Product form
     * @param event
     * @throws IOException
     */
    @FXML
    void productAddButtonListener(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Add Product Form.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Gets the selected item and opens the modify product form
     * Calls the setProduct method of the ModifyProductController class
     * Checks if a product is selected, returns a dialogue box error if not.
     * @param event the event is clicking the modify product button
     * @throws IOException
     */
    @FXML
    void productModifyButtonListener(ActionEvent event) throws IOException {

        try {
            Stage stage;
            stage = (Stage) productModifyButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyProduct.fxml"));
            Parent root = loader.load();
            ModifyProductController controller = loader.getController();
            controller.setProduct(productsTable.getSelectionModel().getSelectedItem());

            Scene scene = new Scene(root);
            stage.setTitle("Modify Product Form");
            stage.setScene(scene);
            stage.show();

        }   catch(NullPointerException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No product selected.");
            alert.setHeaderText("A product must be selected before it can be modified.");
            alert.showAndWait();
        }
    }

    /**
     * Gets a selected product, checks if there are associated parts
     * If yes, throw an error, if no, ask confirmation, then delete product.
     * @param event clicking the delete product button.
     * @throws IOException
     */
    @FXML
    void productDeleteButtonListener(ActionEvent event) throws IOException {

        try {

            product = productsTable.getSelectionModel().getSelectedItem();

            if(!(product.getAllAssociatedParts().size() == 0)) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Deletion Error");
                alert.setHeaderText("Selected Product has a part associated with it.");
                alert.setContentText("Cannot delete a Product with a part associated with it.");
                alert.showAndWait();

            } else if(product.getAllAssociatedParts().size() == 0){

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Delete product confirmation");
                alert.setContentText("Are you sure you want to delete this product?");

                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    Inventory.deleteProduct(product);
                } else {

                }
            }
        }
        catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No product selected.");
            alert.setHeaderText("A product must be selected before it can be deleted.");
            alert.showAndWait();
        }
    }

    /**
     * Gets a selected part, asks for confirmation, then deletes the selected part from the table view.
     * @param event clicking the delete part button.
     * @throws IOException
     */
    @FXML
    void partDeleteButtonListener(ActionEvent event) throws IOException
    {
        try {
            part = partsTable.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete part confirmation");
            alert.setContentText("Are you sure you want to delete this part?");

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                    delete(part.getId());
            } else {

            }

        } catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No part selected.");
            alert.setHeaderText("A part must be selected before it can be deleted.");
            alert.showAndWait();
        }
    }

    /**
     * Gets the submitted text of the text-field, searches the list of products for it, and displays a result
     * Calls searchByProductName and getProductWithID as helper methods
     * @param event the event is the user submitting text to the text-field.
     * @throws IOException
     */
    @FXML
    void productSearchFieldListener(ActionEvent event) throws IOException {

        String q  = productSearchField.getText();
        ObservableList<Product> products = searchByProductName(q);

        if(products.size() == 0) {
            try {
                int id = Integer.parseInt(q);
                Product product1 = getProductWithID(id);
                if (product1 != null) {
                    products.add(product1);
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
            alert.setContentText("Press Enter in the search bar to display all products.");
            alert.showAndWait();
        }
        productsTable.setItems(products);
        productSearchField.setText("");
    }

    /**
     * Helper method for the productSearchFieldListener method.
     * Loops through the existing list of products and creates a new list that can be searched
     * Checks if the submitted text matches the product name
     * @param partialName the user input of the text-field.
     * @return returns the list of products whose names match the user input
     */
    private ObservableList<Product> searchByProductName(String partialName) {

        ObservableList<Product> namedProducts = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = Inventory.getAllProducts();
        found = false;
        for(Product product1 : allProducts) {
            if(product1.getName().contains(partialName)) {
                found = true;
                namedProducts.add(product1);
            }

        }
        return namedProducts;
    }

    /**
     * The second helper method for the productSearchFieldListener method
     * Loops through the newly created list of products
     * Checks if the submitted text matches the product ID
     * @param id the user input
     * @return returns the product with the matching ID, or null if none are found
     */
    private Product getProductWithID(int id)
    {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        found = false;
        for(Product product2 : allProducts) {
            if(product2.getId() == id) {
                found = true;
                return product2;
            }
        }
        return null;
    }

    /**
     * Gets the submitted text of the text-field, searches the list of parts for it, and displays a result
     * Calls searchByPartName and getPartWithID as helper methods
     * @param event the event is the user submitting text to the text-field.
     * @throws IOException
     */
    @FXML
    void partSearchFieldListener(ActionEvent event) throws IOException
    {
        //Inventory.partSearch(partSearchField.getText();
        String q  = partSearchField.getText();
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
        partsTable.setItems(parts);
        partSearchField.setText("");
    }
    /**
     * Helper method for the partSearchFieldListener method.
     * Loops through the existing list of parts and creates a new list that can be searched
     * Checks if the submitted text matches the part name
     * @param partialName the user input of the text-field.
     * @return returns the list of parts whose names match the user input
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
     * The second helper method for the partSearchFieldListener method
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
     * deletes a part with a given ID from the all parts list when called
     * @param id the ID of the part to be deleted
     * @return returns the removal of the part with the matching ID
     */
    public boolean delete(int id) {
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == id) {
                return Inventory.getAllParts().remove(part);
            }
        }
        return false;
    }

    /**
     * Initializes the parts and products tables
     * Sets the parts table to the parts list, and the product table to the bottom list
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        productsTable.setItems(Inventory.getAllProducts());

        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTable.setItems(Inventory.getAllParts());

        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


    }
}




