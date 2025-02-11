package com.fuchankay.engine;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Window {

    private JFrame frame;
    private BufferedImage image;
    private Canvas canvas;
    private BufferStrategy bs;
    private Graphics g;
    
    private int BUFFER_NUM = 2;

    public Window(GameContainer gc) {

        byte[]imageByte=new byte[0];
        Cursor myCursor;
        Point myPoint=new Point(0,0);
        Image cursorImage=Toolkit.getDefaultToolkit().createImage(imageByte);
        myCursor=Toolkit.getDefaultToolkit().createCustomCursor(cursorImage,myPoint,"cursor");
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.image = new BufferedImage(gc.getWidth(), gc.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        this.canvas = new Canvas();
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension screenSize = new Dimension((int) (gc.getWidth() * gc.getScale()),
                (int) (gc.getHeight() * gc.getScale()));
        this.canvas.setPreferredSize(screenSize);
        this.canvas.setMaximumSize(screenSize);
        this.canvas.setMinimumSize(screenSize);

        this.frame = new JFrame(gc.getTitle());    
        if (gc.isChangeIcon()) {
            this.frame.setIconImage(tk.getImage(getClass().getResource(gc.getIconPath())));
        }
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());
        this.frame.add(this.canvas, BorderLayout.CENTER);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setVisible(true);
        frame.setCursor(myCursor);

        this.canvas.createBufferStrategy(BUFFER_NUM);
        this.bs = this.canvas.getBufferStrategy();
        this.g = this.bs.getDrawGraphics();
    }

    public void update() {
        this.g.drawImage(this.image, 0, 0, this.canvas.getWidth(),
                this.canvas.getHeight(), null);
        this.bs.show();
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    public JFrame getFrame() {
        return this.frame;
    }
}
