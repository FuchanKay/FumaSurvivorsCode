package com.fuchankay.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Input
        implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private GameContainer gc;

    private final int NUM_KEYS = 256;
    private boolean[] keys = new boolean[this.NUM_KEYS];
    private boolean[] keysLast = new boolean[this.NUM_KEYS];

    private final int NUM_BUTTONS = 5;
    private boolean[] buttons = new boolean[this.NUM_BUTTONS];
    private boolean[] buttonsLast = new boolean[this.NUM_BUTTONS];

    private int mouseX, mouseY;
    private int scroll;

    public Input(GameContainer gc) {
        this.gc = gc;
        this.mouseX = 0;
        this.mouseY = 0;
        this.scroll = 0;

        gc.getWindow().getCanvas().addKeyListener(this);
        gc.getWindow().getCanvas().addMouseMotionListener(this);
        gc.getWindow().getCanvas().addMouseListener(this);
        gc.getWindow().getCanvas().addMouseWheelListener(this);
    }

    public void update() {

        this.scroll = 0;

        for (int i = 0; i < this.NUM_KEYS; i++) {
            this.keysLast[i] = this.keys[i];
        }
        for (int i = 0; i < this.NUM_BUTTONS; i++) {
            this.buttonsLast[i] = this.buttons[i];
        }
    }

    public boolean isKey(int keyCode) {
        return this.keys[keyCode];
    }

    public boolean isKeyUp(int keyCode) {
        return !this.keys[keyCode] && this.keysLast[keyCode];
    }

    public boolean isKeyDown(int keyCode) {
        return this.keys[keyCode] && !this.keysLast[keyCode];
    }

    public boolean isButton(int button) {
        return this.buttons[button];
    }

    public boolean isButtonUp(int button) {
        return !this.buttons[button] && this.buttonsLast[button];
    }

    public boolean isButtonDown(int button) {
        return this.buttons[button] && !this.buttonsLast[button];
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        this.scroll = e.getWheelRotation();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.mouseX = (int) (e.getX() / this.gc.getScale());
        this.mouseY = (int) (e.getY() / this.gc.getScale());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.mouseX = (int) (e.getX() / this.gc.getScale());
        this.mouseY = (int) (e.getY() / this.gc.getScale());
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.buttons[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.buttons[e.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.keys[e.getKeyCode()] = false;
    }

    public int getMouseX() {
        return this.mouseX;
    }

    public int getMouseY() {
        return this.mouseY;
    }

    public int getScroll() {
        return this.scroll;
    }
}
