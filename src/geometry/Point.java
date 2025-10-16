package geometry;

import java.util.List;

/**
 * creates point with x and y values.
 *
 * @author Matan Badichi 322692419
 * @author Yakir Sharabi 206534893
 */
public class Point {
    private static final double EPSILON = Math.pow(10, -10);

    private double x;
    private double y;

    /**
     * constructor.
     *
     * @param x - set the location on x axis
     * @param y - sets the location on y axis
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * checks distance between to points.
     *
     * @param other - the other point to check the distance from
     * @return the distance of this point to the other point
     */
    public double distance(Point other) {
        return (Math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y)));
    }

    /**
     * finds the closest point to this point from array of points.
     *
     * @param points - array of points.
     * @return the closest point.
     */
    public int findClosestPointFromArrayIndex(List<Point> points) {
        int closestPointIndex = 0;
        // minimum distance initialized to infinity at first.
        double minimumDistance = Double.MAX_VALUE;
        for (int i = 0; i < points.size(); i++) {
            // checks the point is not null
            if (points.get(i) == null) {
                continue;
            }
            // changes the point and the minimum distance if needed.
            if (distance(points.get(i)) < minimumDistance) {
                minimumDistance = distance(points.get(i));
                closestPointIndex = i;
            }
        }
        return closestPointIndex;
    }

    /**
     * checks if the points are equal.
     *
     * @param other - the second point to compare with
     * @return true is the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        if (other != null) {
            return (EPSILON >= Math.abs(other.x - this.x)) && (EPSILON >= Math.abs(this.y - other.y));
        } else {
            return false;
        }
    }

    /**
     * @return x value of the point
     */
    public double getX() {
        return this.x;
    }

    /**
     * @return y value of the point
     */
    public double getY() {
        return this.y;
    }
}