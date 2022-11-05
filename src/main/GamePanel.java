package main;

import input.KeyboardInput;
import input.MouseInput;
import utilities.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilities.Constants.PlayerConstants.*;

// Game panel class allows drawing and painting inside game frame
public class GamePanel extends JPanel {

    // Variables that increase or decrease rectangle position depending on user input
    private float xDelta = 100;
    private float yDelta = 100;

    // Variables that help display frames per second in console
    private int frames = 0;
    private long lastCheck = 0;

    // Variables to store images
    private BufferedImage img;
    private BufferedImage[][] animations;

    // Variables to store animation properties
    private int animTick, animIndex, animSpeed = 15;

    // Variable to store player actions
    private int playerAction = IDLE;

    // Constructor where input is taken care of
    public GamePanel() {

        MouseInput mouseInput = new MouseInput(this);

        importImg();
        loadAnimations();

        setPanelSize();
        addKeyListener(new KeyboardInput(this));
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
    }

    // Method to load and display sprite animations by creating array to store sub images then using a for loop
    private void loadAnimations() {

        animations = new BufferedImage[9][6];

        for(int i = 0; i < animations.length; i++) {
            for(int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(j*64, i*40, 64, 40);
            }
        }
    }

    // Method to import image from resources folder into game
    // Use try catch block to read image input stream
    // Use another try catch block to close the input stream to avoid errors
    private void importImg() {

        InputStream is = getClass().getResourceAsStream("/player_sprites.png");

        try {
            img = ImageIO.read(is);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                is.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to set size for game panel inside game window
    private void setPanelSize() {

        Dimension size = new Dimension(1280, 800);
        setPreferredSize(size);
    }

    // Method to change value of xDelta based on user input
    public void changeXDelta(int value) {

        this.xDelta += value;
    }

    // Method to change value of yDelta based on user input
    public void changeYDelta(int value) {

        this.yDelta += value;
    }

    // Method to draw rectangle at a given position
    public void setRectPos(int x, int y) {

        this.xDelta = x;
        this.yDelta = y;
    }

    // Method to implement animation by cycling through idle animation array at a given speed
    public void updateAnimTick() {

        animTick++;

        if(animTick >= animSpeed) {

            animTick = 0;
            animIndex++;

            if(animIndex >= getSpriteAmount(playerAction)) {

                animIndex = 0;
            }
        }
    }

    // Method to allow painting on game panel
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        updateAnimTick();

        g.drawImage(animations[playerAction][animIndex], (int)xDelta, (int)yDelta, 128, 80, null);
    }
}
