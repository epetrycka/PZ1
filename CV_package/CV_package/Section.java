package CV_package;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Section {
    String title = "";
    List<Paragraph> paragraphs = new ArrayList<>();
    List<ParagraphWithList> paragraphsWithList = new ArrayList<>();

    Section(String title){
       this.title = title;
    }

    Section setTitle(String title) {
        this.title = title;
        return this;
    }

    Section addParagraph(String content) {
        this.paragraphs.add(new Paragraph(content));
        return this;
    }

    Section addParagraph(Paragraph p){
        this.paragraphs.add(p);
        return this;
    }

    Section addParagraph(ParagraphWithList paragraphWithList){
        this.paragraphsWithList.add(paragraphWithList);
        return this;
    }

    void writeHTML(PrintStream out){
        out.printf("<h1>%s</h1>", title);
        for (Paragraph paragraph : paragraphs) {
            paragraph.writeHTML(out);
        }
        for (ParagraphWithList paragraphWithList : paragraphsWithList) {
            paragraphWithList.writeHTML(out);
        }
    }
}
