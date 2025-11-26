import biuoop.DrawSurface;

import java.awt.Color;

public class Block implements Collidable {

    private Rectangle rectangle;
    private Color color;

    public Block(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {

        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        double x = collisionPoint.getX();
        double y = collisionPoint.getY();

        double left = rectangle.getUpperLeft().getX();
        double right = left + rectangle.getWidth();
        double top = rectangle.getUpperLeft().getY();
        double bottom = top + rectangle.getHeight();

        double eps = 3.0;

        boolean hitLeft = Math.abs(x - left) <= eps;
        boolean hitRight = Math.abs(x - right) <= eps;
        boolean hitTop = Math.abs(y - top) <= eps;
        boolean hitBottom = Math.abs(y - bottom) <= eps;

        // פגיעה בקירות עומדיים: להפוך dx
        if (hitLeft || hitRight) {
            dx = -dx;
        }

        // פגיעה בקירות אופקיים: להפוך dy
        if (hitTop || hitBottom) {
            dy = -dy;
        }

        return new Velocity(dx, dy);
    }

    public Color getColor() {
        return this.color;
    }
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        Rectangle r = this.rectangle;

        int x = (int) r.getUpperLeft().getX();
        int y = (int) r.getUpperLeft().getY();
        int w = (int) r.getWidth();
        int h = (int) r.getHeight();

        d.fillRectangle(x, y, w, h);
        d.setColor(Color.BLACK);
        d.drawRectangle(x, y, w, h);
    }

}