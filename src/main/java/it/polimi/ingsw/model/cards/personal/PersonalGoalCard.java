package it.polimi.ingsw.model.cards.personal;

import it.polimi.ingsw.model.board.Tile;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class PersonalGoalCard {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PersonalGoalCard that = (PersonalGoalCard) o;

        return new EqualsBuilder().append(shelfPointsMatrix, that.shelfPointsMatrix).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(shelfPointsMatrix).toHashCode();
    }

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