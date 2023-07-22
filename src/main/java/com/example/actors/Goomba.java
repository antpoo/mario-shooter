package com.example.actors;

import java.io.File;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Goomba extends Enemy {

    // constructor
    public Goomba(GraphicsContext gc, Canvas canvas, int x, int y, int speed) {
        super(gc, canvas, x, y, speed);
        image = new Image(new File("src/main/java/com/example/images/goomba.png").toURI().toString());
    }
    
    // overriding the super move method
    @Override
    public void move() {
        // making enemy fall off platform
        if ((x <= 312 || x >= 561) && y < 382) dy = 3;
        else dy = 0;
        super.move();
    }

}
