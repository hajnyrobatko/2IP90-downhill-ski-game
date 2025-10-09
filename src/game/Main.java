package game;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame("Downhill Ski");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);

            GamePanel panel = new GamePanel();
            
            window.setContentPane(panel);
            window.pack();                        // sizes window to panel
            window.setLocationRelativeTo(null);   // centers on screen
            window.setVisible(true);
        });
    }
}