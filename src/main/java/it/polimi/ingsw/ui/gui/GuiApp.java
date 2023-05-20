package it.polimi.ingsw.ui.gui;


import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.bookshelf.Bookshelf;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.UiGateway;
import javafx.application.Application;
import javafx.fxml.FXML;
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
import static it.polimi.ingsw.model.game.goal.Token.*;


/**
 * NO ASTRATTA
 **/
public class GuiApp extends Application implements UiGateway {

    private static final Logger logger = LoggerFactory.getLogger(GuiApp.class);
    private static final int FIRST=0;
    private static final int SECOND=1;

    private final int dimension = BoardConfiguration.getInstance().getDimension();
    private Game game;
    @FXML
    public GridPane board;
    @FXML
    public GridPane myBookShelf;
    @FXML
    public GridPane player1BookShelf;
    @FXML
    public GridPane player2BookShelf;
    @FXML
    public GridPane player3BookShelf;
    @FXML
    public GridPane player4BookShelf;
    @FXML
    public ImageView endGameToken;
    @FXML
    public ImageView FirstCommonGoalCardToken2;
    @FXML
    public ImageView FirstCommonGoalCardToken4;
    @FXML
    public ImageView FirstCommonGoalCardToken6;
    @FXML
    public ImageView FirstCommonGoalCardToken8;
    @FXML
    public ImageView SecondCommonGoalCardToken2;
    @FXML
    public ImageView SecondCommonGoalCardToken4;
    @FXML
    public ImageView SecondCommonGoalCardToken6;
    @FXML
    public ImageView SecondCommonGoalCardToken8;
    @FXML
    public ImageView firstCommonGoalCard;
    @FXML
    public ImageView secondCommonGoalCard;
    @FXML
    public ImageView personalGoalCard;



    @Override
    public void onGameCreated(Game game, Scene scene) {
        modelUpdate(game, scene);
        game.onGameStarted();
    }


    @Override
    public void modelUpdate(Game game, Scene scene) {
        GuiResources resources = new GuiResources();
        board = scene.boardUpdate(game.getBoard());
        myBookShelf = scene.bookshelfUpdate(game.getCurrentPlayer().getBookshelf());
        player1BookShelf = scene.bookshelfUpdate(game.getCurrentPlayer().getBookshelf());
        player2BookShelf = scene.bookshelfUpdate(game.getCurrentPlayer().getBookshelf());
        player3BookShelf = scene.bookshelfUpdate(game.getCurrentPlayer().getBookshelf());
        player4BookShelf = scene.bookshelfUpdate(game.getCurrentPlayer().getBookshelf());
        endGameToken.setImage(resources.getToken(FULL_SHELF_TOKEN));
        firstCommonGoalCard= scene.commonGoalCardUpdate(game.getCommonGoalCards().get(FIRST).getCommonGoalCard());
        secondCommonGoalCard= scene.commonGoalCardUpdate(game.getCommonGoalCards().get(SECOND).getCommonGoalCard());
        personalGoalCard=scene.personalGoalCardUpdate(game);



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
        //coordinate selezionate
        //game.onPlayerSelectionPhase();
    }

    @Override
    public void gameInsertion() {
        // tile da inserire
        //game.onPlayerSelectionPhase();
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