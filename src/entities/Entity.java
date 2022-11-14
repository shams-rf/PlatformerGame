package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

// Abstract class that stores details that are common between player & enemies
public abstract class Entity {

    // Only classes that extend this Entity class can use these variables
    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Method to draw the hitbox
    protected void drawHitbox(Graphics g, int xLevelOffset) {

        // For debugging purposes
        g.setColor(Color.PINK);
        g.drawRect((int) hitbox.x - xLevelOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    // Method to initialise & create sprite hitbox
    protected void initHitbox(float x, float y, int width, int height) {

        hitbox = new Rectangle2D.Float(x, y, width, height);
    }

    // Method that takes new x, y values & updates the hitbox
    // Use protected keyword so only player or enemy entity can update hitbox as they extend the entity class
//    protected void updateHitbox() {
//
//        hitbox.x = (int) x;
//        hitbox.y = (int) y;
//    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }
}
