package game;

import movement.HitListener;
import shapes.Ball;
import shapes.Block;

/**
 * ScoreTrackingListener class.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * updates the current score.
     *
     * @param currentScore - the counter the field should be updated to.
     */
    public ScoreTrackingListener(Counter currentScore) {
        this.currentScore = currentScore;
    }

    /**
     * called when there is a hot and increases the score accordingly.
     *
     * @param beingHit - the block that being hit
     * @param hitter   - the ball that hits the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        currentScore.increase(5);
    }
}
