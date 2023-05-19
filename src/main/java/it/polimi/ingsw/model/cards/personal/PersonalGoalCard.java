package it.polimi.ingsw.model.cards.personal;

import it.polimi.ingsw.model.board.Tile;

public class PersonalGoalCard {
    private final Tile[][] shelfPointsMatrix;

    public PersonalGoalCard(Tile[][] matrix) {
        shelfPointsMatrix = matrix;
    }

    /**
     * @return the Tile matrix of the personal card
     */
    public Tile[][] getShelfPointMatrix() {
        return shelfPointsMatrix;
    }

}