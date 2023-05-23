package it.polimi.ingsw.ui.gui;


import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.UiGateway;
import it.polimi.ingsw.ui.ViewEventHandler;
import it.polimi.ingsw.ui.gui.config.GuiConfiguration;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import static it.polimi.ingsw.model.game.goal.Token.FULL_SHELF_TOKEN;


/**
 * NO ASTRATTA
 **/
public class GuiApp extends Application implements UiGateway {

    private static final Logger logger = LoggerFactory.getLogger(GuiApp.class);

    private final int first = GuiConfiguration.getInstance().getFirst();
    private final int second = GuiConfiguration.getInstance().getSecond();
    private final int third = GuiConfiguration.getInstance().getThird();
    private final int fourth = GuiConfiguration.getInstance().getFourth();


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
    public ImageView firstCommonGoalCardTopToken;
    @FXML
    public ImageView secondCommonGoalCardTopToken;
    @FXML
    public ImageView firstCommonGoalCard;
    @FXML
    public ImageView secondCommonGoalCard;
    @FXML
    public ImageView personalGoalCard;
    @FXML
    public Tab player1Button;
    @FXML
    public Tab player2Button;
    @FXML
    public Tab player3Button;
    @FXML
    public Tab player4Button;
    @FXML
    public Button selectingButton;
    @FXML
    public Button insertingButton;


    private final ViewEventHandler handler;
    public Game model;
    private final String owner;

    public Scene scene;


    public GuiApp(Game model, ViewEventHandler handler, String owner) {
        this.model = model;
        this.handler = handler;
        this.owner = owner;
    }

    @Override
    public void onGameCreated() {
        //game starting
        model.onGameStarted();
        //GUI initialization
        //TODO change magical numbers to costants

        player1Button.setText(model.getSessions().playerSessions().get(first).getUsername());
        player2Button.setText(model.getSessions().playerSessions().get(second).getUsername());
        player3Button.setText(model.getSessions().playerSessions().get(third).getUsername());
        player4Button.setText(model.getSessions().playerSessions().get(fourth).getUsername());

        //PGC initialization
        firstCommonGoalCard = scene.commonGoalCardUpdate(model.getCommonGoalCards().get(first).getCommonGoalCard());
        secondCommonGoalCard = scene.commonGoalCardUpdate(model.getCommonGoalCards().get(second).getCommonGoalCard());
        personalGoalCard = scene.personalGoalCardUpdate(model);

        //model update
        modelUpdate(model);


    }

    @Override
    public void modelUpdate(Game game) {
        this.model = game;
        board = scene.boardUpdate(model);
        myBookShelf = scene.bookshelfUpdate(model.getCurrentPlayerSession().getBookshelf());

        //TODO change bookshelves, now it's printing always the same one; create id
        player1BookShelf = scene.bookshelfUpdate(model.getCurrentPlayerSession().getBookshelf());
        player2BookShelf = scene.bookshelfUpdate(model.getCurrentPlayerSession().getBookshelf());
        player3BookShelf = scene.bookshelfUpdate(model.getCurrentPlayerSession().getBookshelf());
        player4BookShelf = scene.bookshelfUpdate(model.getCurrentPlayerSession().getBookshelf());

        // CGC token update
        endGameToken.setImage(GuiResources.getToken(FULL_SHELF_TOKEN));
        firstCommonGoalCardTopToken = scene.CommonGoalCardTokenUpdate(model.getCommonGoalCards().get(first));
        secondCommonGoalCardTopToken = scene.CommonGoalCardTokenUpdate(model.getCommonGoalCards().get(second));

    }

    public Coordinate getSelectedCoordinates(Node tileNode) {

        Coordinate coordinate;

        Integer col = GridPane.getColumnIndex(tileNode);
        Integer row = GridPane.getRowIndex(tileNode);

        coordinate = new Coordinate(row, col);

        return coordinate;
    }

    @Override
    public void gameSelection() {

        Set<Coordinate> selectedCoordinatees = new HashSet<>();

        Button button = new Button();
        button.setText("go to INSERTION");

        // aggiungere button per poter terminare prima la selezione in or nel ciclo while
        while (selectedCoordinatees.size() < 3 || button.isPressed()) {

            //coordinate selezionate tramite un evento mouse del client
            board.setOnMouseClicked(mouseEvent -> {
                selectedCoordinatees.add(getSelectedCoordinates(board));
            });
        }

        model.onPlayerSelectionPhase(selectedCoordinatees);
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