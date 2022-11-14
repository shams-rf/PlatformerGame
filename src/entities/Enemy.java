package entities;

import static utilities.Constants.EnemyConstants.*;

// Class that stores properties & methods for enemies in the game
// Abstract class means objects cannot be created from this class.
public abstract class Enemy extends Entity {

    private int enemyState; // Integer to specify if enemy is running, idle, attacking
    private int enemyType;

    // Variables to store animation properties
    private int animIndex, animTick;
    private int animSpeed = 25;

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

    public void update() {

        updateAnimationTick();
    }

    public int getEnemyState() {
        return enemyState;
    }

    public int getAnimIndex() {
        return animIndex;
    }
}
