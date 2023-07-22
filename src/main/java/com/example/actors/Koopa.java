package com.example.actors;

import java.io.File;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Koopa extends Enemy {

    // stores the colour of the koopa troopa 
    private String colour;

    // constructor
    public Koopa(GraphicsContext gc, Canvas canvas, int x, int y, int speed) {
        super(gc, canvas, x, y, speed);
        // 50-50 chance for red or blue 
        if (Math.random() < 0.5) colour = "green";
        else colour = "red";
    }

    // overriding the move method
    @Override
    public void move() {

        // making enemy fall off platform
        if ((x <= 312 || x >= 561) && y < 373) dy = 3;
        else dy = 0;

        // make image face left or right based on direction it is moving 
        if (dx > 0) image = new Image(new File("src/main/java/com/example/images/koopa_" + colour + "_right.png").toURI().toString());
        else image = new Image(new File("src/main/java/com/example/images/koopa_" + colour + "_left.png").toURI().toString());
        super.move();
    }
    
}
