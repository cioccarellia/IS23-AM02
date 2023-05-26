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
import it.polimi.ingsw.model.player.PlayerNumber;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


public class Scene {

    private final int rows = BookshelfConfiguration.getInstance().rows();
    private final int cols = BookshelfConfiguration.getInstance().cols();
    private final int dimension = BoardConfiguration.getInstance().getDimension();

    public GridPane boardUpdate(Game game) {
        GridPane matrix = new GridPane();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                Tile tile = game.getGameMatrix()[i][j];

                if (tile != null) {
                    matrix.add(createImageMatrix(String.valueOf(GuiResources.getTile(tile))), i, j);
                }

            }
        }
        return matrix;
    }

    public ImageView createImageMatrix(String name) {
        ImageView book = new ImageView();
        book.setImage(new Image(name));

        return book;
    }

    public GridPane bookshelfUpdate(Bookshelf bookshelf) {
        GridPane matrix = new GridPane();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Tile tile = bookshelf.getShelfMatrix()[i][j];

                if (tile != null) {
                    matrix.add(createImageMatrix(String.valueOf(GuiResources.getTile(tile))), i, j);
                }
            }
        }

        return matrix;
    }

    public ImageView personalGoalCardUpdate(Game game, String owner) {

        GuiResources resources = new GuiResources();
        ImageView image = new ImageView();
        PersonalGoalCard id = game.getSessions().getByUsername(owner).getPersonalGoalCard();

        image.setImage(GuiResources.getPersonalGC(id));
        return image;
    }

    public ImageView commonGoalCardUpdate(CommonGoalCard commonGoalCard) {
        ImageView image = new ImageView();
        CommonGoalCardIdentifier id = commonGoalCard.getId();

        image.setImage(GuiResources.getCommonGC((id)));

        return image;
    }

    public ImageView CommonGoalCardTokenUpdate(CommonGoalCardStatus commonGoalCard) {
        ImageView image = new ImageView();
        Token id = commonGoalCard.getCardTokens().get(commonGoalCard.getCardTokens().size() - 1);

        image.setImage(GuiResources.getToken(id));

        return image;
    }

    public Coordinate getSelectedCoordinates(Node tileNode) {

        Coordinate coordinate;

        Integer col = GridPane.getColumnIndex(tileNode);
        Integer row = GridPane.getRowIndex(tileNode);

        coordinate = new Coordinate(row, col);

        return coordinate;
    }
}
