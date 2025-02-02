package com.fuchankay.game;

import java.util.LinkedList;

import com.fuchankay.engine.Renderer;
import com.fuchankay.engine.gfx.Image;

public class Projectile extends GameObject{
    private static float HITBOXWIDTH = 15;
    private static float HITBOXHEIGHT = 15;
    
    private static final Image projectileImg = new Image("/Images/paintbrush.png/");
    
    private static final float BASE_PROJECTILE_SPEED = 2f;
    private static final float BASE_PROJECTILE_DMG = 1f;
    private static float projectileSpeed;
    private static float projectileDamage;
    
    public Projectile(float x, float y, float directionX, float directionY, ObjectId id) {
        super(x, y, Projectile.HITBOXWIDTH, Projectile.HITBOXHEIGHT, id);
        projectileDamage = Projectile.BASE_PROJECTILE_DMG * PlayerStats.getDmgMult();
        projectileSpeed = Projectile.BASE_PROJECTILE_SPEED * PlayerStats.getProjSpeedMult();
        this.hitboxWidth *= PlayerStats.getProjSizeMult();
        this.hitboxHeight *= PlayerStats.getProjSizeMult();
        this.velX = directionX - this.x;
        this.velY = directionY - this.y;
        float magnitude = (float)(Math.pow(Math.pow(this.velX, 2) + Math.pow(this.velY, 2), 0.5));
        if (magnitude == 0) {
            return;
        }
        this.velX *= Projectile.projectileSpeed / magnitude;
        this.velY *= Projectile.projectileSpeed / magnitude;
    }

    @Override
    public void tick(LinkedList<GameObject> object) {
        this.x += this.velX;
        this.y += this.velY;
        if (this.x < playerX - centerX * 2 || this.x > playerX + centerX * 2 || 
                this.y < playerY - centerY * 2 || this.y > playerY + centerY * 2) {
            this.kill();
        }
    }

    @Override
    public void renderObject(Renderer r) {
        r.drawImage(projectileImg, (int)(this.x - playerX + centerX) - projectileImg.getWidth() / 2, (int)(this.y - playerY + centerY) - projectileImg.getHeight() / 2);
    }

    @Override
    public float getX() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getY() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setX(float x) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setY(float y) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public float getVelX() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getVelY() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setVelX(float velX) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setVelY(float velY) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public ObjectId getId() {
        // TODO Auto-generated method stub
        return null;
    }

    public static float getProjectileDamage() {
        return projectileDamage;
    }

    
}
