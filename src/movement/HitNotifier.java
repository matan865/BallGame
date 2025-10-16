package movement;
/**
 * HitNotifier class.
 *
 * @author Matan Badichi 322692419
 * @author Yakir Sharabi 206534893
 */
public interface HitNotifier {
    /**
     * adds HitListener to the hit events.
     * @param hl - the instance of HitListener that should be added.
    */
    void addHitListener(HitListener hl);
    /**
     * removes HitListener to the hit events.
     * @param hl - the instance of HitListener that should be removed.
    */
    void removeHitListener(HitListener hl);

}
