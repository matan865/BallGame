package geometry;

import java.util.List;

/**
 * creates line by start and end point.
 */
public class Line {
    private static final double EPSILON = Math.pow(10, -10);
    private Point startPoint;
    private Point endPoint;

    /**
     * first constructor.
     *
     * @param start - the point where the line starts.
     * @param end   - the point where the line ends.
     */
    public Line(Point start, Point end) {
        this.startPoint = start;
        this.endPoint = end;
    }

    /**
     * second constructor.
     *
     * @param x1 - x value of start point
     * @param y1 - y value of start point
     * @param x2 - x value of end point
     * @param y2 - y value of end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.startPoint = new Point(x1, y1);
        this.endPoint = new Point(x2, y2);
    }


    /**
     * @return the length of the line
     */
    public double length() {
        double xDistance = this.endPoint.getX() - this.startPoint.getX();
        double yDistance = this.endPoint.getY() - this.startPoint.getY();
        return Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
    }


    /**
     * @return the middle point of the line
     */
    public Point middle() {
        double divide = 2;
        double middleOfX = (this.startPoint.getX() + this.endPoint.getX()) / divide;
        double middleOfY = (this.startPoint.getY() + this.endPoint.getY()) / divide;
        return (new Point(middleOfX, middleOfY));
    }

    /**
     * @return the start point of the line
     */
    public Point start() {
        return this.startPoint;
    }

    /**
     * @return the end point of the line
     */
    public Point end() {
        return this.endPoint;
    }

    /**
     * @param other - the line to check intersection with
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        return intersectionWith(other) != null;
    }

    /**
     * find the y value in the intersection with y axis.
     *
     * @param checkLine - the line to check
     * @param slope     - the line's slope
     * @return y value in the intersection with y axis
     */
    public double intersectionWithYaxis(Line checkLine, double slope) {
        return (checkLine.start().getY() - (slope * checkLine.start().getX()));
    }

    /**
     * finds line's slope.
     *
     * @param checkLine - the line to check
     * @return the slope
     */
    public double slope(Line checkLine) {
        return (checkLine.end().getY() - checkLine.start().getY())
                / (checkLine.end().getX() - checkLine.start().getX());
    }

    /**
     * checks the intersection point, each case calls the right function to check it.
     *
     * @param other - the line to check intersection with
     * @return he intersection point if the lines intersect and null otherwise
     */
    public Point intersectionWith(Line other) {
        // if this and other is the same line
        if ((this.startPoint.equals(other.startPoint) && this.endPoint.equals(other.endPoint))
                || (this.startPoint.equals(other.endPoint) && this.endPoint.equals(other.startPoint))) {
            return null;
            // if one of the lines is just point(start and end points are the same)
        } else if (this.startPoint.equals(endPoint)) {
            return checkIntersectionWithPoint(other, this.startPoint.getX(), this.startPoint.getY());
        } else if (other.start().equals(other.end())) {
            return checkIntersectionWithPoint(this, other.start().getX(), other.start().getY());
            //if both lines parallel to Y axis
        } else if (other.start().getX() == other.end().getX()
                && this.startPoint.getX() == this.endPoint.getX()) {
            if (other.start().getX() == this.startPoint.getX()) {
                return checkIntersectionForLinesWithSameSlope(other);
            } else {
                return null;
            }
            // if one line parallel to Y axis
        } else if (other.start().getX() == other.end().getX()) {
            return (intersectionWithParallelLine(other, this));
        } else if (this.startPoint.getX() == this.endPoint.getX()) {
            return (intersectionWithParallelLine(this, other));
            // if both lines has the same slope
        } else if (slope(this) == slope(other)) {
            return checkIntersectionForLinesWithSameSlope(other);
        } else {
            return checkIntersectionPoint(other);
        }
    }

    /**
     * checks intersection for point and line.
     *
     * @param checkLine - line to check
     * @param xValue    - x value of the point
     * @param yValue    - y value of the point
     * @return intersection point or null if there is no intersection
     */
    public Point checkIntersectionWithPoint(Line checkLine, double xValue, double yValue) {
        double slope = slope(checkLine);
        double intersectionWithYaxis = intersectionWithYaxis(checkLine, slope);
        if (yValue == slope * xValue + intersectionWithYaxis) {
            return (new Point(xValue, yValue));
        } else {
            return null;
        }
    }

    /**
     * checks intersection for lines with same slope.
     *
     * @param other - the line to check intersection with
     * @return intersection point or null if there is no intersection
     */
    public Point checkIntersectionForLinesWithSameSlope(Line other) {
        // count counts the number of points that are on the second line
        int count = 0;
        if (other.checkIfPointOnLine(this.startPoint/*.getX(), this.startPoint.getY(), other*/)) {
            count++;
        }
        if (other.checkIfPointOnLine(this.endPoint/*.getX(), this.endPoint.getY(), other*/)) {
            count++;
        }
        if (this.checkIfPointOnLine(other.start()/*.getX(), other.start().getY(), this*/)) {
            count++;
        }
        if (this.checkIfPointOnLine(other.end()/*.getX(), other.end().getY(), this*/)) {
            count++;
        }
        if (count == 2) {
            //checks if the lines arent overlapping
            if (this.startPoint.equals(other.start()) || this.startPoint.equals(other.end())) {
                return this.startPoint;
            } else if (this.endPoint.equals(other.start()) || this.endPoint.equals(other.end())) {
                return this.endPoint;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * checks intersection of 2 lines with different slopes.
     *
     * @param other - the line to check intersection wit
     * @return intersection point or null if there is no intersection
     */
    public Point checkIntersectionPoint(Line other) {
        double thisSlope = slope(this);
        double otherSlope = slope(other);
        double thisIntersectionWithY = intersectionWithYaxis(this, thisSlope);
        double otherIntersectionWithY = intersectionWithYaxis(other, otherSlope);
        // a1x+b1 = a2x+b2 => x = b2-b1/a1-a2
        double optionalXValue = ((otherIntersectionWithY - thisIntersectionWithY) / (thisSlope - otherSlope));
        //optional Y value for intersection according to this geometry.Line equation
        double optionalYValue = optionalXValue * thisSlope + thisIntersectionWithY;
        Point optionalPoint = new Point(optionalXValue, optionalYValue);
        if (this.checkIfPointOnLine(optionalPoint) && other.checkIfPointOnLine(optionalPoint)) {
            return optionalPoint;
        }
        return null;
    }

    /**
     * checks if point is on line.
     *
     * @param point - the point to check.
     * @return true if the point is on line and false otherwise.
     */
    public boolean checkIfPointOnLine(Point point) {
        return point.distance(start()) + point.distance(end()) <= this.length() + EPSILON;
    }

    /**
     * finds intersection point for line parallel to y axis.
     *
     * @param firstLine  - the parallel line
     * @param secondLine - the not-parallel line
     * @return true if the point is on the line and false otherwise
     */
    public Point intersectionWithParallelLine(Line firstLine, Line secondLine) {
        double secondLineStartX = Math.min(secondLine.start().getX(), secondLine.end().getX());
        double secondLineEndX = Math.max(secondLine.start().getX(), secondLine.end().getX());
        double firstLineXValue = firstLine.start().getX(); // the x value is the same in start and end point
        double firstLineStartYValue = Math.min(firstLine.start().getY(), firstLine.end().getY());
        double firstLineEndYValue = Math.max(firstLine.start().getY(), firstLine.end().getY());

        if (firstLineXValue >= secondLineStartX && firstLineXValue <= secondLineEndX) {
            double optionalYForIntersection = slope(secondLine) * firstLineXValue
                    + intersectionWithYaxis(secondLine, slope(secondLine));
            if (optionalYForIntersection >= firstLineStartYValue && optionalYForIntersection <= firstLineEndYValue) {
                return (new Point(firstLineXValue, optionalYForIntersection));
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * checks id line are equal.
     *
     * @param other - the line to check with
     * @return - true if they are equal and false otherwise
     */
    public boolean equals(Line other) {
        return ((other.start().equals(this.startPoint)
                && other.end().equals(this.endPoint))
                || (other.start().equals(this.endPoint)
                && other.end().equals(this.startPoint)));
    }

    /**
     * checks the closest intersection point from the start of the line to the rectangle.
     *
     * @param rect - the rectangle to check with.
     * @return the closest point.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> intersectionPointsArray = rect.intersectionPoints(this);
        int indexOfClosestPoint = this.startPoint.findClosestPointFromArrayIndex(intersectionPointsArray);
        return intersectionPointsArray.get(indexOfClosestPoint);
    }
}
