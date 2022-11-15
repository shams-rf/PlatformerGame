package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

// Class that stores properties & methods for game over screen displayed when player dies
public class GameOverOverlay {

    private Playing playing;

    public GameOverOverlay(Playing playing) {
        this.playing = playing;
    }

    // Method to draw game over overlay
    // Make screen darker by adding transparent black foreground
    // Display game over message
    public void draw(Graphics g) {

        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.setColor(Color.white);
        g.drawString("Game Over", Game.GAME_WIDTH / 2, 150);
        g.drawString("Press esc to enter Main Menu!", Game.GAME_WIDTH / 2, 300);
    }

    // Method to reset game & return player to main menu when esc key is pressed
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {

            playing.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
    }
}
