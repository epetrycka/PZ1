import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ZombieFactory implements SpriteFactory {
    BufferedImage tape;
    BufferedImage tapeReversed;

    public ZombieFactory() {
        try {
            tape = ImageIO.read(getClass().getResource("zombie.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            tapeReversed = ImageIO.read(getClass().getResource("zombieR.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Sprite newSprite(int x, int y) {
        double scale = Math.random() * 1.2 + 0.2;
        return new Zombie(x, y, scale, tape, tapeReversed);
    }
}

