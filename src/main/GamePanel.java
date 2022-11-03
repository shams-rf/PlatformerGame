package main;

import input.KeyboardInput;
import input.MouseInput;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

// Game panel class allows drawing and painting inside game frame
public class GamePanel extends JPanel {

    // Variables that increase or decrease rectangle position depending on user input
    private float xDelta = 100;
    private float yDelta = 100;
    private float xDir = 0.01f;
    private float yDir = 0.01f;

    // Variables that help display frames per second in console
    private int frames = 0;
    private long lastCheck = 0;

    private Color color = new Color(194, 34, 123);
    private Random random;

    // Constructor where input is taken care of
    public GamePanel() {

        random = new Random();
        MouseInput mouseInput = new MouseInput(this);
        addKeyListener(new KeyboardInput(this));
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
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

    // Method to allow painting on game panel
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        updateRectangle();
        g.setColor(color);
        g.fillRect((int)xDelta, (int)yDelta, 200, 50);

        // Create FPS counter in console
        frames++;
        if(System.currentTimeMillis() - lastCheck >= 1000) {
            lastCheck = System.currentTimeMillis();
            System.out.println("FPS: " + frames);
            frames = 0;
        }

        repaint();
    }

    // Method to update rectangle position and make it move inside boundaries of game window
    // Update rectangle color everytime it changes direction
    private void updateRectangle() {

        xDelta += xDir;
        if(xDelta > 400 || xDelta < 0) {
            xDir *= -1;
            color = getRandColor();
        }

        yDelta += yDir;
        if(yDelta > 400 || yDelta < 0) {
            yDir *= -1;
            color = getRandColor();
        }
    }

    // Method to create and return a random color
    private Color getRandColor() {

        int r = random.nextInt(255);
        int g = random.nextInt(255);;
        int b = random.nextInt(255);;

        return new Color(r, g, b);
    }
}
