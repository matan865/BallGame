package game;

import movement.SpriteCollection;
import movement.Collidable;
import movement.Sprite;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import geometry.Point;
import geometry.Rectangle;
import shapes.Ball;
import shapes.Block;
import shapes.BallRemover;
import shapes.BlockRemover;
import shapes.Paddle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Game class creates the game.
 */
public class Game {
    private static final int BOARD_WIDTH = 800;
    private static final int BOARD_HEIGHT = 600;
    private static final int BORDERS_WIDTH = 20;
    private static final int BLOCK_WIDTH = 50;
    private static final int BLOCK_HEIGHT = 25;
    private static final int MAX_NUM_OF_BLOCKS_IN_LINE = 11;
    private static final int NUM_OF_LINES = 6;
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 10;
    private static final int BALLS_SPEED = 10;
    private static final int BALLS_RADIUS = 6;

    private Counter currentScore;
    private Counter remainingBalls;
    private Counter remainingBlocks;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;

    /**
     * constructor.
     * initialize sprites, environment and counter.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.remainingBlocks = new Counter();
        this.remainingBalls = new Counter();
        this.currentScore = new Counter();
    }

    /**
     * add movement.Collidable for the game.
     *
     * @param c - the movement.Collidable.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * add sprite for the game.
     *
     * @param s - sprites.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * creates the frame for the game with block.
     */
    public void createFrame() {
        List<Rectangle> frame = new ArrayList<>();

        frame.add(new Rectangle(new Point(0, 0), BOARD_WIDTH, BORDERS_WIDTH));
        frame.add(new Rectangle(new Point(0, BOARD_HEIGHT - BORDERS_WIDTH), BOARD_WIDTH, BORDERS_WIDTH));
        frame.add(new Rectangle(new Point(0, 0), BORDERS_WIDTH, BOARD_WIDTH));
        frame.add(new Rectangle(new Point(BOARD_WIDTH - BORDERS_WIDTH, 0), BORDERS_WIDTH, BOARD_WIDTH));

        for (Object rectangle : frame) {
            Block block = new Block((Rectangle) rectangle, Color.gray);
            block.addToGame(this);
        }
    }

    /**
     * creates list of colors.
     *
     * @param blockColors - the list.
     * @return - the updated list.
     */
    public List<Color> createColors(List<Color> blockColors) {
        blockColors.add(new Color(224, 224, 224));
        blockColors.add(new Color(241, 7, 7));
        blockColors.add(new Color(227, 227, 8));
        blockColors.add(new Color(7, 108, 209));
        blockColors.add(new Color(225, 65, 144));
        blockColors.add(new Color(5, 181, 91));
        return blockColors;
    }

    /**
     * creates the block for the game.
     */
    public void createBlocks() {
        List<Color> blockColors = new ArrayList<Color>();
        BlockRemover blockRemover = new BlockRemover(this, remainingBlocks);
        ScoreTrackingListener scoreTracker = new ScoreTrackingListener(currentScore);
        // gets array of colors for the blocks
        blockColors = createColors(blockColors);
        for (int i = 0; i < NUM_OF_LINES; i++) {
            for (int j = MAX_NUM_OF_BLOCKS_IN_LINE - i; j > 0; j--) {
                Point upperLeft = new Point(BOARD_WIDTH - BORDERS_WIDTH - (j * BLOCK_WIDTH),
                        i * BLOCK_HEIGHT + 100);
                Block block = new Block(new Rectangle(upperLeft, BLOCK_WIDTH, BLOCK_HEIGHT), blockColors.get(i));
                // adds listeners to the listeners array of the block (updates them in hit)
                block.addHitListener(blockRemover);
                // adds the clock to the game:
                block.addToGame(this);
                // increases the amount of block in the game:
                remainingBlocks.increase(1);
                block.addHitListener(scoreTracker);
            }
        }
    }

    /**
     * creates 3 balls for the game.
     */
    public void createBall() {
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            Ball ball = new Ball(new Point(500, 300), BALLS_RADIUS, Color.BLACK);
            ball.addToGame(this);
            ball.setVelocity(random.nextInt(BALLS_SPEED) + 1, random.nextInt(BALLS_SPEED) + 1);
            ball.setGameEnvironment(this.environment);
            remainingBalls.increase(1);
        }
    }

    /**
     * creates the paddle for the game.
     */
    public void createPaddle() {
        Paddle paddle = new Paddle(gui, new Rectangle(new Point(380, 570),
                PADDLE_WIDTH, PADDLE_HEIGHT), Color.black);
        // adds the paddle to the environment and to the game.
        environment.setPaddle(paddle);
        paddle.addToGame(this);
    }

    /**
     * Initialize a new game: create the gui, blocks, balls, frame and paddle by calling other methods.
     */
    public void initialize() {
        // creates the gui
        gui = new GUI("game.Game", 800, 600);
        // crates all other objects for the game
        createBall();
        createPaddle();
        createBlocks();
        createFrame();
        createDeathRegion();
        scoreBoard();
    }

    /**
     * Run the game -- start the animation loop.
     */
    public void run() {
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (true) {
            long startTime = System.currentTimeMillis(); // timing

            DrawSurface d = gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();

            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
            if (remainingBlocks.getValue() == 0) {
                currentScore.increase(100);
                gui.close();
                return;
            }
            if (remainingBalls.getValue() == 0) {
                gui.close();
                return;
            }
        }

    }

    /**
     * removes the collidable from the game environment.
     *
     * @param c - the collidable that should be removed
     */
    public void removeCollidable(Collidable c) {
        environment.removeCollidable(c);
    }

    /**
     * removes the sprite from the sprites array list.
     *
     * @param s - the sprite that should be removed
     */
    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }

    /**
     * Creates the death region in the game.
     * The death region is a block positioned at the bottom of the screen
     * which removes any balls that hit it from the game.
     * The width and height of the death region are determined by the
     * dimensions of the draw surface from the GUI.
     * The block is colored red and is added to the game with a BallRemover
     * listener that decrements the number of remaining balls.
     */
    public void createDeathRegion() {
        int width = gui.getDrawSurface().getWidth();
        int height = gui.getDrawSurface().getHeight();
        Rectangle deathRegionRect = new Rectangle(new Point(0, height - 23), width, 20);
        Block deathRegion = new Block(deathRegionRect, Color.red);
        BallRemover ballRemover = new BallRemover(this, remainingBalls);
        deathRegion.addHitListener(ballRemover);
        deathRegion.addToGame(this);
    }

    /**
     * Adds the score indicator to the game.
     * The score indicator displays the current score and is positioned
     * at the top of the game screen.
     * This method initializes the score indicator with the current score
     * and adds it to the game.
     */
    public void scoreBoard() {
        ScoreIndicator scoreIndicator = new ScoreIndicator(currentScore);
        scoreIndicator.addToGame(this);
    }
}
