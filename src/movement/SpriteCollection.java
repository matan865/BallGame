package movement;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * movement.Sprite collection.
 *
 * @author Matan Badichi 322692419
 * @author Yakir Sharabi 206534893
 */
public class SpriteCollection {

    private List<Sprite> spriteCollectionArray = new ArrayList<>();

    /**
     * adds sprite to the collection.
     *
     * @param s - the sprite.
     */
    public void addSprite(Sprite s) {
        spriteCollectionArray.add(s);
    }


    /**
     * call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        List<Sprite> spritesCopy = new ArrayList<>(this.spriteCollectionArray);
        for (Sprite s : spritesCopy) {
            s.timePassed();
        }
    }


    /**
     * call drawOn(d) on all sprites.
     *
     * @param d - the surface.
     */
    public void drawAllOn(DrawSurface d) {
        for (Object sprite : spriteCollectionArray) {
            ((Sprite) sprite).drawOn(d);
        }
    }

    /**
     * removes sprite to the collection.
     *
     * @param s - the sprite.
     */
    public void removeSprite(Sprite s) {
        spriteCollectionArray.remove(s);
    }
}