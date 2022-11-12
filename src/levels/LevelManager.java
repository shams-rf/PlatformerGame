package levels;

import main.Game;
import utilities.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

// Class that manages & displays different levels
public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite;
    private Level levelOne;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprite();
        levelOne = new Level(LoadSave.getLevelData());
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

    public void draw(Graphics g, int levelOffset) {

        for(int j = 0; j < Game.TILES_IN_HEIGHT; j++) {

            for(int i = 0; i < levelOne.getLevelData()[0].length; i++) {

                int index = levelOne.getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], Game.TILES_SIZE * i - levelOffset, Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE,null);
            }
        }
    }

    public void update() {


    }

    // Method to return the current game level
    public Level getCurrentLevel() {

        return levelOne;
    }
}
