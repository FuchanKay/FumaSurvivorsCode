package com.fuchankay.game;

import java.util.LinkedList;

import com.fuchankay.engine.Renderer;

public class Handler {
    public LinkedList<GameObject> object = new LinkedList<GameObject>();
    private int ENTITY_CAP = 700;
    
    public Handler() {
    }
    
    public GameObject tempObject;
    
    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            tempObject = object.get(i);
            if (!tempObject.alive) {
                object.remove(tempObject);
                continue;
            }                   
            tempObject.tick(object);
        }
    }
    
    public void render(Renderer r) {
        for (int i = 0; i < object.size(); i++) {
            tempObject = object.get(i);
            tempObject.renderObject(r);
        }
    }
    
    public void addObject(GameObject object) {
        if (this.object.size() > ENTITY_CAP) {
            return;
        }
        this.object.add(object);
    }
    
    public void removeObject(GameObject object) {
        this.object.remove(object);    
    }
    
    public int getSize() {
        return this.object.size();
    }
    
    
}
