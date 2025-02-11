package com.fuchankay.game;

import com.fuchankay.engine.AbstractGame;
import com.fuchankay.engine.GameContainer;
import com.fuchankay.engine.Renderer;
import com.fuchankay.engine.gfx.Image;

public class GameManager extends AbstractGame {
    
    private Circle circle;
    private Handler handler;
    private final int RED = 0xff0000;
    private final Image cursor = new Image("/images/cursor.png");
    public GameManager() {
        handler = new Handler();
        circle = new Circle(4000, 500, 50, RED, ObjectId.Circle);
        handler.addObject(circle);
        handler.addObject(new Circle(100, 500, 20, RED, ObjectId.Circle));
        handler.addObject(new Circle(300, 500, 40, RED, ObjectId.Circle));
        handler.addObject(new Circle(200, 500, 10, RED, ObjectId.Circle));
        handler.addObject(new Circle(800, 500, 100, RED, ObjectId.Circle));


        
        //        handler.addObject(new Circle(200, 200, 20, RED, ObjectId.Circle));

//        handler.addObject(new Circle(800, 500, 10, RED, ObjectId.Circle));
//        handler.addObject(new Circle(800, 350, 10, RED, ObjectId.Circle));
//        handler.addObject(new Circle(800, 550, 10, RED, ObjectId.Circle));
//        handler.addObject(new Circle(800, 370, 10, RED, ObjectId.Circle));
//        handler.addObject(new Circle(800, 570, 10, RED, ObjectId.Circle));

    }

    public void init(GameContainer gc) {
        
    }
    
    @Override
    public void update(GameContainer gc, float deltaTime) {
        handler.tick(gc);
    }    
    
    @Override
    public void render(GameContainer gc, Renderer r) {
        handler.render(r);
        r.drawImage(cursor, gc.getInput().getMouseX(), gc.getInput().getMouseY());
    }
}
