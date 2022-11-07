package input;

import main.Game;
import main.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

// Class that deals with mouse input and implements mouse listener and mouse motion listener methods
public class MouseInput implements MouseListener, MouseMotionListener {

    private GamePanel gamePanel;

    public MouseInput(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        System.out.println("Mouse clicked!");
    }

    @Override
    public void mousePressed(MouseEvent e) {

        // If mouse button is pressed, display attacking animation
        if(e.getButton() == MouseEvent.BUTTON1) {

            gamePanel.getGame().getPlayer().setAttacking(true);
        }
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

    }
}
