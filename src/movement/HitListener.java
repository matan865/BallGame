package movement;
import shapes.Ball;
import shapes.Block;
/**
 * HitListener class.
 *
 * @author Matan Badichi 322692419
 * @author Yakir Sharabi 206534893
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     * @param beingHit - the block that being hit
     * @param hitter - the ball that hits the block
    */
    void hitEvent(Block beingHit, Ball hitter);
}
