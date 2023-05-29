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
public class GuiHelper {

    private final static int rows = BookshelfConfiguration.getInstance().rows();
    private final static int cols = BookshelfConfiguration.getInstance().cols();
    private final static int dimension = BoardConfiguration.getInstance().getDimension();

    /**
     * Regenerates the GridPane representing the game board in the GUI based on the provided game model.
     *
     * @param game The game model.
     * @return The regenerated GridPane representing the game board.
     */
    public static GridPane regenerateBoardGridPage(Game game) {
        GridPane matrix = new GridPane();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                Tile currentTile = game.getGameMatrix()[i][j];
                if (currentTile != null) {
                    ImageView tileImageView = generateImageViewForTile(currentTile);
                    matrix.add(tileImageView, i, j);
                }
            }
        }
        return matrix;
    }

    /**
     * Generates an ImageView representing a tile in the GUI.
     *
     * @param tile The tile to generate the ImageView for.
     * @return The ImageView representing the tile.
     */
    public static ImageView generateImageViewForTile(Tile tile) {
        Image tileImage = GuiResources.getTile(tile);

        ImageView tileImageView = new ImageView();

        tileImageView.setImage(tileImage);

        return tileImageView;
    }

    /**
     * Regenerates the GridPane representing a bookshelf in the GUI based on the provided bookshelf.
     *
     * @param bookshelf The bookshelf to regenerate the GridPane for.
     * @return The regenerated GridPane representing the bookshelf.
     */
    public static GridPane regenerateBookshelfGridPane(Bookshelf bookshelf) {
        GridPane matrix = new GridPane();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Tile tile = bookshelf.getShelfMatrix()[i][j];

                if (tile != null) {
                    matrix.add(generateImageViewForTile(tile), i, j);
                }
            }
        }

        return matrix;
    }

    /**
     * Generates an ImageView representing a personal goal card in the GUI.
     *
     * @param game  The game model.
     * @param owner The owner of the personal goal card.
     * @return The ImageView representing the personal goal card.
     */
    public static ImageView generatePersonalGoalCardImageView(Game game, String owner) {
        ImageView personalGoalCardImageView = new ImageView();

        PersonalGoalCard id = game.getSessions().getByUsername(owner).getPersonalGoalCard();

        Image personalGoalCardImage = GuiResources.getPersonalGC(id);

        personalGoalCardImageView.setImage(personalGoalCardImage);

        return personalGoalCardImageView;
    }

    /**
     * Generates an ImageView representing a common goal card in the GUI.
     *
     * @param commonGoalCard The common goal card to generate the ImageView for.
     * @return The ImageView representing the common goal card.
     */
    public static ImageView generateCommonGoalCardImageView(CommonGoalCard commonGoalCard) {
        ImageView commonGoalCardImageView = new ImageView();

        CommonGoalCardIdentifier id = commonGoalCard.getId();

        Image commonGoalCardImage = GuiResources.getCommonGC(id);

        commonGoalCardImageView.setImage(commonGoalCardImage);

        return commonGoalCardImageView;
    }

    /**
     * Generates an ImageView representing a token in the GUI based on the provided common goal card status.
     *
     * @param commonGoalCard The common goal card status containing the token.
     * @return The ImageView representing the token.
     */
    public static ImageView generateTokenImageView(CommonGoalCardStatus commonGoalCard) {
        ImageView tokenImageView = new ImageView();

        Token id = commonGoalCard.getCardTokens().get(commonGoalCard.getCardTokens().size() - 1);

        Image tokenImage = GuiResources.getToken(id);

        tokenImageView.setImage(tokenImage);

        return tokenImageView;
    }

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
