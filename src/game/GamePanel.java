package game;

import entity.Player;
import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {
    
    // SCREEN SETTINGS
    final int originalTileSize = 32;    // 32x32 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;   // 96px
    final int maxScreenCol = 20;    
    final int maxScreenRow = 11;    
    final int screenWidth = tileSize * maxScreenCol;    // 1920 px
    final int screenHeight = tileSize * maxScreenRow;    // 1056 px

    final private Image backgroundImage;

    // FPS

    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyH);


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.requestFocusInWindow();

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

        player.draw(g2);

        g2.dispose();

    }

    protected void update() {
        player.update();
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;  // 0.1666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                // update game loop
                update();
                // redraw the screen
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }


        }

    }

}