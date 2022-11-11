package gamestates;

import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.PauseOverlay;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Playing extends State implements Statemethods {

    // Variables to store sprites
    private Player player;
    private LevelManager levelManager;
    private PauseOverlay pauseOverlay;
    private boolean paused = true; // Toggle to show paused screen

    public Playing(Game game) {
        super(game);
        initClasses();
    }

    // Method to initialise sprites classes
    private void initClasses() {

        levelManager = new LevelManager(game);
        player = new Player(200, 200, (int)(64 * Game.SCALE), (int)(40 * Game.SCALE));
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay();
    }

    @Override
    public void update() {

        levelManager.update();
        player.update();

        pauseOverlay.update();
    }

    @Override
    public void draw(Graphics g) {

        levelManager.draw(g);
        player.render(g);

        pauseOverlay.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        // If mouse button is pressed, display attacking animation
        if(e.getButton() == MouseEvent.BUTTON1) {

            player.setAttacking(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if(paused) {

            pauseOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        pauseOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        pauseOverlay.mouseMoved(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        // Switch statement to detect if W, A, S or D are pressed
        // When one of these buttons is pressed, call appropriate method from game panel to set direction
        switch(e.getKeyCode()) {

            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
            case KeyEvent.VK_BACK_SPACE:
                Gamestate.state = Gamestate.MENU;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        // Switch statement to detect if W, A, S or D are released
        // When one of these buttons is released, set moving state of player to false
        switch(e.getKeyCode()) {

            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;
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
