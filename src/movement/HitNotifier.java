package movement;
/**
 * HitNotifier class.
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
