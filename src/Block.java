import biuoop.DrawSurface;

import java.awt.Color;

public class Block implements Collidable ,Sprite {

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
        double x = collisionPoint.getX();
        double y = collisionPoint.getY();

        double left   = this.rectangle.getUpperLeft().getX();
        double right  = left + this.rectangle.getWidth();
        double top    = this.rectangle.getUpperLeft().getY();
        double bottom = top + this.rectangle.getHeight();

        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        double eps = 0.0001;

        // האם פגענו בצד שמאל או ימין
        boolean hitLeftSide  = Math.abs(x - left)  <= eps;
        boolean hitRightSide = Math.abs(x - right) <= eps;

        // האם פגענו בחלק עליון או תחתון
        boolean hitTop    = Math.abs(y - top)    <= eps;
        boolean hitBottom = Math.abs(y - bottom) <= eps;

        if (hitLeftSide || hitRightSide) {
            dx = -dx;
        }
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

    @Override
    public void timePassed() {

    }
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }


}