import java.util.ArrayList;
import java.util.List;

public class Rectangle {
    Point upperLeft;
    double width;
    double height;


    // Create a new rectangle with location and width/height.
    public Rectangle(Point upperLeft, double width, double height){
        this.upperLeft=upperLeft;
        this.height=height;
        this.width=width;
    }


    // Return a (possibly empty) List of intersection points
    // with the specified line.
    public java.util.List<Point> intersectionPoints(Line line){
        List<Point> intersections = new ArrayList<>();
        // compute rectangle corners
        double x = this.upperLeft.getX();
        double y = this.upperLeft.getY();

        Point upperRight = new Point(x + this.width, y);
        Point lowerLeft  = new Point(x, y + this.height);
        Point lowerRight = new Point(x + this.width, y + this.height);

        // rectangle edges
        Line top    = new Line(this.upperLeft, upperRight);
        Line bottom = new Line(lowerLeft, lowerRight);
        Line left   = new Line(this.upperLeft, lowerLeft);
        Line right  = new Line(upperRight, lowerRight);

        // add intersection points
        addIntersection(intersections, line, top);
        addIntersection(intersections, line, bottom);
        addIntersection(intersections, line, left);
        addIntersection(intersections, line, right);

        // 4. מחזירים את הרשימה (יכולה להיות ריקה, 1 נק', או 2 נק')
        return intersections;
}

    // Return the width and height of the rectangle
    public double getWidth(){
        return this.width;
    }
    public double getHeight(){
      return  this.height;
    }

    // Returns the upper-left point of the rectangle.
    public Point getUpperLeft(){
        return this.upperLeft;
    }
    private void addIntersection(List<Point> list, Line line, Line edge) {
        if (line.isIntersecting(edge)) {
            Point p = line.intersectionWith(edge);
            if (p != null && !containsPoint(list, p)) {
                list.add(p);
            }
        }
    }
    private boolean containsPoint(List<Point> list, Point p) {
        for (Point existing : list) {  // loop on the List  while
            if (existing.equals(p)) {  // useing the metod of equals of Point class
                return true;
            }
        }
        return false;
    }
}