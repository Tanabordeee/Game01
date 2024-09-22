package Entity;

import main.GamePanel;

import java.awt.*;

public class Projectile extends Entity{
    private boolean shouldRemove = false;
    public Projectile(GamePanel gp){
        super(gp);
        solidArea = new Rectangle(0, 0, 10, 10);
    }
    public void set(int worldx , int worldy , String direction){
        this.worldx = worldx;
        this.worldy = worldy;
        this.direction = direction;
        this.life = this.maxLife;
    }
    public void update(){
        solidArea.setBounds(worldx, worldy, solidArea.width, solidArea.height);
        move();
        discrementlife();
        if(isOutOfBounds()){
            shouldRemove = true;
        }

    }
    public void move(){
        if (direction != null && direction.equals("up")) {
            worldy -= speed;
        } else if (direction != null&& direction.equals("down")) {
            worldy += speed;
        } else if (direction != null&& direction.equals("left")) {
            worldx -= speed;
        } else if (direction != null&& direction.equals("right")) {
            worldx += speed;
        }
    }
    public void discrementlife(){
        life--;
        if(life <= 0){
            shouldRemove = true;
        }
    }
    public boolean isOutOfBounds() {
        return worldx < 0 || worldx > gp.getScreenWidth() || worldy < 0 || worldy > gp.getScreenHeight();
    }
    public void markForRemoval() {
        this.shouldRemove = true;
    }
    public boolean shouldRemove(){
        return  shouldRemove;
    }
}
