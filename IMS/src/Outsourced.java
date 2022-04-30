/**
 * class Outsourced
 */
public class Outsourced extends Part {
    private String companyName;


    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * sets the company name
     * @param companyName the company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * gets the company names
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

}
