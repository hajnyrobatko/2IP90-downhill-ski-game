package game;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Background {

    GamePanel gp;
    public BufferedImage background, scalledBackground;

    // how much the screen moved
    private double scrollDefault;
    private double scrollSpeed;

    public Background(GamePanel gp) {
        this.gp = gp;
        setDefaultValues();

        try {
            background = ImageIO.read(getClass().getResourceAsStream("/assets/images/background/background-image.png"));
            scalledBackground = scaleImage(background, gp.screenWidth, gp.screenHeight);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        scrollDefault = 1;
    }

    public void update(double effectiveSpeed, double scrollSpeed) {

        // how much the screen moves every frame
        scrollDefault += scrollSpeed * effectiveSpeed;

        // If scrolled through entire image, back to start
        if (scrollDefault * gp.scale >= gp.screenHeight) {
            scrollDefault = 0;
        }
    }

    public void draw(Graphics2D g2) {

        // Compute where to draw the 2 images
        double mainBgY = scrollDefault * gp.scale;
        double topBgY = mainBgY - gp.screenHeight;

        g2.drawImage(scalledBackground, 0, (int) topBgY, null);
        g2.drawImage(scalledBackground, 0, (int) mainBgY, null);
    }

    private BufferedImage scaleImage(BufferedImage original, int width, int height) {

        BufferedImage scaled = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaled.createGraphics();
        
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();
        return scaled;
    }

    public double getScrollDefault() {
        return scrollDefault;
    }
}