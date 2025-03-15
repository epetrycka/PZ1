package CSVReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

class AdminUnitQueryTest {
    public AdminUnitList adminUnitList = new AdminUnitList();

    @BeforeEach
    void setUp() throws IOException {
        String path = System.getProperty("user.dir") + "\\src\\Resources\\admin-units.csv";
        adminUnitList.read(path);
    }

    @Test
    void execute1() {
        AdminUnitQuery query = new AdminUnitQuery()
                .selectFrom(adminUnitList)
                .where(a->a.area>1000)
                .or(a->a.name.startsWith("Sz"))
                .sort((a,b)->Double.compare(a.area,b.area))
                .limit(100);
        query.execute().list(out);
    }

    @Test
    void execute2() {
        AdminUnitQuery query = new AdminUnitQuery()
                .selectFrom(adminUnitList)
                .where(a -> a.name.contains("Biała") && a.area<500)
                .sort((a,b)->Double.compare(a.population,b.population))
                .limit(10);
        query.execute().list(out);
    }

    @Test
    void execute3() {
        AdminUnitQuery query = new AdminUnitQuery()
                .selectFrom(adminUnitList)
                .where(a -> a.parent.name.equals("powiat nyski"))
                .and(a ->a.parent != null)
                .sort((a,b)->Double.compare(a.population,b.population))
                .limit(10);
        query.execute().list(out);
    }

    @Test
    void execute4() {
        AdminUnitQuery query = new AdminUnitQuery()
                .selectFrom(adminUnitList)
                .where(a -> a.parent.name.equals("województwo mazowieckie"))
                .and(a ->a.parent != null)
                .sort((a,b)->Double.compare(a.area,b.area))
                .limit(20);
        query.execute().list(out);
    }

    @Test
    void execute5() {
        AdminUnitQuery query = new AdminUnitQuery()
                .selectFrom(adminUnitList)
                .where(a -> a.name.contains("gmina"))
                .sort((a,b)->CharSequence.compare(a.name,b.name))
                .limit(20);
        query.execute().list(out);
    }

    @Test
    void execute6() {
        AdminUnitQuery query = new AdminUnitQuery()
                .selectFrom(adminUnitList)
                .where(a -> a.name.contains("leski"));
        query.execute().list(out);
    }
}