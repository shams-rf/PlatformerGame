package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

// Class that deals with mouse input and implements mouse listener and mouse motion listener methods
public class MouseInput implements MouseListener, MouseMotionListener {

    @Override
    public void mouseClicked(MouseEvent e) {

        System.out.println("Mouse clicked!");
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        System.out.println("Mouse moved!");
    }
}
