package Entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
public class Slime extends Entity{
    private Random rand = new Random();
    public Slime(GamePanel gp){
        super(gp);
        speed = 2;
        maxLife = 2.0;
        life = maxLife;
        damage = 0.5;
        worldx = rand.nextInt(gp.getScreenWidth() - 42);
        worldy = rand.nextInt(gp.getScreenHeight() - 30);
        solidAreaDefaultX = 3;
        solidArea.x = solidAreaDefaultX;
        solidAreaDefaultY = 18;
        solidArea.y = solidAreaDefaultY;
        solidArea.width = 42;
        solidArea.height = 30;
        getSlimeImage();
    }

    private BufferedImage loadSprite(String path) throws IOException {
        return ImageIO.read(getClass().getResourceAsStream("/slime/" + path));
    }
    void getSlimeImage(){
        try{
            for(int i = 0 ; i <=9 ; i++){
                walkRight.add(loadSprite(String.format("sprite_%02d.png", i)));
                walkLeft.add(loadSprite(String.format("sprite_%02d.png", i)));
                walkBack.add(loadSprite(String.format("sprite_%02d.png", i)));
                walkFront.add(loadSprite(String.format("sprite_%02d.png", i)));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void update(Player p) {
        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum++;
            spriteCounter = 0;
                if (spriteNum >= 9) {
                    spriteNum = 0;
                }
        }
        int playerX = p.worldx;
        int playerY = p.worldy;
        if(worldx < playerX){
            worldx += speed;
            direction = "right";
        }
        if(worldx > playerX){
            worldx -= speed;
            direction = "left";
        }
        if(worldy < playerY){
            worldy += speed;
            direction = "up";
        }
        if(worldy > playerY){
            worldy -= speed;
            direction = "down";
        }
        solidAreaDefaultX = worldx + 3;
        solidAreaDefaultY = worldy + 18;
        solidArea.setBounds(worldx + 3, worldy + 18, 42, 30);
        interactSlimes(gp.slimes);
    }
    public void Draw(Graphics2D g2){
        BufferedImage image = null;
        switch (direction){
            case "right":
                image = walkRight.get(spriteNum % walkRight.size());
                break;
            case "left":
                image = walkLeft.get(spriteNum % walkLeft.size());
                break;
            case "up":
                image = walkBack.get(spriteNum % walkBack.size());
                break;
            case "down":
                image = walkFront.get(spriteNum % walkFront.size());
                break;
        }
        g2.drawImage(image , worldx , worldy , gp.tileSize, gp.tileSize, null);
    }
    public void interactSlimes(ArrayList<Entity> slimes){
        for(Entity e : slimes){
            if(e == this) continue;
            Slime slime = (Slime) e;
            if(this.solidArea.intersects(slime.solidArea)){
                if (this.worldx < slime.worldx){
                    this.worldx -= 20;
                } else {
                    this.worldx += 20;
                }
                if (this.worldy < slime.worldy){
                    this.worldy -= 20;
                } else {
                    this.worldy += 20;
                }
            }
        }
    }
}
