package main;

import input.KeyboardInput;
import input.MouseInput;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

// Game panel class allows drawing and painting inside game frame
public class GamePanel extends JPanel {

    // Variables that increase or decrease rectangle position depending on user input
    private float xDelta = 100;
    private float yDelta = 100;

    // Variables that help display frames per second in console
    private int frames = 0;
    private long lastCheck = 0;

    // Variables to store images
    private BufferedImage img;
    private BufferedImage subImg;

    // Constructor where input is taken care of
    public GamePanel() {

        MouseInput mouseInput = new MouseInput(this);

        importImg();

        setPanelSize();
        addKeyListener(new KeyboardInput(this));
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
    }

    // Method to import image from resources folder into game
    private void importImg() {

        InputStream is = getClass().getResourceAsStream("/player_sprites.png");

        try {
            img = ImageIO.read(is);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set size for game panel inside game window
    private void setPanelSize() {

        Dimension size = new Dimension(1280, 800);
        setPreferredSize(size);
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

        subImg = img.getSubimage(2*64, 2*40, 64, 40);
        g.drawImage(subImg, (int)xDelta, (int)yDelta, 128, 80, null);
    }
}
