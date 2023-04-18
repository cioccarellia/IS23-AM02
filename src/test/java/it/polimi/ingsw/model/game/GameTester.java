package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.config.board.BoardConfiguration;

public interface GameTester {
    int dimension = BoardConfiguration.getInstance().getDimension();

    String PLAYER_A = "Player Alberto", PLAYER_B = "Player Bortone", PLAYER_C = "Player Cookie", PLAYER_D = "Player D - Marco";

}
