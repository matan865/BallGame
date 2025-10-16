package movement;

import biuoop.DrawSurface;

/**
 * movement.Sprite interface.
 *
 * @author Matan Badichi 322692419
 * @author Yakir Sharabi 206534893
 */
public interface Sprite {
    /**
     * draw the sprite to the screen.
     *
     * @param d - the surface.
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     */
    void timePassed();
}