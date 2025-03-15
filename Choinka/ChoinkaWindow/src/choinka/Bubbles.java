package choinka;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Bubbles implements XmasShape {
    int x;
    int y;
    double scale;
    int height;
    int width;
    List<Bubble> bubbles = new ArrayList<>();
    int bubbleCount;

    Bubbles(int bubbleCount, int x, int y, double scale, int height, int width) {
        this.bubbleCount = bubbleCount;
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.height = height;
        this.width = width;
//        bubbles.add(new Bubble(x, y, scale, height/4));
        Random random = new Random();
        for (int i = 0; i < bubbleCount; i++) {
            int diameter = random.nextInt(height/3);
            int width2 = random.nextInt(width);
            int x2 = x - width/2 + width2;
            int y2 = y - random.nextInt(height);
            if (width2 < width / 2) {
                y2 = y - random.nextInt(Math.max(1, (int) (height * (width2 / (double)(width / 2)))));
            } else if (width2 > width / 2) {
                y2 = y - random.nextInt(Math.max(1, (int) (height * ((width - width2) / (double)(width / 2)))));
            }
            bubbles.add(new Bubble(x2, y2, scale, diameter));
        }
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x, y);
        g2d.scale(scale, scale);
    }

    @Override
    public void render(Graphics2D g2d) {
        for (Bubble bubble : bubbles) {
            bubble.render(g2d);
        }
    }
}
