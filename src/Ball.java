import java.awt.*;
import biuoop.DrawSurface;

public class Ball implements Sprite{
    private GameEnvironment gameEnvironment;
    private Point center;
    private int r;
    private Color color;
    private Velocity velocity;
    // constructor
    public Ball(Point center, int r, java.awt.Color color){
        this.center=center;
        this.r=r;
        this.color=color;
    }




    // accessors
    public int getX(){
        return  (int) Math.round(center.getX());
    }
    public int getY() {
        return  (int) Math.round(center.getY());
    }

    public int getSize(){
        return r;
    }
    public java.awt.Color getColor(){
        return color;
    }
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    public Velocity getVelocity() {
        return this.velocity;
    }
    public void moveOneStep() {
        if (this.gameEnvironment == null) {
            this.center = this.getVelocity().applyToPoint(this.center);
            return;
        }

        // 1. מסלול מהנקודה הנוכחית לנקודה הבאה
        Point current = this.center;
        Point next = this.velocity.applyToPoint(this.center);
        Line trajectory = new Line(current, next);

        // 2. בדיקה האם יש התנגשות בדרך
        CollisionInfo info = this.gameEnvironment.getClosestCollision(trajectory);

        if (info == null) {
            // אין התנגשות – פשוט זזים
            this.center = next;
        } else {
            // יש עצם מתנגש
            Point collisionPoint = info.collisionPoint();
            Collidable object = info.collisionObject();

            // להזיז את הכדור טיפה לפני נקודת ההתנגשות
            double epsilon = 0.001;
            double newX = collisionPoint.getX() - this.velocity.getDx() * epsilon;
            double newY = collisionPoint.getY() - this.velocity.getDy() * epsilon;
            this.center = new Point(newX, newY);

            // לחשב מהירות חדשה דרך hit של האובייקט
            this.velocity = object.hit(collisionPoint, this.velocity);
        }
    }

    @Override
    public void timePassed() {
        this.moveOneStep();
    }



    // draw the ball on the given DrawSurface
    @Override
    public void drawOn(DrawSurface surface){
        surface.setColor(this.color);
        surface.fillCircle(getX(),getY(),getSize());
    }

    public void setVelocity(double dx, double dy){
        this.velocity=new Velocity(dx,dy);
    }

    public void setGameEnvironment(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }
    public void addToGame(Game g) {
        g.addSprite(this);
    }

}