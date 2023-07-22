package com.example.actors;

import java.io.File;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Actor {

    // instance variables
    protected double x, y, dx, dy;
    protected final int SPEED;
    protected Image image;

    // canvas and graphics variables
    protected GraphicsContext gc;
    @FXML
    protected Canvas gameCanvas;

    // constructor with image path
    public Actor(GraphicsContext gc, Canvas canvas, int x, int y, int speed, String imgPath) {
        this.gc = gc;
        this.gameCanvas = canvas;
        this.x = x;
        this.y = y;
        this.SPEED = speed;
        image = new Image(new File(imgPath).toURI().toString());
    }

    // overloading constructor without image path
    public Actor(GraphicsContext gc, Canvas canvas, int x, int y, int speed) {
        this.gc = gc;
        this.gameCanvas = canvas;
        this.x = x;
        this.y = y;
        this.SPEED = speed;
    }

    
    /**
     * Draws the image.
     */
    public void draw() {
        this.gc.drawImage(image, x, y);
    }

    /**
     * Method that gets the rectangle boundary.
     * @return Returns Rectangle2D which encompasses the image.
     */
    private Rectangle2D getBoundary() {
        return new Rectangle2D(x, y, image.getWidth(), image.getHeight());
    }

    /**
     * Checks if this Actor collided with another Actor.
     * @param o Actor to check collision with.
     * @return Returns true if collided, false otherwise.
     */
    protected boolean collided(Actor o) {
        return getBoundary().intersects(o.getBoundary());
    }

    /**
     * Method that moves the player based on their dx and dy. Will prevent them from going into illegal areas.
     */
    protected void move() {
        double tempX = x, tempY = y;
        x += dx;
        y += dy; 
        // preventing the player from walking through the block or off of the page
        if (x < 0 || x > gameCanvas.getWidth() - image.getWidth() || x > 312 && x < 561 && y + getHeight() > 337) {
            x = tempX;
            dx = 0;
        }
    }

    /**
     * @return Returns dx.
     */
    public double getDx() {
        return dx;
    }


    /**
     * @return Returns dy.
     */
    public double getDy() { 
        return dy;
    }

    /**
     * Setter for dx.
     * @param dx Value to set.
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * Setter for dy.
     * @param dy Value to set.
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    /**
     * @return Returns x.
     */
    public double getX() {
        return x;
    }


    /**
     * @return Returns y.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets x.
     * @param x Double to set.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets y.
     * @param x Double to set.
     */
    public void setY(double y) {
        this.y = y;
    }


    /**
     * @return the speed.
     */
    public int getSpeed() {
        return SPEED;
    }


    /**
     * @return the height of the image.
     */
    public int getHeight() {
        return (int) this.image.getHeight();
    }


    /**
     * @return the width of the image.
     */
    public int getWidth() {
        return (int) this.image.getWidth();
    }

}