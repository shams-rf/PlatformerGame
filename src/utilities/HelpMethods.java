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

        int maxWidth = levelData[0].length * Game.TILES_SIZE;
        if(x < 0 || x >= maxWidth) {

            return true;
        }
        if(y < 0 || y >= Game.GAME_HEIGHT) {

            return true;
        }

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        return isTileSolid((int) xIndex, (int) yIndex, levelData);
    }

    public static boolean isTileSolid(int xTile, int yTile, int[][] levelData) {

        int value = levelData[yTile][xTile];

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

    // Method that allows entity to move right next to a roof or right above the floor in the gaem
    public static float getEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {

        int currentTile = (int)(hitbox.y / Game.TILES_SIZE);

        if(airSpeed > 0) {

            // Falling - Touching Floor
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int)(Game.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        }
        else {

            // Jumping
            return currentTile * Game.TILES_SIZE;
        }
    }

    // Method to check if bottom left & bottom right corners of entity hitbox are touching the floor
    // If both corners aren't touching the floor, entity is in air & method returns false, else method returns true
    public static boolean isEntityOnFloor(Rectangle2D.Float hitbox, int[][] levelData) {

        if(!isSolid(hitbox.x, hitbox.y + hitbox.height + 1, levelData)) {

            if(!isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, levelData)) {

                return false;
            }
        }

        return true;
    }

    // Method that returns true if sprite is on the floor & not in the air
    public static boolean isFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] levelData) {

        return isSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, levelData);
    }

    public static boolean isAllTilesWalkable(int xStart, int xEnd, int y, int[][] levelData) {

        for(int i = 0; i < xEnd - xStart; i++) {

            if(isTileSolid(xStart + i, y, levelData)) {

                return false;
            }

            if(!isTileSolid(xStart + i, y + 1, levelData)) {

                return false;
            }
        }

        return true;
    }

    //Method to determine whether enemy has clear sight of player or not
    // Check each tile between enemy & player to see if it's a solid tile
    // If all tiles between enemy & player are solid, return true. Otherwise, return false
    public static boolean isSightClear(int[][] levelData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int tileY) {

        int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);

        if(firstXTile > secondXTile) {

            return isAllTilesWalkable(secondXTile, firstXTile, tileY, levelData);
        }
        else {

            return isAllTilesWalkable(firstXTile, secondXTile, tileY, levelData);
        }
    }
}
