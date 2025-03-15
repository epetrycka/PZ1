package choinka;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Branches implements XmasShape{
    int x;
    int y;
    double scale;
    int height;
    int width;
    Color color;
    Polygon triangle = new Polygon();
    List<Branch> branches = new ArrayList<>();

    Branches(int x, int y, double scale) {
        this(x, y, scale, 80, 300, new Color(94, 168, 74));
    }

    Branches(int x, int y, double scale, int height, int width, Color color) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.height = height;
        this.width = width;
        this.color = color;
        triangle.addPoint(x-(width/2), y);
        triangle.addPoint(x+(width/2), y);
        triangle.addPoint(x, y-height);
        branches.add(new Branch(x, y, 1));
        int height2=height;
        int width2=width;
        int y2=y;
        for (int i=1; i<8; i++)
        {
            y2=y2-height2+height2/4;
            height2=(int)(height2 * Math.pow((double)12/13, i));
            width2=(int)(width2 * Math.pow((double)12/13, i));
            branches.add(new Branch(x, y2, 1, height2, width2, color));
        }
    }

    @Override
    public void transform(Graphics2D g2d){
        g2d.translate(x, y);
        g2d.scale(scale, scale);
    }

    @Override
    public void render(Graphics2D g2d){
        for(Branch b : branches){
            b.render(g2d);
        }
    }
}
