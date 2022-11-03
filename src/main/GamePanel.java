package main;

import input.KeyboardInput;
import input.MouseInput;

import javax.swing.*;
import java.awt.*;

// Game panel class allows drawing and painting inside game frame
public class GamePanel extends JPanel {

    // Constructor where input is taken care of
    public GamePanel() {

        MouseInput mouseInput = new MouseInput();
        addKeyListener(new KeyboardInput());
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
    }

    // Method to allow painting on game panel
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.fillRect(100, 100, 200, 50);
    }
}
