package it.polimi.ingsw.model.cards.personal;

import it.polimi.ingsw.model.board.Tile;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Arrays;

public class PersonalGoalCard implements Serializable {

    private final PersonalGoalCardIdentifier id;
    private final Tile[][] shelfPointsMatrix;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PersonalGoalCard that = (PersonalGoalCard) o;

        return new EqualsBuilder().append(shelfPointsMatrix, that.shelfPointsMatrix).isEquals();
    }

    /**
     * @return the id of the personal card
     */
    public PersonalGoalCardIdentifier getId() {
        return id;
    }

    public PersonalGoalCard(Tile[][] matrix, PersonalGoalCardIdentifier identifier) {
        shelfPointsMatrix = matrix;
        id = identifier;
    }

    /**
     * @return the Tile matrix of the personal card
     */
    public Tile[][] getShelfPointMatrix() {
        return shelfPointsMatrix;
    }

    @Override
    public String toString() {
        return "PersonalGoalCard{" +
                "id=" + id +
                ", shelfPointsMatrix=" + Arrays.toString(shelfPointsMatrix) +
                '}';
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(shelfPointsMatrix).toHashCode();
    }
}