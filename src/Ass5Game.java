import game.Game;

/**
 * inclused the main method to start the game.
 *
 * @author Matan Badichi 322692419
 * @author Yakir Sharabi 206534893
 */
public class Ass5Game {
    /**
     * the main method.
     *
     * @param args - the arguments the main gets.
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}
