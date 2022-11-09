package entities;

import java.awt.*;

// Abstract class that stores details that are common between player & enemies
public abstract class Entity {

    // Only classes that extend this Entity class can use these variables
    protected float x, y;
    protected int width, height;
    protected Rectangle hitbox;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        initHitbox();
    }

    // Method to draw the hitbox
    protected void drawHitbox(Graphics g) {

        // For debugging purposes
        g.setColor(Color.PINK);
        g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    // Method to initialise & create sprite hitbox
    private void initHitbox() {

        hitbox = new Rectangle((int) x, (int) y, width, height);
    }

    // Method that takes new x, y values & updates the hitbox
    // Use protected keyword so only player or enemy entity can update hitbox as they extend the entity class
    protected void updateHitbox() {

        hitbox.x = (int) x;
        hitbox.y = (int) y;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
