package CV_package;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class Main{

    public static void main(String[] args) throws IOException {
        Document cv = new Document("Curriculum Vitae");
        cv.setPhoto("myPhoto.jpg");
        cv.addSection("Wykształcenie")
                .addParagraph("2000-2005 Przedszkole im. Królewny Snieżki w ...")
                .addParagraph("2006-2012 SP7 im Ronalda Regana w ...")
                .addParagraph(
                        new ParagraphWithList().setContent("Kursy")
                                .addListItem("Języka Angielskiego")
                                .addListItem("Języka Hiszpańskiego")
                                .addListItem("Szydełkowania")
                );
        cv.addSection("Umiejętności")
                .addParagraph(
                        new ParagraphWithList().setContent("Znane technologie")
                                .addListItem("C")
                                .addListItem("C++")
                                .addListItem("Java")
                );
        //cv.writeHTML(System.out);
        cv.writeHTML(new PrintStream("cv.html","UTF-8"));
        System.out.println(cv.toJson().toString());
        System.out.println(cv.toJson2().toString());
        new PrintStream("cv.json", StandardCharsets.UTF_8).println(cv.toJson2().toString());
    }
}