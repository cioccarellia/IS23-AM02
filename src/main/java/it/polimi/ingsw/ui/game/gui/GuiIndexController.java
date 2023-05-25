package it.polimi.ingsw.ui.game.gui;


import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.game.GameGateway;
import it.polimi.ingsw.ui.game.GameViewEventHandler;
import it.polimi.ingsw.ui.game.gui.config.GuiConfiguration;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static it.polimi.ingsw.model.game.goal.Token.FULL_SHELF_TOKEN;


/**
 * NO ASTRATTA
 **/
public class GuiIndexController extends Application implements GameGateway {

    private static final Logger logger = LoggerFactory.getLogger(GuiIndexController.class);

    private static final int first = GuiConfiguration.getInstance().getFirst();
    private static final int second = GuiConfiguration.getInstance().getSecond();
    private static final int third = GuiConfiguration.getInstance().getThird();
    private static final int fourth = GuiConfiguration.getInstance().getFourth();
    private static final int fifth = GuiConfiguration.getInstance().getFifth();


    private static final int maxSelectionSize = LogicConfiguration.getInstance().maxSelectionSize();
    private static int col = 0;

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
    @FXML
    public ToggleGroup column;
    @FXML
    public RadioButton column1;
    @FXML
    public RadioButton column2;
    @FXML
    public RadioButton column3;
    @FXML
    public RadioButton column4;
    @FXML
    public RadioButton column5;
    @FXML
    public ImageView tile1Selected;
    @FXML
    public ImageView tile2Selected;
    @FXML
    public ImageView tile3Selected;
    @FXML
    public Label label1;
    @FXML
    public Label label2;
    @FXML
    public Label label3;
    @FXML
    public ImageView insertionCommonGoalCard1;
    @FXML
    public ImageView insertionCommonGoalCard2;
    @FXML
    public ImageView insertionPersonalGoalCard;


    private final GameViewEventHandler handler;
    public Game model;
    private final String owner;

    public Scene scene;
    public Scene insertionScene; //to remeber that insertion scene needs an inzialization


    public GuiIndexController(Game model, GameViewEventHandler handler, String owner) {
        this.model = model;
        this.handler = handler;
        this.owner = owner;
    }

    @Override
    public void onGameCreated() {
        //game starting
        model.onGameStarted();
        //GUI initialization

        player1Button.setText(model.getSessions().playerSessions().get(first).getUsername());
        player2Button.setText(model.getSessions().playerSessions().get(second).getUsername());
        player3Button.setText(model.getSessions().playerSessions().get(third).getUsername());
        player4Button.setText(model.getSessions().playerSessions().get(fourth).getUsername());

        //PGC + CGC initialization
        firstCommonGoalCard = scene.commonGoalCardUpdate(model.getCommonGoalCards().get(first).getCommonGoalCard());
        secondCommonGoalCard = scene.commonGoalCardUpdate(model.getCommonGoalCards().get(second).getCommonGoalCard());
        personalGoalCard = scene.personalGoalCardUpdate(model);
        //insertion scene PGC +CGC inizialization
        insertionCommonGoalCard1 = insertionScene.commonGoalCardUpdate(model.getCommonGoalCards().get(first).getCommonGoalCard());
        insertionCommonGoalCard2 = insertionScene.commonGoalCardUpdate(model.getCommonGoalCards().get(second).getCommonGoalCard());
        insertionPersonalGoalCard = insertionScene.personalGoalCardUpdate(model);
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

    @Override
    public void onGameSelectionReply(SingleResult<TileSelectionFailures> turnResult) {

    }

    @Override
    public void onGameInsertionReply(SingleResult<BookshelfInsertionFailure> turnResult) {

    }

    public Coordinate getSelectedCoordinates(Node tileNode) {
        Coordinate coordinate;

        Integer col = GridPane.getColumnIndex(tileNode);
        Integer row = GridPane.getRowIndex(tileNode);

        coordinate = new Coordinate(row, col);

        return coordinate;
    }

    /**
     * To be invoked when it's the player turn to select
     * */
    public void gameSelection() {
        Set<Coordinate> selectedCoordinatees = new HashSet<>();

        board.setOnMouseClicked(mouseEvent -> {
            if (selectedCoordinatees.size() < maxSelectionSize) {
                selectedCoordinatees.add(getSelectedCoordinates(board));
            }

        });

        selectingButton.setOnMouseClicked(mouseEvent -> {
            SceneManager.changeScene(SceneManager.getActualController(), "Insertion.fxml");
            handler.onViewSelection(selectedCoordinatees);
        });

    }

    /**
     * To be invoked when it's the player turn to insert
     * */
    public void gameInsertion() {
        List<Tile> orderedTiles = new ArrayList<>();

        column1.setOnMouseClicked(mouseEvent -> {
            column.selectToggle(column1);
            col = first;
        });


        column2.setOnMouseClicked(mouseEvent -> {
            column.selectToggle(column2);
            col = second;
        });

        column3.setOnMouseClicked(mouseEvent -> {
            column.selectToggle(column3);
            col = third;
        });

        column4.setOnMouseClicked(mouseEvent -> {
            column.selectToggle(column4);
            col = fourth;
        });

        column5.setOnMouseClicked(mouseEvent -> {
            column.selectToggle(column5);
            col = fifth;
        });

        tile1Selected.setOnMouseClicked(mouseEvent -> {
            if (orderedTiles.size() == 0) {
                orderedTiles.add(GuiResources.getTileType(tile1Selected.getImage()));
                label1.setText(String.valueOf(second));
            } else if (orderedTiles.size() == 1) {
                orderedTiles.add(GuiResources.getTileType(tile1Selected.getImage()));
                label1.setText(String.valueOf(third));
            } else if (orderedTiles.size() == 2) {
                orderedTiles.add(GuiResources.getTileType(tile1Selected.getImage()));
                label1.setText(String.valueOf(fourth));
            }
        });
        tile2Selected.setOnMouseClicked(mouseEvent -> {
            if (orderedTiles.size() == 0) {
                orderedTiles.add(GuiResources.getTileType(tile2Selected.getImage()));
                label2.setText(String.valueOf(second));
            } else if (orderedTiles.size() == 1) {
                orderedTiles.add(GuiResources.getTileType(tile2Selected.getImage()));
                label2.setText(String.valueOf(third));
            } else if (orderedTiles.size() == 2) {
                orderedTiles.add(GuiResources.getTileType(tile2Selected.getImage()));
                label2.setText(String.valueOf(fourth));
            }
        });
        tile3Selected.setOnMouseClicked(mouseEvent -> {
            if (orderedTiles.size() == 0) {
                orderedTiles.add(GuiResources.getTileType(tile3Selected.getImage()));
                label3.setText(String.valueOf(second));
            } else if (orderedTiles.size() == 1) {
                orderedTiles.add(GuiResources.getTileType(tile3Selected.getImage()));
                label3.setText(String.valueOf(third));
            } else if (orderedTiles.size() == 2) {
                orderedTiles.add(GuiResources.getTileType(tile3Selected.getImage()));
                label3.setText(String.valueOf(fourth));
            }
        });


        insertingButton.setOnMouseClicked(mouseEvent -> {
            handler.onViewInsertion(col, orderedTiles);
            SceneManager.changeScene(SceneManager.getActualController(), "index.fxml");
        });
    }

    @Override
    public void start(Stage stage) throws Exception {
        // javafx start
    }

}