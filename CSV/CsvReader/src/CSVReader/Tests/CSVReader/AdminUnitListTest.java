package CSVReader;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class AdminUnitListTest {
    public AdminUnitList adminUnitList = new AdminUnitList();

    @BeforeEach
    void setUp() throws IOException {
        String path = System.getProperty("user.dir") + "\\src\\Resources\\admin-units.csv";
        adminUnitList.read(path);
    }

    @Test
    void selectByName() {
        AdminUnitList file = adminUnitList.selectByName("^wojew.*", true);
        file.list(System.out);
    }

    @Test
    void findByName() throws IOException {
        AdminUnit mojaWies = adminUnitList.findByName("Kobło-Kolonia");
        AdminUnit.show(mojaWies, System.out);
    }

    @Test
    void getNeighbors() throws IOException {
        AdminUnit unitToFind = adminUnitList.findByNameAndLevel("gmina Horodło", 7);
        double t1 = System.nanoTime() / 1e6;
        AdminUnitList neighbours = adminUnitList.getNeighbours(unitToFind);
        double t2 = System.nanoTime() / 1e6;
        neighbours.list(System.out);
        System.out.println(neighbours.getWKT());
        System.out.println("Czas wyszukiwania: " + (t2-t1));
    }

    @Test
    void getNeighboursByChildren(){
        AdminUnit unitToFind = adminUnitList.findByNameAndLevel("gmina Horodło", 7);
        double t1 = System.nanoTime() / 1e6;
        AdminUnitList neighbours = adminUnitList.getNeighboursByChildren(unitToFind);
        double t2 = System.nanoTime() / 1e6;
        neighbours.list(System.out);
        System.out.println(neighbours.getWKT());
        System.out.println("Czas wyszukiwania: " + (t2-t1));
    }

    @Test
    void getNeighborsVillage() throws IOException {
        AdminUnit unitToFind = adminUnitList.findByNameAndLevel("Horodło", 8);
        double t1 = System.nanoTime() / 1e6;
        AdminUnitList neighbours = adminUnitList.getNeighbours(unitToFind, 15);
        double t2 = System.nanoTime() / 1e6;
        neighbours.list(System.out);
        System.out.println(neighbours.getWKT());
        System.out.println("Czas wyszukiwania: " + (t2-t1));
        System.out.println("Ilosc sasiadów: " + neighbours.units.size());
    }

    @Test
    void getNeighboursByChildrenVillage(){
        AdminUnit unitToFind = adminUnitList.findByNameAndLevel("Horodło", 8);
        double t1 = System.nanoTime() / 1e6;
        AdminUnitList neighbours = adminUnitList.getNeighboursByChildren(unitToFind, 15);
        double t2 = System.nanoTime() / 1e6;
        neighbours.list(System.out);
        System.out.println(neighbours.getWKT());
        System.out.println("Czas wyszukiwania: " + (t2-t1));
        System.out.println("Ilosc sasiadów: " + neighbours.units.size());
    }

    @Test
    void getNeighboursByChildrenMyVillage(){
        AdminUnit unitToFind = adminUnitList.findByNameAndLevel("gmina Brzeszcze", 7);
        double t1 = System.nanoTime() / 1e6;
        AdminUnitList neighbours = adminUnitList.getNeighboursByChildren(unitToFind, 15);
        double t2 = System.nanoTime() / 1e6;
        neighbours.list(System.out);
        System.out.println(neighbours.getWKT());
        System.out.println("Czas wyszukiwania: " + (t2-t1));
        System.out.println("Ilosc sasiadów: " + neighbours.units.size());
    }

    @Test
    void findByNameAndLevel(){
        AdminUnit findCity = adminUnitList.findByNameAndLevel("Horodło", 8);
        AdminUnit.show(findCity, System.out);

        AdminUnit findCityNull = adminUnitList.findByNameAndLevel("Wiktoria", 8);
        AdminUnit.show(findCityNull, System.out);
    }

    @Test
    void testFilterArea() {
        adminUnitList.filter(a->a.name.startsWith("Ż")).sortInplaceByArea().list(System.out);
    }

    @Test
    void testFilterName() {
        adminUnitList.filter(a->a.name.startsWith("K")).sortInplaceByName().list(System.out);
    }

    @Test
    void testFilterPowiatsMalopolskie() {
        adminUnitList
                .filter(a -> a.adminLevel != null && a.adminLevel == 6
                        && a.parent != null
                        && a.parent.name != null
                        && a.parent.name.equals("województwo małopolskie"))
                .sortInplaceByName()
                .list(System.out);
    }

    @Test
    void testFilter3() {
        Predicate<AdminUnit> populationInRange = a -> a.population != null && a.population >= 50_000 && a.population <= 200_000;
        Predicate<AdminUnit> areaGreaterThan500 = a -> a.area != null && a.area > 500;

        adminUnitList
                .filter(populationInRange.and(areaGreaterThan500))
                .sortInplaceByPopulation()
                .list(System.out);
    }

    @Test
    void testMarcinsWies() {
        Predicate<AdminUnit> areaSmallerThan100 = a -> a.area != null && a.area < 100;
        Predicate<AdminUnit> populationSmallerThan500 = a -> a.population != null && a.population < 500;

        adminUnitList
                .filter(areaSmallerThan100.and(populationSmallerThan500), 10)
                .sortInplaceByName()
                .list(System.out);
    }

    @Test
    void doesMarcinHaveNeighbours(){
        double t1 = System.nanoTime() / 1e6;
        AdminUnitList opolskie = adminUnitList.selectByParent("województwo opolskie", true);
        AdminUnitList Nysa = adminUnitList.selectByParent("powiat prudnicki", true);
        double t2 = System.nanoTime() / 1e6;
        Nysa.list(System.out);
        System.out.println(Nysa.getWKT());
        System.out.println("Czas wyszukiwania: " + (t2-t1));
    }

    @Test
    void multipleNames(){
        AdminUnitList biala = adminUnitList.filter(a -> a.name.contains("Biała"));
        biala.list(System.out);
    }

    @Test
    void children() throws IOException{
        AdminUnit lhr = adminUnitList.findByName("gmina Horodło");
        System.out.println(lhr);
        AdminUnit.showChildren(lhr, System.out);
    }

    @Test
    void children2() throws IOException{
        AdminUnit lhr = adminUnitList.findByName("Chełm");
        System.out.println(lhr);
        AdminUnit.showChildren(lhr, System.out);
    }

    @Test
    void children3() throws IOException{
        AdminUnit lhr = adminUnitList.findByName("powiat hrubieszowski");
        System.out.println(lhr);
        AdminUnit.showChildren(lhr, System.out);
    }
}