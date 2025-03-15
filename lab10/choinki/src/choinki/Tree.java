package choinki;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tree implements XmasShape{
    int x;
    int y;
    List<XmasShape> decorations = new ArrayList<>();

    Tree(int x, int y) {
        this.x = x;
        this.y = y;

        decorations.add(new Branch(0, 0, 1.5, new Color(0, 128, 0), new Color(0, 100, 0), 300, 100));
        decorations.add(new Branch(0, 100, 1.2, new Color(0, 128, 0), new Color(0, 100, 0), 250, 100));
        decorations.add(new Branch(0, 200, 1.0, new Color(0, 128, 0), new Color(0, 100, 0), 200, 100));

        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            int bx = r.nextInt(200) - 100;
            int by = r.nextInt(300);
            double scale = 0.5 + r.nextDouble() * 0.5;
            Color fillColor = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
            Color lineColor = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
            decorations.add(new Bubble(bx, by, scale, lineColor, fillColor));
        }

        decorations.add(new Star(0, -30, 1, Color.YELLOW));
        decorations.add(new Trunk(0, 300, 50, 100, new Color(139, 69, 19)));

    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x, y);
    }

    @Override
    public void render(Graphics2D g2d) {
        for (XmasShape s : decorations) {
            s.draw(g2d);
        }
    }
}
