package object;

import main.GamePanel;
import main.UtilityTool; // Make sure to import UtilityTool

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ObjHeart {
    public BufferedImage image1, image2;
    public String name;
    public int worldX, worldY;
    UtilityTool uTool;
    GamePanel gp;

    public ObjHeart(GamePanel gp) {
        this.gp = gp;
        name = "Heart";
        uTool = new UtilityTool();

        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/object/heart_full.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/object/heart_blank.png"));
            image1 = uTool.scaleImage(image1, gp.tileSize, gp.tileSize);
            image2 = uTool.scaleImage(image2, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
