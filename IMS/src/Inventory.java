import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * class Inventory
 */
public class Inventory {
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart)
    {
        allParts.add(newPart);
    }
    public static void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }

    /**
     * Used to update a value of a product in the product list.
     * @param index An index to insert a new product
     * @param newProduct the new product to be inserted into the list
     */
    public static void updateProduct(int index, Product newProduct) {
            Inventory.getAllProducts().set(index, newProduct);
        }

    /**
     * Used to delete a product from the product list.
     * @param selectedProduct The product to be deleted
     * @return Returns the removal of the product from the list.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        for (Product product : Inventory.getAllProducts()) {
            if ( product.getId() == selectedProduct.getId()) {
                return Inventory.getAllProducts().remove(product);
            }
        }
        return false;
    }

    /**
     * Used to delete a part from the part list
     * @param selectedPart The part to be deleted
     * @return Returns the removal of the part from the list.
     */
    public static boolean deletePart(Part selectedPart) {
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == selectedPart.getId()) {
                return Inventory.getAllParts().remove(part);
            }
        }
        return false;
    }
   /**
    * @return Returns the list of all parts
    */
    public static ObservableList<Part> getAllParts() {
    return allParts;
    }

    /**
     * @return Returns the list of all products
     */
    public static ObservableList<Product> getAllProducts() {
    return allProducts;
    }

}


