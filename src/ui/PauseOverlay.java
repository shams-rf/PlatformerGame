package ui;

import main.Game;
import utilities.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

// Class that stores properties & methods for the pause overlay in menu
public class PauseOverlay {

    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;

    public PauseOverlay() {

        loadBackground();
    }

    // Method to load pause menu background image & place it in center of window
    private void loadBackground() {

        backgroundImg = LoadSave.getSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
        bgW = (int) (backgroundImg.getWidth() * Game.SCALE);
        bgH = (int) (backgroundImg.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (25 * Game.SCALE);
    }

    public void update() {


    }

    // Method to draw & display loaded background image on game panel
    public void draw(Graphics g) {

        g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);
    }

    public void mouseDragged(MouseEvent e) {


    }

    public void mousePressed(MouseEvent e) {


    }

    public void mouseReleased(MouseEvent e) {


    }

    public void mouseMoved(MouseEvent e) {


    }
}
