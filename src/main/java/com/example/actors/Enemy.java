package com.example.actors;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class Enemy extends Actor {

    // constructor with image path
    public Enemy(GraphicsContext gc, Canvas canvas, int x, int y, int speed, String imgPath) {
        super(gc, canvas, x, y, speed, imgPath);
    }

    // constructor without image path
    public Enemy(GraphicsContext gc, Canvas canvas, int x, int y, int speed) {
        super(gc, canvas, x, y, speed);
    }

    // calls the super move class 
    @Override
    public void move() {
        super.move();
    }

}