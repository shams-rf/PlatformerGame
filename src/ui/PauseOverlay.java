package ui;

import main.Game;
import utilities.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilities.Constants.UI.PauseButtons.*;

// Class that stores properties & methods for the pause overlay in menu
public class PauseOverlay {

    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;
    private SoundButton musicButton, sfxButton;

    public PauseOverlay() {

        loadBackground();
        createSoundButtons();
    }

    // Load each sound button in its correct position in pause menu
    private void createSoundButtons() {

        int soundX = (int) (450 * Game.SCALE);
        int musicY = (int) (140 * Game.SCALE);
        int sfxY = (int) (186 * Game.SCALE);

        musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
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

        musicButton.update();
        sfxButton.update();
    }

    // Method to draw & display loaded background image & loaded sound buttons on game panel
    public void draw(Graphics g) {

        // Background
        g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);

        // Sound buttons
        musicButton.draw(g);
        sfxButton.draw(g);
    }

    public void mouseDragged(MouseEvent e) {


    }

    public void mousePressed(MouseEvent e) {

        if(isIn(e, musicButton)) {

            musicButton.setMousePressed(true);
        }
        else if(isIn(e, sfxButton)) {

            sfxButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {

        if(isIn(e, musicButton)) {

            if(musicButton.isMousePressed()) {

                musicButton.setMuted(!musicButton.isMuted());
            }
        }
        else if(isIn(e, sfxButton)) {

            if(sfxButton.isMousePressed()) {

                sfxButton.setMuted(!sfxButton.isMuted());
            }
        }

        musicButton.resetBools();
        sfxButton.resetBools();
    }

    public void mouseMoved(MouseEvent e) {

        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);

        if(isIn(e, musicButton)) {

            musicButton.setMouseOver(true);
        }
        else if(isIn(e, sfxButton)) {

            sfxButton.setMouseOver(true);
        }
    }

    // Method that returns true if mouse is inside button's hitbox in pause menun, otherwise returns false
    private boolean isIn(MouseEvent e, PauseButton b) {

        return b.getBounds().contains(e.getX(), e.getY());
    }
}
