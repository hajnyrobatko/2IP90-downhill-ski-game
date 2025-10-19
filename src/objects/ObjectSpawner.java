package objects;

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
    private BufferedImage goldCoin, silverCoin;

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
            goldCoin = ImageIO.read(getClass().getResourceAsStream("/assets/images/objects/gold-coin/gold-coin-1.png"));
            silverCoin = ImageIO
                    .read(getClass().getResourceAsStream("/assets/images/objects/silver-coin/silver-coin-1.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(int playerX, int playerY, int playerWidth, int playerHeight) {

        // spawn randomly gold Coins
        if (random.nextInt(180) == 0) {

            int x = randomX(goldCoin.getWidth());
            int y = -goldCoin.getHeight(); // start above the screen

            obstacles.add(new Obstacle(x, y, goldCoin, "goldCoin"));
        }

        // spawn randomly silver Coins
        if (random.nextInt(80) == 0) {

            int x = randomX(silverCoin.getWidth());
            int y = -silverCoin.getHeight(); // start above the screen

            obstacles.add(new Obstacle(x, y, silverCoin, "silverCoin"));
        }

        // move down + check collisions
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            obstacle.y += scrollSpeed;

            boolean removeThis = false;

            if (collidesWithPlayer(obstacle, playerX, playerY, playerWidth, playerHeight)) {

                if ("goldCoin".equals(obstacle.getType())) {
                    score += 3;
                    System.out.println("Score: " + score);
                } else if ("silverCoin".equals(obstacle.getType())) {
                    score += 1;
                    System.out.println("Score: " + score);
                } else {

                }

                removeThis = true;
            }

            // remove off-screen obstacles
            if (obstacle.y > screenHeight + obstacle.h) {
                removeThis = true;
            }

            if (removeThis) {
                obstacles.remove(i--);
            }
        }
    }

    public void draw(Graphics2D g) {
        for (Obstacle o : obstacles) {
            g.drawImage(o.img, o.x, o.y, null);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Score: " + score, 50, 50);
    }

    private boolean collidesWithPlayer(Obstacle obstacle, int playerX, int playerY, int playerWidth, int playerHeight) {

        boolean overlapsHorizontally = obstacle.x + obstacle.w > playerX && // obstacle right and player’s left edge
                obstacle.x < playerX + playerWidth; // obstacle left edge and player’s right edge

        boolean overlapsVertically = obstacle.y + obstacle.h > playerY && // obstacle bottom edge and player’s top edge
                obstacle.y < playerY + playerHeight; // obstacle top edge and player’s bottom edge

        return overlapsHorizontally && overlapsVertically;
    }

    private int randomX(int objectWidth) {
        int minX = leftLine;
        int maxX = rightLine - objectWidth;
        return random.nextInt(maxX - minX + 1) + minX;
    }
}
