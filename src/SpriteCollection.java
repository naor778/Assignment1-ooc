import biuoop.DrawSurface;
import java.util.ArrayList;
import java.util.List;

public class SpriteCollection {
    private List<Sprite> sprites;

    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }

    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }


    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }
    // call timePassed() on all sprites.
    public void notifyAllTimePassed(){

        List<Sprite> copy = new ArrayList<>(this.sprites);
        for (Sprite s : copy) {
            s.timePassed();
        }
    }

    // call drawOn(d) on all sprites
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.sprites) {
            s.drawOn(d);
        }
    }
}
