import java.util.ArrayList;
import java.util.List;

public class Line {
    private Point start;
    private Point end;
    // If this line does not intersect with the rectangle, return null.
    // Otherwise, return the closest intersection point to the
    // start of the line.
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> ints = rect.intersectionPoints(this);

        if (ints.isEmpty()) {
            return null;
        }

        Point best = ints.get(0);

        for (Point p : ints) {
            if (this.start.distance(p) < this.start.distance(best)) {
                best = p;
            }
        }

        return best;
    }


    // constructors
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    // Return the length of the line
    public double length() {
        return start.distance(end);
    }

    // Returns the middle point of the line
    public Point middle() {
        double midx = ((start.getX() + end.getX()) / (2));
        double midy = ((start.getY() + end.getY()) / (2));
        return new Point(midx, midy);
    }

    // Returns the start point of the line
    public Point start() {
        return start;
    }

    // Returns the end point of the line
    public Point end() {
        return end;
    }

    public Double slope() {
        double xs = start.getX() - end.getX();
        double ys = start.getY() - end.getY();
        if (xs == 0) {
            return null;
        }
        return ys / xs;
    }

    // Returns true if the lines intersect, false otherwise
    public boolean isIntersecting(Line other) {
        Double ml1 = this.slope();
        Double ml2 = other.slope();


        //if the 2 slopes are 0 we chacking if thay have the same x and y in some points
        if (ml1 == null && ml2 == null) {
            if (this.start().getX() != other.start().getX()) {
                return false;
            }
//this is chakeing if one or the other lines is  on the other line and if yes thay are intersecting
            return (inRange(this.start().getY(), this.end().getY(), other.start().getY()) ||
                    inRange(this.start().getY(), this.end().getY(), other.end().getY()) ||
                    inRange(other.start().getY(), other.end().getY(), this.start().getY()) ||
                    inRange(other.start().getY(), other.end().getY(), this.end().getY()));

        }
        // if scope of 1 is not 0 and the other one is 0 so we need to see if its intersection
        if (ml1 == null && ml2 != null) {
            double x = this.start.getX();
            double b2 = other.start.getY() - ml2 * other.start.getX();
            double yOfOther = ml2 * x + b2;
            boolean onThis = (inRange(this.start.getY(), this.end.getY(), yOfOther));
            boolean onOther = (inRange(other.start().getX(), other.end().getX(), x) &&
                    inRange(other.start().getY(), other.end().getY(), yOfOther));

            return onThis && onOther;

        }
        if (ml1 != null && ml2 == null) {
            double x = other.start().getX();

            double b1 = this.start.getY() - ml1 * this.start.getX();
            double yOfThis = ml1 * x + b1;


            boolean onOther =
                    (inRange(other.start().getY(), other.end().getY(), yOfThis));

            boolean onThis =
                    (inRange(this.start().getX(), this.end().getX(), x) &&
                            inRange(this.start().getY(), this.end().getY(), yOfThis));

            return onThis && onOther;
        }
        if (ml1 != null && ml2 != null) {
            // geting the intersection whit y
            double bThis = this.start.getY() - ml1 * this.start.getX();
            double bOther = other.start.getY() - ml2 * other.start.getX();
            //if the slope is the same than we need same Y intersections
            if (ml1.equals(ml2)) {
                if (bThis != bOther) {
                    return false;
                }
                // chakeing that if the slope same than  if thay are sharing any points of X or Y
                return inRange(this.start.getX(), this.end.getX(), other.start.getX()) ||
                        inRange(this.start.getX(), this.end.getX(), other.end.getX()) ||
                        inRange(other.start.getX(), other.end.getX(), this.start.getX()) ||
                        inRange(other.start.getX(), other.end.getX(), this.end.getX());
            }
            // if  slope arnt the same so we finding same X
            double x = ((bOther - bThis) / (ml1 - ml2));
            double y = (ml1 * x) + bThis;
            return (inRange(this.start.getX(), this.end.getX(), x) &&
                    inRange(this.start.getY(), this.end.getY(), y)) &&
                    (inRange(other.start.getX(), other.end.getX(), x) &&
                            inRange(other.start.getY(), other.end.getY(), y));


        }
        return false;
    }

    // Returns the intersection point if the lines intersect,
    // and null otherwise.
    public Point intersectionWith(Line other) {
        //if thay are not intersecting at all so return null
        if (!this.isIntersecting(other)) {
            return null;
        }
        Double m1 = this.slope();
        Double m2 = other.slope();
        //if thay both null
        if (m1 == null && m2 == null) {

            double x = this.start.getX();

            double thisMinY = Math.min(this.start.getY(), this.end.getY());
            double thisMaxY = Math.max(this.start.getY(), this.end.getY());
            double otherMinY = Math.min(other.start.getY(), other.end.getY());
            double otherMaxY = Math.max(other.start.getY(), other.end.getY());

            double intersectY = Math.max(thisMinY, otherMinY);

            return new Point(x, intersectY);
        }
        //if this  = null  no slope and other have slope
        if (((this.slope() == null) && other.slope() != null)) {
            double x = this.start.getX();
            double b2 = other.start().getY() - m2 * other.start().getX();  // b = y - m*x
            double y = m2 * x + b2;                       // y = m*x + b
            return new Point(x, y);
        }

        // now  if this have slope and other have no slope mean null
        if (((other.slope() == null) && this.slope() != null)) {
            double x = other.start.getX();
            double b1 = this.start().getY() - m1 * this.start().getX();  // b = y - m*x
            double y = m1 * x + b1;                       // y = m*x + b
            return new Point(x, y);

        }
        // if thay both have slope and non of them is null  and thay have 1 point of intersecting
        if (this.slope() != null && other.slope() != null) {
            double b1 = this.start().getY() - m1 * this.start().getX();
            double b2 = other.start().getY() - m2 * other.start().getX();

            // if slopes are the same
            if (m1 == m2) {
                // if thay are standing on eche other fully
                if (b1 == b2) {
                    // we will bring back the start
                    double intersectX = Math.max(Math.min(this.start.getX(), this.end.getX()),
                            Math.min(other.start.getX(), other.end.getX()));
                    double intersectY = m1 * intersectX + b1;
                    return new Point(intersectX, intersectY);
                }} else {
                    // if no so we bring back the point that thay are interections
                    double x = (b2 - b1) / (m1 - m2);
                    double y = m1 * x + b1;
                    return new Point(x, y);
                }

        }
        // return null if all the above dont work
        return null;
    }

    // equals -- return true is the lines are equal, false otherwise
    public boolean equals(Line other) {
        if ((this.start.getX() == other.start.getX()) && (this.start.getY() == other.start.getY() &&
                this.end.getX() == other.end.getX()) && (this.end.getY() == other.end.getY())) {
            return true;
        } else {
            return false;
        }
    }

    // inRange -- return true if the point of intersection is in range of the limited Lines
    public boolean inRange(double a, double b, double isIn) {
        if (isIn <= Math.max(a, b) && isIn >= Math.min(a, b)) {
            return true;
        }
        return false;
    }


}

