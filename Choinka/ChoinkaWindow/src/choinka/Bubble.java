package choinka;

import java.awt.*;
import java.util.Random;

public class Bubble implements XmasShape {
    int x;
    int y;
    int diameter;
    double scale;
    Color color;

    public enum Colors {
        PINK(255, 0, 127),
        ORANGE(255, 178, 102),
        RED(255, 0, 0),
        PURPLE(153, 51, 255),
        BLUE(0, 255, 255),
        YELLOW(255, 255, 51);

        private final Color color;

        Colors(int r, int g, int b) {
            this.color = new Color(r, g, b);
        }

        public Color getColor() {
            return color;
        }

        public static Color getRandomColor() {
            Random random = new Random();
            Colors[] colors = Colors.values();
            int randomIndex = random.nextInt(colors.length);
            return colors[randomIndex].getColor();
        }
    }

    Bubble(int x, int y, double scale, int diameter) {
        this.x = x - diameter/2;
        this.y = y - diameter/2;
        this.scale = scale;
        this.diameter = diameter;
        this.color = Colors.getRandomColor();
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x, y);
        g2d.scale(scale, scale);
    }

    @Override
    public void render(Graphics2D g2d){
        g2d.setColor(color);
        g2d.fillOval(x, y, diameter, diameter);
    }
}
