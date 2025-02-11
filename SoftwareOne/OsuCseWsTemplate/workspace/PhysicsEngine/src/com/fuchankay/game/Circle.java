package com.fuchankay.game;

import java.awt.event.MouseEvent;
import java.util.LinkedList;

import com.fuchankay.engine.GameContainer;
import com.fuchankay.engine.Renderer;

public class Circle extends GameObject{
    
    private final float radius;
    private final float mass;
    private double color;
    private double colorRate;
    private final float G = 1;
    private final float SPEED_LIMIT = 40;
    
    public Circle(float x, float y, float radius, double color, ObjectId id) {
        super(x, y, 0, 0, id);
        this.radius = radius;
        this.color = color;
        this.colorRate = 255 / (2.0 * radius);
        this.velX = 10;
        this.velY = 10;
        this.forceX = 0;
        this.forceY = 0;
        this.mass = (float)Math.pow(radius, 2);
    }
    @Override
    public void tick(LinkedList<GameObject> object, GameContainer gc) {
        GameObject tempObject;
        for (int i = 0; i < object.size(); i++) {
            tempObject = object.get(i);
            if (tempObject == this) {
                continue;
            }
            if (tempObject.getId().equals(this.id) && this.isColliding((Circle)tempObject)) {
                Circle tempCircle = (Circle)tempObject;
                float angle = (float)(Math.atan2(this.y - tempCircle.getY(), this.x - tempCircle.getX()));
                float v1 = Utilities.magnitude(this.velX, this.velY);
                float v2 = Utilities.magnitude(tempCircle.getVelX(), tempCircle.getVelY());
                float m1 = this.mass;
                float m2 = tempCircle.mass;
//                float v3 = (2 * m1 * v1 - m2 * v2 - m1 * v2) / (m1 + m2);
//                float v4 = (2 * m2 * v2 - m1 * v1 - m2 * v1) / (m1 + m2);
                //m1v1 + m2v2 = m1v3
                //m1v1 + m2v2 = m2v4
                float v3 = (m1 * v1 + m2 * v2) / (m1 + m2);
                float v4 = (m1 * v1 + m2 * v2) / (m1 + m2);
                this.velX = (float)Math.cos(angle) * v3;
                this.velY = (float)Math.sin(angle) * v3;
                tempCircle.setVelX((float)(v4 * Math.cos(angle)));
                tempCircle.setVelY((float)(v4 * Math.sin(angle)));
            }
            if (gc.getInput().isButtonUp(MouseEvent.BUTTON1) && this.isColliding(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
                this.velX += 30;
                this.velY -= 30;
            }
        }
        
        if (this.x + this.radius > GameContainer.WIDTH && this.velX > 0) {
            this.velX *= -1;
        }
        if (this.x - this.radius < 0 && this.velX < 0) {
            this.velX *= -1;
        }
        if (this.y + this.velY + this.radius > GameContainer.HEIGHT && velY > 0) {
            this.velY *= -1;
        }
        if (this.y - this.velY - this.radius < 0 && velY < 0) {
            this.velY *= -1;
        }
        this.velX += this.forceX;
        this.velY += this.forceY + (G / 2);
        if (this.velX > SPEED_LIMIT) {
            this.velX = this.velX / (float)Math.abs(this.velX) * SPEED_LIMIT * (float)Math.sqrt(2) / 2;
        }
        if (this.velY > SPEED_LIMIT) {
            this.velY = this.velY / (float)Math.abs(this.velY) * SPEED_LIMIT * (float)Math.sqrt(2) / 2;
        }
        this.x += this.velX;
        if (this.y + this.velY + this.radius < GameContainer.HEIGHT) {
            this.y += this.velY;            
        }
    }

    @Override
    public void renderObject(Renderer r) {
        double tempColor = this.color;
        for (int i = (int)(this.x - this.radius); i < (int)(this.x + this.radius); i++) {
            for (int j = (int)(this.y - this.radius); j < (int)(this.y + this.radius); j++) {
                float distance = (float)(Math.pow(Math.pow(i - this.x, 2) + Math.pow(j - this.y, 2), 0.5));
                if (distance < this.radius) {
                    r.setPixel(i, j, (int)this.color);
                }
            }
            this.color += colorRate;
        }
        this.color = tempColor;
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
        this.x = x;
        
    }

    @Override
    public void setY(float y) {
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
    
    public boolean isColliding(Circle circ) {
        double distance = Math.sqrt((Math.pow(circ.getX() + circ.getVelX() - this.x - this.velX, 2) + Math.pow(circ.getY() + circ.getVelY() - this.y - this.velY, 2)));
//        System.out.print("Circle: (" + circ.getX()+","+circ.getY()+") ");
//        System.out.print("this: (" + this.x+","+this.y+") ");
        return distance <= this.radius + circ.getRadius();
    }
//    public boolean isColliding(Circle circ) {
//        double distance = Math.sqrt((Math.pow(circ.getX() - this.x, 2) + Math.pow(circ.getY() - this.y, 2)));
////        System.out.print("Circle: (" + circ.getX()+","+circ.getY()+") ");
////        System.out.print("this: (" + this.x+","+this.y+") ");
//        return distance <= this.radius + circ.getRadius();
//    }
    public boolean isColliding(int x, int y) {
        double distance = Utilities.magnitude(this.x - x, this.y - y);
        return distance < this.radius;
    }
    
    public float getRadius() {
        return radius;
    }

    @Override
    public ObjectId getId() {
        return ObjectId.Circle;
    }

    public float getMass() {
        return mass;
    }

    @Override
    public float getForceX() {
        // TODO Auto-generated method stub
        return this.forceX;
    }

    @Override
    public float getForceY() {
        // TODO Auto-generated method stub
        return this.forceY;
    }

    @Override
    public void setForceX(float forceX) {
        // TODO Auto-generated method stub
        this.forceX = forceX;
    }

    @Override
    public void setForceY(float forceY) {
        // TODO Auto-generated method stub
        this.forceY = forceY;
    }
    
    
}
