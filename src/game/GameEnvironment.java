package game;

import movement.Collidable;
import movement.CollisionInfo;
import shapes.Paddle;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * game.Game Environment class.
 *
 * @author Matan Badichi 322692419
 * @author Yakir Sharabi 206534893
 */
public class GameEnvironment {

    private List<Collidable> collidableArray = new ArrayList<Collidable>();
    private Paddle paddle;

    /**
     * constructor- sets the array of the collidables.
     */
    public GameEnvironment() {
        this.collidableArray = new ArrayList<Collidable>();
    }

    /**
     * sets the paddle for the enviroment.
     *
     * @param setPaddle - the paddle for the game.
     */
    public void setPaddle(Paddle setPaddle) {
        this.paddle = setPaddle;
    }

    /**
     * get the paddle.
     *
     * @return the paddle
     */
    public Paddle getPaddle() {
        return this.paddle;
    }

    /**
     * get the array of the collidables.
     *
     * @return collidableArray
     */
    public List getCollidableArray() {
        return this.collidableArray;
    }

    /**
     * adds the given collidable to the environment (collidableArray).
     *
     * @param c - the collidable.
     */
    public void addCollidable(Collidable c) {
        collidableArray.add(c);
    }

    /**
     * checks if the ball will hit any collidable in the next move.
     *
     * @param trajectory - the balls trajectory
     * @return the collision info (the collidable and the intersection point) or null if there is no intersection.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        boolean checkIfThereIsIntersection = false;
        //the list will contain all closest intersection points with the instances from collidable Array.
        List<Point> intersectionPointsList = new ArrayList<Point>();
        for (int i = 0; i < this.collidableArray.size(); i++) {
            //checks if the rectangle in index i has intersection with the line
            Collidable checkCollidable = collidableArray.get(i);
            if (checkCollidable.getCollisionRectangle().intersectionPoints(trajectory).size() > 0) {
                //finds the closest intersection of the rectangle and the line start point
                Rectangle checkRectangle = checkCollidable.getCollisionRectangle();
                Point closestIntersection = trajectory.closestIntersectionToStartOfLine(checkRectangle);
                intersectionPointsList.add(closestIntersection);
                checkIfThereIsIntersection = true;
            } else {
                intersectionPointsList.add(null);
            }
        }
        if (checkIfThereIsIntersection) {
            int closestIntersection = trajectory.start().findClosestPointFromArrayIndex(intersectionPointsList);
            return (new CollisionInfo((Point) intersectionPointsList.get(closestIntersection),
                    (Collidable) collidableArray.get(closestIntersection)));
        } else {
            return null;
        }
    }

    /**
     * removes collidables from collidable Array.
     *
     * @param c - the collidable to remove
     */
    public void removeCollidable(Collidable c) {
        collidableArray.remove(c);
    }

}