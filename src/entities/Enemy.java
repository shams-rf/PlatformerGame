package entities;

import main.Game;

import static utilities.Constants.Directions.*;
import static utilities.Constants.EnemyConstants.*;
import static utilities.HelpMethods.*;

// Class that stores properties & methods for enemies in the game
// Abstract class means objects cannot be created from this class.
public abstract class Enemy extends Entity {

    private int enemyState; // Integer to specify if enemy is running, idle, attacking
    private int enemyType;

    // Variables to store animation properties
    private int animIndex, animTick;
    private int animSpeed = 25;

    // Variables that handle enemy movement & falling
    private boolean firstUpdate = true; // If it's the enemy's first update, make them fall to the ground from the air
    private boolean inAir;
    private float fallSpeed;
    private float gravity = 0.04f * Game.SCALE;
    private float walkSpeed = 0.5f * Game.SCALE;
    private int walkDir = LEFT;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
    }

    private void updateAnimationTick() {

        animTick++;

        if(animTick >= animSpeed) {

            animTick = 0;
            animIndex++;

            if(animIndex >= getSpriteAmount(enemyType, enemyState)) {

                animIndex = 0;
            }
        }
    }

    public void update(int[][] levelData) {

        updateMove(levelData);
        updateAnimationTick();
    }

    // Method to update movement of enemy
    // If enemy just spawned (first update), then make them fall to the ground from the air
    // Otherwise, enemy patrols in a set pattern until it sees a player
    private void updateMove(int[][] levelData) {

        if(firstUpdate) {

            if(!isEntityOnFloor(hitbox, levelData)) {

                inAir = true;
            }

            firstUpdate = false;
        }

        if(inAir) {

            if(canMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, levelData)) {

                hitbox.y += fallSpeed;
                fallSpeed += gravity;
            }
            else {

                inAir = false;
                hitbox.y = getEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
            }
        }
        else {

            switch(enemyState) {

                case IDLE:
                    enemyState = RUNNING;
                    break;
                case RUNNING:
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

                    break;
            }
        }
    }

    // Method to change enemy walking direction from right to left & vice versa
    private void changeWalkDir() {

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
