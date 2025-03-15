package choinki;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Branch implements XmasShape{
    int x;
    int y;
    double scale;
    Color lineColor;
    Color fillColor;
    int width;
    int height;

    Branch(int x, int y, double scale, Color fillColor, Color lineColor, int width, int height) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.lineColor = lineColor;
        this.fillColor = fillColor;
        this.width = width;
        this.height = height;
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x, y);
        g2d.scale(scale, scale);
    }

    @Override
    public void render(Graphics2D g2d) {
        GradientPaint gp = new GradientPaint(0, 0, fillColor, 0, height, fillColor.darker());
        g2d.setPaint(gp);
        int[] xPoints = {0, -width / 2, width / 2};
        int[] yPoints = {0, height, height};
        g2d.fillPolygon(xPoints, yPoints, 3);

        g2d.setColor(lineColor);
        g2d.drawPolygon(xPoints, yPoints, 3);
    }
}
