package levels;

// Class that holds data for the level
public class Level {

    private int[][] levelData;

    public Level(int[][] levelData) {
        this.levelData = levelData;
    }

    public int getSpriteIndex(int x, int y) {

        return levelData[y][x];
    }
}
