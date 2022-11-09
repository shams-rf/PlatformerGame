package utilities;

import main.Game;

import java.awt.geom.Rectangle2D;

// Class that stores static helper methods for use by any class in the program
public class HelpMethods {

    // Method that takes a position as argument then checks if that position overlaps any tiles
    // Returns true if position doesn't overlap tiles, else return false
    public static boolean canMoveHere(float x, float y, float width, float height, int[][] levelData) {

        if(!isSolid(x, y, levelData)) {

            if(!isSolid(x + width, y + height, levelData)) {

                if(!isSolid(x + width, y, levelData)) {

                    if(!isSolid(x, y + height, levelData)) {

                        return true;
                    }
                }
            }
        }

        return false;
    }

    // Method that takes a position as argument then checks if that position is inside game window
    // Returns a boolean based on if the position is inside game window or not
    private static boolean isSolid(float x, float y, int[][] levelData) {

        if(x < 0 || x >= Game.GAME_WIDTH) {

            return true;
        }
        if(y < 0 || y >= Game.GAME_HEIGHT) {

            return true;
        }

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        int value = levelData[(int) yIndex][(int) xIndex];

        if(value >= 48 || value < 0 || value != 11) {

            return true;
        }
        return false;
    }

    // Method that allows entity to move next to a wall in the game without overlapping
    public static float getEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {

        int currentTile = (int)(hitbox.x / Game.TILES_SIZE);
        if(xSpeed > 0) {

            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int)(Game.TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        }
        else {

            return currentTile * Game.TILES_SIZE;
        }
    }
}
