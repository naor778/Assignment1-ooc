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
        // אם אין מהירות – אין תנועה
        if (this.velocity == null) {
            return;
        }

        // אם אין סביבה – תנועה חופשית
        if (this.gameEnvironment == null) {
            this.center = this.velocity.applyToPoint(this.center);
            return;
        }

        // 1) המסלול שהכדור היה עושה בלי מכשולים
        Point start = this.center;
        Point end = this.velocity.applyToPoint(this.center);
        Line trajectory = new Line(start, end);

        // 2) שואלים את הסביבה על ההתנגשות הקרובה
        CollisionInfo info = this.gameEnvironment.getClosestCollision(trajectory);

        // אין פגיעה → זזים עד סוף המסלול
        if (info == null) {
            this.center = end;
            return;
        }

        // יש פגיעה
        Point collisionPoint = info.collisionPoint();
        Collidable collidable = info.collisionObject();

        double dx = this.velocity.getDx();
        double dy = this.velocity.getDy();
        double len = Math.sqrt(dx * dx + dy * dy);
        if (len == 0) {
            return; // ליתר ביטחון
        }

        double ux = dx / len;
        double uy = dy / len;

        // נביא את המלבֵן של האובייקט שפגענו בו
        Rectangle rect = collidable.getCollisionRectangle();
        double left   = rect.getUpperLeft().getX();
        double top    = rect.getUpperLeft().getY();
        double right  = left + rect.getWidth();
        double bottom = top + rect.getHeight();

        double x = collisionPoint.getX();
        double y = collisionPoint.getY();

        // eps לזיהוי באיזה צד פגענו (לא קטן מדי)
        double eps = 2.0;

        boolean hitLeft   = Math.abs(x - left)   <= eps;
        boolean hitRight  = Math.abs(x - right)  <= eps;
        boolean hitTop    = Math.abs(y - top)    <= eps;
        boolean hitBottom = Math.abs(y - bottom) <= eps;

        double cx;
        double cy;
        double margin = 0.5; // מרווח קטן מעבר לרדיוס

        if (hitLeft) {
            // פגענו בצד שמאל של הבלוק → נשים את המרכז משמאל לבלוק
            cx = left - this.r - margin;
            cy = y;
        } else if (hitRight) {
            // פגיעה בצד ימין
            cx = right + this.r + margin;
            cy = y;
        } else if (hitTop) {
            // פגיעה בצד העליון
            cx = x;
            cy = top - this.r - margin;
        } else if (hitBottom) {
            // פגיעה בצד התחתון
            cx = x;
            cy = bottom + this.r + margin;
        } else {
            // לא זוהה צד ברור (מקרה קצה / פינה) → נעשה backOff רגיל לאורך המסלול
            double backOff = this.r + 1;
            cx = x - ux * backOff;
            cy = y - uy * backOff;
        }

        // מעדכנים את מרכז הכדור למיקום החדש, מחוץ לבלוק
        this.center = new Point(cx, cy);

        // ולבסוף – מעדכנים מהירות לפי מה שהאובייקט מחזיר
        this.velocity = collidable.hit(collisionPoint, this.velocity);
    }
    @Override
    public void timePassed() {
        this.moveOneStep();
    }





    // draw the ball on the given DrawSurface
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
}