package CV_package;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

class UnorderedListTest {
    @Test
    public void writeHTML() throws Exception {
        ListItem item1 = new ListItem("Pierwszy element");
        ListItem item2 = new ListItem("Drugi element");
        UnorderedList ul = new UnorderedList();
        ul.addItem(item1).addItem(item2);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os, true, "UTF-8");
        ul.writeHTML(ps);
        String result = null;
        try {
            result = os.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        assertNotNull(result);
        assertTrue(result.contains("<ul>"));
        assertTrue(result.contains("</ul>"));
        assertTrue(result.contains("<li>Pierwszy element</li>"));
        assertTrue(result.contains("<li>Drugi element</li>"));
    }
}