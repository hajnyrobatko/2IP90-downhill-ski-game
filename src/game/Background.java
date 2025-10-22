package game;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Background {

    GamePanel gp;
    public BufferedImage background;

    // how much the screen moved
    private int scrollDefault;
    private int scrollSpeed;

    public Background(GamePanel gp) {
        this.gp = gp;
        setDefaultValues();

        try {
            background = ImageIO.read(getClass().getResourceAsStream("/assets/images/background/background-image.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        scrollDefault = 1;
        scrollSpeed = 1; //plus effective speed
    }

    public void update() {

        // how much the screen moves every frame
        scrollDefault += scrollSpeed;

        // If scrolled through entire image, back to start
        if (scrollDefault >= gp.originalHeight)
            scrollDefault -= gp.originalHeight;
    }

    public void draw(Graphics2D g2) {

        // Compute where to draw the 2 images
        int mainBgY = scrollDefault * gp.scale;
        int topBgY = mainBgY - gp.screenHeight;

        g2.drawImage(background, 0, topBgY, gp.screenWidth, topBgY + gp.screenHeight, 0, 0, gp.originalWidth,
                gp.originalHeight, null);
        g2.drawImage(background, 0, mainBgY, gp.screenWidth, mainBgY + gp.screenHeight, 0, 0, gp.originalWidth,
                gp.originalHeight, null);
    }

    public int getScrollDefault() {
        return scrollDefault;
    }
}