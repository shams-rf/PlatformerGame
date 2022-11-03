package main;

public class Game {

    // Constructor to begin game by creating new GameWindow and GamePanel objects
    public Game() {
        GamePanel gamePanel = new GamePanel();
        GameWindow gameWindow = new GameWindow(gamePanel);
    }
}
