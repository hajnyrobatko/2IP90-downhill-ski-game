package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import objects.ObjectSpawner;

public class UI {
    
    GamePanel gp;
    Font arial_40;
    Font arial_24_bold;
    BufferedImage coinImage;
    Color goldColor;
    public boolean messageOn = false;
    public String message = "";


    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_24_bold = new Font("Arial", Font.BOLD, 24);

        goldColor = new Color(245, 219, 56);
        coinImage = gp.spawner.getGoldCoinImage();

    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        g2.setFont(arial_24_bold);
        g2.setColor(goldColor);
        g2.drawImage(coinImage, 50, 10, gp.tileSize, gp.tileSize, null);
        g2.drawString("Score: " + gp.spawner.getScore(),110, 50);

        // MESSAGE
        if(messageOn) {
            g2.drawString(message, 300, 300);
        }

    }
}
