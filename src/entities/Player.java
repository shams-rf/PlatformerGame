package entities;

import utilities.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilities.Constants.Directions.*;
import static utilities.Constants.Directions.DOWN;
import static utilities.Constants.PlayerConstants.*;

// Player class that extends the entity class and inherits its properties
public class Player extends Entity{

    private BufferedImage[][] animations;

    // Variables to store animation properties
    private int animTick, animIndex, animSpeed = 15;

    // Variables to store player actions & directions
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down;
    private float playerSpeed = 2.0f;

    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
    }

    // Method to implement logic for game and update animations
    public void update() {

        updatePos();
        updateAnimTick();
        setAnimation();
    }

    // Method to render & draw player
    public void render(Graphics g) {

        g.drawImage(animations[playerAction][animIndex], (int)x, (int)y, 128, 80, null);
    }

    // Method to implement animation by cycling through idle animation array at a given speed
    public void updateAnimTick() {

        animTick++;

        if(animTick >= animSpeed) {

            animTick = 0;
            animIndex++;

            if(animIndex >= getSpriteAmount(playerAction)) {

                animIndex = 0;
                attacking = false;
            }
        }
    }

    // Method to determine what type of animation to display
    // If player is moving, set animation to running, else set animation to idle
    private void setAnimation() {

        int startAnim = playerAction;

        if(moving) {

            playerAction = RUNNING;
        }
        else {

            playerAction = IDLE;
        }

        if(attacking) {

            playerAction = ATTACK_1;
        }

        // If animation isn't equal to the current player action (a new animation has been called)
        // Reset the animation tick & animation index to display the full animation
        if(startAnim != playerAction) {

            resetAnimTick();
        }
    }

    // Method to reset animation tick & index
    private void resetAnimTick() {

        animIndex = 0;
        animTick = 0;
    }

    // Method to move player sprite by changing x & y values depending on direction
    // Use if statements to determine which direction player is moving
    // Set moving variable to false by default, set to true if player is moving to show running animation
    private void updatePos() {

        moving = false;

        if(left && !right) {

            x-=playerSpeed;
            moving = true;
        }
        else if(right && !left) {

            x+=playerSpeed;
            moving = true;
        }

        if(up && !down) {

            y-=playerSpeed;
            moving = true;
        }
        else if(down && !up) {

            y+=playerSpeed;
            moving = true;
        }
    }

    // Method to load and display sprite animations by creating array to store sub images then using a for loop
    private void loadAnimations() {

        // Load player sprite image
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[9][6];

        for(int i = 0; i < animations.length; i++) {
            for(int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(j*64, i*40, 64, 40);
            }
        }
    }

    // Method to reset booleans that store player directions
    public void resetDirBooleans() {

        left = false;
        up = false;
        right = false;
        down = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
}
