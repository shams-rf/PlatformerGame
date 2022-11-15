package entities;

import main.Game;
import utilities.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilities.Constants.Directions.*;
import static utilities.Constants.Directions.DOWN;
import static utilities.Constants.PlayerConstants.*;
import static utilities.HelpMethods.*;

// Player class that extends the entity class and inherits its properties
public class Player extends Entity{

    private BufferedImage[][] animations;

    // Variables to store animation properties
    private int animTick, animIndex, animSpeed = 15;

    // Variables to store player actions & directions
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down, jump;
    private float playerSpeed = 1.0f * Game.SCALE;
    private int[][] levelData;  // Store level data in Player class to allow collision detection

    // Offset for accurate player hitbox compared to original hitbox
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;

    // Variables for jumping / gravity
    private float airSpeed = 0f;    // Variable for speed at which sprite travels through the air
    private float gravity = 0.04f * Game.SCALE; // Constant for game gravity. The lower the value, the higher the jump
    private float jumpSpeed = -2.25f * Game.SCALE;  // Value applied to airSpeed when jump button is clicked
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    // Variables to store health & power bar sizes & position
    BufferedImage statusBarImg;
    private int statusBarWidth = (int) (192 * Game.SCALE);
    private int statusBarHeight = (int) (58 * Game.SCALE);
    private int statusBarX = (int) (10 * Game.SCALE);
    private int statusBarY = (int) (10 * Game.SCALE);

    // Variables to store size & position for health bar itself
    private int healthBarWidth = (int) (150 * Game.SCALE);
    private int healthBarHeight = (int) (4 * Game.SCALE);
    private int healthBarXStart = (int) (34 * Game.SCALE);
    private int healthBarYStart = (int) (14 * Game.SCALE);

    private int maxHealth = 100;
    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, (int) (20 * Game.SCALE), (int) (27 * Game.SCALE));
    }

    // Method to implement logic for game and update animations
    public void update() {

        updateHealthBar();

        updatePos();
        updateAnimTick();
        setAnimation();
    }

    private void updateHealthBar() {

        healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
    }

    // Method to render & draw player
    public void render(Graphics g, int levelOffset) {

        g.drawImage(animations[playerAction][animIndex], (int)(hitbox.x - xDrawOffset) - levelOffset, (int)(hitbox.y - yDrawOffset), width, height, null);

        drawUI(g);
    }

    private void drawUI(Graphics g) {

        g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.red);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
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

        if(inAir) {

            if(airSpeed < 0) {

                playerAction = JUMP;
            }
            else {

                playerAction = FALLING;
            }
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
        
        if(jump) {
            
            jump();
        }

//        if(!left && !right && !inAir) {
//
//            return;
//        }

        if(!inAir) {

            if((!left && !right) || (left && right)) {

                return;
            }
        }

        float xSpeed = 0;

        if(left) {

            xSpeed -= playerSpeed;
        }
        if(right) {

            xSpeed += playerSpeed;
        }

        // Check if entity is on floor or not
        // If not on floor, then display falling animation until entity is on floor again
        // This is to prevent entity from floating or running on empty air
        if(!inAir) {

            if(!isEntityOnFloor(hitbox, levelData)) {

                inAir = true;
            }
        }

        if(inAir) {

            if(canMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {

                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            }
            else {

                hitbox.y = getEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);

                if(airSpeed > 0) {
                    
                    resetInAir();
                }
                else {

                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPos(xSpeed);
            }
        }
        else {

            updateXPos(xSpeed);
        }

        moving = true;
    }

    // Method that allows entity to jump
    // If already in air, return
    // If not, then display initial jump animation
    private void jump() {

        if(inAir) {

            return;
        }
        else {

            inAir = true;
            airSpeed = jumpSpeed;
        }
    }

    // Method that resets variables inAir & airSpeed once entity hits the ground after falling
    private void resetInAir() {

        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {

        if(canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {

            hitbox.x += xSpeed;
        }
        else {

            hitbox.x = getEntityXPosNextToWall(hitbox, xSpeed);
        }
    }

    // Method to change current player health
    // Prevent current health going under 0 or over max health
    public void changeHealth(int value) {

        currentHealth += value;

        if(currentHealth <= 0) {

            currentHealth = 0;
            //gameOver();
        }
        else if(currentHealth >= maxHealth) {

            currentHealth = maxHealth;
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

        statusBarImg = LoadSave.getSpriteAtlas(LoadSave.STATUS_BAR);
    }

    // Method to load level data to allow collision detection
    public void loadLevelData(int[][] levelData) {

        this.levelData = levelData;

        if(!isEntityOnFloor(hitbox, levelData)) {

            inAir = true;
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

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}
