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
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


public class GuiHelper {

    private final static int rows = BookshelfConfiguration.getInstance().rows();
    private final static int cols = BookshelfConfiguration.getInstance().cols();
    private final static int dimension = BoardConfiguration.getInstance().getDimension();

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

    public static ImageView generateImageViewForTile(Tile tile) {
        Image tileImage = GuiResources.getTile(tile);

        ImageView tileImageView = new ImageView();
        tileImageView.setImage(tileImage);

        return tileImageView;
    }

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

    public static ImageView generatePersonalGoalCardImageView(Game game, String owner) {
        ImageView image = new ImageView();
        PersonalGoalCard id = game.getSessions().getByUsername(owner).getPersonalGoalCard();

        image.setImage(GuiResources.getPersonalGC(id));
        return image;
    }

    public static ImageView generateCommonGoalCardImageView(CommonGoalCard commonGoalCard) {
        ImageView image = new ImageView();
        CommonGoalCardIdentifier id = commonGoalCard.getId();

        image.setImage(GuiResources.getCommonGC((id)));

        return image;
    }

    public static ImageView generateTokenImageView(CommonGoalCardStatus commonGoalCard) {
        ImageView image = new ImageView();
        Token id = commonGoalCard.getCardTokens().get(commonGoalCard.getCardTokens().size() - 1);

        image.setImage(GuiResources.getToken(id));

        return image;
    }

    public static Coordinate getSelectedCoordinates(Node tileNode) {
        Coordinate coordinate;

        Integer col = GridPane.getColumnIndex(tileNode);
        Integer row = GridPane.getRowIndex(tileNode);

        coordinate = new Coordinate(row, col);

        return coordinate;
    }
}
