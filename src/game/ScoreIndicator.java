package game;

import biuoop.DrawSurface;
import movement.Sprite;

import java.awt.Color;


/**
 * ScoreIndicator class.
 *
 * @author Matan Badichi 322692419
 * @author Yakir Sharabi 206534893
 */
public class ScoreIndicator implements Sprite {
    private Counter count;

    /**
     * constructor.
     *
     * @param count - update the field.
     */
    public ScoreIndicator(Counter count) {
        this.count = count;
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d - the surface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.white);
        d.drawText(d.getWidth() / 2 - 50, 15, "Score: " + this.count.getValue(), 15);
    }

    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {

    }

    /**
     * adds the instance to the game.
     *
     * @param game - the game
     */
    public void addToGame(Game game) {
        game.addSprite(this);
    }
}
