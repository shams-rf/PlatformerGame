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

            firstUpdateCheck(levelData);
        }

        if(inAir) {

            updateInAir(levelData);
        }
        else {

            switch(enemyState) {

                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    move(levelData);
                    break;
            }
        }
    }
}
