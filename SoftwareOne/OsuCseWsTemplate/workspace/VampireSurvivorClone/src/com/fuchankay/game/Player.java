package com.fuchankay.game;

import java.util.LinkedList;

import com.fuchankay.engine.Renderer;
import com.fuchankay.engine.gfx.ImageTile;

public class Player extends GameObject{

    private static int playerFace = 0;
    private ImageTile playerImg = new ImageTile("/images/anitest.png", 64, 64);
    private final int centerX;
    private final int centerY;
    
    private float maxHp = 100;
    private float hp = maxHp;

    private final int RED = 0xff0000;
    private final int GREEN = 0x00ff00;
    private final int HP_BAR_WIDTH = 48;
    private final int HP_BAR_HEIGHT = 7;
    private final int HP_BAR_OFFSETX = 12;
    private final int HP_BAR_OFFSETY = 50;
    
    private static final float HITBOXWIDTH = 20;
    private static final float HITBOXHEIGHT = 20;
    
    public Player(float x, float y, int centerX, int centerY, 
            ObjectId id) {
        super(x, y, Player.HITBOXWIDTH, Player.HITBOXHEIGHT, id);
        GameObject.setPlayerX(x);
        GameObject.setPlayerY(y);
        this.centerX = centerX;
        this.centerY = centerY;
    }

    @Override
    public void tick(LinkedList<GameObject> object) {
        this.x = GameObject.playerX;
        this.y = GameObject.playerY;
        GameObject tempObject;
        for (int i = 0; i < object.size(); i++) {
            tempObject = object.get(i);
            if (tempObject.getId() == ObjectId.Enemy && this.isColliding(tempObject)) {
                this.hp -= 0.5;
            }
        }
        if (this.hp <= 0) {
            this.kill();
        }
    }

    @Override
    public void renderObject(Renderer r) {
        r.drawImageTile(playerImg, centerX - playerImg.getTileWidth() / 2, centerY - playerImg.getTileHeight() / 2, playerFace % 4, playerFace / 4);
        r.drawRect(GREEN, 
                (int)(centerX - playerImg.getTileWidth() / 2 + HP_BAR_OFFSETX), 
                (int)(centerY - playerImg.getTileHeight() / 2 + HP_BAR_OFFSETY), 
                HP_BAR_WIDTH - 1, 
                HP_BAR_HEIGHT);
        r.drawRect(RED, 
                (int)(centerX - playerImg.getTileWidth() / 2 + HP_BAR_OFFSETX + HP_BAR_WIDTH * this.hp / this.maxHp), 
                (int)(centerY - playerImg.getTileHeight() / 2 + HP_BAR_OFFSETY), 
                (int)(HP_BAR_WIDTH * (1 - this.hp / this.maxHp)), HP_BAR_HEIGHT);
        
    }

    @Override
    public float getX() {
        // TODO Auto-generated method stub
        return this.x;
    }

    @Override
    public float getY() {
        // TODO Auto-generated method stub
        return this.y;
    }

    @Override
    public void setX(float x) {
        // TODO Auto-generated method stub
        this.x = x;
    }

    @Override
    public void setY(float y) {
        // TODO Auto-generated method stub
        this.y = y;
    }

    @Override
    public float getVelX() {
        // TODO Auto-generated method stub
        return this.velX;
    }

    @Override
    public float getVelY() {
        // TODO Auto-generated method stub
        return this.velY;
    }

    @Override
    public void setVelX(float velX) {
        // TODO Auto-generated method stub
        this.velX = velX;
    }

    @Override
    public void setVelY(float velY) {
        // TODO Auto-generated method stub
        this.velY = velY;
    }

    @Override
    public ObjectId getId() {
        // TODO Auto-generated method stub
        return this.id;
    }

    public static void setPlayerFace(int playerFace) {
        Player.playerFace = playerFace;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }
}
