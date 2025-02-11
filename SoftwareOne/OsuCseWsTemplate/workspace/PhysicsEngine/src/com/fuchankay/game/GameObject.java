package com.fuchankay.game;

import java.util.LinkedList;

import com.fuchankay.engine.GameContainer;
import com.fuchankay.engine.Renderer;

public abstract class GameObject {
    protected boolean alive = true;
    protected boolean hasGravity = true;
    protected float x, y;
    protected float velX, velY;
    protected float forceX, forceY; 
    protected float hitboxWidth, hitboxHeight;
    protected ObjectId id;
    
    public GameObject(float x, float y, float hitboxWidth, float hitboxHeight, ObjectId id) {
        this.x = x;
        this.y = y;
        this.hitboxWidth = hitboxWidth;
        this.hitboxHeight = hitboxHeight;
        this.id = id;
    }
    
    public abstract void tick(LinkedList<GameObject> object, GameContainer gc);
    
    public abstract void renderObject(Renderer r);
    
    public abstract float getX();
    
    public abstract float getY();
    
    public abstract void setX(float x);
    
    public abstract void setY(float y);
    
    public abstract float getVelX();
    
    public abstract float getVelY();
    
    public abstract void setVelX(float velX);
    
    public abstract void setVelY(float velY);
    
    public abstract float getForceX();
    
    public abstract float getForceY();
    
    public abstract void setForceX(float forceX);
    
    public abstract void setForceY(float forceY);
    
    public abstract ObjectId getId();
    
    public void kill() {
        this.alive = false;
    }
    
    public boolean isHasGravity() {
        return hasGravity;
    }

    public void setHasGravity(boolean hasGravity) {
        this.hasGravity = hasGravity;
    }
}
