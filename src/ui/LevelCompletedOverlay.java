package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilities.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilities.Constants.UI.UrmButtons.URM_SIZE;

public class LevelCompletedOverlay {

    private Playing playing;
    private UrmButton menu, next;
    private BufferedImage img;

    // Variables to store position & size of image
    private int bgX, bgY, bgW, bgH;

    public LevelCompletedOverlay(Playing playing) {
        this.playing = playing;
        initImg();
        initButtons();
    }

    // Method to load & position level completed menu buttons
    private void initButtons() {

        int menuX = (int) (330 * Game.SCALE);
        int nextX = (int) (445 * Game.SCALE);
        int y = (int) (195 * Game.SCALE);
        next = new UrmButton(nextX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
    }

    // Method to load & import image
    private void initImg() {

        img = LoadSave.getSpriteAtlas(LoadSave.COMPLETED_SPRITE);
        bgW = (int) (img.getWidth() * Game.SCALE);
        bgH = (int) (img.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (75 * Game.SCALE);
    }

    public void draw(Graphics g) {

        g.drawImage(img, bgX, bgY, bgW, bgH, null);
        next.draw(g);
        menu.draw(g);
    }

    public void update() {

        next.update();
        menu.update();
    }

    // Method that returns true if mouse is inside button when being moved or pressed
    private boolean isIn(UrmButton b, MouseEvent e) {

        return b.getBounds().contains(e.getX(), e.getY());
    }

    public void mouseMoved(MouseEvent e) {

        next.setMouseOver(false);
        menu.setMouseOver(false);

        if(isIn(menu, e)) {

            menu.setMouseOver(true);
        }
        else if(isIn(next, e)) {

            next.setMouseOver(true);
        }
    }

    public void mousePressed(MouseEvent e) {

        if(isIn(menu, e)) {

            menu.setMousePressed(true);
        }
        else if(isIn(next, e)) {

            next.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {

        if(isIn(menu, e)) {

            if(menu.isMousePressed()) {

                playing.resetAll();
                Gamestate.state = Gamestate.MENU;
            }
        }
        else if(isIn(next, e)) {

            if(next.isMousePressed()) {

                playing.loadNextLevel();
            }
        }

        menu.resetBools();
        next.resetBools();
    }
}
