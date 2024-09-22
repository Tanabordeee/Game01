package main;

import object.ObjHeart;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    public Font arial_40;
    BufferedImage heart_full , heart_blank;
    UI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial" , Font.PLAIN , 30);

        //Create HUB OBJECT
        ObjHeart heart = new ObjHeart(gp);
        heart_full = heart.image1;
        heart_blank = heart.image2;
        speedButton = new Rectangle(100, 100, 400, 250);
        damageButton = new Rectangle(560, 100, 400, 250);
        maxLifeButton = new Rectangle(100, 400, 400, 250);
        speedBulletButton = new Rectangle(560, 400, 400, 250);
    }
    public void draw(Graphics2D g2){
        if(gp.gameState == gp.titleState){
            drawTitleScreen(g2);
        }else if(gp.gameState == gp.gameoverState){
            drawGameOver(g2);
        }else if(gp.gameState == gp.pauseState){
            drawUpgrade(g2);
        }
        else if(gp.gameState == gp.playState){
            drawPlayerLife(g2);
            drawCount(g2);
        }
    }
    public void drawCount(Graphics2D g2){
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        g2.drawString("COUNT "+ gp.player.count , 870 , 40 );
    }
    public void drawPlayerLife(Graphics2D g2){
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;
        // วาด เลือดเต็ม
        while(i < gp.player.maxLife){
            g2.drawImage(heart_blank , x ,y , null);
            i++;
            x += gp.tileSize;
        }
        // reset
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;
        // วาดเลือดที่มีอยู่
        while (i < gp.player.life){
            g2.drawImage(heart_full , x  ,y , null);
            i++;
            x+= gp.tileSize;
        }

    }
    public void drawTitleScreen(Graphics2D g2){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD , 96F));
        g2.setColor(Color.WHITE);
        g2.drawString("Hunting Slime", 180, 100);
        g2.drawImage(gp.player.standright.get(0) , 450 ,200 , gp.tileSize*2 ,gp.tileSize*2 , null);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD , 50F));
        g2.drawString("> New game" , 360 , 500);
    }
    public void drawGameOver(Graphics2D g2){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD , 96F));
        g2.setColor(Color.WHITE);
        g2.drawString("GAME OVER", 230, 100);
        g2.drawImage(gp.player.standright.get(0) , 450 ,200 , gp.tileSize*2 ,gp.tileSize*2 , null);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD , 50F));
        g2.drawString("> RETRY" , 400 , 500);
    }
    private Rectangle speedButton;
    private Rectangle damageButton;
    private Rectangle maxLifeButton;
    private Rectangle speedBulletButton;
    public Rectangle getSpeedButton() {
        return speedButton;
    }

    public Rectangle getDamageButton() {
        return damageButton;
    }

    public Rectangle getMaxLifeButton() {
        return maxLifeButton;
    }

    public Rectangle getSpeedBulletButton() {
        return speedBulletButton;
    }

    public void drawUpgrade(Graphics2D g2){
        // วาดปุ่ม Speed
        g2.setColor(new Color(255, 179, 186));
        g2.fillRect(speedButton.x, speedButton.y, speedButton.width, speedButton.height);
        g2.setColor(Color.black);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD , 50F));
        g2.drawString("Speed", speedButton.x + 110, speedButton.y + 130 );

        // วาดปุ่ม Damage
        g2.setColor(new Color(186, 255, 179));
        g2.fillRect(damageButton.x, damageButton.y, damageButton.width, damageButton.height);
        g2.setColor(Color.black);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD , 50F));
        g2.drawString("Damage", damageButton.x + 100, damageButton.y + 130);

        // วาดปุ่ม Max Life
        g2.setColor(new Color(179, 186, 255));
        g2.fillRect(maxLifeButton.x, maxLifeButton.y, maxLifeButton.width, maxLifeButton.height);
        g2.setColor(Color.black);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD , 50F));
        g2.drawString("Max Life", maxLifeButton.x + 80, maxLifeButton.y + 150);

        // วาดปุ่ม Speed Bullet
        g2.setColor(new Color(255, 255, 179));
        g2.fillRect(speedBulletButton.x, speedBulletButton.y, speedBulletButton.width, speedBulletButton.height);
        g2.setColor(Color.black);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD , 50F));
        g2.drawString("Bullet Speed", speedBulletButton.x + 60, speedBulletButton.y + 150);
    }
}
