package game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;

public class ObjectSpawner {

    private final int leftLine;
    private final int rightLine;
    private final int screenHeight;
    private final int scrollSpeed;
    private final Random random = new Random();

    private final List<Obstacle> obstacles = new ArrayList<>();
    private BufferedImage coinImage;

    public int score = 0; 

    public ObjectSpawner(int leftLine, int rightLine, int screenHeight, int scrollSpeed) {
        this.leftLine = leftLine;
        this.rightLine = rightLine;
        this.screenHeight = screenHeight;
        this.scrollSpeed = scrollSpeed;

        loadImages();
    }

    private void loadImages() {
        try {
            coinImage = ImageIO.read(getClass().getResourceAsStream("/assets/images/objects/gold-coin/gold-coin-1.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void update(int playerX, int playerY, int playerWidth, int playerHeight) {

        // spawn random
        if (random.nextInt(60) == 0) {

            int x = randomX(coinImage.getWidth());
            int y = -coinImage.getHeight(); // start above the screen

            obstacles.add(new Obstacle(x, y, coinImage));
        }

        // move down + check collisions
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obsatcle = obstacles.get(i);
            obsatcle.y += scrollSpeed;

            if (collidesWithPlayer(obsatcle, playerX, playerY, playerWidth, playerHeight)) {
                obstacles.remove(i);
                i--;
                score += 1;   
                System.out.println("Score: " + score);
                continue;
            }

            // remove off-screen obstacles
            if (obsatcle.y > screenHeight + obsatcle.h) {
                obstacles.remove(i);
                i--;
            }
        }
    }

    public void draw(Graphics2D g) {
        for (Obstacle o : obstacles) {
            g.drawImage(o.img, o.x, o.y, null);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Score: " + score, 50, 50);
    }

    private boolean collidesWithPlayer(Obstacle obstacle, int playerX, int playerY, int playerWidth, int playerHeight) {

        boolean overlapsHorizontally = 
            obstacle.x + obstacle.w > playerX &&        // obstacle right edge is past player’s left edge
            obstacle.x < playerX + playerWidth;  // obstacl left edge is before player’s right edge

        boolean overlapsVertically = 
            obstacle.y + obstacle.h > playerY &&        // obstacle bottom edge is past player’s top edge
            obstacle.y < playerY + playerHeight; // obstacle top edge is before player’s bottom edge

        return overlapsHorizontally && overlapsVertically;
    }

    private int randomX(int objectWidth) {
        int minX = leftLine;
        int maxX = rightLine - objectWidth;
        return random.nextInt(maxX - minX + 1) + minX;
    }

    // --- inner class for objects ---
    private static class Obstacle {
        int x, y, w, h;
        BufferedImage img;
        Obstacle(int x, int y, BufferedImage img) {
            this.x = x;
            this.y = y;
            this.img = img;
            this.w = img.getWidth();
            this.h = img.getHeight();
        }
    }
}