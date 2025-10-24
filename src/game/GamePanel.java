package game;

import entity.Player;
import java.awt.*;
import javax.swing.*;
import objects.ObjectSpawner;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 4;

    public final int tileSize = originalTileSize * scale; // 64px
    final int maxScreenCol = 20;
    final int maxScreenRow = 11;
    final int screenWidth = tileSize * maxScreenCol; // 1280 px
    final int screenHeight = tileSize * maxScreenRow; // 704 px

    final int originalWidth = maxScreenCol * originalTileSize; // 320 px - background size
    final int originalHeight = maxScreenRow * originalTileSize; // 176 px - background size

    // FPS
    int FPS = 60;

    // SYSTEM
    KeyHandler keyH = new KeyHandler();
    Sound se = new Sound();
    Sound music = new Sound();
    public CollisionBorder border = new CollisionBorder(300, 1000);
    private Background background = new Background(this);
    Thread gameThread;

    // ENTITY AND OBJECTS
    Player player = new Player(this, keyH);
    public ObjectSpawner spawner = new ObjectSpawner(300, 1000, screenHeight, background.getScrollDefault() * scale, this);

    double effectiveSpeed;

    public UI ui = new UI(this);
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    public void setupGame() {
        playMusic(0);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        background.draw(g2);
        player.draw(g2);
        spawner.draw(g2);
        ui.draw(g2);

        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }

    protected void update() {
        effectiveSpeed = (0.05*spawner.getScore()+2) * (Math.cos(Math.toRadians(player.getAngle())));

        background.update(effectiveSpeed);
        player.update();
        spawner.update(player.x, player.y, tileSize, tileSize, effectiveSpeed);
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS; // 0.1666 seconds
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