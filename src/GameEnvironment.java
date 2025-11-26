import java.util.List;
import java.util.ArrayList;

public class GameEnvironment {

    private List<Collidable> collidables;

    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    // add the given collidable to the environment.
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }
    // Assume an object moving from line.start() to line.end().
    // If this object will not collide with any of the collidables
    // in this collection, return null. Else, return the information
    // about the closest collision that is going to occur.
    public CollisionInfo getClosestCollision(Line trajectory) {

        Point closestPoint = null;
        Collidable closestObject = null;
        double minDist = Double.POSITIVE_INFINITY;

        // עוברים על כל האובייקטים שניתן להתנגש בהם
        for (Collidable c : this.collidables) {
            Rectangle rect = c.getCollisionRectangle();


            Point p = trajectory.closestIntersectionToStartOfLine(rect);

            if (p != null) {
                double dist = trajectory.start().distance(p);
                if (dist < minDist) {
                    minDist = dist;
                    closestPoint = p;
                    closestObject = c;
                }
            }
        }

        if (closestPoint == null) {
            return null; // אין שום התנגשות בדרך
        }

        return new CollisionInfo(closestPoint, closestObject);
    }
}

