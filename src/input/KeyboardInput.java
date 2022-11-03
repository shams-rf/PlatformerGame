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

        // Switch statement to detect if W, A, S or D are pressed
        switch(e.getKeyCode()) {

            case KeyEvent.VK_W:
                System.out.println("Pressed W");
                break;
            case KeyEvent.VK_A:
                System.out.println("Pressed A");
                break;
            case KeyEvent.VK_S:
                System.out.println("Pressed S");
                break;
            case KeyEvent.VK_D:
                System.out.println("Pressed D");
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
