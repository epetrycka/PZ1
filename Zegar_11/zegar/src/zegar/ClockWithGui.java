package zegar;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.time.LocalTime;

public class ClockWithGui extends JPanel {
    LocalTime time = LocalTime.now();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clock");
        frame.setContentPane(new ClockWithGui());
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    ClockWithGui() {
        new ClockThread().start();
    }

    @Override
    public void paintComponent(Graphics g) {
        setBackground(new Color(252, 166, 248));
        super.paintComponent(g);
        Graphics2D g2d=(Graphics2D)g;
        g2d.translate(getWidth()/2,getHeight()/2);

        g.setColor(new Color(255, 70, 160, 255));
        g.fillOval(-151, -152,303,303);
        g.setColor(new Color(71, 0, 71));
        g.drawOval(-151, -152,303,303);

        for(int i=1;i<13;i++){
            AffineTransform at = new AffineTransform();
            at.rotate(2*Math.PI/12*i);
            Point2D src = new Point2D.Float(0,-120);
            Point2D trg = new Point2D.Float();
            at.transform(src,trg);
            g2d.drawString(Integer.toString(i),(int)trg.getX(),(int)trg.getY());
        }

        for (int i = 1; i <= 60; i++) {
            AffineTransform at = new AffineTransform();
            at.rotate(2 * Math.PI / 60 * i);

            Point2D src = new Point2D.Float(0, -150);

            Point2D trg = new Point2D.Float();
            at.transform(src, trg);

            double dx = -trg.getX();
            double dy = -trg.getY();

            double length = Math.sqrt(dx * dx + dy * dy);

            double unitX = dx / length;
            double unitY = dy / length;

            double newX = trg.getX() + unitX * 5;
            double newY = trg.getY() + unitY * 5;

            g2d.drawLine((int) newX, (int) newY, (int) trg.getX(), (int) trg.getY());
        }

        for (int i = 1; i <= 60; i++) {
            AffineTransform at = new AffineTransform();
            at.rotate(2 * Math.PI / 60 * i);

            Point2D src = new Point2D.Float(0, -150);

            Point2D trg = new Point2D.Float();
            at.transform(src, trg);

            g2d.drawString(Integer.toString(i),(int)trg.getX(),(int)trg.getY());

            double dx = -trg.getX();
            double dy = -trg.getY();

            double length = Math.sqrt(dx * dx + dy * dy);

            double unitX = dx / length;
            double unitY = dy / length;

            double newX = trg.getX() + unitX * 5;
            double newY = trg.getY() + unitY * 5;

            g2d.drawLine((int) newX, (int) newY, (int) trg.getX(), (int) trg.getY());
        }

        AffineTransform saveAT = g2d.getTransform();
        g2d.setStroke(new BasicStroke(5));
        g2d.rotate(time.getHour()%12*2*Math.PI/12 + ((double) time.getMinute()/60)*2*Math.PI/12);
        g.setColor(new Color(0, 87, 135));
        g2d.drawLine(0,0,0,-70);
        g2d.setTransform(saveAT);
        g2d.setStroke(new BasicStroke(4));
        g2d.rotate(((double) time.getMinute()/60)*2*Math.PI + ((double) time.getSecond()/360)*2*Math.PI/12);
        g.setColor(new Color(0, 152, 113));
        g2d.drawLine(0,0,0,-100);
        g2d.setTransform(saveAT);
        g2d.setStroke(new BasicStroke(2));
        g2d.rotate(((double) time.getSecond()/60)*2*Math.PI);
        g.setColor(new Color(129, 0, 57));
        g2d.drawLine(0,0,0,-110);
        g2d.setTransform(saveAT);
    }

    class ClockThread extends Thread{
        @Override
        public void run() {
            for(;;){
                time = LocalTime.now();
                System.out.printf("%02d:%02d:%02d\n",time.getHour(),time.getMinute(),time.getSecond());

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                repaint();
            }
        }
    }
}
