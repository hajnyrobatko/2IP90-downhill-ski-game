package objects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import game.GamePanel;

public class ObjectSpawner {

    private final int leftLine;
    private final int rightLine;
    private final int screenHeight;
    private final double scrollSpeed;
    private final Random random = new Random();

    private final List<Obstacle> obstacles = new ArrayList<>();
    private BufferedImage goldCoin, silverCoin, tree, goldCoinIcon;

    GamePanel gp;

    public boolean gameEnd = false;

    public int score = 0;

    public ObjectSpawner(int leftLine, int rightLine, int screenHeight, double scrollSpeed, GamePanel gp) {
        this.leftLine = leftLine;
        this.rightLine = rightLine;
        this.screenHeight = screenHeight;
        this.scrollSpeed = scrollSpeed; // = 4
        this.gp = gp;

        loadImages();
    }

    private void loadImages() {

        try {
            goldCoin = ImageIO.read(getClass().getResourceAsStream("/assets/images/objects/gold-coin/gold-coin-1.png"));
            goldCoinIcon = ImageIO.read(getClass().getResourceAsStream("/assets/images/objects/gold-coin/gold-coin-icon.png"));
            silverCoin = ImageIO
                    .read(getClass().getResourceAsStream("/assets/images/objects/silver-coin/silver-coin-1.png"));
            tree = ImageIO.read(getClass().getResourceAsStream("/assets/images/objects/trees/tree-4.png"));

            // scale tree
            tree = new AffineTransformOp(AffineTransform.getScaleInstance(2.0, 2.0), AffineTransformOp.TYPE_BILINEAR)
                    .filter(tree, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void spawnObject(int interval, String type, BufferedImage spawnObject) {

        if (random.nextInt(interval) == 0) {
            int x = randomX(spawnObject.getWidth());
            int y = -spawnObject.getHeight(); // start above the screen
            obstacles.add(new Obstacle(x, y, spawnObject, type));
        }
    }

    public void update(int playerX, int playerY, int playerWidth, int playerHeight, double effectiveSpeed) {

        // spawn randomly

        //TODO: if effective speed is less than something spawn this much and if more spawn more

        spawnObject(180, "goldCoin", goldCoin);
        spawnObject(80, "silverCoin", silverCoin);
        spawnObject(180, "tree", tree);

        // move down + check collisions
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            obstacle.y += this.scrollSpeed*(effectiveSpeed); // plus effective speed
            
            boolean removeThis = false;

            if (collidesWithPlayer(obstacle, playerX, playerY, playerWidth, playerHeight)) {

                if ("goldCoin".equals(obstacle.getType())) {
                    gp.playSE(1);
                    score += 3;
                    removeThis = true;
                    System.out.println("Score: " + score);

                } else if ("silverCoin".equals(obstacle.getType())) {
                    gp.playSE(1);
                    score += 1;
                    removeThis = true;
                    System.out.println("Score: " + score);

                } else if ("tree".equals(obstacle.getType())) {
                    gp.playSE(2);
                    gp.ui.showMessage("GAME OVER. YOU HAVE CRASHED!");
                    gameEnd();

                }
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

    public boolean gameEnd() {
        gameEnd = true;
        return gameEnd;
    }

    public void draw(Graphics2D g) {
        for (Obstacle o : obstacles) {
            g.drawImage(o.img, o.x, (int) o.y, null);
        }
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

    public int getScore() {
        return score;
    }

    public BufferedImage getGoldCoinImage() {
        return goldCoinIcon;
    }
}