package movement;

import geometry.Point;

/**
 * movement.CollisionInfo saves the collisionPoint and the object the ball collides with.
 */
public class CollisionInfo {

    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * constructor.
     *
     * @param collisionPoint  - the collision point.
     * @param collisionObject - the object the ball collides with.
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * returns the collisionPoint.
     *
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * returns the object the ball collide with.
     *
     * @return the collisionObject.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}
