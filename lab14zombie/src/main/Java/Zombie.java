import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Zombie implements Sprite {
    BufferedImage tape;
    BufferedImage tapeReversed;
    int x = 500;
    int y = 500;
    double scale = 1;
    boolean reversed = false;

    int index = 1;  // numer wyświetlanego obrazka
    int HEIGHT = 312; // z rysunku;
    int WIDTH = 200; // z rysunku;

    Zombie(int x, int y, double scale, BufferedImage tape, BufferedImage tapeReversed) {
        this.tape = tape;
        this.tapeReversed = tapeReversed;
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.reversed = false;
    }

    /**
     * Pobierz odpowiedni podobraz klatki (odpowiadającej wartości zmiennej index)
     * i wyświetl go w miejscu o współrzędnych (x,y)
     *
     * @param g
     * @param parent
     */
    public void draw(Graphics g, JPanel parent) {
        if (x <= 0) {
            reversed = true;
            index = 9;
        }
        if (x >= parent.getWidth() - ((WIDTH * scale))) {
            reversed = false;
            index = 0;
        }
        if (reversed) {
            Image img = tapeReversed.getSubimage(index * WIDTH, 0, WIDTH, HEIGHT); // pobierz klatkę
            g.drawImage(img, x, y - (int) (HEIGHT * scale) / 2, (int) (WIDTH * scale), (int) (HEIGHT * scale), parent);
        } else {
            Image img = tape.getSubimage(index * WIDTH, 0, WIDTH, HEIGHT); // pobierz klatkę
            g.drawImage(img, x, y - (int) (HEIGHT * scale) / 2, (int) (WIDTH * scale), (int) (HEIGHT * scale), parent);
        }
    }

    /**
     * Zmień stan - przejdź do kolejnej klatki
     */
    public void next() {
        if (reversed) {
            x += 10 * scale;
            index = (index - 1) % 10;
            if (index < 0) index = 9;
        }
        if (!reversed) {
            x -= 10 * scale;
            index = (index + 1) % 10;
        }
    }

    public boolean isVisible(int screenWidth) {
        return x + WIDTH * scale > 0 && x < screenWidth;
    }

    public boolean isHit(int _x, int _y) {
        return _x >= x && _x <= (x + WIDTH * scale) &&
                _y >= (y - (int) (HEIGHT * scale) / 2) &&
                _y <= (y - (int) (HEIGHT * scale) / 2 + (int) (HEIGHT * scale) / 2);
    }

    public boolean isCloser(Sprite other) {
        if (other instanceof Zombie) {
            Zombie z = (Zombie) other;
            return this.scale > z.scale;
        }
        return false;
    }
}
