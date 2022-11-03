package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Class that deals with keyboard input and implements KeyListener interface methods
public class KeyboardInput implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        System.out.println("A key is pressed!");
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
