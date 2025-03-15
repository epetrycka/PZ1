package CV_package;

import java.io.PrintStream;
import java.io.PrintWriter;

public class Photo {
    String url;

    Photo (String url){
        this.url = url;
    }

    void writeHTML(PrintStream out){
        out.printf("<img id=\"photo\" src=\"%s\" alt=\"Golden Retiver\" height=\"100\" width=\"100\"/>",url);
    }
}
