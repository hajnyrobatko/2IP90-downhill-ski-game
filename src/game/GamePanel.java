package game;

import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    private static final int WIDTH = 960;
    private static final int HEIGHT = 540;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(180, 220, 255)); // light-blue background
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}