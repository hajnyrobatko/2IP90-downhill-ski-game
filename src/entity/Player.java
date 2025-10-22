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
        x = 500;
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
            if (angle > -90){
            angle -= 5;
            }
        }

        else if (keyH.rightPressed == true) {
            if (angle < 90){
            angle += 5;
            }
        }
        System.out.println((Math.sin(Math.toRadians(angle))));
        change_x = (int)(10*(Math.sin(Math.toRadians(angle))));
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = straight;
        x = x + change_x;
        System.out.println(x);
        g2.rotate(Math.toRadians(angle), x, y);
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

    }
}
