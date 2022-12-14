package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilities.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilities.Constants.UI.PauseButtons.*;
import static utilities.Constants.UI.UrmButtons.*;
import static utilities.Constants.UI.VolumeButtons.*;

// Class that stores properties & methods for the pause overlay in menu
public class PauseOverlay {

    private Playing playing;
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;
    private SoundButton musicButton, sfxButton;
    private UrmButton menuB, replayB, unpauseB;
    private VolumeButton volumeButton;

    public PauseOverlay(Playing playing) {

        this.playing = playing;

        loadBackground();
        createSoundButtons();
        createUrmButtons();
        createVolumeButton();
    }

    // Load volumn button & slider in its correct position in pause menu
    private void createVolumeButton() {

        int vX = (int) (309 * Game.SCALE);
        int vY = (int) (278 * Game.SCALE);
        volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    // Load each URM button in its correct position in pause menu
    private void createUrmButtons() {

        int menuX = (int) (313 * Game.SCALE);
        int replayX = (int) (387 * Game.SCALE);
        int unpauseX = (int) (462 * Game.SCALE);
        int bY = (int) (325 * Game.SCALE);

        unpauseB = new UrmButton(unpauseX, bY, URM_SIZE, URM_SIZE, 0);
        replayB = new UrmButton(replayX, bY, URM_SIZE, URM_SIZE, 1);
        menuB = new UrmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);
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

        unpauseB.update();
        replayB.update();
        menuB.update();

        volumeButton.update();
    }

    // Method to draw & display loaded background image & loaded sound buttons on game panel
    public void draw(Graphics g) {

        // Background
        g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);

        // Sound buttons
        musicButton.draw(g);
        sfxButton.draw(g);

        // Urm buttons
        unpauseB.draw(g);
        replayB.draw(g);
        menuB.draw(g);

        // Volume slider
        volumeButton.draw(g);
    }

    public void mouseDragged(MouseEvent e) {

        if(volumeButton.isMousePressed()) {

            volumeButton.changeX(e.getX());
        }
    }

    public void mousePressed(MouseEvent e) {

        if(isIn(e, musicButton)) {

            musicButton.setMousePressed(true);
        }
        else if(isIn(e, sfxButton)) {

            sfxButton.setMousePressed(true);
        }
        else if(isIn(e, menuB)) {

            menuB.setMousePressed(true);
        }
        else if(isIn(e, replayB)) {

            replayB.setMousePressed(true);
        }
        else if(isIn(e, unpauseB)) {

            unpauseB.setMousePressed(true);
        }
        else if(isIn(e, volumeButton)) {

            volumeButton.setMousePressed(true);
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
        else if(isIn(e, menuB)) {

            if(menuB.isMousePressed()) {

                Gamestate.state = Gamestate.MENU;
                playing.unpauseGame();
            }
        }
        else if(isIn(e, replayB)) {

            if(replayB.isMousePressed()) {

                playing.resetAll();
                playing.unpauseGame();
            }
        }
        else if(isIn(e, unpauseB)) {

            if(unpauseB.isMousePressed()) {

                playing.unpauseGame();
            }
        }

        musicButton.resetBools();
        sfxButton.resetBools();
        menuB.resetBools();
        replayB.resetBools();
        unpauseB.resetBools();
        volumeButton.resetBools();
    }

    public void mouseMoved(MouseEvent e) {

        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);
        volumeButton.setMouseOver(false);

        if(isIn(e, musicButton)) {

            musicButton.setMouseOver(true);
        }
        else if(isIn(e, sfxButton)) {

            sfxButton.setMouseOver(true);
        }
        else if(isIn(e, menuB)) {

            menuB.setMouseOver(true);
        }
        else if(isIn(e, replayB)) {

            replayB.setMouseOver(true);
        }
        else if(isIn(e, unpauseB)) {

            unpauseB.setMouseOver(true);
        }
        else if(isIn(e, volumeButton)) {

            volumeButton.setMouseOver(true);
        }
    }

    // Method that returns true if mouse is inside button's hitbox in pause menun, otherwise returns false
    private boolean isIn(MouseEvent e, PauseButton b) {

        return b.getBounds().contains(e.getX(), e.getY());
    }
}
