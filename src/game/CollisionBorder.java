package game;

public class CollisionBorder {

    private final int leftLine;
    private final int rightLine;

    public CollisionBorder(int leftLine, int rightLine) {
        this.leftLine = leftLine;
        this.rightLine = rightLine;
    }

    // Makes sure the player does not go past left/right walls

    public int clampX(int x, int width) {
        // stop left edge
        if (x < leftLine) {
            x = leftLine;
        }

        // stop right edge
        if (x + width > rightLine) {
            x = rightLine - width;
        }

        return x;
    }

    }
