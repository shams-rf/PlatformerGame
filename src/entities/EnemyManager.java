package entities;

import gamestates.Playing;
import utilities.LoadSave;

import java.awt.*;
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
        addEnemies();
    }

    private void addEnemies() {

        crabbies = LoadSave.getCrabs();
        System.out.println("Size of crabs = " + crabbies.size());
    }

    public void update(int[][] levelData, Player player) {

        for(Crabby c: crabbies) {

            c.update(levelData, player);
        }
    }

    public void draw(Graphics g, int xLevelOffset) {

        drawCrabs(g, xLevelOffset);
    }

    private void drawCrabs(Graphics g, int xLevelOffset) {

        for(Crabby c: crabbies) {

            g.drawImage(crabbyArr[c.getEnemyState()][c.getAnimIndex()], (int) c.getHitbox().x - xLevelOffset - CRABBY_DRAWOFFSET_X, (int) c.getHitbox().y - CRABBY_DRAWOFFSET_Y, CRABBY_WIDTH, CRABBY_HEIGHT, null);
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
}
