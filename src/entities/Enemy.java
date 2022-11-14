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

    protected void updateAnimationTick() {

        animTick++;

        if(animTick >= animSpeed) {

            animTick = 0;
            animIndex++;

            if(animIndex >= getSpriteAmount(enemyType, enemyState)) {

                animIndex = 0;
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
