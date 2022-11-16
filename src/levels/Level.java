package levels;

import entities.Crabby;
import main.Game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilities.HelpMethods.*;

// Class that holds data for each level
public class Level {

    private BufferedImage img;
    private int[][] levelData;
    private ArrayList<Crabby> crabs;

    // Variables that store offset for each level
    private int levelTilesWide; // Store entire width of level
    private int maxTilesOffset;  // (entire level width - visible tiles)
    private int maxLevelOffsetX;

    public Level(BufferedImage img) {
        this.img = img;
        createLevelData();
        createEnemies();
        calcLevelOffsets();
    }

    private void calcLevelOffsets() {

        levelTilesWide = img.getWidth();
        maxTilesOffset = levelTilesWide - Game.TILES_IN_WIDTH;
        maxLevelOffsetX = Game.TILES_SIZE * maxTilesOffset;
    }

    private void createEnemies() {

        crabs = GetCrabs(img);
    }

    private void createLevelData() {

        levelData = GetLevelData(img);
    }

    public int getSpriteIndex(int x, int y) {

        return levelData[y][x];
    }

    public int[][] getLevelData() {
        return levelData;
    }

    public int getLevelOffset() {

        return maxLevelOffsetX;
    }

    public ArrayList<Crabby> getCrabs() {
        return crabs;
    }
}
