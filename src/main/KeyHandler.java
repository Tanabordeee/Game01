package main;

import object.Bullet;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;

public class KeyHandler implements KeyListener {
    Rectangle speedButton;
    Rectangle damageButton;
    Rectangle maxLifeButton;
    Rectangle speedBulletButton;
    GamePanel gp;
    KeyHandler(GamePanel gp){
        this.gp = gp;
    }
    KeyHandler(GamePanel gp, Rectangle speedButton, Rectangle damageButton, Rectangle maxLifeButton, Rectangle speedBulletButton){
        this.gp = gp;
        this.speedButton = speedButton;
        this.damageButton = damageButton;
        this.maxLifeButton = maxLifeButton;
        this.speedBulletButton = speedBulletButton;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    public boolean uppress , downpress , leftpress ,rightpress;
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // เก็บ code ของการ input key ใน keyboard
        if(gp.gameState == gp.titleState){
            if(code == KeyEvent.VK_ENTER){
                gp.gameState = gp.playState;
            }
        }
        if(gp.gameState == gp.gameoverState){
            if(code == KeyEvent.VK_ENTER){
                gp.gameState = gp.playState;
                gp.player.reset();
                gp.slimes.clear();
                gp.totalSlimesToSpawn = 2;
                gp.countUpgrade = 0;
                gp.playMusic(0);
            }
        }
        if(code == KeyEvent.VK_W) //เช็คว่า user กดปุ่ม w ไหมถ้ากดจะทำไร
        {
            uppress = true;
        }
        if(code == KeyEvent.VK_A){
            leftpress = true;
        }
        if(code == KeyEvent.VK_S){
            downpress = true;
        }
        if(code == KeyEvent.VK_D){
            rightpress = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode(); // เก็บ code ของการ input key ใน keyboard
        if(code == KeyEvent.VK_W) //เช็คว่า user ปล่อยปุ่ม w
        {
            uppress = false;
        }
        if(code == KeyEvent.VK_A){
            leftpress = false;
        }
        if(code == KeyEvent.VK_S){
            downpress = false;
        }
        if(code == KeyEvent.VK_D){
            rightpress = false;
        }
    }

    public void checkUpgradeClick(int mouseX, int mouseY) {
        if (speedButton.contains(mouseX, mouseY)) {
            gp.playSE(8);
            gp.player.speed+=1;
            gp.gameState = gp.playState;
            gp.checkPause = false;

            System.out.println("Speed upgraded!");
        } else if (damageButton.contains(mouseX, mouseY)) {
            gp.playSE(6);
            for(Bullet bullet : gp.projectiles){
                bullet.damage+=0.5;
            }
            gp.gameState = gp.playState;
            gp.checkPause = false;
            System.out.println("Damage upgraded!");
        } else if (maxLifeButton.contains(mouseX, mouseY)) {
            gp.player.maxLife += 1;
            gp.playSE(7);
            gp.player.life = gp.player.maxLife;
            gp.gameState = gp.playState;
            gp.checkPause = false;
            System.out.println("Max Life upgraded!");
        } else if (speedBulletButton.contains(mouseX, mouseY)) {
            gp.playSE(6);
            gp.player.setshootInterval();
            gp.gameState = gp.playState;
            gp.checkPause = false;
            System.out.println("Bullet Speed upgraded!");
        }
        gp.playMusic(0);
    }
}
