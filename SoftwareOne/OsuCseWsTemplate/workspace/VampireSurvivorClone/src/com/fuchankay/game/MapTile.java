package com.fuchankay.game;

public class MapTile {
    private boolean isGrass;
    private boolean isRoad;
    
    public MapTile() {
    }
    
    public void setGrass(boolean isGrass) {
        this.isGrass = isGrass;
    }
    
    public void setRoad(boolean isRoad) {
        this.isRoad = isRoad;
    }
    
    public boolean isRoad() {
        return this.isRoad;
    }
    
    public boolean isGrass() {
        return this.isGrass;
    }
    
    
    
}
