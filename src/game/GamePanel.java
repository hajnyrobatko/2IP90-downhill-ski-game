package game;

import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {
    
    // SCREEN SETTINGS
    final int originalTileSize = 16;    // 16x16 tile
    final int scale = 3;

    final int tileSize = originalTileSize * scale;  // 48x48 tile
    final int maxScreenCol = 20;    // 320 px
    final int maxScreenRow = 12;    // 180 px
    final int screenWidth = tileSize * maxScreenCol;     // 960 px
    final int screenHeight = tileSize * maxScreenRow;    // 576 px

    final private Image backgroundImage;

    Thread gameThread;

    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setFocusable(true);
        requestFocusInWindow();

        // Load background image
        backgroundImage = new ImageIcon(getClass().getResource("/assets/images/background-test.jpg")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        

        // Draw the background image
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
    }

    protected void update() {

    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {

            // update game loop
            update();

            // redraw the screen
            repaint();
        }

    }

}