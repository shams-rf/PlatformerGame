package entities;

import gamestates.Playing;
import levels.Level;
import utilities.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilities.Constants.EnemyConstants.*;

// Class that handles enemy actions such as movement, attacking, patrolling, etc.
public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] crabbyArr;
    private ArrayList<Crabby> crabbies = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
    }

    public void loadEnemies(Level level) {

        crabbies = level.getCrabs();
    }

    public void update(int[][] levelData, Player player) {

        boolean isAnyActive = false;

        for(Crabby c: crabbies) {

            if(c.isActive()) {

                c.update(levelData, player);
                isAnyActive = true;
            }
        }

        if(!isAnyActive) {

            playing.setLevelCompleted(true);
        }
    }

    public void draw(Graphics g, int xLevelOffset) {

        drawCrabs(g, xLevelOffset);
    }

    private void drawCrabs(Graphics g, int xLevelOffset) {

        for(Crabby c: crabbies) {

            if(c.isActive()) {

                g.drawImage(crabbyArr[c.getEnemyState()][c.getAnimIndex()],
                        (int) c.getHitbox().x - xLevelOffset - CRABBY_DRAWOFFSET_X + c.flipX(),
                        (int) c.getHitbox().y - CRABBY_DRAWOFFSET_Y,
                        CRABBY_WIDTH * c.flipW(), CRABBY_HEIGHT, null);
            }
        }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {

        for(Crabby c : crabbies) {

            if(c.isActive()) {

                if(attackBox.intersects(c.getHitbox())) {

                    c.hurt(10);
                    return;
                }
            }
        }
    }

    private void loadEnemyImgs() {

        crabbyArr = new BufferedImage[5][9];
        BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.CRABBY_SPRITE);

        for(int i = 0; i < crabbyArr.length; i++) {

            for(int j = 0; j < crabbyArr[i].length; j++) {

                crabbyArr[i][j] = temp.getSubimage(j * CRABBY_WIDTH_DEFAULT, i * CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
            }
        }
    }

    // Method to reset all enemy variables to their original value
    public void resetAllEnemies() {

        for(Crabby c : crabbies) {

            c.resetEnemy();
        }
    }
}
