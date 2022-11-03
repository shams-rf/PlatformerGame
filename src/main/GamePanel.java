package main;

import input.KeyboardInput;

import javax.swing.*;
import java.awt.*;

// Game panel class allows drawing and painting inside game frame
public class GamePanel extends JPanel {

    // Constructor where input is taken care of
    public GamePanel() {

        addKeyListener(new KeyboardInput());
    }

    // Method to allow painting on game panel
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.fillRect(100, 100, 200, 50);
    }
}
