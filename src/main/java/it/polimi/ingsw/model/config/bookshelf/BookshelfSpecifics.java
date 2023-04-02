package it.polimi.ingsw.model.config.bookshelf;

import it.polimi.ingsw.model.config.Specifics;

public record BookshelfSpecifics(
        int rows,
        int cols
) implements Specifics {
}