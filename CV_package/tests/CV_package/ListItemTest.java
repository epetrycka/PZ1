package CV_package;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

class ListItemTest {
    @Test
    public void writeHTML() throws Exception {
        String content = "Przykladowy tekst listy";
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        new ListItem(content).writeHTML(ps);

        String result = null;
        try {
            result = os.toString("ISO-8859-2");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println(result);

        assertTrue(result.contains("<li>"));
        assertTrue(result.contains("</li>"));
        assertTrue(result.contains(content));
    }
}
