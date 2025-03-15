package choinka;

import java.awt.*;

public class Trunk implements XmasShape{
    int x;
    int y;
    double scale;
    int height;
    int width;
    Color color;

    Trunk(int x, int y, double scale) {
        this(x, y, scale, 70, 50, new Color(197, 105, 74));
    }

    Trunk(int x, int y, double scale, int height, int width, Color color) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.height = height;
        this.width = width;
        this.color = color;
    }

    @Override
    public void transform(Graphics2D g2d){
        g2d.translate(x, y);
        g2d.scale(scale, scale);
    }

    @Override
    public void render(Graphics2D g2d){
        g2d.setColor(color);
        g2d.fillRect(x-(width/2), y, width, height);
    }
}
