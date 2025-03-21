package main.java.com.zombie;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Zombie");
        System.out.println(System.getProperty("user.dir"));
        DrawPanel panel = new DrawPanel(Main.class.getResource("assets/6473205443_7df3397e72_b.jpg"));
        frame.setContentPane(panel);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
    }
}