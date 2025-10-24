package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Font base_mono_reg, base_mono_bold;
    Font mono_bold_48, mono_bold_24, mono_bold_20;
    BufferedImage coinImage;
    Color goldColor, black_50_op;

    public boolean messageOn = false;
    public String message = "";
    int messageCounter;
    public boolean gameOver = false;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;
        loadFonts();

        mono_bold_48 = base_mono_bold.deriveFont(48f);
        mono_bold_24 = base_mono_bold.deriveFont(24f);
        mono_bold_20 = base_mono_bold.deriveFont(20f);

        goldColor = new Color(245, 219, 56);
        black_50_op = new Color(0, 0, 0, 128);
        coinImage = gp.spawner.getGoldCoinImage();

        messageCounter = 0;

    }

    public void loadFonts() {
        try {
            InputStream is = getClass().getResourceAsStream("/assets/font/SpaceMono-Regular.ttf");
            base_mono_reg = Font.createFont(Font.TRUETYPE_FONT, is);

            is = getClass().getResourceAsStream("/assets/font/SpaceMono-Bold.ttf");
            base_mono_bold = Font.createFont(Font.TRUETYPE_FONT, is);

        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        if (gameOver == true) {

            g2.setColor(black_50_op);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            g2.setFont(mono_bold_48);
            g2.setColor(Color.white);

            String text;
            int textLength;
            int x;
            int y;

            // return the length of the text to display
            text = "GAME OVER! YOU HAVE CRASHED!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 - 30;
            g2.drawString(text, x, y);

            // return the length of the text to display
            text = "YOUR SCORE: " + gp.spawner.getScore();
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + 50;
            g2.drawString(text, x, y);

            // return the length of the text to display
            text = "YOUR TIME: " + dFormat.format(playTime);
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + 100;
            g2.drawString(text, x, y);

            gp.gameThread = null;

        } else {
            g2.setFont(mono_bold_24);
            g2.setColor(goldColor);
            g2.drawImage(coinImage, 50, 10, gp.tileSize, gp.tileSize, null);
            g2.drawString("Score: " + gp.spawner.getScore(), 110, 50);

            // TIME
            playTime += (double) 1 / 60;
            g2.setFont(mono_bold_24);
            g2.setColor(goldColor);
            g2.drawString("Time: " + dFormat.format(playTime), gp.screenWidth - 200, 50);

            // MESSAGE
            if (messageOn) {
                g2.setFont(mono_bold_20);
                g2.setColor(Color.white);
                g2.drawString(message, 66, 95);
                messageCounter++;

                if (messageCounter > 80) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
    }
}
