package it.polimi.ingsw.ui.gui;


import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.UiGateway;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import static it.polimi.ingsw.model.game.GameMode.GAME_MODE_2_PLAYERS;

/** NO ASTRATTA **/
public class GuiApp extends Application implements UiGateway {

    private static final Logger logger = LoggerFactory.getLogger(GuiApp.class);

    private final int dimension = BoardConfiguration.getInstance().getDimension();
    private Game game;


    @Override
    public void onGameCreated() {

        Game game = new Game(GAME_MODE_2_PLAYERS);
    }

    @Override
    public void modelUpdate(Game game) {
        GridPane matrix = new GridPane();
        Tile tile;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                tile = game.getGameMatrix()[i][j];

                switch (tile) {
                    case BOOK -> matrix.add(createImageMatrix("img/tiles/book1.1.png"), i, j);
                    case CAT -> matrix.add(createImageMatrix("img/tiles/cat1.1.png"), i, j);
                    case GAME -> matrix.add(createImageMatrix("img/tiles/game1.1.png"), i, j);
                    case TROPHY -> matrix.add(createImageMatrix("img/tiles/trophy1.1.png"), i, j);
                    case PLANT -> matrix.add(createImageMatrix("img/tiles/plant1.1.png"), i, j);
                    case FRAME -> matrix.add(createImageMatrix("img/tiles/frame1.1.png"), i, j);
                }
            }
        }
    }

    public ImageView createImageMatrix(String name) {
        ImageView book = new ImageView();
        book.setImage(new Image(name));

        return book;
    }

    public Set<Coordinate> getSelectedCoordinates(Node tileNode) {

        Set<Coordinate> selectedCoordinate = new HashSet<>();
        Coordinate coordinate;

        Integer col = GridPane.getColumnIndex(tileNode);
        Integer row = GridPane.getRowIndex(tileNode);

        coordinate = new Coordinate(row, col);

        selectedCoordinate.add(coordinate);
        return selectedCoordinate;
    }

    @Override
    public void gameSelection() {

    }

    @Override
    public void gameInsertion() {

    }

    @Override
    public void onGameEnded() {

    }


    @Override
    public void start(Stage stage) throws Exception {
        // javafx start
    }

    @Override
    public void run() {
        // thread start
    }
}