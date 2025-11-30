import java.awt.*;
import biuoop.DrawSurface;

public class Ball implements Sprite{

    private static final int SCREEN_WIDTH =800 ;
    private static final int SCREEN_HEIGHT = 600;

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
            // גם במקרה שאין סביבה – עדיין נשמור על המסך
            keepInScreen();
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

        // *** תיקונים נגד "להיכנס לבלוק" ו"בריחה מהמסך" ***
        fixIfInsideBlocks();
        keepInScreen();
    }




    @Override
    public void timePassed() {
        this.moveOneStep();
    }


//
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
    // אם הכדור בטעות נכנס לתוך בלוק – מוציאים אותו החוצה
    private void fixIfInsideBlocks() {
        if (this.gameEnvironment == null) {
            return;
        }

        for (Collidable c : this.gameEnvironment.getCollidables()) {
            Rectangle rect = c.getCollisionRectangle();
            if (rect == null) {
                continue;
            }

            double left   = rect.getUpperLeft().getX();
            double top    = rect.getUpperLeft().getY();
            double right  = left + rect.getWidth();
            double bottom = top + rect.getHeight();

            double x = this.center.getX();
            double y = this.center.getY();

            // אם המרכז *לא* בתוך המלבנים – אין בעיה
            if (x <= left || x >= right || y <= top || y >= bottom) {
                continue;
            }

            // הכדור נמצא בתוך בלוק -> מוציאים אותו לצד הקרוב ביותר
            double distLeft   = x - left;
            double distRight  = right - x;
            double distTop    = y - top;
            double distBottom = bottom - y;

            double min = Math.min(Math.min(distLeft, distRight), Math.min(distTop, distBottom));
            double eps = 0.1;

            double dx = this.velocity.getDx();
            double dy = this.velocity.getDy();

            if (min == distLeft) {
                // מוציאים לצד שמאל של הבלוק
                this.center = new Point(left - this.r - eps, y);
                dx = -Math.abs(dx);
            } else if (min == distRight) {
                // מוציאים לצד ימין של הבלוק
                this.center = new Point(right + this.r + eps, y);
                dx = Math.abs(dx);
            } else if (min == distTop) {
                // מוציאים למעלה
                this.center = new Point(x, top - this.r - eps);
                dy = -Math.abs(dy);
            } else { // bottom
                // מוציאים למטה
                this.center = new Point(x, bottom + this.r + eps);
                dy = Math.abs(dy);
            }

            this.velocity = new Velocity(dx, dy);
        }
    }
    private void keepInScreen() {
        double x = this.center.getX();
        double y = this.center.getY();
        double dx = this.velocity.getDx();
        double dy = this.velocity.getDy();
        boolean changed = false;

        if (x - this.r < 0) {
            x = this.r;
            dx = Math.abs(dx);
            changed = true;
        }
        if (x + this.r > SCREEN_WIDTH) {
            x = SCREEN_WIDTH - this.r;
            dx = -Math.abs(dx);
            changed = true;
        }
        if (y - this.r < 0) {
            y = this.r;
            dy = Math.abs(dy);
            changed = true;
        }
        if (y + this.r > SCREEN_HEIGHT) {
            y = SCREEN_HEIGHT - this.r;
            dy = -Math.abs(dy);
            changed = true;
        }

        if (changed) {
            this.center = new Point(x, y);
            this.velocity = new Velocity(dx, dy);
        }
    }



}