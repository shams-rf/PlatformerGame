package main;

import javax.swing.*;
import java.awt.*;

// Game panel class allows drawing and painting inside game frame
public class GamePanel extends JPanel {

    public GamePanel() {

    }

    // Method to allow painting on game panel
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.drawRect(100, 100, 200, 50);
    }
}
