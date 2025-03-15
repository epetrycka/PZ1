package choinki;

import java.awt.*;

public class Bubble implements XmasShape {
    int x;
    int y;
    double scale;
    Color lineColor;
    Color fillColor;

    Bubble(int x, int y, double scale, Color lineColor, Color fillColor) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.lineColor = lineColor;
        this.fillColor = fillColor;
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x, y);
        g2d.scale(scale, scale);
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(fillColor);
        g2d.fillOval(-10, -10, 20, 20);

        g2d.setColor(lineColor);
        g2d.drawOval(-10, -10, 20, 20);
    }
}