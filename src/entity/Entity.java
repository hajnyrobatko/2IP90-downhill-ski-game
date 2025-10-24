package entity;

import java.awt.image.BufferedImage;

public class Entity {
    public int change_x;
    public int x, y;
    public int speed;
    public int angle;

    public BufferedImage straightDefault, leftDefault, rightDefault;
    public BufferedImage straightNeon, leftNeon, rightNeon;
    public BufferedImage straightDune, leftDune, rightDune;

    public BufferedImage straight, left, right;
    public String direction;
}
