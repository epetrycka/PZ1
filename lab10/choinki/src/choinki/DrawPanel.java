package choinki;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel {
    List<XmasShape> shapes = new ArrayList<>();

    DrawPanel() {
        setBackground(new Color(211, 252, 166));
        shapes.add(new Tree(400, 50));
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gp = new GradientPaint(0, 0, new Color(135, 206, 235), 0, getHeight(), new Color(211, 252, 166));
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        super.paintComponent(g);

        for (XmasShape s : shapes) {
            s.draw((Graphics2D) g);
        }
    }
}