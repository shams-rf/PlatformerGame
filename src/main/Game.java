package main;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;

    // Variables to set frames per second & updates per second
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

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

    public void update() {

        gamePanel.updateGame();
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
}
