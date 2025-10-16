package game;

/**
 * Counter class.
 */
public class Counter {
    private int counter = 0;

    /**
     * add number to current count.
     *
     * @param number - the number to add
     */
    public void increase(int number) {
        counter += number;
    }

    /**
     * subtract number from current count.
     *
     * @param number - the number to decrease.
     */
    public void decrease(int number) {
        counter -= number;
    }

    /**
     * get the current count.
     *
     * @return the current count
     */
    public int getValue() {
        return counter;
    }
}
