package Entity;

import main.GamePanel;
import Entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Slime extends Entity implements Runnable {
    private Random rand = new Random();
    private Thread thread;
    private boolean running = true; // Control thread execution
    private GamePanel gp;
    private Player player; // Reference to the player

    public Slime(GamePanel gp) {
        super(gp);
        this.gp = gp;
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
        startThread();
    }

    private BufferedImage loadSprite(String path) throws IOException {
        return ImageIO.read(getClass().getResourceAsStream("/slime/" + path));
    }

    void getSlimeImage() {
        try {
            for (int i = 0; i <= 9; i++) {
                walkRight.add(loadSprite(String.format("sprite_%02d.png", i)));
                walkLeft.add(loadSprite(String.format("sprite_%02d.png", i)));
                walkBack.add(loadSprite(String.format("sprite_%02d.png", i)));
                walkFront.add(loadSprite(String.format("sprite_%02d.png", i)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(Player p) {
        // This method is modified to take a Player parameter directly
        if (p != null) {
            int playerX = p.worldx;
            int playerY = p.worldy;
            if (worldx < playerX) {
                worldx += speed;
                direction = "right";
            }
            if (worldx > playerX) {
                worldx -= speed;
                direction = "left";
            }
            if (worldy < playerY) {
                worldy += speed;
                direction = "down"; // Corrected direction
            }
            if (worldy > playerY) {
                worldy -= speed;
                direction = "up"; // Corrected direction
            }

            solidArea.setBounds(worldx + 3, worldy + 18, 42, 30);
            interactSlimes(gp.slimes);
        }
    }

    public void Draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
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
        g2.drawImage(image, worldx, worldy, gp.tileSize, gp.tileSize, null);
    }

    public void interactSlimes(ArrayList<Entity> slimes) {
        for (Entity e : slimes) {
            if (e == this) continue;
            Slime slime = (Slime) e;
            if (this.solidArea.intersects(slime.solidArea)) {
                if (this.worldx < slime.worldx) {
                    this.worldx -= 20;
                } else {
                    this.worldx += 20;
                }
                if (this.worldy < slime.worldy) {
                    this.worldy -= 20;
                } else {
                    this.worldy += 20;
                }
            }
        }
    }

    private void startThread() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (running) {
            update(player);
            spriteNum++;
            spriteNum %= walkRight.size();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopThread() {
        running = false;
    }
}
