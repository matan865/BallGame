package shapes;

import game.Counter;
import game.Game;
import movement.HitListener;

/**
 * BallRemover class.
 */
public class BallRemover implements HitListener {
    private Game game;
    private Counter remainingBalls;

    /**
     * constructor.
     *
     * @param game           - the game
     * @param remainingBalls - the counter witch checks the number of blocks reminded.
     */
    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    /**
     * Handles the event when a ball hits a block.
     * This method is called whenever a ball hits a block in the game.
     * It removes the ball from the game and decrements the count of remaining balls.
     *
     * @param beingHit the block that is being hit by the ball
     * @param ball     the ball that hits the block
     */
    @Override
    public void hitEvent(Block beingHit, Ball ball) {
        ball.removeFromGame(game);
        remainingBalls.decrease(1);
    }
}
