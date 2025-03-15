package CSVReader;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        AdminUnitList adminUnitList = new AdminUnitList();
        adminUnitList.read("admin-units.csv");
        AdminUnit unit =  adminUnitList.findByNameAndLevel("Sosnowiec", 8);
        double t1 = System.nanoTime() / 1e6;
        AdminUnitList neighbours = adminUnitList.getNeighboursByChildren(unit, 30);
        double t2 = System.nanoTime() / 1e6;
        neighbours.list(System.out);
        System.out.println(neighbours.getWKT());
        System.out.println("Czas wyszukiwania: " + (t2-t1));
        double t12 = System.nanoTime() / 1e6;
        AdminUnitList neighbours2 = adminUnitList.getNeighbours(unit, 30);
        double t22 = System.nanoTime() / 1e6;
        neighbours2.list(System.out);
        System.out.println(neighbours2.getWKT());
        System.out.println("Czas wyszukiwania: " + (t22-t12));
        AdminUnitQuery query = new AdminUnitQuery()
                .selectFrom(adminUnitList)
                .where(a->a.area != null && a.area>1000)
                .or(a->a.name.startsWith("Sz"))
                .sort((a,b)->Double.compare(a.area,b.area))
                .limit(100);
        //query.execute().list(System.out);
    }
}