package shapes;

import movement.Collidable;
import movement.Sprite;
import movement.Velocity;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.Game;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;

/**
 * shapes.Paddle class creates the paddle for the game.
 *
 * @author Matan Badichi 322692419
 * @author Yakir Sharabi 206534893
 */
public class Paddle implements Sprite, Collidable {
    private static final int PADDLES_SPEED = 5;
    private static final int RIGHT_BORDER = 780;
    private static final int LEFT_BORDER = 20;
    private static final double EPSILON = Math.pow(10, -10);

    private biuoop.KeyboardSensor keyboard;
    private GUI gui;
    private Rectangle paddle;
    private Color paddleColor;

    /**
     * constructor.
     *
     * @param gui       - the gui the paddle should be shown in.
     * @param rectangle - the shape pf the paddle.
     * @param color     - the paddle's color.
     */
    public Paddle(GUI gui, Rectangle rectangle, Color color) {
        this.gui = gui;
        this.paddle = rectangle;
        this.paddleColor = color;
    }

    /**
     * move left moves the paddle to the left side.
     */
    public void moveLeft() {
        // checks if there is more space in the left side
        if (this.paddle.getUpperLeft().getX() > LEFT_BORDER) {
            this.paddle = new Rectangle(new Point(this.paddle.getUpperLeft().getX() - PADDLES_SPEED,
                    this.paddle.getUpperLeft().getY()), this.paddle.getWidth(), this.paddle.getHeight());
        } else {
            this.paddle = new Rectangle(new Point(RIGHT_BORDER - this.paddle.getWidth(),
                    this.paddle.getUpperLeft().getY()), this.paddle.getWidth(), this.paddle.getHeight());
        }
    }

    /**
     * move right moves the paddle to the right side.
     */
    public void moveRight() {
        // checks if there is more space in the right side
        if (this.paddle.getUpperRight().getX() < RIGHT_BORDER) {
            this.paddle = new Rectangle(new Point(this.paddle.getUpperLeft().getX() + PADDLES_SPEED,
                    this.paddle.getUpperLeft().getY()), this.paddle.getWidth(), this.paddle.getHeight());
        } else {
            this.paddle = new Rectangle(new Point(LEFT_BORDER,
                    this.paddle.getUpperLeft().getY()), this.paddle.getWidth(), this.paddle.getHeight());
        }
    }

    /**
     * check if the left key or right key is pressed.
     */
    public void timePassed() {
        biuoop.KeyboardSensor clickedKeyboard = gui.getKeyboardSensor();
        if (clickedKeyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (clickedKeyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    /**
     * draws the paddle on the surface.
     *
     * @param d - the surface.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.paddleColor);
        d.fillRectangle((int) this.paddle.getUpperLeft().getX(), (int) this.paddle.getUpperLeft().getY(),
                (int) this.paddle.getWidth(), (int) this.paddle.getHeight());
    }

    // implementation of the movement.Collidable interface:

    /**
     * returns the paddle.
     *
     * @return the paddle.
     */
    public Rectangle getCollisionRectangle() {
        return this.paddle;
    }

    /**
     * Handles the hit event and calculates the new velocity of the ball after the collision.
     * This method determines the new velocity of the ball when it collides with the paddle.
     * If the collision point is on the left or right edge of the paddle, the horizontal
     * velocity is reversed. If the collision point is on the top of the paddle, the vertical
     * velocity is reversed and the angle is adjusted based on which part of the paddle is hit.
     *
     * @param hitter the ball that hits the paddle
     * @param collisionPoint the point at which the collision occurs
     * @param currentVelocity the current velocity of the ball
     * @return the new velocity of the ball after the collision
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // if the collision point is with the left or right borders the velocity need to change horizontally.
        if (Math.abs(this.paddle.getUpperLeft().getX() - collisionPoint.getX()) <= EPSILON
                || Math.abs(this.paddle.getUpperRight().getX() - collisionPoint.getX()) <= EPSILON) {
            return new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
            // if the collision point is not on the left or right lines of the rectangle it must collide
            // with the top or bottom lines and the velocity needs to change vertically.
        } else {
            // checks the part the ball hit.
            int checkRegion = (int) ((collisionPoint.getX() - paddle.getUpperLeft().getX())
                    / (this.paddle.getWidth() / 5));
            // changes the vertical velocity.
            Velocity newVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
            // changes the angel according to the part the ball hit.
            switch (checkRegion) {
                case 0:
                    return Velocity.fromAngleAndSpeed(300, newVelocity.getSpeed());
                case 1:
                    return Velocity.fromAngleAndSpeed(330, newVelocity.getSpeed());
                case 2:
                    return newVelocity;
                case 3:
                    return Velocity.fromAngleAndSpeed(30, newVelocity.getSpeed());
                default:
                    return Velocity.fromAngleAndSpeed(60, newVelocity.getSpeed());
            }
        }
    }

    /**
     * Add this paddle to the game.
     *
     * @param g - the game.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}