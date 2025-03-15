package CSVReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BoundingBoxTest {
    public AdminUnitList adminUnitList = new AdminUnitList();

    @BeforeEach
    void setUp() throws IOException {
        String path = System.getProperty("user.dir") + "\\src\\Resources\\admin-units.csv";
        adminUnitList.read(path);
    }

    @Test
    void resize() {
        AdminUnit unitToFind = adminUnitList.findByNameAndLevel("gmina Horodło", 7);
        BoundingBox resizedLHR = BoundingBox.resize(unitToFind.bbox, 15);
        System.out.println(unitToFind.bbox.getWKT());
        System.out.println(resizedLHR.getWKT());
    }
}