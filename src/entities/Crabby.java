package entities;

import main.Game;

import static utilities.Constants.Directions.LEFT;
import static utilities.Constants.EnemyConstants.*;
import static utilities.HelpMethods.*;

// Crabby class that extends & inherits from Enemy class
public class Crabby extends Enemy {

    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitbox(x, y, (int) (22 * Game.SCALE), (int) (19 * Game.SCALE));
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
}
