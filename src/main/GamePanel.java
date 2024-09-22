package main;

import Entity.Entity;
import Entity.Player;
import Entity.Slime;
import object.Bullet;
import object.ObjHeart;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class GamePanel extends JPanel implements Runnable{
    // SCREEN SETTING
    //public int count = 0;
    final int originalTileSize = 32;
    final int scale = 2;
    public final int tileSize = originalTileSize * scale; // 64*64
    final int maxScreenCol = 16;
    final int MaxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * MaxScreenRow;
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 3;
    public final int gameoverState = 2;
    public final int titleState = 0;
    public int getScreenWidth(){
        return screenWidth;
    }
    public int getScreenHeight(){
        return screenHeight;
    }

    //SOUND
    private Sound music = new Sound();
    private Sound se = new Sound();
    //FPS
    int FPS = 60;
    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;//ทำให้เกม run ตลอดเวลา
    public Player player = new Player(this , keyH);
    ObjHeart heart = new ObjHeart(this);

    //array bullet
    public ArrayList<Bullet> projectiles = new ArrayList<>();
    public ArrayList<Entity> slimes = new ArrayList<>();
    public ArrayList<Bullet> projectilesToRemove = new ArrayList<Bullet>();
    public ArrayList<Entity> slimesToRemove = new ArrayList<>();
    public UI ui = new UI(this);
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth , screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        keyH = new KeyHandler(this ,ui.getSpeedButton() , ui.getDamageButton() , ui.getMaxLifeButton() , ui.getSpeedBulletButton());
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                keyH.checkUpgradeClick(mouseX, mouseY);
            }
        });
        slimes.add(new Slime(this));
        slimes.add(new Slime(this));
        gameState = titleState;
    }

    public  void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        playMusic(0);
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while(gameThread != null){
            update();
            repaint(); //call paintComponent
            try{
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000.0;
                if(remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    private boolean hasSpawnedSlimes = false;
    public int totalSlimesToSpawn = 2;
    public int countUpgrade = 0;
    public boolean checkPause = false;
    public void update(){
        if(gameState == titleState || gameState == gameoverState || checkPause){
            return;
        }
        if(player.count % 2 == 0 && slimes.isEmpty() && !hasSpawnedSlimes){
            for(int i = 0 ; i < totalSlimesToSpawn ; i++){
                slimes.add(new Slime(this));
            }
            if(player.count % 20 == 0){
                for(Entity newslime : slimes){
                    if(newslime instanceof Slime){
                        Slime newslimes = (Slime) newslime;
                        newslimes.maxLife+=0.5;
                        newslimes.life = newslimes.maxLife;
                    }
                }
            }
            hasSpawnedSlimes = true;
            totalSlimesToSpawn+=2;
            countUpgrade++;
            if(countUpgrade % 2 == 0 && countUpgrade > 0 ){
                stopMusic();
                playSE(5);
                gameState = pauseState;
                checkPause = true;
            }
        }
        player.update();
        Iterator<Bullet> bulletIterator = projectiles.iterator();
        while(bulletIterator.hasNext()){
            Bullet bullet = bulletIterator.next();
            bullet.update();
            if(bullet.life <= 0 || bullet.isOutOfBounds()){
                bulletIterator.remove();
            }
        }
        for (int i = 0; i < slimes.size(); i++) {
            Slime slime = (Slime) slimes.get(i);
            slime.update(player);
        }
        removeProjectiles();
        removeSlimes();
        if(slimes.isEmpty()){
            hasSpawnedSlimes = false;
        }
        player.worldx = Math.max(0, Math.min(player.worldx, screenWidth - tileSize));
        player.worldy = Math.max(0, Math.min(player.worldy, screenHeight - tileSize));
        if(player.life <= 0){
            gameState = gameoverState;
        }
    }
    @Override
    // ทำการ override Method PaintComponent
    public void paintComponent(Graphics g){
        super.paintComponent(g); // เรียก PaintComponent ของ class แม่โดยส่ง Object Graphics ไป
        Graphics2D g2 = (Graphics2D)g;
        if(gameState == titleState){
            ui.draw(g2);
        }else if(gameState == gameoverState){
            stopMusic();
            ui.draw(g2);
        }else if(gameState == pauseState){
            ui.draw(g2);
        }else if(gameState == playState){
            player.Draw(g2);
            for (Entity entity : slimes) {
                if (entity instanceof Slime) {
                    Slime slime = (Slime) entity;
                    slime.Draw(g2);
                }
            }
            for (Bullet bullet : projectiles) {
                bullet.draw(g2); // ต้องสร้างฟังก์ชัน Draw ใน Bullet เพื่อวาดกระสุน
            }
            ui.draw(g2);
        }
        g2.dispose();
    }
    private void removeProjectiles(){
        if(!projectilesToRemove.isEmpty()){
            projectiles.removeAll(projectilesToRemove);
            projectilesToRemove.clear();
        }
    }

    private void removeSlimes(){
        if(!slimesToRemove.isEmpty()){
            slimes.removeAll(slimesToRemove);
            slimesToRemove.clear();
        }
    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE(int i ){
        se.setFile(i);
        se.play();
    }
}
