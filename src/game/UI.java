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
    Graphics2D g2;
    Font base_mono_reg, base_mono_bold;
    Font mono_bold_48, mono_bold_24, mono_bold_30, mono_bold_20;
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
        mono_bold_30 = base_mono_bold.deriveFont(30f);
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

        this.g2 = g2;

        if (gp.gameState == gp.playState) {
            if (gameOver == true) {
                gameOver();
            } else {
                gameRunning();
            }
        }

        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
    }

    public void drawPauseScreen() {

        String text;

        g2.setColor(black_50_op);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(mono_bold_48);
        g2.setColor(Color.white);

        text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);

        g2.setFont(mono_bold_24);
        text = "Press P to resume.";
        x = getXforCenteredText(text);
        y = gp.screenHeight / 2 + 50;
        g2.drawString(text, x, y);

        text = "Press E to EXIT.";
        x = getXforCenteredText(text);
        y = gp.screenHeight / 2 + 100;
        g2.drawString(text, x, y);

    }

    public void gameRunning() {
        // box bound
        g2.setColor(black_50_op);
        g2.fillRoundRect(40, 20, 250, 145, 10, 10);

        // score
        g2.setFont(mono_bold_24);
        g2.setColor(goldColor);
        g2.drawImage(coinImage, 50, 20, gp.tileSize, gp.tileSize, null);
        g2.drawString("Score: " + gp.spawner.getScore(), 110, 60);

        // timer
        playTime += (double) 1 / 60;
        g2.setFont(mono_bold_24);
        g2.setColor(goldColor);
        g2.drawString("Time: " + dFormat.format(playTime), 66, 104);

        // message coin
        if (messageOn) {
            g2.setFont(mono_bold_20);
            g2.setColor(Color.white);
            g2.drawString(message, 66, 140);
            messageCounter++;

            if (messageCounter > 80) {
                messageCounter = 0;
                messageOn = false;
            }
        }
    }

    public void gameOver() {
        g2.setColor(black_50_op);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(mono_bold_48);
        g2.setColor(Color.white);

        String text;
        int textLength;
        int x;
        int y;

        // GAME OVER! YOU HAVE CRASHED!

        // shadow
        g2.setColor(Color.black);
        text = "GAME OVER! YOU HAVE CRASHED!";
        x = getXforCenteredText(text);
        y = gp.screenHeight / 2 - 50;
        g2.drawString(text, x, y);

        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);

        // SCORE
        g2.setFont(mono_bold_30);
        text = "Your score: " + gp.spawner.getScore();
        x = getXforCenteredText(text);
        y = gp.screenHeight / 2 + 25;
        g2.drawString(text, x, y);

        // TIME
        text = "Your time: " + dFormat.format(playTime);
        x = getXforCenteredText(text);
        y = gp.screenHeight / 2 + 75;
        g2.drawString(text, x, y);

        // RESTART (press R)
        g2.setFont(mono_bold_24);
        text = "To restart, press R";
        x = getXforCenteredText(text);
        y = gp.screenHeight / 2 + 150;
        g2.drawString(text, x, y);

        // GET BACK TO TITLE SCREEN
        text = "To go back HOME, press B";
        x = getXforCenteredText(text);
        y = gp.screenHeight / 2 + 200;
        g2.drawString(text, x, y);

        // E to exit
        text = "Press E to EXIT.";
        x = getXforCenteredText(text);
        y = gp.screenHeight / 2 + 250;
        g2.drawString(text, x, y);

        gp.gameThread = null;
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
}
