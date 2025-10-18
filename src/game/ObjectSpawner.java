package game;

import java.util.Random;

public class ObjectSpawner {

    private final int leftLine;
    private final int rightLine;
    private final Random random = new Random();

    public ObjectSpawner(int leftLine, int rightLine) {
        this.leftLine = leftLine;
        this.rightLine = rightLine;
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