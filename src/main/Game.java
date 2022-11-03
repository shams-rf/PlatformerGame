package main;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;

    // Constructor to begin game by creating new GameWindow and GamePanel objects
    // Start the game loop
    public Game() {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();   // Request input focus for game panel
        startGameLoop();
    }

    // Method to initialise game loop thread
    private void startGameLoop() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        // Variable to store how long each frame should last
        double timePerFrame = 1000000000.0 / FPS_SET;
        long lastFrame = System.nanoTime();
        long now = System.nanoTime();

        int frames = 0;
        long lastCheck = System.currentTimeMillis();

        while(true) {

            now = System.nanoTime();

            // Check if now minus last frame is longer than duration a frame should last
            // If yes then refresh screen
            if(now - lastFrame >= timePerFrame) {

                gamePanel.repaint();
                lastFrame = now;
                frames++;
            }

            // Create FPS counter in console
            if(System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
    }
}
