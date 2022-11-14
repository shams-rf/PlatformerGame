package gamestates;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.PauseOverlay;
import utilities.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import static utilities.Constants.Environment.*;

public class Playing extends State implements Statemethods {

    // Variables to store sprites
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private PauseOverlay pauseOverlay;
    private boolean paused = false; // Toggle to show paused screen

    // Variables to allow shifting of level once player passes border
    private int xLevelOffset;   // Offset added or removed to draw level further to left or right
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int levelTilesWide = LoadSave.getLevelData()[0].length; // Store entire width of level
    private int maxTilesOffset = levelTilesWide - Game.TILES_IN_WIDTH;  // (entire level width - visible tiles)
    private int maxLevelOffsetX = maxTilesOffset * Game.TILES_SIZE;

    // Variables to store images for environment
    private BufferedImage backgroundImg, bigCloud, smallCloud;
    private int[] smallCloudsPos;
    private Random rnd = new Random();

    public Playing(Game game) {
        super(game);
        initClasses();

        backgroundImg = LoadSave.getSpriteAtlas(LoadSave.PLAYING_BG_IMG);
        bigCloud = LoadSave.getSpriteAtlas(LoadSave.BIG_CLOUDS);
        smallCloud = LoadSave.getSpriteAtlas(LoadSave.SMALL_CLOUDS);
        smallCloudsPos = new int[8];
        for(int i = 0; i < smallCloudsPos.length; i++) {

            smallCloudsPos[i] = (int) (90 * Game.SCALE) + rnd.nextInt((int) (100 * Game.SCALE));
        }
    }

    // Method to initialise sprites classes
    private void initClasses() {

        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        player = new Player(200, 200, (int)(64 * Game.SCALE), (int)(40 * Game.SCALE));
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay(this);
    }

    @Override
    public void update() {

        if(!paused) {

            levelManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData());
            checkCloseToBorder();
        }
        else {

            pauseOverlay.update();
        }
    }

    // Method that checks if player has passed any of the borders
    // If yes, then shift level to the right or left appropriately
    private void checkCloseToBorder() {

        int playerX = (int) (player.getHitbox().x);
        int diff = playerX - xLevelOffset;

        if(diff > rightBorder) {

            xLevelOffset += diff - rightBorder;
        }
        else if(diff < leftBorder) {

            xLevelOffset += diff - leftBorder;
        }

        if(xLevelOffset > maxLevelOffsetX) {

            xLevelOffset = maxLevelOffsetX;
        }
        else if(xLevelOffset < 0) {

            xLevelOffset = 0;
        }
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        drawClouds(g);

        levelManager.draw(g, xLevelOffset);
        player.render(g, xLevelOffset);
        enemyManager.draw(g, xLevelOffset);

        if(paused) {

            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        }
    }

    // Method to draw clouds in background game environment
    private void drawClouds(Graphics g) {

        for(int i = 0; i < 3; i++) {

            g.drawImage(bigCloud,i * BIG_CLOUD_WIDTH - (int) (xLevelOffset * 0.3), (int) (204 * Game.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
        }

        for(int i = 0; i < smallCloudsPos.length; i++) {

            g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int) (xLevelOffset * 0.7), smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
        }
    }

    public void mouseDragged(MouseEvent e) {

        if(paused) {

            pauseOverlay.mouseDragged(e);
        }
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
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
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

    // Method to unpause game & hide pause menu
    public void unpauseGame() {

        paused = false;
    }

    // Method to reset booleans that store player directions in player class
    public void windowFocusLost() {

        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }
}
