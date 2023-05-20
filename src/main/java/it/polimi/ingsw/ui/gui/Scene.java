package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.bookshelf.Bookshelf;
import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import it.polimi.ingsw.model.cards.common.CommonGoalCardIdentifier;
import it.polimi.ingsw.model.cards.personal.PersonalGoalCard;
import it.polimi.ingsw.model.cards.personal.PersonalGoalCardMatrixContainer;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.utils.model.BoardUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.polimi.ingsw.model.cards.personal.PersonalGoalCardMatrixContainer.*;
import static it.polimi.ingsw.model.cards.personal.PersonalGoalCardMatrixContainer.personalGoalCardDomain;


public class Scene {

    Game game;

    private final int rows = BookshelfConfiguration.getInstance().rows();
    private final int cols = BookshelfConfiguration.getInstance().cols();
    private final int dimension = BoardConfiguration.getInstance().getDimension();

    private static final List<PersonalGoalCard> personalGoalCards = new ArrayList<>(personalGoalCardDomain);


    public GridPane boardUpdate(Board board) {
        GuiResources resource = new GuiResources();
        GridPane matrix = new GridPane();
        Tile tile;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                tile = game.getGameMatrix()[i][j];

                switch (tile) {
                    case BOOK ->
                            matrix.add(createImageMatrix(String.valueOf(resource.getTile(Tile.BOOK))), i, j);
                    case CAT ->
                            matrix.add(createImageMatrix(String.valueOf(resource.getTile(Tile.CAT))), i, j);
                    case GAME ->
                            matrix.add(createImageMatrix(String.valueOf(resource.getTile(Tile.GAME))), i, j);
                    case TROPHY ->
                            matrix.add(createImageMatrix(String.valueOf(resource.getTile(Tile.TROPHY))), i, j);
                    case PLANT ->
                            matrix.add(createImageMatrix(String.valueOf(resource.getTile(Tile.PLANT))), i, j);
                    case FRAME ->
                            matrix.add(createImageMatrix(String.valueOf(resource.getTile(Tile.FRAME))), i, j);
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
        Tile tile;
        GuiResources resource = new GuiResources();
        for (int i = 0; i < rows; i++){
            for(int j=0; j<cols;j++){
                if (bookshelf.getShelfMatrix()[i][j] != null) {

                    tile = bookshelf.getShelfMatrix()[i][j];
                    switch (tile) {
                        case BOOK -> matrix.add(createImageMatrix(String.valueOf(resource.getTile(Tile.BOOK))), i, j);
                        case CAT -> matrix.add(createImageMatrix(String.valueOf(resource.getTile(Tile.CAT))), i, j);
                        case GAME -> matrix.add(createImageMatrix(String.valueOf(resource.getTile(Tile.GAME))), i, j);
                        case TROPHY -> matrix.add(createImageMatrix(String.valueOf(resource.getTile(Tile.TROPHY))), i, j);
                        case PLANT -> matrix.add(createImageMatrix(String.valueOf(resource.getTile(Tile.PLANT))), i, j);
                        case FRAME -> matrix.add(createImageMatrix(String.valueOf(resource.getTile(Tile.FRAME))), i, j);
                    }
                }
            }
        }
            return matrix;
    }

    public ImageView personalGoalCardUpdate(Game game) {
        GuiResources resources = new GuiResources();
        ImageView image = new ImageView();
        /*switch (game.getCurrentPlayer().getPersonalGoalCard()) {
            case p1 ->{

            }
        }*/
        return image;
    }
    public ImageView commonGoalCardUpdate(CommonGoalCard commonGoalCard)
    {
        GuiResources resources = new GuiResources();
        ImageView image = new ImageView();
        CommonGoalCardIdentifier id = commonGoalCard.getId();
        switch (id) {
            case SIX_PAIRS -> {

            }
            case DIAGONAL -> {

            }
            case FOUR_GROUP_FOUR -> {

            }
            case FOUR_MAX3DIFF_LINES -> {

            }
            case FOUR_CORNERS -> {


            }
            case TWO_DIFF_COLUMNS -> {

            }
            case TWO_SQUARES -> {

            }
            case TWO_DIFF_LINES -> {

            }
            case THREE_MAX3DIFF_COLUMNS -> {

            }
            case X_TILES -> {

            }
            case EIGHT_TILES -> {

            }
            case STAIRS -> {

            }
        }
        return image;
    }


}
