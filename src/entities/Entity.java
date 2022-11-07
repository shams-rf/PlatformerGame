package entities;

// Abstract class that stores details that are common between player & enemies
public abstract class Entity {

    protected float x, y;   // Only classes that extend this class can use these variables

    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
