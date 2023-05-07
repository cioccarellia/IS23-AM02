package it.polimi.ingsw.net.tcp.messages.response;

import it.polimi.ingsw.model.game.Game;

public class ModelUpdateEvent extends Response {
    final private Game game;

    public ModelUpdateEvent(Game game){
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
