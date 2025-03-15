package main.java.com.zombie;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class DrawPanel extends JPanel {
    BufferedImage background;

    DrawPanel(URL backgroundImagageURL){
        try {
            background = ImageIO.read(backgroundImagageURL);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public void paintComponent(Graphics g){
        Graphics2D g2d= (Graphics2D)g;
        g.drawImage(background,0,0,getWidth(),getHeight(),this);
    }
}
