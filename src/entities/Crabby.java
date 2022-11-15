package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilities.Constants.Directions.LEFT;
import static utilities.Constants.Directions.RIGHT;
import static utilities.Constants.EnemyConstants.*;
import static utilities.HelpMethods.*;

// Crabby class that extends & inherits from Enemy class
public class Crabby extends Enemy {

    // Attack box
    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;

    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitbox(x, y, (int) (22 * Game.SCALE), (int) (19 * Game.SCALE));
        initAttackBox();
    }

    private void initAttackBox() {

        attackBox = new Rectangle2D.Float(x, y, (int) (82 * Game.SCALE), (int) (19 * Game.SCALE));
        attackBoxOffsetX = (int) (Game.SCALE * 30);
    }

    public void update(int[][] levelData, Player player) {

        updateBehaviour(levelData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    // Method to update attack box to allow it to move with the crab
    // Attack box correlates to the enemy hitbox but with a certain offset
    private void updateAttackBox() {

        attackBox.x = hitbox.x - attackBoxOffsetX;
        attackBox.y = hitbox.y;
    }

    // Method to update behaviour of enemy
    // If enemy just spawned (first update), then make them fall to the ground from the air
    // Otherwise, enemy patrols in a set pattern until it sees a player
    // If enemy sees player, it turns & walk towards the player
    private void updateBehaviour(int[][] levelData, Player player) {

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
                    if(canSeePlayer(levelData, player)) {

                        turnTowardsPlayer(player);
                    }
                    if(isPlayerCloseForAttack(player)) {

                        newState(ATTACK);
                    }

                    move(levelData);
                    break;
                case ATTACK:
                    if(animIndex == 0) {

                        attackChecked = false;
                    }

                    if(animIndex == 3 && !attackChecked) {
                        checkEnemyHit(attackBox, player);
                    }
                    break;
                case HIT:
                    break;
            }
        }
    }

    public void drawAttackBox(Graphics g, int xLevelOffset) {

        g.setColor(Color.red);
        g.drawRect((int) (attackBox.x - xLevelOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    public int flipX() {

        if(walkDir == RIGHT) {

            return width;
        }
        else {

            return 0;
        }
    }

    public int flipW() {

        if(walkDir == RIGHT) {

            return -1;
        }
        else {

            return 1;
        }
    }
}
