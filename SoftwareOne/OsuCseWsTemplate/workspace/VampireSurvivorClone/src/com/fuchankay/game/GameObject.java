package com.fuchankay.game;

import java.util.LinkedList;

import com.fuchankay.engine.Renderer;

public abstract class GameObject {
    protected boolean alive = true;
    protected static float playerX, playerY;
    protected static int centerX, centerY;
    protected float x, y;
    protected float velX, velY;
    protected float hitboxWidth, hitboxHeight;
    protected ObjectId id;
    
    public GameObject(float x, float y, float hitboxWidth, float hitboxHeight, ObjectId id) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.hitboxWidth = hitboxWidth;
        this.hitboxHeight = hitboxHeight;
    }
    
    public abstract void tick(LinkedList<GameObject> object);
    
    public abstract void renderObject(Renderer r);
    
    public abstract float getX();
    
    public abstract float getY();
    
    public abstract void setX(float x);
    
    public abstract void setY(float y);
    
    public abstract float getVelX();
    
    public abstract float getVelY();
    
    public abstract void setVelX(float velX);
    
    public abstract void setVelY(float velY);
    
    public abstract ObjectId getId();
   
    public static void setPlayerX(float playerX) {
        GameObject.playerX = playerX;
    }
    public static void setPlayerY(float playerY) {
        GameObject.playerY = playerY;
    }
    public static void setCenterX(int centerX) {
        GameObject.centerX = centerX;   
    }
    public static void setCenterY(int centerY) {
        GameObject.centerY = centerY;
    }
    
    public void kill() {
        this.alive = false;
    }
    
    public boolean isColliding(GameObject object) {
        boolean isCollidedX = this.x <= object.x + object.hitboxWidth && object.x <= this.x + object.hitboxWidth;
        boolean isCollidedY = this.y <= object.y + object.hitboxHeight && object.y <= this.y + object.hitboxHeight;
        return isCollidedX && isCollidedY;
    }
    public boolean isColliding(GameObject object, float offX, float offY) {
        boolean isCollidedX = this.x + offX <= object.x + object.hitboxWidth && object.x <= this.x + object.hitboxWidth + offX;
        boolean isCollidedY = this.y + offY <= object.y + object.hitboxHeight && object.y <= this.y + object.hitboxHeight + offY;
        return isCollidedX && isCollidedY;
    }
    public boolean isColliding(GameObject object, float offX1, float offY1, float offX2, float offY2) {
        boolean isCollidedX = this.x + offX1 <= object.x + object.hitboxWidth + offX2 && object.x + offY2 <= this.x + object.hitboxWidth + offX1;
        boolean isCollidedY = this.y + offY1 <= object.y + object.hitboxHeight + offY2 && object.y + offY2 <= this.y + object.hitboxHeight + offY1;
        return isCollidedX && isCollidedY;
    }

    public static float getPlayerX() {
        return playerX;
    }

    public static float getPlayerY() {
        return playerY;
    }
}
