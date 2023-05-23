package it.polimi.ingsw.network.tcp.messages.response;

import it.polimi.ingsw.model.game.Game;

public class GameStartedEvent extends Response {

    private final Game game;

    public GameStartedEvent(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public String toString() {
        return "GameStartedEvent{" +
                "game=" + game +
                '}';
    }
}