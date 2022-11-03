package main;

import javax.swing.*;

public class GameWindow {

    private JFrame jframe;

    // Constructor to create game window, set size to 400 * 400 pixels, set visble to true
    public GameWindow() {

        jframe = new JFrame();

        jframe.setSize(400, 400);
        jframe.setVisible(true);
    }
}
