package Entity;

import main.GamePanel;
import main.KeyHandler;
import object.Bullet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;
    public int count = 0;
    private int shootInterval = 60;
    private int shootCounter = 0;

    // Collision cooldown variables
    private int collisionCooldown = 60;
    private int collisionCounter = 0;

    public Player(GamePanel gp , KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        worldx = 250;
        worldy = 250;
        speed = 4;
        maxLife = 3.0;
        life = maxLife;
        direction = "standFront";
        solidArea.x = 10;
        solidArea.y = 10;
        solidArea.width = 50;
        solidArea.height = 40;
        projectile = new Bullet(gp);
        collisionCounter = 0;
    }

    private BufferedImage loadSprite(String path) throws IOException {
        return ImageIO.read(getClass().getResourceAsStream("/player/" + path));
    }

    public void getPlayerImage(){
        try{
            standright.add(loadSprite("sprite_00.png"));
            standright.add(loadSprite("sprite_01.png"));
            standleft.add(loadSprite("sprite_02.png"));
            standleft.add(loadSprite("sprite_03.png"));
            standBack.add(loadSprite("sprite_20.png"));
            standBack.add(loadSprite("sprite_21.png"));
            standFront.add(loadSprite("sprite_28.png"));
            standFront.add(loadSprite("sprite_29.png"));
            for (int i = 4; i <= 11; i++) {
                walkRight.add(loadSprite(String.format("sprite_%02d.png", i)));
            }
            for(int i = 12; i<=19; i++){
                walkLeft.add(loadSprite(String.format("sprite_%02d.png", i)));
            }
            for(int i = 22 ; i<=27 ;i++){
                walkBack.add(loadSprite(String.format("sprite_%02d.png", i)));
            }
            for(int i = 30; i<=33 ;i++){
                walkFront.add(loadSprite(String.format("sprite_%02d.png", i)));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update(){
        boolean isMoving = false;
        if (collisionCounter > 0) {
            collisionCounter--;
        }
        if (keyH.uppress) {
            direction = "up";
            worldy -= speed;
            isMoving = true;
        }
        if (keyH.downpress) {
            direction = "down";
            worldy += speed;
            isMoving = true;
        }
        if (keyH.leftpress) {
            direction = "left";
            worldx -= speed;
            isMoving = true;
        }
        if (keyH.rightpress) {
            direction = "right";
            worldx += speed;
            isMoving = true;
        }

        if (!isMoving) {
            switch (direction) {
                case "up":
                    direction = "standDown";
                    break;
                case "down":
                    direction = "standFront";
                    break;
                case "left":
                    direction = "standLeft";
                    break;
                case "right":
                    direction = "standRight";
                    break;
            }
        }
        solidArea.setBounds(worldx + 10, worldy + 10, 50, 40);
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
            } else if (direction.equals("standDown") || direction.equals("standFront") ||
                    direction.equals("standLeft") || direction.equals("standRight")) {
                if (spriteNum >= 3) {
                    spriteNum = 0;
                }
            }
        }

        shootCounter++;
        if(shootCounter >= shootInterval){
            shoot();
            shootCounter = 0;
        }


        interactSlimes(gp.slimes);
    }

    public void Draw(Graphics2D g2){
        BufferedImage image = null;
        switch (direction) {
            case "up":
                image = walkBack.get(spriteNum % walkBack.size());
                break;
            case "down":
                image = walkFront.get(spriteNum % walkFront.size());
                break;
            case "left":
                image = walkLeft.get(spriteNum % walkLeft.size());
                break;
            case "right":
                image = walkRight.get(spriteNum % walkRight.size());
                break;
            case "standFront":
                image = standFront.get(spriteNum % standFront.size());
                break;
            case "standDown":
                image = standBack.get(spriteNum % standBack.size());
                break;
            case "standLeft":
                image = standleft.get(spriteNum % standleft.size());
                break;
            case "standRight":
                image = standright.get(spriteNum % standright.size());
                break;
        }
        g2.drawImage(image ,worldx ,worldy ,gp.tileSize , gp.tileSize , null);
    }

    public void reset(){
        this.maxLife = 3.0;
        this.life = maxLife;
        this.worldx = 250;
        this.worldy = 250;
        collisionCounter = 0;
        this.count = 0;
        this.shootInterval = 60;
        this.speed = 4;
    }

    public void shoot(){
        Bullet bullet = new Bullet(gp);

        int bulletX = this.worldx + (gp.tileSize / 2) - (gp.tileSize / 4);
        int bulletY = this.worldy + (gp.tileSize / 2) - (gp.tileSize / 4);
        String bulletDirection = "";

        switch(this.direction){
            case "up":
            case "standDown":
                bulletDirection = "up";
                break;
            case "down":
            case "standFront":
                bulletDirection= "down";
                break;
            case "left":
            case "standLeft":
                bulletDirection = "left";
                break;
            case "right":
            case "standRight":
                bulletDirection = "right";
                break;
            default:
                bulletDirection = "down";
                break;
        }
        gp.playSE(1);
        bullet.set(bulletX, bulletY, bulletDirection);
        gp.projectiles.add(bullet);
    }

    public void interactSlimes(ArrayList<Entity> slimes){
        if (collisionCounter > 0) {
            return;
        }
        boolean collided = false;
        for(Entity e : slimes){
            Slime slime = (Slime) e;
            if(this.solidArea.intersects(slime.solidArea)){
                gp.playSE(4);
                collided = true;
                this.life -= slime.damage;
                if (this.worldx < slime.worldx){
                    this.worldx -= 100;
                } else {
                    this.worldx += 100;
                }
                if (this.worldy < slime.worldy){
                    this.worldy -= 100;
                } else {
                    this.worldy += 100;
                }
            }
        }
        if(collided){
            collisionCounter = collisionCooldown;
        }
    }
    public void setshootInterval(){
        shootInterval -= 5;
    }
}
