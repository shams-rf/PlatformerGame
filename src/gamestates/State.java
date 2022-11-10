package gamestates;

import main.Game;

// Super class to store properties for all game states such as menu, playing, etc.
public class State {

    protected Game game;

    public State(Game game) {

        this.game = game;
    }

    public Game getGame() {

        return game;
    }
}
