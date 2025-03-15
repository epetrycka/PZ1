package CV_package;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class UnorderedList {
    List<ListItem> unorderedList = new ArrayList<>();

    UnorderedList addItem(ListItem item) {
        this.unorderedList.add(item);
        return this;
    }

    void writeHTML(PrintStream out){
        out.print("<ul>");
        for (ListItem item : unorderedList) {
            item.writeHTML(out);
        }
        out.print("</ul>");
    }
}
