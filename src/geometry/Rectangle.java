package geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * rectangle class.
 */
public class Rectangle {

    private Point upperLeftPoint;
    private Point upperRightPoint;
    private Point lowerLeftPoint;
    private Point lowerRightPoint;
    private double width;
    private double height;

    /**
     * constructor.
     *
     * @param upperLeft - the upper left corner of the rectangle
     * @param width     - rectangle width
     * @param height    - rectangle height
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeftPoint = upperLeft;
        this.width = width;
        this.height = height;
        setCorners();
    }

    // Return the width and height of the rectangle

    /**
     * gets the rectangle width.
     *
     * @return the width.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * gets the rectangle height.
     *
     * @return the height.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * gets the upper-left point of the rectangle.
     *
     * @return upper-left point
     */
    public Point getUpperLeft() {
        return this.upperLeftPoint;
    }

    /**
     * gets the upper-right point of the rectangle.
     *
     * @return upper-right point
     */
    public Point getUpperRight() {
        return this.upperRightPoint;
    }

    /**
     * gets the lower-right point of the rectangle.
     *
     * @return lower-right point
     */
    public Point getLowerRight() {
        return this.lowerRightPoint;
    }

    /**
     * sets all rectangle corners according to the upper left point, width an height.
     * the constructor automatically uses this function to sets the fields accordingly.
     */
    public void setCorners() {
        this.upperRightPoint = new Point(this.upperLeftPoint.getX() + this.width, this.upperLeftPoint.getY());
        this.lowerLeftPoint = new Point(this.upperLeftPoint.getX(), this.upperLeftPoint.getY() + this.height);
        this.lowerRightPoint = new Point(upperRightPoint.getX(), lowerLeftPoint.getY());
    }

    /**
     * gets the left line of the rectangle.
     *
     * @return the left line.
     */
    public Line getLeftLine() {
        return new Line(this.upperLeftPoint, this.lowerLeftPoint);
    }

    /**
     * gets the right line of the rectangle.
     *
     * @return the right line.
     */
    public Line getRightLine() {
        return new Line(this.upperRightPoint, this.lowerRightPoint);
    }

    /**
     * gets the top line of the rectangle.
     *
     * @return the top line.
     */
    public Line getTopLine() {
        return new Line(this.upperLeftPoint, this.upperRightPoint);
    }

    /**
     * gets the bottom line of the rectangle.
     *
     * @return the bottom line.
     */
    public Line getBottomLine() {
        return new Line(this.lowerLeftPoint, this.lowerRightPoint);
    }

    /**
     * get the rectangle lines in List.
     *
     * @param rectanglesLines - the list to add the lines to.
     * @return the updated list.
     */
    public List<Line> getRectanglesLinesList(List<Line> rectanglesLines) {
        rectanglesLines.add(this.getTopLine());
        rectanglesLines.add(this.getBottomLine());
        rectanglesLines.add(this.getLeftLine());
        rectanglesLines.add(this.getRightLine());
        return rectanglesLines;
    }

    /**
     * removes point that appear twice in the array.
     *
     * @param intersectionPointsList - list of all point.
     * @return the updated list.
     */
    public List<Point> removeDuplicatePoints(List<Point> intersectionPointsList) {
        // iterates threw all point in the array
        for (int i = 0; i < intersectionPointsList.size(); i++) {
            for (int j = i + 1; j < intersectionPointsList.size(); j++) {
                if (intersectionPointsList.get(i) == intersectionPointsList.get(j)) {
                    intersectionPointsList.remove(i);
                }
            }
        }
        return intersectionPointsList;
    }

    // Return a (possibly empty) List of intersection points
    // with the specified line.

    /**
     * checks intersection of the rectangle with specific line.
     *
     * @param line - the line to check with.
     * @return list of intersection points.
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        List<Point> intersectionPointsList = new ArrayList<>();
        List<Line> rectanglesLines = new ArrayList<>();
        rectanglesLines = getRectanglesLinesList(rectanglesLines);
        // counts the intersection points
        int counter = 0;
        for (int i = 0; i < 4; i++) {
            if (line.intersectionWith(rectanglesLines.get(i)) != null) {
                counter++;
                intersectionPointsList.add(line.intersectionWith(rectanglesLines.get(i)));
            }
        }
        // there is maximum 2 intersection point so if the counter is bigger there is duplicate points
        if (counter < 3) {
            return intersectionPointsList;
        } else {
            return removeDuplicatePoints(intersectionPointsList);
        }

    }
}
