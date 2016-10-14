package com.jamescho.game.model;

import com.jamescho.framework.util.RandomNumberGenerator;
import com.jamescho.game.main.Game;
import com.jamescho.game.main.GameMain;
import com.jamescho.game.main.Resources;

import java.awt.*;

/**
 * Created by jjcoy on 10/3/16.
 */
public class Ball {
    int x;
    int y;
    int height;
    int width;
    int velX;
    int velY;
    private Rectangle rect;
    private static final int INITIAL_VEL = 5;      // pixels per second

    // constructor
    public Ball(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        velX = INITIAL_VEL;
        rect = new Rectangle(x, y, width, height);
        velY = RandomNumberGenerator.getRandIntBetween((-INITIAL_VEL+1), INITIAL_VEL);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void update() {
        // update all logic for the Ball here
        x = x + velX;
        y = y + velY;

        // check for collisions with top/bottom of screen
        correctYCollisions();

        correctXCollisons();
        // update bounding box
        updateRect();
    }

    public void correctYCollisions() {
        // check for out of range
        if (y < 0) { // off top
            y = 0;
        } else if (y > (GameMain.GAME_HEIGHT - height)) { // off bottom
            y = GameMain.GAME_HEIGHT - height; // height of paddle
        } else { // in middle of screen
            return; // skip remaining code
        }

        // reverse direction
        velY = -velY;

        // play sound
        Resources.bounce.play();
    }

    public void correctXCollisons() {
        // check for out of range
        if (x < 0) { //off left
            x = 0;
        } else if (x > (GameMain.GAME_WIDTH - width)) {
            x = GameMain.GAME_WIDTH -width;
            return;
        }
        velX = -velX;
    }

    public boolean isDead() {
        // we go off the screen in either x direction, ball is dead
        if ((x<0)||(x>(GameMain.GAME_WIDTH - width))) {
            return true;    // off the edge!
        } else {
            return false;
        }
    }

    public void reset() {
        // reuse this same object
        x = 300;
        y = 200;
        velX = INITIAL_VEL;
        velY = RandomNumberGenerator.getRandIntBetween((-INITIAL_VEL+1), INITIAL_VEL);
    }

    private void updateRect() {
        rect.setBounds(x, y, width, height);
    }

    public Rectangle getRect() {
        return rect;
    }

    public void onCollidesWith(Paddle p) {
        // if we are on the left half of the screen
        if (x < GameMain.GAME_WIDTH/2) {
            // back the ball up so it is not inside the paddle
            x = p.getX() + p.getWidth();
        } else { // we are on the right half of the screen
            x = p.getX() - width;
        }

        // reverse direction in x
        velX = -velX;
        // randomize y direction speed
        velY += RandomNumberGenerator.getRandIntBetween(-2, 3);
    }
}
