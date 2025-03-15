package CSVReader;

import org.junit.jupiter.api.*;
import java.io.IOException;
import java.io.StringReader;
import java.util.Locale;

class CSVReaderTest {

    @Test
    void withHeader() throws IOException {
        System.out.println("Working directory: " + System.getProperty("user.dir"));
        String path = System.getProperty("user.dir") + "\\src\\Resources\\with-header.csv";
        CSVReader file = new CSVReader(path, ";", true);
        System.out.println(file.getColumnLabels());
    }

    @Test
    void withoutHeader() throws IOException {
        System.out.println("Working directory: " + System.getProperty("user.dir"));
        String path = System.getProperty("user.dir") + "\\src\\Resources\\no-header.csv";
        CSVReader file = new CSVReader(path, ";", false);
        System.out.println(file.getColumnLabels());
    }

    @Test
    void testUsage() throws IOException {
        String text = """
            a,b,c
            123.4,567.8,91011.12""";
        CSVReader reader = new CSVReader(new StringReader(text),",",true);
    }

    @Test
    void testReadCSV() throws IOException {
        System.out.println("Working directory: " + System.getProperty("user.dir"));
        String path = System.getProperty("user.dir") + "\\src\\Resources\\missing-values.csv";
        CSVReader file = new CSVReader(path);
        System.out.println(file.getColumnLabels());
        while (file.next()) {
            int id = file.getInt(0);
            Long parent = file.isMissing(1) ? null : file.getLong(1);
            String name = file.get(2);
            int admin_level = file.getInt(3);
            Double population = file.isMissing(4) ? null : file.getDouble(4);
            Double area = file.isMissing(5) ? null : file.getDouble(5);
            Double density = file.isMissing(6) ? null : file.getDouble(6);

            System.out.printf(Locale.US, "%d %s %s %d %s %s %s\n",
                    id,
                    parent == null ? "null" : parent.toString(),
                    name,
                    admin_level,
                    population == null ? "null" : population.toString(),
                    area == null ? "null" : area.toString(),
                    density == null ? "null" : density.toString()
            );
        }
    }

    @Test
    void testReadDoubles() throws IOException {
        System.out.println("Working directory: " + System.getProperty("user.dir"));
        String path = System.getProperty("user.dir") + "\\src\\Resources\\accelerator.csv";
        CSVReader file = new CSVReader(path);
        System.out.println(file.getColumnLabels());
        while (file.next()) {
            Double X = file.isMissing("X") ? null : file.getDouble("X");
            Double Y = file.isMissing("Y") ? null : file.getDouble("Y");
            Double Z = file.isMissing("Z") ? null : file.getDouble("Z");
            Double longitude = file.isMissing("longitude") ? null : file.getDouble("longitude");
            Double latitude = file.isMissing("latitude") ? null : file.getDouble("latitude");
            int speed = file.getInt("speed");
            Double time = file.isMissing("time") ? null : file.getDouble("time");
            int label = file.getInt("label");

            System.out.printf(Locale.US, "%f %f %f %f %f %d %f %d \n",
                    X,
                    Y,
                    Z,
                    longitude,
                    latitude,
                    speed,
                    time,
                    label
            );
        }
    }
}