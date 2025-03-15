package CSVReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class Main {

    public static void main(String[] args) throws IOException {
        out.println("Current working directory: " + System.getProperty("user.dir") + "CsvReader\\src\\CSVReader\\products.csv");
        ProductsList productsList = new ProductsList();
        productsList.read(System.getProperty("user.dir") + "\\CsvReader\\src\\CSVReader\\products.csv");

        ProductsQuery query = new ProductsQuery()
                .selectFrom(productsList)
                .where(a->a.getProduct_name().contains("Orange"))
                .and(a->a.getProduct_name().contains("Juice"))
                .limit(100);

        /***
         Wypisz produkty, w których nazwie znajdują się równocześnie słowa 'Orange' i 'Juice'
         ***/
        query.execute().list(out);

        List<String> uniqueAisles = productsList.ProductsList.stream()
                .map(Product::getAisle)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        /***
         Wypisz posortowane alfabetycznie wszystkie nazwy alei (aisle)
         ***/

        for (String aisle : uniqueAisles) {
            System.out.println(aisle);
        }

        Map<String, Integer> departmentCount = new HashMap<>();

        for (Product product : productsList.ProductsList) {
            departmentCount.put(
                    product.getDepartment(),
                    departmentCount.getOrDefault(product.getDepartment(), 0) + 1
            );
        }

        String maxDepartment = null;
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : departmentCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxDepartment = entry.getKey();
            }
        }

        /***
            Znajdź dział (department) zawierający najwięcej produktów
        ***/
        System.out.println(maxDepartment + " (" + maxCount + ")");

        Map<String, Integer> dryGoodsPasta = new HashMap<>();

        for (Product product : productsList.ProductsList) {
            if (product.getDepartment().equals("dry goods pasta")) {
                dryGoodsPasta.put(
                        product.getAisle(),
                        dryGoodsPasta.getOrDefault(product.getAisle(), 0) + 1
                );
            }
        }
        /***
         Wypisz informacje ile produktów znajduje się w poszczególnych alejach działu 'dry goods pasta'. Posortuj wyniki według liczby produktów
        ***/
//        for (Map.Entry<String, Integer> entry : dryGoodsPasta.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }

//        ProductsQuery query2 = new ProductsQuery()
//                .selectFrom(productsList)
//                .where(a -> a.getDepartment().contains("Champagne"))
//                .or(a -> a.getAisle().contains("Champagne"));
//
//        ProductsList result = query2.execute();
//
//        Map<String, String> depAisleChampagne = new HashMap<>();
//
//        for (Product product : result.ProductsList) {
//            depAisleChampagne.put(
//                    product.getDepartment(),
//                    product.getAisle()
//            );
//        }
//
//        for (Map.Entry<String, String> entry : depAisleChampagne.entrySet()) {
//            System.out.println("Department: " + entry.getKey() + ", Aisle: " + entry.getValue());
//        }

        Set<String> uniqueDepartmentsAisles = new HashSet<>();

        for (Product product : productsList.ProductsList) {
            if (product.getProduct_name().contains("Champagne")) {
                String departmentAislePair = product.getDepartment() + " , " + product.getAisle();
                uniqueDepartmentsAisles.add(departmentAislePair);
            }
        }

        /***
         Wypisz (ale tylko raz) działy i aleje produktów, które w nazwie mają słowo 'Champagne'
         ***/

        for (String departmentAisle : uniqueDepartmentsAisles) {
            System.out.println(departmentAisle);
        }
    }
}