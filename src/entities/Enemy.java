package entities;

import main.Game;

import static utilities.Constants.Directions.*;
import static utilities.Constants.EnemyConstants.*;
import static utilities.HelpMethods.*;

// Class that stores properties & methods for enemies in the game
// Abstract class means objects cannot be created from this class.
public abstract class Enemy extends Entity {

    protected int enemyState; // Integer to specify if enemy is running, idle, attacking
    protected int enemyType;

    // Variables to store animation properties
    protected int animIndex, animTick;
    protected int animSpeed = 25;

    // Variables that handle enemy movement & falling
    protected boolean firstUpdate = true; // If it's the enemy's first update, make them fall to the ground from the air
    protected boolean inAir;
    protected float fallSpeed;
    protected float gravity = 0.04f * Game.SCALE;
    protected float walkSpeed = 0.5f * Game.SCALE;
    protected int walkDir = LEFT;

    // Variables for handling enemy observation & attacking actions
    protected int tileY;    // Y position of tile that enemy is standing on
    protected float attackDistance = Game.TILES_SIZE;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
    }

    // Method called when an enemy is first added into the game
    // Checks if enemy on floor. If not then sets inAir variable to true
    protected void firstUpdateCheck(int[][] levelData) {

        if(!isEntityOnFloor(hitbox, levelData)) {

            inAir = true;
        }

        firstUpdate = false;
    }

    // Method that allows enemy to fall from the air onto the ground
    protected void updateInAir(int[][] levelData) {

        if(canMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, levelData)) {

            hitbox.y += fallSpeed;
            fallSpeed += gravity;
        }
        else {

            inAir = false;
            hitbox.y = getEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
            tileY = (int) (hitbox.y / Game.TILES_SIZE);
        }
    }

    // Method that implements enemy move and patrol action
    protected void move(int[][] levelData) {

        float xSpeed = 0;

        if(walkDir == LEFT) {

            xSpeed = -walkSpeed;
        }
        else {

            xSpeed = walkSpeed;
        }

        if(canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {

            if(isFloor(hitbox, xSpeed, levelData)) {

                hitbox.x += xSpeed;
                return;
            }
        }

        changeWalkDir();
    }

    // Method to change enemy walking direction towards player
    // If player on left side of enemy, go left. If on right side, go right
    protected void turnTowardsPlayer(Player player) {

        if(player.hitbox.x > hitbox.x) {

            walkDir = RIGHT;
        }
        else {

            walkDir = LEFT;
        }
    }

    // Method that returns true if enemy can see the player
    // First check if enemy & player are on the same level on Y axis
    // Next, check if player is in range, then check if there's clear line of sight
    protected boolean canSeePlayer(int[][] levelData, Player player) {

        int playerTileY = (int) (player.getHitbox().y / Game.TILES_SIZE);
        if(playerTileY == tileY) {

            if(isPlayerInRange(player)) {

                if(isSightClear(levelData, hitbox, player.hitbox, tileY)) {

                    return true;
                }
            }
        }

        return false;
    }

    // Method to determine if player is in range of sight from enemy
    // Return true if player is in range, otherwise return false
    protected boolean isPlayerInRange(Player player) {

        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);    // Distance between player & enemy
        return absValue <= attackDistance * 5;
    }

    // Method to determine if player is in range for attack
    // Return true if player is in range, otherwise return false
    protected boolean isPlayerCloseForAttack(Player player) {

        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);    // Distance between player & enemy
        return absValue <= attackDistance;
    }

    // Method to change current enemy state with a specified state
    // Reset animTick & animIndex to prevent animation starting from middle
    protected void newState(int enemyState) {

        this.enemyState = enemyState;
        animTick = 0;
        animIndex = 0;
    }

    protected void updateAnimationTick() {

        animTick++;

        if(animTick >= animSpeed) {

            animTick = 0;
            animIndex++;

            if(animIndex >= getSpriteAmount(enemyType, enemyState)) {

                animIndex = 0;
                if(enemyState == ATTACK) {

                    enemyState = IDLE;
                }
            }
        }
    }

    // Method to change enemy walking direction from right to left & vice versa
    protected void changeWalkDir() {

        if(walkDir == LEFT) {

            walkDir = RIGHT;
        }
        else {

            walkDir = LEFT;
        }
    }

    public int getEnemyState() {
        return enemyState;
    }

    public int getAnimIndex() {
        return animIndex;
    }
}
