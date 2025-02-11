package com.fuchankay.game;

import com.fuchankay.engine.GameContainer;

public class Main {
    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.start(); 
    }
}       
