package it.polimi.ingsw.network.tcp.messages.response;

import it.polimi.ingsw.model.GameModel;

public class GameStartedEvent extends Response {

    private final GameModel game;

    public GameStartedEvent(GameModel game) {
        this.game = game;
    }

    public GameModel getGame() {
        return game;
    }

    @Override
    public String toString() {
        return "GameStartedEvent{" +
                "game=" + game +
                '}';
    }
}