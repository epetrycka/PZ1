import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CrossHair implements MouseMotionListener, MouseListener {

    DrawPanel parent;
    int x;
    int y;
    boolean activated = false;
    Timer timer = new Timer("Timer");

    public CrossHair(DrawPanel parent) {
        this.parent = parent;
        parent.addMouseMotionListener(this);
        parent.addMouseListener(this);
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));

        if (activated) {
            g2d.setColor(Color.RED);
        } else {
            g2d.setColor(Color.WHITE);
        }
        g2d.drawOval(x - 15, y - 15, 30, 30);
        g2d.drawOval(x - 5, y - 5, 10, 10);
        g2d.drawLine(x, y - 20, x, y + 20);
        g2d.drawLine(x - 20, y, x + 20, y);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        parent.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        activated = true;
        parent.repaint();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                activated = false;
                parent.repaint();
            }
        }, 300);

        notifyListeners();
    }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    private List<CrossHairListener> listeners = new ArrayList<>();

    public void addCrossHairListener(CrossHairListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (CrossHairListener listener : listeners) {
            listener.onShotsFired(x, y);
        }
    }
    public void stopTimer() {
        timer.cancel();
    }
}