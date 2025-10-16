import game.Game;

/**
 * inclused the main method to start the game.
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
