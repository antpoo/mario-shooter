package com.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.example.actors.Attack;
import com.example.actors.Enemy;
import com.example.actors.Goomba;
import com.example.actors.Koopa;
import com.example.actors.Player;
import com.example.actors.Powerup;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GameScreenController {

    // App fields
    @FXML private Canvas gameCanvas;
    private GraphicsContext gc;
    private Scene gameScene;

    // username field
    private String username;

    // stage
    private Stage stage;

    /**
     * Sets the usernmae
     * @param username username to set
     */
      public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the stage
     * @param stage stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sets the gameScene
     * @param stage stage to get scene from
     */
    public void setScene(Stage stage) {
        gameScene = stage.getScene();
    }

    // nanosecond stuff
    private long firstNanosecond, cooldownBegin;

    // powerup spawning variables
    private boolean mushroomSpawned, fireSpawned;

    // jumping variables
    private final int JUMP_HEIGHT = 100; // integer to store the jumping height
    private boolean firstJump, reachedPeak, falling;
    private int peakHeight, landHeight;

    // shooting
    private boolean shot;

    /**
     * Main game loop
     */
    public void gameLoop() {

        gc = gameCanvas.getGraphicsContext2D();

        // background image
        Image background = new Image(
                new File("src/main/java/com/example/images/gamebackground.png").toURI().toString());

        // Player
        Player player = new Player(gc, gameCanvas, 300, 382, 3,
                "src/main/java/com/example/images/mario_small_right.png");

        // Enemy list
        ArrayList<Enemy> enemies = new ArrayList<>();

        // Powerup list
        ArrayList<Powerup> powerups = new ArrayList<>();

        // bullet list
        ArrayList<Attack> attacks = new ArrayList<>();

        // position variables
        final int BLOCK_LEFT = 312, BLOCK_RIGHT = 561, BLOCK_HEIGHT = 337, GROUND_HEIGHT = 412;

        final int LEFT_SPAWN_X = 10, RIGHT_SPAWN_X = 522;

        // main timer loop
        AnimationTimer at = new AnimationTimer() {

            // variables to control dying part
            boolean first = true, dead = false, firstTempTime = false;
            long tempElapsedTime = 0, lastTime = 0;
            
            @Override
            public void handle(long currentNanoTime) {
                if (first) {
                    firstNanosecond = currentNanoTime;
                    first = false;
                }
                // draw background image
                gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
                gc.drawImage(background, 0, 0);

                // getting elapsed time 
                long elapsedTime = currentNanoTime - firstNanosecond;
                elapsedTime /= (long) 1e9;
                
                // if player has died
                if (dead) {
                    // add the time score to the player's score, 4 points per second alive
                    if (!firstTempTime) {
                        tempElapsedTime = elapsedTime;
                        firstTempTime = true;
                    }
                    if (tempElapsedTime > 0) {
                        tempElapsedTime--;
                        player.addScore(4);
                        lastTime = currentNanoTime;
                    }
                    // pause 4 seconds and then go to end screen 
                    else {
                        if (currentNanoTime - lastTime > (long) 4e9) {
                            try {
                                App.endGame(stage, username, player.getScore());
                                // stops the loop
                                this.stop();
                                return;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } 

                    // display the score and time
                    player.displayScoreTime(tempElapsedTime);

                } 

                // if the player is alive
                else {
                    // checking for key presses
                    gameScene.setOnKeyPressed(e -> {
                        String code = e.getCode().toString();

                        // if player is moving left or right
                        if (code.equals("LEFT"))
                            player.setDx(-player.getSpeed());
                        else if (code.equals("RIGHT"))
                            player.setDx(player.getSpeed());

                        // if player wants to jump
                        else if (code.equals("UP") && player.getJumping() == 0) {
                            player.setJumping(1);
                            reachedPeak = false;
                        }

                        // if fire mario wants to shoot
                        else if (code.equals("SPACE") && player.getFire() == 1 && !shot) {
                            Attack a = null;
                            // if they are facing left
                            if (player.getDirection() == 0)
                                a = new Attack(gc, gameCanvas, (int) player.getX(),
                                        (int) player.getY() + player.getHeight() / 2, -6, 3);
                            // if they are facing right
                            else
                                a = new Attack(gc, gameCanvas, (int) player.getX() + player.getWidth(),
                                        (int) player.getY() + player.getHeight() / 2, 6, 3);
                                        
                            // adding the attack to the player's queue
                            player.addAttack(a);
                            shot = true;
                        }

                    });

                    // checking for key releases
                    gameScene.setOnKeyReleased(e -> {
                        String code = e.getCode().toString();

                        // if player stops moving left or right
                        if (code.equals("LEFT") || code.equals("RIGHT"))
                            player.setDx(0);

                        // allowing player to shoot again once they release space bar
                        if (code.equals("SPACE"))
                            shot = false;
                    });

                    // if they pressed space and they're not falling off
                    if (player.getJumping() == 1 && !falling) {
                        // determine where the peak of their jump is
                        if (!firstJump) {
                            peakHeight = (int) (player.getY() + player.getHeight() - JUMP_HEIGHT);
                            firstJump = true;
                        }
                        // move up until they hit the peak height
                        if (player.getY() + player.getHeight() > peakHeight && !reachedPeak) {
                            player.setDy(-3);
                        } else
                            reachedPeak = true;

                        // figure out where they need to land
                        if (player.getX() > BLOCK_LEFT && player.getX() < BLOCK_RIGHT)
                            landHeight = BLOCK_HEIGHT;
                        else
                            landHeight = GROUND_HEIGHT;

                        // move them down until they hit their landing spot
                        if (reachedPeak && player.getY() + player.getHeight() < landHeight)
                            player.setDy(3);

                        // once they have reached their landing spot
                        else if (reachedPeak && player.getY() + player.getHeight() >= landHeight) {
                            player.setJumping(0);
                            player.setDy(0);
                            firstJump = false;
                        }

                    }
                    // if they are falling off the block
                    else if ((player.getX() <= BLOCK_LEFT || player.getX() >= BLOCK_RIGHT)
                            && player.getY() + player.getHeight() < GROUND_HEIGHT) {
                        player.setDy(3);
                        player.setJumping(1);
                        falling = true;
                    }
                    // once they've reached the ground
                    else {
                        player.setDy(0);
                        player.setJumping(0);
                        falling = false;
                    }

                    // moving and drawing player
                    player.move();
                    player.draw();

                    // enemy code
                    // one in 2 hundred chance for enemy to spawn each tick
                    long chance = (long) (Math.random() * 2e2);
                    if (chance == 0) {

                        // 40 percent chance for enemy to spawn on the right
                        int gx = LEFT_SPAWN_X, gy = GROUND_HEIGHT - 30, kx = LEFT_SPAWN_X, ky = 300;
                        if (Math.random() < 0.4) {
                            gx = kx = RIGHT_SPAWN_X;
                            gy = BLOCK_HEIGHT - 30;
                            ky = 294;
                        }

                        // 50/50 chance for goomba or koopa
                        if (Math.random() < 0.5)
                            enemies.add(new Goomba(gc, gameCanvas, gx, gy, 1));
                        else
                            enemies.add(new Koopa(gc, gameCanvas, kx, ky, 1));
                    }

                    // moving and drawing enemies
                    for (Enemy enemy : enemies) {
                        if (enemy.getX() < player.getX())
                            enemy.setDx(enemy.getSpeed());
                        else
                            enemy.setDx(-enemy.getSpeed());
                        enemy.move();
                        enemy.draw();

                    }

                    // spawn mushroom if mario is small and no mushroom has been spawned, spawns
                    // after roughly 100 frames by chance
                    if (player.getSize() == 0 && !mushroomSpawned && Math.random() < 0.01) {
                        powerups.add(new Powerup(gc, gameCanvas, 360, 307, Powerup.MUSHROOM));
                        mushroomSpawned = true;
                    }
                    // spawn fireflower if mario is big and no fireflower has been spawned, spawns
                    // after roughly 200 frames by chance
                    if (player.getSize() == 1 && !fireSpawned && Math.random() < 0.005) {
                        powerups.add(new Powerup(gc, gameCanvas, 360, 307, Powerup.FIRE_FLOWER));
                        fireSpawned = true;
                    }

                    // drawing powerups and checking if player collides
                    for (int i = powerups.size() - 1; i >= 0; i--) {
                        Powerup powerup = powerups.get(i);
                        powerup.draw();

                        if (player.collided(powerup)) {

                            // making player big if mushroom
                            if (powerup.getType() == Powerup.MUSHROOM) {
                                player.setSize(1);
                                player.setY(BLOCK_HEIGHT - 63);
                                player.setDy(0);
                            }   
                            // making fire mario if fire flower
                            else if (powerup.getType() == Powerup.FIRE_FLOWER) {
                                player.setFire(1);
                                player.setY(BLOCK_HEIGHT - 55);
                                player.setDy(0);
                            }
                            powerups.remove(i);
                        }

                        // remove any previously spawned fireflower if player becomes small
                        if (player.getSize() == 0 && powerup.getType() == Powerup.FIRE_FLOWER) {
                            powerups.remove(i);
                            fireSpawned = false;
                        }
                    }

                    // checking if player has any shots
                    Attack a = player.getNextShot();
                    if (a != null) {
                        attacks.add(a);
                    }

                    for (int i = attacks.size() - 1; i >= 0; i--) {
                        Attack attack = attacks.get(i);

                        // move the attack if it needs to go vertically
                        // figure out where it should land
                        // if it is going to land on the ground
                        if ((attack.getX() <= BLOCK_LEFT || attack.getX() >= BLOCK_RIGHT)
                                && attack.getY() + attack.getHeight() < GROUND_HEIGHT + 3) {
                            attack.setDy(3);
                        }
                        // if it is going to land on the block
                        else if (attack.getX() > BLOCK_LEFT && attack.getX() < BLOCK_RIGHT
                                && attack.getY() + attack.getHeight() < BLOCK_HEIGHT - 3) {
                            attack.setDy(3);
                        } 
                        
                        else {
                            attack.setDy(0);
                        }

                        // check if the attack collides with an enemy
                        for (int j = enemies.size() - 1; j >= 0; j--) {
                            Enemy enemy = enemies.get(j);
                            // remove bullet and enemy and add score
                            if (attack.collided(enemy)) {
                                enemies.remove(j);
                                attacks.remove(i);
                                player.addScore(100);
                            }
                        }

                        // if the bullet hits the side or a wall
                        if (attack.getDx() == 0)
                            attacks.remove(i);


                        // moving and drawing the bullets
                        attack.move();
                        attack.draw();

                    }

                    // displaying the player's score and time in seconds
                    player.displayScoreTime(elapsedTime);

                    // checking if player lands on an enemy or if enemy touches player
                    for (int i = enemies.size() - 1; i >= 0; i--) {
                        Enemy enemy = enemies.get(i);
                        if (player.collided(enemy)) {
                            // if they land on enemy
                            if (player.getSize() == 1 && player.getY() + player.getHeight() <= enemy.getY() + 3
                                    && player.getDy() > 0) {
                                player.addScore(100);
                                enemies.remove(i);
                            }
                            // if they get hit
                            else {
                                // if fire mario, revert to big mario
                                if (player.getFire() == 1) {
                                    player.setFire(0);
                                    // allows fireflowers to spawn again
                                    fireSpawned = false;
                                    cooldownBegin = currentNanoTime; // invincibility frames so they don't instantly die
                                                                     // again
                                    player.setY(player.getY() - 5);
                                }
                                // if big mario, revert to small mario
                                else if (player.getSize() == 1 && currentNanoTime - cooldownBegin > (long) 3e9) {
                                    player.setSize(0);

                                    // if they get hit on the block, place them on the block 
                                    if (player.getX() > BLOCK_LEFT && player.getX() < BLOCK_RIGHT) {
                                        player.setDy(0);
                                        player.move();
                                        player.setY(BLOCK_HEIGHT - player.getHeight());
                                    }
                                    // allows mushrooms to spawn again
                                    mushroomSpawned = false;
                                    cooldownBegin = currentNanoTime;
                                }
                                // if small mario, game over
                                else if (player.getSize() == 0 && currentNanoTime - cooldownBegin > (long) 3e9) {
                                    dead = true;
                                }
                            }
                        }
                    }

                }

            }
        };
        // starting the animation timer (that is the game loop)
        at.start();
    }

}
