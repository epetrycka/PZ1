package CSVReader;

public class Product {

    private Long product_id;
    private String product_name;
    private String department;
    private String aisle;

    public Product(Long product_id, String product_name, String department, String aisle) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.department = department;
        this.aisle = aisle;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getDepartment() {
        return department;
    }

    public String getAisle() {
        return aisle;
    }

    public String toString() {
        return "Product{" + "product_id=" + product_id + ", product_name=" + product_name + ", department=" + department + ", aisle=" + aisle + '}';
    }
}
