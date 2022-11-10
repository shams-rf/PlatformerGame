package gamestates;

import main.Game;
import ui.MenuButton;

import java.awt.event.MouseEvent;

// Super class to store properties for all game states such as menu, playing, etc.
public class State {

    protected Game game;

    public State(Game game) {

        this.game = game;
    }

    // Method that returns true if mouse clicked inside button hitbox, otherwise returns false
    public boolean isIn(MouseEvent e, MenuButton mb) {

        return mb.getBounds().contains(e.getX(), e.getY());
    }

    public Game getGame() {

        return game;
    }
}
