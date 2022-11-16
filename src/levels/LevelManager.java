package levels;

import gamestates.Gamestate;
import main.Game;
import utilities.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

// Class that manages & displays different levels
public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int levelIndex = 0;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprite();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    // Method to load next level when current level completed & next button pressed
    // Increment level index
    // Check if there is a next level
    public void loadNextLevel() {

        levelIndex++;

        if(levelIndex >= levels.size()) {

            levelIndex = 0;
            System.out.println("No more levels!");
            Gamestate.state = Gamestate.MENU;
        }

        Level newLevel = levels.get(levelIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLevelData(newLevel.getLevelData());
        game.getPlaying().setMaxLevelOffsetX(newLevel.getLevelOffset());
    }

    private void buildAllLevels() {

        BufferedImage[] allLevels = LoadSave.getAllLevels();
        for(BufferedImage img : allLevels) {

            levels.add(new Level(img));
        }
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

            for(int i = 0; i < levels.get(levelIndex).getLevelData()[0].length; i++) {

                int index = levels.get(levelIndex).getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], Game.TILES_SIZE * i - levelOffset, Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE,null);
            }
        }
    }

    public void update() {


    }

    // Method to return the current game level
    public Level getCurrentLevel() {

        return levels.get(levelIndex);
    }

    public int getAmountOfLevels() {

        return levels.size();
    }
}
