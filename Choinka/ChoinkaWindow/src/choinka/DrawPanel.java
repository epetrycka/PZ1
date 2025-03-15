package choinka;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DrawPanel extends JPanel {
    List<XmasShape> shapes = new ArrayList<>();

    DrawPanel() {
        setBackground(new Color(241, 166, 252));
        shapes.add(new Tree(200,200,1));
        Random r = new Random();
        for (int i = 0; i < 700; i++) {
            shapes.add(new Snowflakes(r.nextInt(1000), r.nextInt(700), r.nextInt(5) + 2));
        }
        for (int i=-100; i<600; i=i+100){
            shapes.add(new BigSnow(i, 230, 1000));
        }
    }

    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        super.paintComponent(g2d);

        for (XmasShape shape : shapes) {
            shape.draw(g2d);
        }
    }
}
