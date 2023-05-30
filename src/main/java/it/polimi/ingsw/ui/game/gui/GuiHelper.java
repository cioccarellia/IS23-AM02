package it.polimi.ingsw.ui.game.gui;

import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.bookshelf.Bookshelf;
import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import it.polimi.ingsw.model.cards.common.CommonGoalCardIdentifier;
import it.polimi.ingsw.model.cards.personal.PersonalGoalCard;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.goal.CommonGoalCardStatus;
import it.polimi.ingsw.model.game.goal.Token;
import it.polimi.ingsw.ui.game.gui.utils.GuiResources;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * The GuiHelper class provides helper methods for generating and updating GUI elements
 * in the game graphical user interface (GUI).
 */
public class GuiHelper{
    /**
     * Retrieves the coordinates of a selected tile node in the GUI.
     *
     * @param tileNode The Node representing the selected tile.
     * @return The coordinates of the selected tile.
     */
    public static Coordinate getSelectedCoordinates(Node tileNode) {
        Coordinate coordinate;

        Integer col = GridPane.getColumnIndex(tileNode);

        Integer row = GridPane.getRowIndex(tileNode);

        coordinate = new Coordinate(row, col);

        return coordinate;
    }
}
