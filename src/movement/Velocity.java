package movement;

import geometry.Point;

/**
 * movement.Velocity specifies the change in position on the `x` and the `y` axes.
 *
 * @author Matan Badichi 322692419
 * @author Yakir Sharabi 206534893
 */
public class Velocity {

    private double dx;
    private double dy;

    /**
     * constructors.
     *
     * @param dx - steps on x axis
     * @param dy - steps on y axis
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }


    /**
     * @return dx
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * @return dy
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * gives the option to set the velocity according to angle and speed.
     *
     * @param angle - angle of movement
     * @param speed - the speed of the ball
     * @return the velocity according to the parameters
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = speed * Math.sin(Math.toRadians(180 - angle));
        double dy = speed * Math.cos(Math.toRadians(180 - angle));
        return new Velocity(dx, dy);
    }

    /**
     * gets the speed from the velocity dx and dy.
     *
     * @return - the speed.
     */
    public double getSpeed() {
        return Math.sqrt(Math.pow(this.dx, 2) + Math.pow(this.dy, 2));
    }


    /**
     * Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
     *
     * @param p - previous point
     * @return the new point
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + dx, p.getY() + dy);
    }
}