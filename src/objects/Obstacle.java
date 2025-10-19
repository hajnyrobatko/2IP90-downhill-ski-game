package objects;

import java.awt.image.BufferedImage; 

    public class Obstacle {

        int x, y, w, h;
        BufferedImage img;
        String type;

        Obstacle(int x, int y, BufferedImage img, String type) {
            this.x = x;
            this.y = y;
            this.img = img;
            this.w = img.getWidth();
            this.h = img.getHeight();
            this.type = type;
        }

        public String getType() {
            return type;
    }
    }

