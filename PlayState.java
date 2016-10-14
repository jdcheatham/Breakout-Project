package com.jamescho.game.state;

import com.jamescho.game.main.GameMain;
import com.jamescho.game.main.Resources;
import com.jamescho.game.model.Ball;
import com.jamescho.game.model.Paddle;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Created by jjcoy on 9/29/16.
 */
public class PlayState extends State {

    // objects in this screen
    private Paddle paddleLeft, paddleRight;
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 15;
    private Ball ball;
    private static final int BALL_DIAMETER = 20;

    // score variables
    private int playerScore = 0;
    private Font scoreFont;  // used to display score

    @Override
    public void init() {
        // setup objects in this screen
        paddleLeft = new Paddle(0, 195, PADDLE_WIDTH, PADDLE_HEIGHT);
        paddleRight = new Paddle(GameMain.GAME_WIDTH-PADDLE_WIDTH, 195, PADDLE_WIDTH, PADDLE_HEIGHT);
        ball = new Ball(300, 200, BALL_DIAMETER, BALL_DIAMETER);

        // initialize score and font
        playerScore = 0;
        scoreFont = new Font("SansSerif", Font.BOLD, 25);
    }

    @Override
    public void update() {
        // update all objects on this screen
        paddleLeft.update();
        paddleRight.update();
        ball.update();

        // check for collision
        if (ballCollides(paddleLeft)) {
            // check for left paddle collision
            playerScore++;
            ball.onCollidesWith(paddleLeft); // have ball react to collision
            Resources.hit.play();
        } else if (ballCollides(paddleRight)) {
            // check for right paddle collision
            playerScore++;
            ball.onCollidesWith(paddleRight); // have ball react to collision
            Resources.hit.play();
        } else if (ball.isDead()) {
            // oops off the screen
            playerScore -= 3;
            ball.reset();
        }
    }

    @Override
    public void render(Graphics g) {
        // draw from back to front

        // draw background:  blue all over, red on the right side
        g.setColor(Resources.darkBlue);
        g.fillRect(0,0, GameMain.GAME_WIDTH, GameMain.GAME_HEIGHT);
        g.setColor(Resources.darkRed);
        g.fillRect(GameMain.GAME_WIDTH/2, 0, GameMain.GAME_WIDTH/2, GameMain.GAME_HEIGHT);

        // draw the separator
        g.drawImage(Resources.line, ((GameMain.GAME_WIDTH/2)-2), 0, null);

        // draw the paddles
        g.setColor(Color.WHITE);
        g.fillRect(paddleLeft.getX(), paddleLeft.getY(), paddleLeft.getWidth(), paddleLeft.getHeight());
        g.fillRect(paddleRight.getX(), paddleRight.getY(), paddleRight.getWidth(), paddleRight.getHeight());

        // draw ball
        g.drawRect(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());

        // draw score
        g.setFont(scoreFont);
        g.drawString(Integer.toString(playerScore), 350, 40);
    }

    @Override
    public void onClick(MouseEvent e) {

    }

    @Override
    public void onKeyPress(KeyEvent e) {
        // if they press up, move up
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            paddleLeft.accelUp();
            paddleRight.accelDown();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) { // else if they press down move down
            paddleLeft.accelDown();
            paddleRight.accelUp();
        }
        // if they press left move left, press right move right
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            paddleLeft.accelRight();
            paddleRight.accelLeft();
        } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            paddleLeft.accelLeft();
            paddleRight.accelRight();
        }

    }

    @Override
    public void onKeyRelease(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_UP) || (e.getKeyCode() == KeyEvent.VK_DOWN) || (e.getKeyCode() == KeyEvent.VK_RIGHT) || (e.getKeyCode() == KeyEvent.VK_LEFT) ) { // else if they press down move down
            paddleLeft.stop();
            paddleRight.stop();
        }

    }

    private boolean ballCollides(Paddle p) {
        return ball.getRect().intersects(p.getRect());
    }
}
