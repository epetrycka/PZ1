package CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class ProductsList {
    public List<Product> ProductsList = new ArrayList<>();

    public void read(String filename) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(filename), ";", true);

        while (reader.next()) {
            Long product_id = reader.getLong("product_id");
            String product_name = reader.get("product_name");
            String department = reader.get("department");
            String aisle = reader.get("aisle");

            //System.out.println(product_id + " " + product_name + " " + department + " " + aisle);
            ProductsList.add(new Product(product_id, product_name, department, aisle));
        }
    }

    void list(PrintStream out) {
        for (Product product : ProductsList) {
            System.out.println(product.toString());
        }
    }

    void list(PrintStream out, int offset, int limit) {
        if (offset < 0 || limit > ProductsList.size() || offset > limit) {
            throw new IllegalArgumentException("Invalid offset or limit");
        }
        for (int i = offset; i < limit; i++) {
            System.out.println(ProductsList.get(i).toString());
        }
    }

    ProductsList sortInplace(Comparator<Product> cmp) {
        ProductsList.sort(cmp);
        return this;
    }

//    AdminUnitList sort(Comparator<AdminUnit> cmp) {
//        AdminUnitList copy = new AdminUnitList();
//        copy.units = new ArrayList<>(this.units);
//        copy.sortInplace(cmp);
//        return copy;
//    }

    ProductsList filter(Predicate<Product> pred) {
        if (pred == null) {
            throw new IllegalArgumentException("Predicate cannot be null");
        }
        ProductsList copy = new ProductsList();
        for (Product product : ProductsList) {
            if (product != null && pred != null && pred.test(product)) {
                copy.ProductsList.add(product);
            }
        }
        return copy;
    }

    ProductsList filter(Predicate<Product> pred, int offset, int limit){
        if (pred == null) {
            throw new IllegalArgumentException("Predicate cannot be null");
        }
        ProductsList copy = new ProductsList();
        int count = 0;
        for (Product product : ProductsList) {
            if (product != null && pred != null && pred.test(product)) {
                count++;
                if (count >= offset && count < limit) {
                    copy.ProductsList.add(product);
                }
            }
            if (count >= limit) {
                break;
            }
        }
        return copy;
    }
}
