package main;

import javax.swing.*;

public class GameWindow {

    private JFrame jframe;

    // Constructor to create game window and set its properties
    public GameWindow(GamePanel gamePanel) {

        jframe = new JFrame();

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Set window to terminate program on close
        jframe.add(gamePanel);  // Add game panel inside game frame
        jframe.setLocationRelativeTo(null); // Spawn window in centre of screen
        jframe.setResizable(false); // Don't allow user to resize window
        jframe.pack();  // Create window that fits dimensions of game panel
        jframe.setVisible(true);    // Set visible property to true
    }
}
