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

    private static final int maxSelectionSize = LogicConfiguration.getInstance().maxSelectionSize();
    private static final int commonGoalCardsAmount = LogicConfiguration.getInstance().commonGoalCardAmount();

    private static int col = 0;

    @FXML
    public GridPane board;
    @FXML
    public GridPane myBookShelf;
    @FXML
    public GridPane player1BookShelf; //
    @FXML
    public GridPane player2BookShelf; //
    @FXML
    public GridPane player3BookShelf; //
    @FXML
    public GridPane player4BookShelf; //
    public List<GridPane> bookshelves = List.of(player1BookShelf, player2BookShelf, player3BookShelf, player4BookShelf);
    @FXML
    public ImageView endGameToken;
    @FXML
    public ImageView firstCommonGoalCardTopToken; //
    @FXML
    public ImageView secondCommonGoalCardTopToken; //
    public List<ImageView> topTokens = List.of(firstCommonGoalCardTopToken, secondCommonGoalCardTopToken);
    @FXML
    public ImageView firstCommonGoalCard; //
    @FXML
    public ImageView secondCommonGoalCard; //
    public List<ImageView> commonGoalCards = List.of(firstCommonGoalCard, secondCommonGoalCard);
    @FXML
    public ImageView personalGoalCard;
    @FXML
    public Tab player1Button; //
    @FXML
    public Tab player2Button; //
    @FXML
    public Tab player3Button; //
    @FXML
    public Tab player4Button; //
    public List<Tab> playersButtons = List.of(player1Button, player2Button, player3Button, player4Button);
    @FXML
    public Button selectingButton;
    @FXML
    public Button insertingButton;
    @FXML
    public ToggleGroup column;
    @FXML
    public RadioButton column1; //
    @FXML
    public RadioButton column2; //
    @FXML
    public RadioButton column3; //
    @FXML
    public RadioButton column4; //
    @FXML
    public RadioButton column5; //
    public List<RadioButton> columnButtons = List.of(column1, column2, column3, column4, column5);
    @FXML
    public ImageView tile1Selected; //
    @FXML
    public ImageView tile2Selected; //
    @FXML
    public ImageView tile3Selected; //
    public List<ImageView> selectedTiles = List.of(tile1Selected, tile2Selected, tile3Selected);
    @FXML
    public Label label1; //
    @FXML
    public Label label2; //
    @FXML
    public Label label3; //
    public List<Label> labels = List.of(label1, label2, label3);
    @FXML
    public ImageView insertionCommonGoalCard1; //
    @FXML
    public ImageView insertionCommonGoalCard2; //
    public List<ImageView> insertionCommonGoalCard = List.of(insertionCommonGoalCard1, insertionCommonGoalCard2);
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

        //todo
        for (int i = 0; i < model.getGameMode().maxPlayerAmount(); i++) {
            playersButtons.get(i).setText(model.getSessions().playerSessions().get(i).getUsername());
        }

        //PGC + CGC initialization
        //todo
        for (int i = 0; i < commonGoalCardsAmount; i++) {
            commonGoalCards.set(i, scene.commonGoalCardUpdate(model.getCommonGoalCards().get(i).getCommonGoalCard()));
        }

        //todo print the owner's personal goal card;
        personalGoalCard = scene.personalGoalCardUpdate(model);

        //insertion scene PGC +CGC initialization
        //to
        for (int i = 0; i < commonGoalCardsAmount; i++) {
            insertionCommonGoalCard.set(i, insertionScene.commonGoalCardUpdate(model.getCommonGoalCards().get(i).getCommonGoalCard()));
        }

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
        for(int i = 0; i < model.getGameMode().maxPlayerAmount(); i++){
            bookshelves.set(i, scene.bookshelfUpdate(model.getCurrentPlayerSession().getBookshelf()));
        }

        //player1BookShelf = scene.bookshelfUpdate(model.getCurrentPlayerSession().getBookshelf());
        //player2BookShelf = scene.bookshelfUpdate(model.getCurrentPlayerSession().getBookshelf());
        //player3BookShelf = scene.bookshelfUpdate(model.getCurrentPlayerSession().getBookshelf());
        //player4BookShelf = scene.bookshelfUpdate(model.getCurrentPlayerSession().getBookshelf());

        // CGC token update
        endGameToken.setImage(GuiResources.getToken(FULL_SHELF_TOKEN));

        //to
        for(int i = 0; i < commonGoalCardsAmount; i++){
            topTokens.set(i, scene.CommonGoalCardTokenUpdate(model.getCommonGoalCards().get(i)));
        }
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
     */
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
     */
    public void gameInsertion() {
        List<Tile> orderedTiles = new ArrayList<>();

        //todo
        for(int i = 0; i < columnButtons.size(); i++){
            RadioButton columnButton = columnButtons.get(i);
            int value = i;
            columnButton.setOnMouseClicked(mouseEvent -> {
                column.selectToggle(columnButton);
                col = value;
            });
        }

        //todo
        for(int i = 0; i < selectedTiles.size(); i++){
            ImageView selTile = selectedTiles.get(i);
            Label label = labels.get(i);
            int value = i;

            selTile.setOnMouseClicked(mouseEvent -> {
                if (orderedTiles.size() == 0) {
                    orderedTiles.add(GuiResources.getTileType(selTile.getImage()));
                    label.setText(String.valueOf(value + 1));
                } else if (orderedTiles.size() == 1) {
                    orderedTiles.add(GuiResources.getTileType(selTile.getImage()));
                    label.setText(String.valueOf(value + 2));
                } else if (orderedTiles.size() == 2) {
                    orderedTiles.add(GuiResources.getTileType(selTile.getImage()));
                    label.setText(String.valueOf(value + 3));
                }
            });
        }

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