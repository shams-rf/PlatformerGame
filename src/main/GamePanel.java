package main;

import input.KeyboardInput;
import input.MouseInput;

import javax.swing.*;
import java.awt.*;

// Game panel class allows drawing and painting inside game frame
public class GamePanel extends JPanel {

    // Variables that increase or decrease rectangle position depending on user input
    private int xDelta = 100;
    private int yDelta = 100;

    // Constructor where input is taken care of
    public GamePanel() {

        MouseInput mouseInput = new MouseInput(this);
        addKeyListener(new KeyboardInput(this));
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
    }

    // Method to change value of xDelta based on user input
    public void changeXDelta(int value) {

        this.xDelta += value;
        repaint();
    }

    // Method to change value of yDelta based on user input
    public void changeYDelta(int value) {

        this.yDelta += value;
        repaint();
    }

    // Method to draw rectangle at a given position
    public void setRectPos(int x, int y) {

        this.xDelta = x;
        this.yDelta = y;
        repaint();
    }

    // Method to allow painting on game panel
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.fillRect(xDelta, yDelta, 200, 50);
    }
}
