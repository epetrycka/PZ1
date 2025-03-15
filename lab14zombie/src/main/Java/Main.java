import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("DrawPanel.Zombie");
        System.out.println(System.getProperty("user.dir"));
        DrawPanel panel = new DrawPanel(Main.class.getResource("background.jpg"), new ZombieFactory());
        frame.setContentPane(panel);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                panel.thread.stopThread();
                panel.getCrossHair().stopTimer();
                System.exit(0);
            }
        });
    }
}