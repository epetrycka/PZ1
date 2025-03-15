import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;


public class DrawPanel extends JPanel implements CrossHairListener{
    BufferedImage background;
    AnimationThread thread = new AnimationThread();
    SpriteFactory factory;
    boolean initialized = false;
    private final List<Sprite> zombieList = new ArrayList<>();
    private final Semaphore mutex = new Semaphore(1);
    private CrossHair crossHair;
    private int score = 0;

    public DrawPanel(URL backgroundImageURL, SpriteFactory factory) {
        try {
            background = ImageIO.read(backgroundImageURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.crossHair = new CrossHair(this);
        crossHair.addCrossHairListener(this);
        this.factory = factory;
        thread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        if (!initialized) {
            zombieList.add(factory.newSprite(getWidth(), 500));
            initialized = true;
        }
        try {
            mutex.acquire();
            for (Sprite zombie : zombieList) {
                zombie.draw(g2d, this);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.release();
        }
        crossHair.draw(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 25);
    }

    @Override
    public void onShotsFired(int x, int y) {
        for (int i = zombieList.size() - 1; i >= 0; i--) {
            Sprite zombie = zombieList.get(i);
            if (zombie.isHit(x, y)) {
                zombieList.remove(i);
                score += 1;
                break;
            }
        }
    }

    public CrossHair getCrossHair() {
        return crossHair;
    }

    class AnimationThread extends Thread {
        private boolean running = true;

        public void stopThread() {
            running = false;
        }
        public void run() {
            while (running) {
                try {
                    mutex.acquire();
                    for (Sprite zombie : zombieList) {
                        zombie.next();
                    }
                    if (zombieList.size() < 6) {
                        zombieList.add(factory.newSprite(getWidth(), 500 - (int) (Math.random() * 100)));
                    }
                    zombieList.sort((z1, z2) -> z1.isCloser(z2) ? 1 : -1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    mutex.release();
                }
                repaint();
                try {
                    sleep(1000 / 30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

