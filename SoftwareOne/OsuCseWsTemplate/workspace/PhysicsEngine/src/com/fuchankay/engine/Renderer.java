package com.fuchankay.engine;

import java.awt.image.DataBufferInt;

import com.fuchankay.engine.gfx.Image;
import com.fuchankay.engine.gfx.ImageTile;

public class Renderer {
    private int pixelWidth, pixelHeight;
    private int[] pixels;
    private int backgroundColor;

    public Renderer(GameContainer gc) {
        this.pixelWidth = gc.getWidth();
        this.pixelHeight = gc.getHeight();
        this.pixels = ((DataBufferInt) gc.getWindow().getImage().getRaster()
                .getDataBuffer()).getData();
        this.backgroundColor = gc.getBackgroundColor();
    }
    
    public void updateRenderer(GameContainer gc) {
        this.pixelWidth = gc.getWidth(); 
        this.pixelHeight = gc.getHeight();
    }

    public void clear() {
        for (int i = 0; i < this.pixels.length; i++) {
            this.pixels[i] = backgroundColor; 
        }
    }

    public void setPixel(int x, int y, int value) {
        //pixels with hexadecimal value ff00ff (hot pink) will not be rendered (transparent)
        if ((x < 0 || x >= this.pixelWidth || y < 0 || y >= this.pixelHeight)
                || value == 0xffff00ff) {
            return;
        }
        this.pixels[x + y * this.pixelWidth] = value;
    }

    public void drawImage(Image image, int offX, int offY) {
        //prevents unnecessary rendering
        if (offX < -image.getWidth()) return;
        if (offY < -image.getHeight()) return;
        if (offX >= pixelWidth) return;
        if (offY >= pixelHeight) return;
        
        int newX = 0;
        int newY = 0;
        int newWidth = image.getWidth();
        int newHeight = image.getHeight();
        

        
        //Clipping code
        if (offX < 0) {newX -= offX;}
        if (offY < 0) {newY -= offY;}
        if (newWidth + offX > pixelWidth) {newWidth -= newWidth + offX - pixelWidth;}
        if (newHeight + offY > pixelHeight) {newHeight -= newHeight + offY - pixelHeight;}
        
        for (int x = newX; x < newWidth; x++) {
            for (int y = newY; y < newHeight; y++) {
                this.setPixel(x + offX, y + offY,
                        image.getPixels()[x + y * image.getWidth()]);
            }
        }
    }

    public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {
        //prevents unnecessary rendering
        if (offX < -image.getTileWidth()) return;
        if (offY < -image.getTileHeight()) return;
        if (offX >= pixelWidth) return;
        if (offY >= pixelHeight) return;
        
        int newX = 0;
        int newY = 0;
        int newWidth = image.getTileWidth();
        int newHeight = image.getTileHeight();
        

        
        //Clipping code
        if (offX < 0) {newX -= offX;}
        if (offY < 0) {newY -= offY;}
        if (newWidth + offX > pixelWidth) {newWidth -= newWidth + offX - pixelWidth;}
        if (newHeight + offY > pixelHeight) {newHeight -= newHeight + offY - pixelHeight;}
        
        for (int x = newX; x < newWidth; x++) {
            for (int y = newY; y < newHeight; y++) {
                this.setPixel(x + offX, y + offY,
                        image.getPixels()[(x + tileX * image.getTileWidth()) + (y + tileY * image.getTileHeight()) * image.getWidth()]);
            }
        }
    }
    
    public void drawRect(int value, int offX, int offY, int width, int height) {
        //prevents unnecessary rendering
        if (offX < -width) return;
        if (offY < -height) return;
        if (offX >= pixelWidth) return;
        if (offY >= pixelHeight) return;
        
        int newX = 0;
        int newY = 0;
        int newWidth = width;
        int newHeight = height;

        //Clipping code
        if (offX < 0) {newX -= offX;}
        if (offY < 0) {newY -= offY;}
        if (newWidth + offX > pixelWidth) {newWidth -= newWidth + offX - pixelWidth;}
        if (newHeight + offY > pixelHeight) {newHeight -= newHeight + offY - pixelHeight;}
       
        for (int x = newX; x < newWidth; x++) {
            for (int y = newY; y < newHeight; y++) {
                this.setPixel(x + offX, y + offY, value);
            }
        }
    }
    
    public int[] getPixels() {
        return pixels;
    }
}
