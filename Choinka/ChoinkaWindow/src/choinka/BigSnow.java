package choinka;

import java.awt.*;

public class BigSnow implements XmasShape{
    int x;
    int y;
    int size;

    BigSnow(int x, int y, int size) {
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
        g2d.fillOval(x, y, size, size);
    }
}
