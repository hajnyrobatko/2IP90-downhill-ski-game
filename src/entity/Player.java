package entity;

import game.GamePanel;
import game.KeyHandler;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        change_x = 0;
        x = 610;
        y = 500;
        speed = 8;
        direction = "straight";
        angle = 0;
    }

    public void getPlayerImage() {

        try {
            straight = ImageIO.read(getClass().getResourceAsStream("/assets/images/skier/skier-straight.png"));
            left = ImageIO.read(getClass().getResourceAsStream("/assets/images/skier/skier-turn-left.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/assets/images/skier/skier-turn-right.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if (keyH.leftPressed == true) {
            if (angle > -70) {
                angle -= 2;
                direction = "right";
            }
        }

        else if (keyH.rightPressed == true) {
            if (angle < 70) {
                angle += 2;
                direction = "left";
            }
        }

        else if (angle != 0) {
            angle -= 0.5 * (Math.abs(angle) / angle);
            direction = "straight";
        }

        change_x = (int) (10 * (Math.sin(Math.toRadians(angle))));
        x = x + change_x;

        x = gp.border.clampX(x, gp.tileSize);
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
            case "left":
                image = left;
                break;

            case "right":
                image = right;
                break;

            case "straight":
                image = straight;
                break;
        }

        var oldTransform = g2.getTransform();

        g2.rotate(Math.toRadians(angle), x, y);
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

        // Restore transform so coins aren’t affected
        g2.setTransform(oldTransform);
    }

    public int getAngle() {
        return angle;
    }
}
