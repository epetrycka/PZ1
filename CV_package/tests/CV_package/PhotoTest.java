package CV_package;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

class PhotoTest {
    @Test
    public void writeHTML() throws Exception {
        String imageUrl = "jan-kowalski.png";
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        new Photo(imageUrl).writeHTML(ps);
        String result = null;
        try {
            result = os.toString("ISO-8859-2");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        assertTrue(result.contains("<img"));
        assertTrue(result.contains("/>"));
        assertTrue(result.contains("src="));
        assertTrue(result.contains(imageUrl));
    }
}