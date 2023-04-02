package it.polimi.ingsw.model.config.board;

import it.polimi.ingsw.model.config.Specifics;

public record BoardSpecifics(
        int dimension,
        int[][] matrix
) implements Specifics {
}