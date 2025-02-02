package com.fuchankay.game;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.fuchankay.engine.AbstractGame;
import com.fuchankay.engine.GameContainer;
import com.fuchankay.engine.Renderer;
import com.fuchankay.engine.gfx.Image;

public class GameManager extends AbstractGame {
    
    Random rand = new Random();
    
    private int pixelHeight;
    private int pixelWidth;
    
    private int centerX; 
    private int centerY;
    
    private final int MAP_WIDTH = 500;
    private final int MAP_HEIGHT = 500;
    
    private Image cursor = new Image("/images/cursor.png");
    
    private MapTile[][] mapTiles = new MapTile[MAP_WIDTH][MAP_HEIGHT];
    private Image grassImg = new Image("/images/grassTile.png/");
    private Image roadImg = new Image("/images/road.png/");
    
    private int mapStartX;
    private int mapStartY; 
    private float time = 0;
    private final float GAME_TIME_CYCLE = 0.2f;
    
    private final float PLAYER_SPEED = 2;
    private final float DIAGONAL_SPEED = (float) (0.707 * PLAYER_SPEED);

    private final int UP = 0;
    private final int UPRIGHT = 1;
    private final int RIGHT = 2;
    private final int DOWNRIGHT = 3;
    private final int DOWN = 4;
    private final int DOWNLEFT = 5;
    private final int LEFT = 6;
    private final int UPLEFT = 7;
    
    Handler handler; 
    Player player;
    
    private void tick() {
        handler.tick();
    }
      
    public GameManager() {
    }
    
    public void init(GameContainer gc) {
        pixelHeight = gc.getHeight();
        pixelWidth = gc.getWidth();
        centerX = pixelWidth / 2;
        centerY = pixelHeight / 2;
        mapStartX = (pixelWidth - grassImg.getWidth() * MAP_WIDTH) / 2;
        mapStartY = (pixelHeight - grassImg.getHeight() * MAP_HEIGHT) / 2;

        GameObject.setCenterX(centerX);
        GameObject.setCenterY(centerY);
        
        handler = new Handler();
        player = new Player(0, 0, centerX, centerY, ObjectId.Player);
        handler.addObject(player);
        editMap(mapTiles);   
    }
    @Override
    public void update(GameContainer gc, float deltaTime) {
        if (player.getHp() <= 0) {
            GameContainer gc2 = new GameContainer(new GameManager());
            gc2.start(); 
            return;
        }
        GameObject.setPlayerX(GameObject.getPlayerX());
        GameObject.setPlayerY(GameObject.getPlayerY());
        Enemy.updatePlayerLocation(GameObject.getPlayerX(), GameObject.getPlayerY());
        time += deltaTime;
        tick();
        if (time > GAME_TIME_CYCLE) {
            time = 0;
            float [] location = Enemy.enemyLocationGen();
            handler.addObject(new Enemy(location[0], location[1], ObjectId.Enemy));
        }
        checkInputs(gc);
    }


    @Override
    public void render(GameContainer gc, Renderer r) {
        for (int i = 0; i < mapTiles.length; i++) {
            for (int j = 0; j < mapTiles[0].length; j++) {
                if (mapTiles[i][j].isGrass()) {
                    r.drawImage(grassImg, grassImg.getWidth() * i + mapStartX - (int)GameObject.getPlayerX(), grassImg.getHeight() * j + mapStartY - (int)GameObject.getPlayerY());
                }
                if (mapTiles[i][j].isRoad()) {
                    r.drawImage(roadImg, roadImg.getWidth() * i + mapStartX - (int)GameObject.getPlayerX(), roadImg.getHeight() * j + mapStartY - (int)GameObject.getPlayerY());
                }
            }
        }
        handler.render(r);
        r.drawImage(cursor, centerX - (int)GameObject.getPlayerX(), centerY - (int)GameObject.getPlayerY());
        r.drawImage(cursor, centerX - (int)GameObject.getPlayerX(), 500 - (int)GameObject.getPlayerY() + centerY);
        r.drawImage(cursor, 500 - (int)GameObject.getPlayerX() + centerX, centerY - (int)GameObject.getPlayerY());
        r.drawImage(cursor, 500 - (int)GameObject.getPlayerX() + centerX, 500 - (int)GameObject.getPlayerY() + centerY);
        r.drawImage(cursor, gc.getInput().getMouseX(), gc.getInput().getMouseY());
    }
    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.start(); 
    }
    private void checkInputs(GameContainer gc) {
        movementInput(gc);
        if (gc.getInput().isButtonUp(MouseEvent.BUTTON1) || gc.getInput().isKeyUp(KeyEvent.VK_SPACE)) {
//            handler.addObject(new Enemy(
//                    (GameObject.getPlayerX() + gc.getInput().getMouseX() - centerX),     
//                    (GameObject.getPlayerY() + gc.getInput().getMouseY() - centerY), ObjectId.Enemy)
//                    );
            handler.addObject(new Projectile(
                    GameObject.getPlayerX(), 
                    GameObject.getPlayerY(), 
                    GameObject.getPlayerX() + gc.getInput().getMouseX() - centerX, 
                    GameObject.getPlayerY() + gc.getInput().getMouseY() - centerY, 
                    ObjectId.Projectile));
            handler.addObject(new Projectile(
                    GameObject.getPlayerX(), 
                    GameObject.getPlayerY(), 
                    GameObject.getPlayerX() + gc.getInput().getMouseX() * 0.9f - centerX, 
                    GameObject.getPlayerY() + gc.getInput().getMouseY() * 0.9f - centerY, 
                    ObjectId.Projectile));
            handler.addObject(new Projectile(
                    GameObject.getPlayerX(), 
                    GameObject.getPlayerY(), 
                    GameObject.getPlayerX() + gc.getInput().getMouseX() * 1.1f - centerX, 
                    GameObject.getPlayerY() + gc.getInput().getMouseY() * 1.1f - centerY, 
                    ObjectId.Projectile));
//            System.out.println("mouseX: " + gc.getInput().getMouseX());
//            System.out.println("mouseY: " + gc.getInput().getMouseY());
//            System.out.println("X: " + GameObject.getPlayerX());
//            System.out.println("Y: " + GameObject.getPlayerY());
        }
        if (gc.getInput().isKeyUp(KeyEvent.VK_SPACE)) {
            System.out.println("X: " + GameObject.getPlayerX());
            System.out.println("Y: " + GameObject.getPlayerY());
        }
    }

    private void movementInput(GameContainer gc) {
        if (upRightInput(gc)) {
            GameObject.setPlayerX(GameObject.getPlayerX() + DIAGONAL_SPEED);
            GameObject.setPlayerY(GameObject.getPlayerY() - DIAGONAL_SPEED);
            Player.setPlayerFace(UPRIGHT);
        }   
        else if (downRightInput(gc)) {
            GameObject.setPlayerX(GameObject.getPlayerX() + DIAGONAL_SPEED);
            GameObject.setPlayerY(GameObject.getPlayerY() + DIAGONAL_SPEED);
            Player.setPlayerFace(DOWNRIGHT);
        }
        else if (downLeftInput(gc)) {
            GameObject.setPlayerX(GameObject.getPlayerX() - DIAGONAL_SPEED);
            GameObject.setPlayerY(GameObject.getPlayerY() + DIAGONAL_SPEED);
            Player.setPlayerFace(DOWNLEFT);
        }
        else if (upLeftInput(gc)) {
            GameObject.setPlayerX(GameObject.getPlayerX() - DIAGONAL_SPEED);
            GameObject.setPlayerY(GameObject.getPlayerY() - DIAGONAL_SPEED);
            Player.setPlayerFace(UPLEFT);
        }
        else if (upInput(gc)) {
            GameObject.setPlayerY(GameObject.getPlayerY() - PLAYER_SPEED);
            Player.setPlayerFace(UP);
        }
        else if (rightInput(gc)) {
            GameObject.setPlayerX(GameObject.getPlayerX() + PLAYER_SPEED);
            Player.setPlayerFace(RIGHT);
        }
        else if (downInput(gc)) {
            GameObject.setPlayerY(GameObject.getPlayerY() + PLAYER_SPEED);
            Player.setPlayerFace(DOWN);
        }
        else if (leftInput(gc)) {
            GameObject.setPlayerX(GameObject.getPlayerX() - PLAYER_SPEED);
            Player.setPlayerFace(LEFT);;
        }
    }

    private boolean leftInput(GameContainer gc) {
        return gc.getInput().isKey(KeyEvent.VK_LEFT) || gc.getInput().isKey(KeyEvent.VK_A);
    }

    private boolean downInput(GameContainer gc) {
        return gc.getInput().isKey(KeyEvent.VK_DOWN) || gc.getInput().isKey(KeyEvent.VK_S);
    }

    private boolean rightInput(GameContainer gc) {
        return gc.getInput().isKey(KeyEvent.VK_RIGHT) || gc.getInput().isKey(KeyEvent.VK_D);
    }

    private boolean upInput(GameContainer gc) {
        return gc.getInput().isKey(KeyEvent.VK_UP) || gc.getInput().isKey(KeyEvent.VK_W);
    }

    private boolean upLeftInput(GameContainer gc) {
        return gc.getInput().isKey(KeyEvent.VK_UP) && gc.getInput().isKey(KeyEvent.VK_LEFT) || gc.getInput().isKey(KeyEvent.VK_W) && gc.getInput().isKey(KeyEvent.VK_A);
    }

    private boolean downLeftInput(GameContainer gc) {
        return gc.getInput().isKey(KeyEvent.VK_DOWN) && gc.getInput().isKey(KeyEvent.VK_LEFT) || gc.getInput().isKey(KeyEvent.VK_S) && gc.getInput().isKey(KeyEvent.VK_A);
    }

    private boolean downRightInput(GameContainer gc) {
        return gc.getInput().isKey(KeyEvent.VK_DOWN) && rightInput(gc) || gc.getInput().isKey(KeyEvent.VK_S) && gc.getInput().isKey(KeyEvent.VK_D);
    }

    private boolean upRightInput(GameContainer gc) {
        return gc.getInput().isKey(KeyEvent.VK_UP) && rightInput(gc) || gc.getInput().isKey(KeyEvent.VK_W) && gc.getInput().isKey(KeyEvent.VK_D);
    }    
    public static void editMap(MapTile[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = new MapTile();
            }
        }
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j].setGrass(true);
            }
        }
        for (int i = 2; i < map.length; i += 30) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j].setRoad(true);
                map[i - 1][j].setRoad(true);
                map[i - 2][j].setRoad(true);
            }
        }
        for (int i = 0; i < map.length; i ++) {
            for (int j = 2; j < map[0].length; j += 30) {
                map[i][j].setRoad(true);
                map[i][j - 1].setRoad(true);
                map[i][j - 2].setRoad(true);
            }
        }
    }
//    private float[] generateRandomLocationX() {
//        float[] coordinates = new float[2];
//        int quadrant = rand.nextInt(4);

//    }
}

