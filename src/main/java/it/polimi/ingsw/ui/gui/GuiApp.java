package it.polimi.ingsw.ui.gui;


import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.UiGateway;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import it.polimi.ingsw.ui.ViewEventHandler;

import java.util.HashSet;
import java.util.Set;

import static it.polimi.ingsw.model.game.goal.Token.*;


/**
 * NO ASTRATTA
 **/
public class GuiApp extends Application implements UiGateway {

    private static final Logger logger = LoggerFactory.getLogger(GuiApp.class);

    private static final int FIRST = 0;
    private static final int SECOND = 1;

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
    public ImageView firstCommonGoalCardToken;
    @FXML
    public ImageView secondCommonGoalCardToken;
    @FXML
    public ImageView firstCommonGoalCard;
    @FXML
    public ImageView secondCommonGoalCard;
    @FXML
    public ImageView personalGoalCard;


    private final ViewEventHandler handler;
    public Game model;


    public GuiApp(Game model, final ViewEventHandler handler) {
        this.model = model;
        this.handler = handler;
    }


    @Override
    public void onGameCreated(Game game, Scene scene) {
        //PGC initialization
        firstCommonGoalCard = scene.commonGoalCardUpdate(game.getCommonGoalCards().get(FIRST).getCommonGoalCard());
        secondCommonGoalCard = scene.commonGoalCardUpdate(game.getCommonGoalCards().get(SECOND).getCommonGoalCard());
        personalGoalCard = scene.personalGoalCardUpdate(game);
        //model update
        modelUpdate(game, scene);
        //game starting
        game.onGameStarted();
    }


    @Override
    public void modelUpdate(Game game, Scene scene) {
        board = scene.boardUpdate(game);
        myBookShelf = scene.bookshelfUpdate(game.getCurrentPlayer().getBookshelf());

        player1BookShelf = scene.bookshelfUpdate(game.getCurrentPlayer().getBookshelf());
        player2BookShelf = scene.bookshelfUpdate(game.getCurrentPlayer().getBookshelf());
        player3BookShelf = scene.bookshelfUpdate(game.getCurrentPlayer().getBookshelf());
        player4BookShelf = scene.bookshelfUpdate(game.getCurrentPlayer().getBookshelf());

        // CGC token update
        endGameToken.setImage(GuiResources.getToken(FULL_SHELF_TOKEN));
        firstCommonGoalCardToken = scene.CommonGoalCardTokenUpdate(game.getCommonGoalCards().get(FIRST));
        secondCommonGoalCardToken = scene.CommonGoalCardTokenUpdate(game.getCommonGoalCards().get(SECOND));

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
        //coordinate selezionate tramite un evento mouse del client
        //game.onPlayerSelectionPhase();
    }

    @Override
    public void gameInsertion() {
        // tile da inserire tramite un evento mouse del client
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