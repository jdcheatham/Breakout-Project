package com.jamescho.game.model;

import com.jamescho.game.main.GameMain;

import java.awt.*;

/**
 * Created by jjcoy on 9/30/16.
 */
public class Paddle {
    int x;
    int y;
    int height;
    int width;
    int velY;
    int velX;  //added for X plane motion
    private Rectangle rect;
    private static final int MOVE_SPEED_Y = 4;      // pixels per second
    private static final int MOVE_SPEED_X = 4;  //added to give X plane movement

    // constructor
    public Paddle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        rect = new Rectangle(x, y, width, height);
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

    public void accelUp() {
        velY = -MOVE_SPEED_Y; // negative is up
    }

    public void accelLeft () {velX = - MOVE_SPEED_X;}  // negative is left

    public void accelDown() {
        velY = MOVE_SPEED_Y; // positive is down
    }

    public void accelRight() {velX = MOVE_SPEED_X;}  //positive is right

    public void stop() {
        velY = 0;
        velX =0;   // Stop X movement
    }

    public void update() {
        // update all logic for the Paddle here
        y = y + velY;
        x = x + velX;  //logic for X movement

        // check for out of range
        if (y < 0) { // off top
            y = 0;
        } else if (y > (GameMain.GAME_HEIGHT - height)){ // off bottom
            y = GameMain.GAME_HEIGHT - height; // height of paddle
        }
        //check for out of range on X plane
        if (x <0 ){//off left
            x =0;
        } else if (x > (GameMain.GAME_WIDTH-width)) {  //off right
            x = GameMain.GAME_WIDTH - width;
        }

        updateRect();
    }

    private void updateRect() {
        rect.setBounds(x, y, width, height);
    }

    public Rectangle getRect() {
        return rect;
    }
}
