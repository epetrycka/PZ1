package choinki;

import java.awt.*;

class Snowflake implements XmasShape {
    int x, y, size;

    Snowflake(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x, y);
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.fillOval(-size / 2, -size / 2, size, size);
    }
}
