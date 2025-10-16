package shapes;

import movement.CollisionInfo;
import biuoop.DrawSurface;
import game.Game;
import game.GameEnvironment;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import movement.Collidable;
import movement.Sprite;
import movement.Velocity;

/**
 * the ball class creates the ball for the game.
 *
 * @author Matan Badichi 322692419
 * @author Yakir Sharabi 206534893
 */
public class Ball implements Sprite {
    private Point center;
    private int radius;
    private java.awt.Color ballColor;
    private Velocity velocity;
    private int minHeight;
    private int maxHeight;
    private int minWidth;
    private int maxWidth;
    private GameEnvironment gameEnvironment;

    /**
     * first constructor.
     *
     * @param center - center of the ball
     * @param r      - balls radius
     * @param color  - balls color
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.ballColor = color;
        // default velocity
        this.setVelocity(2, 2);
    }

    /**
     * second constructor.
     *
     * @param x     - location of the center point on x axis
     * @param y     - location of the center point on y axis
     * @param r     - balls radius
     * @param color - balls color
     */
    public Ball(double x, double y, int r, java.awt.Color color) {
        this(new Point(x, y), r, color);
    }

    /**
     * third constructor.
     *
     * @param r         - balls radius
     * @param color     - balls color
     * @param minHeight - minimum height the ball can be in
     * @param maxHeight - maximum height the ball can be in
     * @param minWidth  - minimum width the ball can be in
     * @param maxWidth  - maximum width the ball can be in
     * @param center    - the ball's center point
     */
    public Ball(Point center, int r, java.awt.Color color, int minHeight,
                int maxHeight, int minWidth, int maxWidth) {
        this(center, r, color);
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
    }

    // accessors:

    /**
     * @return the location of the center point on x axis
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * @return the location of the center point on y axis
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * @return the balls radius
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * @return the balls color
     */
    public java.awt.Color getColor() {
        return this.ballColor;
    }

    /**
     * Sets the ball's color.
     *
     * @param color the new color of the ball.
     */
    public void setColor(java.awt.Color color) {
        this.ballColor = color;
    }

    /**
     * sets the game environment for the ball.
     *
     * @param setGameEnvironment - the game environment.
     */
    public void setGameEnvironment(GameEnvironment setGameEnvironment) {
        this.gameEnvironment = setGameEnvironment;
    }

    /**
     * draw the ball on the given DrawSurface.
     *
     * @param surface - current surface to draw on
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.ballColor);
        surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
    }

    /**
     * changes the balls velocity.
     *
     * @param v - velocity
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * sets the balls speed.
     *
     * @param dx - num of steps in x axis
     * @param dy - num of steps in y axis
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * @return balls velocity
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * sets the balls center according to the point the method got.
     *
     * @param newCenter - balls center
     */
    public void setCenter(Point newCenter) {
        this.center = newCenter;
    }

    /**
     * sets the balls radius according to the radius the method got.
     *
     * @param newRadius -balls radius
     */
    public void setRadius(int newRadius) {
        this.radius = newRadius;
    }

    @Override
    /**
     * call moveOneStep method.
     */
    public void timePassed() {
        moveOneStep();
    }

    /**
     * moves the ball according to the velocity and limits.
     */
    public void moveOneStep() {
        //the next current ball trajectory
        Line trajectory = new Line(this.center, this.velocity.applyToPoint(this.center));
        // finds the closest collision to the start line
        CollisionInfo closestCollisionInfo = this.gameEnvironment.getClosestCollision(trajectory);
        // if there is no collision, the ball will continue in the trajectory
        if (closestCollisionInfo == null) {
            this.center = this.getVelocity().applyToPoint(this.center);
            // if there is a collision the ball will stop near the collision point
        } else {
            this.center = new Point(this.center.getX() - 0.5 * this.getVelocity().getDx(),
                    this.center.getY() - 0.5 * this.getVelocity().getDy());
            Point closestCollisionPoint = closestCollisionInfo.collisionPoint();
            Collidable closestCollidable = closestCollisionInfo.collisionObject();
            //changes the velocity accordingly
            this.velocity = closestCollidable.hit(this, closestCollisionPoint, this.velocity);
        }

        // check the paddle wont override the ball
        Rectangle paddleRectangle = gameEnvironment.getPaddle().getCollisionRectangle();
        if (paddleRectangle.getUpperLeft().getX() <= this.center.getX() && paddleRectangle.getUpperRight().getX()
                >= this.center.getX()
                && paddleRectangle.getUpperRight().getY() <= this.center.getY()
                && paddleRectangle.getLowerRight().getY()
                >= this.center.getY()) {
            setVelocity(gameEnvironment.getPaddle().hit(this, this.center, this.velocity));
            this.center = this.getVelocity().applyToPoint(this.center);
        }
    }


    /**
     * adds the ball .
     *
     * @param g - the game.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }

    /**
     * removes the ball to the game.
     *
     * @param g - the game.
     */
    public void removeFromGame(Game g) {
        g.removeSprite(this);
    }
}
