package choinki;

import java.awt.*;

class Star implements XmasShape {
    int x, y, size;
    Color color;

    Star(int x, int y, int size, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x, y);
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(color);
        for (int i = 0; i < 5; i++) {
            g2d.fillPolygon(new int[]{0, size / 3, -size / 3},
                    new int[]{-size, size / 2, size / 2}, 3);
            g2d.rotate(Math.PI / 2.5);
        }
    }
}

