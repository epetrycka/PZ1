package CV_package;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Document {
    String title = "";
    Photo photo;
    List<Section> sections = new ArrayList<>();

    Document(String title) {
        this.title = title;
    }

    Document setTitle(String title) {
        this.title = title;
        return this;
    }

    Document setPhoto(String photoUrl) {
        this.photo = new Photo(photoUrl);
        return this;
    }

    Section addSection(String sectionTitle) {
        Section section = new Section(sectionTitle);
        this.sections.add(section);
        return section;
    }

    Document addSection(Section section) {
        this.sections.add(section);
        return this;
    }

    void addCSS(PrintStream out) {
        out.printf("""
                <style>
                .myDiv {
                background-color: pink;
                border: 2px solid maroon;
                border-radius: 25px;
                padding: 20px;
                text.align: center;
                color: maroon;
                width: 1000px;
                margin: auto;
                padding: 200px 0 80px 100px;
                font-family: "Verdana", Sans-serif;
                }
                body {
                background-color: linen;
                
                }
                #photo {
                position: right top;
                margin-right: 10px;
                
                }
                #paragraph1 {
                background-color: violet;
                color: maroon;
                text-align: center;
                }
                </style>
                """,this.photo.url);
    }

    void writeHTML(PrintStream out) {
        out.printf("""
                <!DOCTYPE html>
                <html>
                <head>
                <link rel="stylesheet" type="text/css" href="css/style.css">
                <title>%s</title>
                """, this.title);
        this.addCSS(out);
        this.photo.writeHTML(out);
        out.printf("""
                </head>
                <body style="background-color: linen">
                <div class="myDiv">
                """);
        for (Section section : this.sections) {
            section.writeHTML(out);
        }
        out.print("</div></body></html>");
    }

    String toJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    Document fromJson(String jsonString) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonString, Document.class);
    }

    String toJson2() {
        RuntimeTypeAdapterFactory<Paragraph> adapter =
                RuntimeTypeAdapterFactory
                        .of(Paragraph.class)
                        .registerSubtype(Paragraph.class)
                        .registerSubtype(ParagraphWithList.class);
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(adapter).setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
