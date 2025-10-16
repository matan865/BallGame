package shapes;

import game.Counter;
import game.Game;
import movement.HitListener;

/**
 * BlockRemover class - is in charge of removing blocks from the game and count the number of blocks that remain.
 *
 * @author Matan Badichi 322692419
 * @author Yakir Sharabi 206534893
 */

public class BlockRemover implements HitListener {
    private Game game;
    private Counter remainingBlocks;

    /**
     * constructor.
     *
     * @param game            - the game
     * @param remainingBlocks - the counter which represent the number of block thar remained.
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * called when there is hit.
     *
     * @param beingHit - the block that being hit
     * @param hitter   - the ball that hits the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeFromGame(game);
        remainingBlocks.decrease(1);
        beingHit.removeHitListener(this);
    }

}