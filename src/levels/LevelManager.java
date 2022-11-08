package levels;

import main.Game;
import utilities.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite;

    public LevelManager(Game game) {
        this.game = game;
//        levelSprite = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);
        importOutsideSprite();
    }

    // Break level sprite up into sections & store it in an array
    private void importOutsideSprite() {

        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for(int i = 0; i < 4; i++) {

            for(int j = 0; j < 12; j++) {

                int index = i * 12 + j;
                levelSprite[index] = img.getSubimage(j * 32, i * 32, 32, 32);
            }
        }
    }

    public void draw(Graphics g) {

        g.drawImage(levelSprite[2], 0, 0, null);
    }

    public void update() {


    }
}
