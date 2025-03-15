package choinka;

import java.awt.*;

public class Branch implements XmasShape{
    int x;
    int y;
    double scale;
    int height;
    int width;
    Color color;
    Polygon triangle = new Polygon();
    Bubbles bubbles;

    Branch(int x, int y, double scale) {
        this(x, y, scale, 80, 300, new Color(94, 168, 74));
    }

    Branch(int x, int y, double scale, int height, int width, Color color) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.height = height;
        this.width = width;
        this.color = color;
        triangle.addPoint(x-(width/2), y);
        triangle.addPoint(x+(width/2), y);
        triangle.addPoint(x, y-height);
        this.bubbles = new Bubbles(height/5, x, y, scale, height, width);
    }

    @Override
    public void transform(Graphics2D g2d){
        g2d.translate(x, y);
        g2d.scale(scale, scale);
    }

    @Override
    public void render(Graphics2D g2d){
        g2d.setColor(color);
        g2d.fillPolygon(triangle);
        bubbles.render(g2d);
    }
}
