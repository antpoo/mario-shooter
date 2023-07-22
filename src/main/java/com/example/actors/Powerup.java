package com.example.actors;

import java.io.File;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Powerup extends Actor {

    // public static fields denoting the type of powerup
    public static final int NONE = 0, MUSHROOM = 1, FIRE_FLOWER = 2;

    // power up type 
    private int type;

    // constructor
    public Powerup(GraphicsContext gc, Canvas canvas, int x, int y, int type) {
        super(gc, canvas, x, y, 0, "");
        this.gc = gc;
        this.x = x;
        this.y = y;
        this.type = type;

        // image based on powerup 
        if (type == MUSHROOM) {
            image = new Image(new File("src/main/java/com/example/images/mushroom.png").toURI().toString());
        }
        else if (type == FIRE_FLOWER) {
            image = new Image(new File("src/main/java/com/example/images/fireflower.png").toURI().toString());
        }
    }

    /**
     * Draws the image
     */
    public void draw() {
        this.gc.drawImage(image, x, y);
    }

    /**
     * @return the type of powerup.
     */
    public int getType() {
        return type;
    }
    
    
}
