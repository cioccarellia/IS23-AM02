package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.config.board.BoardConfiguration;

public interface GameTester {

    int dimension = BoardConfiguration.getInstance().getDimension();
    String PLAYER_A = "Player A - Alberto", PLAYER_B = "Player B - Giulia", PLAYER_C = "Player C - Cookie", PLAYER_D = "Player D - Marco";
}
