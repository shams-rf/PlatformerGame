package entities;

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
    private int playerDir = -1;
    private boolean moving = false;

    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
    }

    // Method to implement logic for game and update animations
    public void update() {

        updateAnimTick();
        setAnimation();
        updatePos();
    }

    // Method to render & draw player
    public void render(Graphics g) {

        g.drawImage(animations[playerAction][animIndex], (int)x, (int)y, 128, 80, null);
    }

    // Method to set player direction based on keyboard input
    public void setDirection(int direction) {

        this.playerDir = direction;
        moving = true;
    }

    // Method to set player state as moving or not moving
    public void setMoving(boolean moving) {

        this.moving = moving;
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

    // Method to determine what type of animation to display
    // If player is moving, set animation to running, else set animation to idle
    private void setAnimation() {

        if(moving) {

            playerAction = RUNNING;
        }
        else {

            playerAction = IDLE;
        }
    }

    // Method to move player sprite by changing xDelta & yDelta values depending on direction
    private void updatePos() {

        if(moving) {

            switch (playerDir) {

                case LEFT:
                    x-=5;
                    break;
                case UP:
                    y-=5;
                    break;
                case RIGHT:
                    x+=5;
                    break;
                case DOWN:
                    y+=5;
                    break;
            }
        }
    }

    // Method to load and display sprite animations by creating array to store sub images then using a for loop
    private void loadAnimations() {

        // Method to import image from resources folder into game
        // Use try catch block to read image input stream
        // Use another try catch block to close the input stream to avoid errors
        InputStream is = getClass().getResourceAsStream("/player_sprites.png");

        try {
            BufferedImage img = ImageIO.read(is);

            animations = new BufferedImage[9][6];

            for(int i = 0; i < animations.length; i++) {
                for(int j = 0; j < animations[i].length; j++) {
                    animations[i][j] = img.getSubimage(j*64, i*40, 64, 40);
                }
            }
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
}
