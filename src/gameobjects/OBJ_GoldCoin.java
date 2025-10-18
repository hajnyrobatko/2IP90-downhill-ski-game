package gameobjects;

import java.io.IOException;
import javax.imageio.ImageIO;

public class OBJ_GoldCoin extends SuperObject {

    public OBJ_GoldCoin() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/assets/images/objects/gold-coin/gold-coin-1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
