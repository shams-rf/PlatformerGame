package ui;

import utilities.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilities.Constants.UI.PauseButtons.*;

public class SoundButton extends PauseButton {

    private BufferedImage[][] soundImgs;

    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);

        loadSoundImgs();
    }

    // Method to load sound button images into the game
    // Create a temporary buffered image variable to store the button images
    // Store the sound button images in soundImgs 2D array
    private void loadSoundImgs() {

        BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.SOUND_BUTTONS);
        soundImgs = new BufferedImage[2][3];

        for(int i = 0; i < soundImgs.length; i++) {

            for(int j = 0; j < soundImgs[i].length; j++) {

                soundImgs[i][j] = temp.getSubimage(j * SOUND_SIZE_DEFAULT, i * SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
            }
        }
    }

    public void update() {


    }

    public void draw(Graphics g) {

        g.drawImage(soundImgs[0][0], x, y, width, height, null);
    }
}
