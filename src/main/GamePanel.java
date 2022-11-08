package main;

import input.KeyboardInput;
import input.MouseInput;
import utilities.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;
import static utilities.Constants.PlayerConstants.*;
import static utilities.Constants.Directions.*;

// Game panel class allows drawing and painting inside game frame
public class GamePanel extends JPanel {

    private Game game;

    // Variables to store images
    private BufferedImage img;
    private BufferedImage[][] animations;

    // Constructor where input is taken care of
    public GamePanel(Game game) {

        MouseInput mouseInput = new MouseInput(this);
        this.game = game;

        setPanelSize();
        addKeyListener(new KeyboardInput(this));
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
    }

    // Method to set size for game panel inside game window
    private void setPanelSize() {

        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
    }

    // Method to implement logic for game and update animations
    public void updateGame() {

    }

    // Method to allow painting on game panel
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return game;
    }
}
