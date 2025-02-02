package com.fuchankay.game;

import java.util.LinkedList;
import java.util.Random;

import com.fuchankay.engine.Renderer;
import com.fuchankay.engine.gfx.Image;

public class Enemy extends GameObject{
    private static Random random = new Random();
    
    private static float enemySpeed = 2f; 
    private static final float COLLISION_CONSTANT = 1f;
    private boolean stopMoving;
    private static Image enemyImg = new Image("/images/enemySprite.png");
       
    private static final float HITBOXWIDTH = 25;
    private static final float HITBOXHEIGHT = 25;
    private float hp = 2;
    
    private static float hpMult = 1;

    private Random rand = new Random();
       
    public static void updatePlayerLocation(float playerX, float playerY) {
    }
    
    @Override
    public void renderObject(Renderer r) {
        r.drawImage(enemyImg, (int)(this.x - playerX + centerX - enemyImg.getWidth() / 2), (int)(this.y - playerY + centerY - enemyImg.getHeight() / 2));
    }
    
    public void updateVelocity(LinkedList<GameObject> object) {
        this.velX = playerX - this.x;
        this.velY = playerY - this.y;
        float magnitude = (float)(Math.pow(Math.pow(this.velX, 2) + Math.pow(this.velY, 2), 0.5));
        if (magnitude == 0) {
            return;
        }
        this.velX *= Enemy.enemySpeed / magnitude;
        this.velY *= Enemy.enemySpeed / magnitude;
    }
    
    public void moveTowardsPlayer(LinkedList<GameObject> object) {
        if (stopMoving) {
            stopMoving = false;
            return;
        }
        for (int i = 0; i < object.size(); i++){
            GameObject tempObject = object.get(i);
            if (tempObject == this) {
                continue;
            }
            if (this.isColliding(tempObject) && tempObject.id.equals(ObjectId.Projectile)) {
                this.hp -= Projectile.getProjectileDamage();
                tempObject.kill();
            }
            if (this.hp <= 0) {
                this.kill();
            }
            if (this.isColliding(tempObject, this.velX, this.velY) && tempObject.id.equals(ObjectId.Enemy)) {
                float magnitude = (float)(Math.pow(Math.pow(this.velX, 2) + Math.pow(this.velY, 2), 0.5));
                if (magnitude == 0) {
                    return;
                }
                this.velX *= COLLISION_CONSTANT * Enemy.enemySpeed / magnitude;
                this.velY *= COLLISION_CONSTANT * Enemy.enemySpeed / magnitude;
                this.x -= this.velX;
                this.y -= this.velY;
                tempObject.x += this.velX;
                tempObject.y += this.velY;
                return;
            }
            if (this.x < playerX - centerX * 3 || this.x > playerX + centerX * 3 || this.y < playerY - centerY * 3 || this.y > playerY + centerY * 3) {
                this.kill();
            }
        }
        this.x += this.velX;
        this.y += this.velY;
    }
    public Enemy(float x, float y, ObjectId id) {
        super(x, y, Enemy.HITBOXWIDTH, Enemy.HITBOXHEIGHT, id);
        this.hp *= Enemy.hpMult;
    }
    @Override
    public void tick(LinkedList<GameObject> object) {
        updateVelocity(object);
        moveTowardsPlayer(object);
    }
    public static float[] enemyLocationGen() {
        float[] arr = new float[2];
        int quadrant = random.nextInt(4);
        float randX = centerX * 2 - random.nextFloat(centerX * 2);
        float randY = centerY * 2 - random.nextFloat(centerY * 2);
        if (quadrant == 0) {
            arr[0] = GameObject.getPlayerX() + centerX;
            arr[1] = GameObject.getPlayerY() + randY;
        } else if (quadrant == 1) {
            arr[0] = GameObject.getPlayerX() - centerX;
            arr[1] = GameObject.getPlayerY() + randY;
        } else if (quadrant == 2) {
            arr[0] = GameObject.getPlayerX() + randX;
            arr[1] = GameObject.getPlayerY() - centerY;
        } else {
            arr[0] = GameObject.getPlayerX() + randX;
            arr[1] = GameObject.getPlayerY() + centerY;
        }
        return arr;
    }
    @Override
    public float getX() {
        return this.x;
    }
    @Override
    public float getY() {
        return this.y;
    }
    @Override
    public void setX(float x) {
        this.x = x;
        
    }
    @Override
    public void setY(float y) {
        this.y = y;
        
    }
    @Override
    public float getVelX() {
        return 0;
    }
    @Override
    public float getVelY() {
        return 0;
    }
    @Override
    public void setVelX(float velX) {
        
    }
    @Override
    public void setVelY(float velY) {
        
    }
    
    public float randomDirection() {
        return 2 * (float) (rand.nextDouble() - 0.5);
    }
    
    public static void setCenterX(int centerX) {
        Enemy.centerX = centerX;
    }
    public static void setCenterY(int centerY) {
        Enemy.centerY = centerY;
    }
    
    public static float getHpMult() {
        return Enemy.hpMult;
    }
    
    public static void setHpMult(float hpMult) {
        Enemy.hpMult = hpMult;
    }
    
    @Override
    public ObjectId getId() {
        return this.id;
    }
}

