package choinki;

import java.awt.*;

class Trunk implements XmasShape {
    int x, y, width, height;
    Color color;

    Trunk(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x, y);
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fillRect(-width / 2, 0, width, height);
    }
}
