package CV_package;

import java.io.PrintStream;

public class Paragraph {
    String content = "";

    Paragraph(String content) {
        this.content = content;
    }

    Paragraph() {
        content = "";
    }

    Paragraph setContent(String content){
        this.content = content; return this;
    }

    ParagraphWithList addListToParapgraph(){
        ParagraphWithList paragraphWithList = new ParagraphWithList(this);
        return paragraphWithList;
    }

    void writeHTML(PrintStream out){
        out.print("<p>" + this.content + "</p>");
    }
}
