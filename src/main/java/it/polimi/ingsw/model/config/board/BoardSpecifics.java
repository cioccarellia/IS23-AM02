package it.polimi.ingsw.model.config.board;

import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.model.config.Specifics;

import java.io.Serializable;

public record BoardSpecifics(
        @SerializedName("dimension")
        int dimension,
        @SerializedName("matrix")
        int[][] matrix
) implements Specifics, Serializable {
}