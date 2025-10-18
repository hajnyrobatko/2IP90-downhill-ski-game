package game;

import gameobjects.OBJ_GoldCoin;
import java.util.Random;

public class ObjectSpawner {

    GamePanel gp;

    private final int leftLine;
    private final int rightLine;
    private final Random random = new Random();

    public ObjectSpawner(int leftLine, int rightLine, GamePanel gp) {
        this.leftLine = leftLine;
        this.rightLine = rightLine;
        this.gp = gp;
    }

    public void setObject() {
        int objectWidth = gp.tileSize;

        gp.obj[0] = new OBJ_GoldCoin();
        gp.obj[0].worldX = randomX(objectWidth);
        gp.obj[0].worldY = randomY(0, 0);

        gp.obj[1] = new OBJ_GoldCoin();
        gp.obj[1].worldX = randomX(objectWidth);
        gp.obj[1].worldY = randomY(600, 800);

    }

    // Returns a random x position fully within the playable corridor
    public int randomX(int objectWidth) {
        int minX = leftLine;
        int maxX = rightLine - objectWidth;
        return random.nextInt(maxX - minX + 1) + minX;
    }

    // Returns a random y position, e.g. above the screen
    public int randomY(int minY, int maxY) {
        return random.nextInt(maxY - minY + 1) + minY;
    }
}