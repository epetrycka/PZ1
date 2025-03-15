package choinka;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Tree implements XmasShape{
    int x;
    int y;
    double scale;
    List<XmasShape> decorations = new ArrayList<>();

    Tree(int x, int y, double scale){
        this.x = x;
        this.y = y;
        this.scale = scale;
        decorations.add(new Trunk(x, y, scale));
        decorations.add(new Branches(x, y, scale));
    }

    @Override
    public void transform(Graphics2D g2d){
        g2d.translate(x, y);
        g2d.scale(scale, scale);
    }

    @Override
    public void render(Graphics2D g2d){
        for (XmasShape decoration : decorations){
            decoration.render(g2d);
        }
    }
}
