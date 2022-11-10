package ui;

import gamestates.Gamestate;
import utilities.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilities.Constants.UI.Buttons.*;

// Class that stores buttons displayed in the menu
public class MenuButton {

    private int xPos, yPos, rowIndex, index;
    private int xOffsetCenter = B_WIDTH / 2;    // Variable to help display menu in center of game window
    private Gamestate state;
    private BufferedImage[] imgs;    // Array variable to store menu button images
    private boolean mouseOver, mousePressed;    // Variables to determine whether mouse is hovering over menu option or pressing it
    private Rectangle bounds;   // Variable to store hitbox of menu button

    public MenuButton(int xPos, int yPos, int rowIndex, Gamestate state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImgs();
        initBounds();
    }

    // Method to initialise menu button hitbox to allow mouse detection
    private void initBounds() {

        bounds = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
    }

    private void loadImgs() {

        imgs = new BufferedImage[3];
        BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.MENU_BUTTONS);

        for(int i = 0; i < imgs.length; i++) {

            imgs[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
        }
    }

    public void draw(Graphics g) {

        g.drawImage(imgs[index], xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT, null);
    }

    // Method to update menu based on user action
    // If mouse hovering over an option, display appropriate image
    // If user clicks on an option, display appropriate image
    public void update() {

        index = 0;
        if(mouseOver) {

            index = 1;
        }
        if(mousePressed) {

            index = 2;
        }
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    // Method to change game state when an option is pressed from the menu
    public void applyGameState() {

        Gamestate.state = state;
    }

    public void resetBools() {

        mouseOver = false;
        mousePressed = false;
    }
}
