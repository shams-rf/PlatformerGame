package main;

import entities.Player;
import levels.LevelManager;

import java.awt.*;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;

    // Variables to set frames per second & updates per second
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    // Variables to store sprites
    private Player player;
    private LevelManager levelManager;

    // Variables to store sizes for game
    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.5f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    // Constructor to begin game by creating new GameWindow and GamePanel objects
    // Start the game loop
    public Game() {
        initClasses();  // Initialise player, enemy classes, etc.
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();   // Request input focus for game panel
        startGameLoop();
    }

    // Method to initialise sprites classes
    private void initClasses() {

        levelManager = new LevelManager(this);
        player = new Player(200, 200, (int)(64 * SCALE), (int)(40 * SCALE));
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
    }

    // Method to initialise game loop thread
    private void startGameLoop() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {

        player.update();
        levelManager.update();
    }

    public void render(Graphics g) {

        levelManager.draw(g);
        player.render(g);
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET;   // Variable to store how long each frame should last
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while(true) {

            long currentTime = System.nanoTime();

            // Statement where deltaU will reach 1.0 when duration since last update is equal or more than time per update
            deltaU += (currentTime - previousTime) / timePerUpdate;

            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            // Ensure that time usually wasted is instead stored in deltaU which allows updates to occur sooner
            // Example: If deltaU reaches 1.1, 1 is removed and 0.1 is stored for next iteration instead of being ignored
            if(deltaU >= 1) {

                update();
                updates++;
                deltaU--;
            }

            if(deltaF >= 1) {

                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            // Create FPS counter in console
            if(System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    // Method to reset booleans that store player directions in player class
    public void windowFocusLost() {

        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }
}
