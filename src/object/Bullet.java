package object;

import Entity.Entity;
import Entity.Projectile;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import Entity.Slime;
public class Bullet extends Projectile {
    GamePanel gp;
    String name;
    public Bullet(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = "Bullet";
        speed = 10;
        maxLife = 80;
        life = maxLife;
        damage = 1.0;
        solidArea = new Rectangle(0, 0, 10, 10);
        getBulletimage();
    }
    private BufferedImage loadSprite(String path) throws IOException {
        return ImageIO.read(getClass().getResourceAsStream("/bullet/" + path));
    }
    public void getBulletimage(){
        try{
            for (int i = 6; i <= 7; i++) {
                walkRight.add(loadSprite(String.format("sprite_%d.png", i)));
            }
            for(int i = 4; i<=5; i++){
                walkLeft.add(loadSprite(String.format("sprite_%d.png", i)));
            }
            for(int i = 0 ; i<=1 ;i++){
                walkBack.add(loadSprite(String.format("sprite_%d.png", i)));
            }
            for(int i = 2; i<=3 ;i++){
                walkFront.add(loadSprite(String.format("sprite_%d.png", i)));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2){
        if(direction.equals("up")){
            g2.drawImage(walkBack.get(spriteNum % walkBack.size()) , worldx ,worldy ,gp.tileSize / 2, gp.tileSize / 2, null);
        }else if(direction.equals("down")){
            g2.drawImage(walkFront.get(spriteNum % walkFront.size())  , worldx ,worldy ,gp.tileSize / 2, gp.tileSize / 2, null);
        }else if(direction.equals("left")){
            g2.drawImage(walkLeft.get(spriteNum % walkLeft.size())  , worldx ,worldy ,gp.tileSize / 2, gp.tileSize / 2, null);
        }else if(direction.equals("right")){
            g2.drawImage(walkRight.get(spriteNum % walkRight.size()), worldx, worldy, gp.tileSize / 2, gp.tileSize / 2, null);
        }
    }
    @Override
    public void update(){
        super.update();
        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum++;
            spriteCounter = 0;

            if (direction.equals("up") || direction.equals("down")) {
                if (spriteNum >= 4) {
                    spriteNum = 0;
                }
            } else if (direction.equals("right") || direction.equals("left")) {
                if (spriteNum >= 9) {
                    spriteNum = 0;
                }
            }else if (direction.equals("standDown") || direction.equals("standFront") || direction.equals("standLeft") || direction.equals("standRight")) {
                if (spriteNum >= 3) {
                    spriteNum = 0;
                }
            }
        }
        interactSlimes(gp.slimes);
        if(shouldRemove()){
            gp.projectilesToRemove.add(this);
        }
    }
    public boolean isOutOfBounds(){
        return worldx < 0 || worldx > gp.getScreenWidth() || worldy < 0 || worldy > gp.getScreenHeight();
    }

    public void interactSlimes(ArrayList<Entity> slimes){
        for(Entity e : slimes){
            Slime slime = (Slime) e;
            if(this.solidArea.intersects(slime.solidArea)){
                gp.playSE(2);
                slime.life -= this.damage;
                if(slime.life <= 0){
                    gp.playSE(3);
                    gp.slimesToRemove.add(slime);
                    gp.player.count += 1;
                }
                this.markForRemoval();
                break;
            }
        }
    }
}
