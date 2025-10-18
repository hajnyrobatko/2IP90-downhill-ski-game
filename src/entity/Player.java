package entity;

import game.GamePanel;
import game.KeyHandler;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    double angle;

    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        x = 610; //player centered
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
        int dx = 0;

        if (keyH.leftPressed == true) {
            direction = "left";
            x -= speed;
            angle = -10;
        }

        else if (keyH.rightPressed == true) {
            direction = "right";
            x += speed;
            angle = +10;
        }

        else if ((keyH.rightPressed == false) && (keyH.leftPressed == false)) {
            direction = "straight";
            angle = 0;
        }

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

        g2.rotate(Math.toRadians(angle), x, y);
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

    }
}
