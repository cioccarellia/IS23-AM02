package it.polimi.ingsw.network.tcp.messages.response;

import it.polimi.ingsw.model.GameModel;

public class ModelUpdateEvent extends Response {
    final private GameModel game;

    public ModelUpdateEvent(GameModel game) {
        this.game = game;
    }

    public GameModel getGame() {
        return game;
    }

    @Override
    public String toString() {
        return "ModelUpdateEvent{" +
                "game=" + game +
                '}';
    }
}