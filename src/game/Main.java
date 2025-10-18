package game;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame window = new JFrame("Downhill OG");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(true);

            GamePanel gamePanel = new GamePanel();

            window.setContentPane(gamePanel);
            window.pack(); // sizes window to panel
            window.setLocationRelativeTo(null); // centers on screen
            window.setVisible(true);

            gamePanel.setupGame();
            gamePanel.startGameThread(); // lllll
        });
    }
}