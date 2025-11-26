import biuoop.GUI;
import biuoop.DrawSurface;
import java.awt.Color;

public class Main {
        public static void main(String[] args) {

            GUI gui = new GUI("Collision Test", 800, 600);
            int width = 800;
            int height = 600;

            GameEnvironment env = new GameEnvironment();

            // ➕ קירות אוטומטיים
            Block[] borders = addScreenBorders(env, width, height);

            // יצירת כמה בלוקים לבדיקות
            Block b1 = new Block(new Rectangle(new Point(100, 100), 200, 20), Color.BLUE);
            Block b5 = new Block(new Rectangle(new Point(150, 170), 200, 20), Color.BLUE);
            Block b2 = new Block(new Rectangle(new Point(300, 300), 200, 20), Color.GREEN);
            Block b3 = new Block(new Rectangle(new Point(200,300),200, 30),Color.cyan);
            env.addCollidable(b3);
            env.addCollidable(b5);
            env.addCollidable(b1);
            env.addCollidable(b2);
            for (int i=0;i<borders.length;i++){
                env.addCollidable(borders[i]);
            }

            // כדור
            Ball ball = new Ball(new Point(200, 150), 10, Color.RED);
            ball.setVelocity(3, 4);
            ball.setGameEnvironment(env);

            while (true) {
                DrawSurface d = gui.getDrawSurface();

                // ציור בלוקים
                b3.drawOn(d);
                b1.drawOn(d);
                b2.drawOn(d);
                b5.drawOn(d);
                // מצייר את הבלוקים שחוסמים את המסך
                for (Block border : borders) {
                    border.drawOn(d);
                }

                // ציור קירות — אם תרצה אפשר גם להחזיר אותם מהפונקציה כדי לצייר.
                // (כרגע הם רק ב-collidables ולכן הם עובדים)

                // ציור הכדור
                ball.drawOn(d);

                gui.show(d);
                ball.moveOneStep();

                try { Thread.sleep(15); } catch (Exception ignored) {}
            }
        }


    private static Block[] addScreenBorders(GameEnvironment env, int width, int height) {

        int thickness = 10;  // עובי הקיר

        Block top = new Block(
                new Rectangle(new Point(0, 0), width, thickness),
                Color.GRAY
        );

        Block bottom = new Block(
                new Rectangle(new Point(0, height - thickness), width, thickness),
                Color.GRAY
        );

        Block left = new Block(
                new Rectangle(new Point(0, 0), thickness, height),
                Color.GRAY
        );

        Block right = new Block(
                new Rectangle(new Point(width - thickness, 0), thickness, height),
                Color.GRAY
        );


        env.addCollidable(top);
        env.addCollidable(bottom);
        env.addCollidable(left);
        env.addCollidable(right);

        return new Block[]{top, bottom, left, right};
    }

}
