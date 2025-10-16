package shapes;

import biuoop.DrawSurface;
import game.Game;
import geometry.Point;
import geometry.Rectangle;
import movement.Collidable;
import movement.HitListener;
import movement.HitNotifier;
import movement.Sprite;
import movement.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * shapes.Block class - creates the blocks.
 *
 * @author Matan Badichi 322692419
 * @author Yakir Sharabi 206534893
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private static final double EPSILON = Math.pow(10, -10);

    private Rectangle block;
    private Color color;
    private List<HitListener> hitListeners;


    /**
     * first constructor.
     *
     * @param block - creates the block by rectangle.
     */
    public Block(Rectangle block) {
        this.block = block;
        this.hitListeners = new ArrayList<>();
    }

    /**
     * second constructor.
     *
     * @param block - creates the block by rectangle.
     * @param color - the block color.
     */
    public Block(Rectangle block, Color color) {
        this.block = block;
        this.color = color;
        this.hitListeners = new ArrayList<>();
    }

    /**
     * gets the block.
     *
     * @return the block.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.block;
    }

    /**
     * return the velocity the ball should have after hitting collidable.
     *
     * @param collisionPoint  - the closest collision point to the ball.
     * @param currentVelocity - the current velocity of the ball.
     * @return the velocity it should have after the hit.
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        // if the collision point is with the left or right borders the velocity need to change horizontally.
        if ((Math.abs(this.block.getUpperLeft().getX() - collisionPoint.getX()) <= EPSILON
                && currentVelocity.getDx() > 0)
                || (Math.abs(this.block.getUpperRight().getX() - collisionPoint.getX()) <= EPSILON
                && currentVelocity.getDx() < 0)) {
            dx = -dx;
        }
        // if the collision point is with the top or bottom borders the velocity need to change vertically.
        if ((Math.abs(this.block.getUpperLeft().getY() - collisionPoint.getY()) <= EPSILON)
                || (Math.abs(this.block.getLowerRight().getY() - collisionPoint.getY()) <= EPSILON)) {
            dy = -dy;
        }
        if (!ballColorMatch(hitter)) {
            this.notifyHit(hitter);
        }
        return new Velocity(dx, dy);
    }

    /**
     * gets the current block.
     *
     * @return the block.
     */
    public Rectangle getBlock() {
        return this.block;
    }

    /**
     * draws the block on the surface.
     *
     * @param surface - the surface to draw on.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillRectangle((int) this.block.getUpperLeft().getX(), (int) this.block.getUpperLeft().getY(),
                (int) this.block.getWidth(), (int) this.block.getHeight());
        surface.setColor(Color.BLACK);
        surface.drawRectangle((int) this.block.getUpperLeft().getX(), (int) this.block.getUpperLeft().getY(),
                (int) this.block.getWidth(), (int) this.block.getHeight());
    }

    /**
     * time passed.
     */
    @Override
    public void timePassed() {

    }

    /**
     * adds thr block to the game.
     *
     * @param g - the game.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Checks if the color of the given ball matches the color of this object.
     *
     * @param ball the ball to check the color of
     * @return true if the ball's color matches this object's color, false otherwise
     */
    public Boolean ballColorMatch(Ball ball) {
        if (ball.getColor().equals(this.color)) {
            return true;
        }
        return false;
    }

    /**
     * removes the block from the game.
     *
     * @param game - the game to remove from
     */
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * Adds a HitListener to this block.
     *
     * @param hl the HitListener to add
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Removes a HitListener from this block.
     *
     * @param hl the HitListener to remove
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }


    /**
     * Notifies all registered HitListeners about a hit event.
     * This method creates a copy of the current hitListeners list to avoid
     * concurrent modification issues and then iterates over the copy, notifying
     * each listener of the hit event. It also sets the color of the hitting ball
     * to this block's color.
     *
     * @param hitter the ball that hit the block
     */
    private void notifyHit(Ball hitter) {
// Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
// Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hitter.setColor(this.color);
            hl.hitEvent(this, hitter);
        }
    }
}
