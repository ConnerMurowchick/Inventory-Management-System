import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * class Product
 */
public class Product {

    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * sets the ID
     * @param id the ID
     */
    public void setId(int id) {
        this.id = id;

    }

    /**
     * sets the name
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * sets the price
     * @param price the price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * sets the inventory
     * @param stock the inventory
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * sets the min
     * @param min the min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * sets the max
     * @param max the max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * gets the id
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * gets the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * gets the price
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * gets the stock
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * gets the min
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * gets the max
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * adds a part to the observable list of associated parts
     * @param part the part to be added
     */
    public void addAssociatedPart(Part part) {

        // take a part and add it to the observable list
        associatedParts.add(part);

    }

    /**
     * removes a part from the observable list of associated parts
     * @param selectedAssociatedPart the part to be remove
     * @return returns true if successful
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {

        // take a part and remove it from the observable list
        associatedParts.remove(selectedAssociatedPart);

    return true;
    }

    /**
     * gets the associated parts list
     * @return the associated parts list
     */
    public ObservableList<Part> getAllAssociatedParts()
    {
        return associatedParts;
    }




}
