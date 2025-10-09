package game;

import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1080;
    private Image backgroundImage;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocusInWindow();

        // Load background image
        backgroundImage = new ImageIcon(getClass().getResource("/assets/images/background-test.jpg")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
    }
}