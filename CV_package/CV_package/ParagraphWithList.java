package CV_package;

import java.io.PrintStream;

public class ParagraphWithList extends Paragraph {
    Paragraph paragraph;
    UnorderedList unorderedList;

    public ParagraphWithList(Paragraph paragraph){
        this.paragraph = paragraph;
    }

    ParagraphWithList(Paragraph paragraph, UnorderedList unorderedList) {
        this.paragraph = paragraph;
        this.unorderedList = unorderedList;
    }

    public ParagraphWithList(){
        paragraph = new Paragraph();
        unorderedList = new UnorderedList();
    }

    ParagraphWithList setContent(String content) {
        this.paragraph.setContent(content);
        return this;
    }

    ParagraphWithList addUnorderedList(UnorderedList unorderedList) {
        this.unorderedList = unorderedList;
        return this;
    }

    void addItemToUnorderedList(ListItem item) {
        this.unorderedList.addItem(item);
    }

    void writeHTML(PrintStream out){
        this.paragraph.writeHTML(out);
        this.unorderedList.writeHTML(out);
    }

    public ParagraphWithList addListItem(String item) {
        this.unorderedList.addItem(new ListItem(item));
        return this;
    }
}
