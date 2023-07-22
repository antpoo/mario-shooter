package com.example.actors;

import java.io.File;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Attack extends Actor {

    // constructor
    public Attack(GraphicsContext gc, Canvas canvas, int x, int y, int dx, int speed) {
        super(gc, canvas, x, y, speed);
        this.dx = dx;
        image = new Image(new File("src/main/java/com/example/images/fireball_left.png").toURI().toString());
    }

    // same as Actor's collided method
    @Override
    public boolean collided(Actor o) {
        return super.collided(o);
    }

    // sets the image of the attack, overrides the move method
    @Override
    public void move() {
        super.move();
        // if the fireball is going left
        if (dx < 0) image = new Image(new File("src/main/java/com/example/images/fireball_left.png").toURI().toString());

        // if the fireball is going right 
        else image = new Image(new File("src/main/java/com/example/images/fireball_right.png").toURI().toString());
    }

}