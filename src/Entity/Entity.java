package Entity;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import main.GamePanel;
import java.awt.Rectangle;
public class Entity {

    public int worldx,worldy;
    GamePanel gp;
    public int speed;
    public double maxLife;
    public double life;
    public double damage;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX = solidArea.x;
    public int solidAreaDefaultY = solidArea.y;
    public ArrayList<BufferedImage> standright = new ArrayList<BufferedImage>();
    public ArrayList<BufferedImage> standleft = new ArrayList<BufferedImage>();
    public ArrayList<BufferedImage> walkRight = new ArrayList<BufferedImage>();
    public ArrayList<BufferedImage> walkLeft = new ArrayList<BufferedImage>();
    public ArrayList<BufferedImage> standBack = new ArrayList<BufferedImage>();
    public ArrayList<BufferedImage> walkBack = new ArrayList<BufferedImage>();
    public ArrayList<BufferedImage> standFront = new ArrayList<BufferedImage>();
    public ArrayList<BufferedImage> walkFront = new ArrayList<BufferedImage>();
    public Projectile projectile;
    public String direction;
    public int spriteCounter =0;
    public int spriteNum = 1;
    public boolean collisionOn = false;
    Entity(GamePanel gp){
        this.gp = gp;
    }
    Entity(){};
}
