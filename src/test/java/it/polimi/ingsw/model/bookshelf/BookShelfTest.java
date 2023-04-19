package it.polimi.ingsw.model.bookshelf;


import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static it.polimi.ingsw.model.board.Tile.*;
import static org.junit.jupiter.api.Assertions.*;

public class BookShelfTest implements ShelfMatrixTester {
    private static final int maxSelectionSize = LogicConfiguration.getInstance().maxSelectionSize();

}