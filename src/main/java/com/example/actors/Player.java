package com.example.actors;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class Player extends Actor {

    // queue stores the attacks
    private Queue<Attack> attacks;

    // stores the score
    private int score;

    // integers to store states of the player (to be used for the 4D array bleow)
    private int jumping; // 0 if not jumping, 1 if jumping (being used as integer boolean so it can be used in array)
    private int fire; // 0 if no fire, 1 if has fire (same as above)
    private int size; // 0 if not small, 1 if big
    private int direction; // 0 if left, 1 if right


    // 4D Array to store images to make it easier to reference them 
    // IMAGES[small/big][noFire/fire][ground/jumping][left/right]
    private final int LEFT = 0, RIGHT = 1;
    private final Image[][][][] IMAGES = {
        // small 
        {
            // no fire
            {
                // ground
                {getImage("mario_small_left.png"), getImage("mario_small_right.png")}, // left, right
                // jumping
                {getImage("mario_small_jump_left.png"), getImage("mario_small_jump_right.png")} // left, right
            }, 

        }, 
        // big
        {
            // no fire
            {
                // ground 
                {getImage("mario_big_left.png"), getImage("mario_big_right.png")}, // left, right 
                // jumping
                {getImage("mario_big_jumping_left.png"), getImage("mario_big_jumping_right.png")} // left, right 
            }, 
            // fire 
            {
                // ground
                {getImage("mario_fire_left.png"), getImage("mario_fire_right.png")}, // left, right 
                // jumping
                {getImage("mario_fire_jumping_left.png"), getImage("mario_fire_jumping_right.png")} // left, right
            }
        }
    };


    /**
     * @param path Path of the image 
     * @return the image 
     */
    private Image getImage(String path) {
        return new Image(new File("src/main/java/com/example/images/" + path).toURI().toString());
    }

    // initializing all fields
    public Player(GraphicsContext gc, Canvas canvas, int x, int y, int speed, String imgPath) {
        super(gc, canvas, x, y, speed, imgPath);
        attacks = new LinkedList<>();
    }


    /**
     * Adds attack to the player's attack Queue
     * @param a Attack to be added 
     */
    public void addAttack(Attack a) {
        attacks.add(a);
    }

    // overriding hte move method, sets the image properly
    @Override
    public void move() {
        if (dx > 0) direction = RIGHT;
        else if (dx < 0) direction = LEFT;
        super.move();
        image = IMAGES[size][fire][jumping][direction];
    }

    /**
     * Displays the time and the score
     * @param time time in seconds 
     */
    public void displayScoreTime(long time) {
        String scoreStr = "Score: " + score;
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        gc.setFill(Color.RED);
        gc.fillText(scoreStr, 20, 50);

        String timeStr = "Seconds elapsed: " + time;
        gc.fillText(timeStr, 20, 90);
    }

    /**
     * @return 1 if player jumping, 0 if not
     */
    public int getJumping() {
        return jumping;
    }

    /**
     * Sets player's jumping state
     * @param x 0 or 1
     */
    public void setJumping(int x) {
        if (x == 0 || x == 1) jumping = x;
        image = IMAGES[size][fire][jumping][direction];
    }

    /**
     * @return 1 if player is fire mario, 0 if not 
     */
    public int getFire() {
        return fire;
    }

    /**
     * Sets player's fire state
     * @param x 0 or 1
     */
    public void setFire(int x) {
        if (x == 0 || x == 1) fire = x;
        if (fire == 1) size = 1;
    }

    /**
     * @return 1 if player is big, 0 if small
     */
    public int getSize() {
        return size;
    }

    /**
     * @param x size to set
     */
    public void setSize(int x) {
        if (x == 0 || x == 1) size = x;
    }

    /**
     * @return the player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Adds to player's score
     * @param x score to add
     */
    public void addScore(int x) {
        score += x;
    }

    /**
     * @return 1 if facing right, 0 if facing left
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Removes and returns the next shot from the queue.
     * @return the player's next shot
     */
    public Attack getNextShot() {
        return attacks.poll();
    }

    // overriding the collided method
    @Override
    public boolean collided(Actor o) {
        return super.collided(o);
    }

}