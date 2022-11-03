package main;

import javax.swing.*;

public class GameWindow {

    private JFrame jframe;

    // Constructor to create game window and set its properties
    public GameWindow(GamePanel gamePanel) {

        jframe = new JFrame();

        jframe.setSize(400, 400);   // Set window size to 400 * 400 pixels
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Set window to terminate program on close
        jframe.add(gamePanel);  // Add game panel inside game frame
        jframe.setVisible(true);    // Set visible property to true
    }
}
