package it.polimi.ingsw.model.config.bookshelf;

import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.model.config.Specifics;

public record BookshelfSpecifics(
        @SerializedName("rows")
        int rows,
        @SerializedName("cols")
        int cols
) implements Specifics {
}