package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.board.Tile;

public class PersonalGoalCard {
    private final Tile[][] shelfPointsMatrix;

    public PersonalGoalCard(Tile[][] matrix) {
        shelfPointsMatrix = matrix;
    }

    public Tile[][] getShelfPointMatrix() {
        return shelfPointsMatrix;
    }
}