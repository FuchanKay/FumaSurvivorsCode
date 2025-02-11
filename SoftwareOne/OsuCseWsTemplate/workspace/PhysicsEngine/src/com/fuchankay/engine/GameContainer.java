package com.fuchankay.engine;

public class GameContainer implements Runnable {

    private Thread thread;
    private Window window;
    private Renderer renderer;
    private Input input;
    private AbstractGame game; 
    
    private final boolean changeIcon = true;
    private final String iconPath = "/images/fumaIcon.png";
    private final double UPDATE_CAP = 1.0 / 60.0;
    private final double ENINE = Math.pow(10, 9);
    private final float scale = 1f;
    private final String title = "FumaEngine v1.0";
    
    private int backgroundColor = 0xff000000;
    public static int WIDTH = 1600, HEIGHT = 840;
    private int width = WIDTH, height = HEIGHT;
    private boolean running = false;
    private boolean rendererNotified = false;

    public GameContainer(AbstractGame game) {
        this.game = game;
    }

    public void start() {
        window = new Window(this);
        thread = new Thread(this);
        renderer = new Renderer(this);
        input = new Input(this);
        thread.run();
    }

    public void stop() {
        
    }

    @Override
    public void run() {
        this.running = true;

        boolean render = false;
        double firstTime = 0;
        double lastTime = System.nanoTime() / this.ENINE;
        double passedTime = 0;
        double unprocessedTime = 0;

        double frameTime = 0;
        int frames = 0;
        int fps = 0;

        this.game.init(this);
        
        while (running) {
            render = false;
            firstTime = System.nanoTime() / ENINE;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;

            unprocessedTime += passedTime;
            frameTime += passedTime;

            while (unprocessedTime >= UPDATE_CAP) {
                unprocessedTime -= UPDATE_CAP;
                render = true;

                this.game.update(this, (float) this.UPDATE_CAP);

                this.input.update();

                if (frameTime >= 1.0) {
                    if (rendererNotified) {
                        
                    }
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                    System.out.println("FPS: " + fps);
                }
            }

            if (render) {
                renderer.clear();
                game.render(this, renderer);
                window.update();
                frames++;
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        dispose();
    }

    public void dispose() {

    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getScale() {
        return this.scale;
    }

    public String getTitle() {
        return this.title;
    }

    public Window getWindow() {
        return this.window;
    }

    public Input getInput() {
        return this.input;
    }

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public boolean isChangeIcon() {
        return changeIcon;
    }

    public String getIconPath() {
        return iconPath;
    }

}
