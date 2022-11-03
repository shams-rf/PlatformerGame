package main;

import input.KeyboardInput;
import input.MouseInput;

import javax.swing.*;
import java.awt.*;

// Game panel class allows drawing and painting inside game frame
public class GamePanel extends JPanel {

    // Variables that increase or decrease rectangle position depending on user input
    private int xDelta = 0;
    private int yDelta = 0;

    // Constructor where input is taken care of
    public GamePanel() {

        MouseInput mouseInput = new MouseInput();
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

    // Method to allow painting on game panel
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.fillRect(100 + xDelta, 100 + yDelta, 200, 50);
    }
}
