package it.polimi.ingsw.ui.game.guiv2;


import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.controller.server.result.types.TileInsertionSuccess;
import it.polimi.ingsw.controller.server.result.types.TileSelectionSuccess;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.Renderable;
import it.polimi.ingsw.ui.game.GameGateway;
import it.polimi.ingsw.ui.game.GameViewEventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.*;


/**
 * The GuiGameControllerV2 class is responsible for managing the graphical user interface (GUI)
 * for the game. It handles user interactions, updates the GUI elements based on the game model,
 * and communicates with the game logic through the GameViewEventHandler interface.
 */
public class GuiGameControllerV2 implements GameGateway, Initializable, Renderable {

    private static final Logger logger = LoggerFactory.getLogger(GuiGameControllerV2.class);

    private static final int maxSelectionSize = LogicConfiguration.getInstance().maxSelectionSize();
    private static final int commonGoalCardsAmount = LogicConfiguration.getInstance().commonGoalCardAmount();


    // region Main Layer
    // GridPanes
    @FXML
    public GridPane boardGridPane;
    @FXML
    public GridPane ownerBookshelfGridPane;
    @FXML
    public GridPane enemyBookshelfGridPane;


    // Tokens ImageViews
    @FXML
    public ImageView firstCommonGoalCardTopTokenImageView;
    @FXML
    public ImageView secondCommonGoalCardTopTokenImageView;
    @FXML
    public ImageView endGameTokenImageView;


    // Personal + Common Goal Cards ImageViews
    @FXML
    public ImageView personalGoalCardImageView;
    @FXML
    public ImageView firstCommonGoalCardImageView;
    @FXML
    public ImageView secondCommonGoalCardImageView;


    // Enemy Buttons
    @FXML
    public Button enemy1Button;
    @FXML
    public Button enemy2Button;
    @FXML
    public Button enemy3Button;


    // Action & Selection Buttons
    @FXML
    public Button selectionButton;


    // Status Label
    @FXML
    public Label statusLabel;
    @FXML
    public Label playerErrorLabel;
    // end region Main Layer


    private GameViewEventHandler handler;
    private Game model;
    private String owner;


    /**
     * Initializes the game model, event handler, and owner for the GUI controller.
     *
     * @param model   The game model.
     * @param handler The event handler for game events.
     * @param owner   The owner of the GUI controller.
     */
    public void injectModelData(Game model, GameViewEventHandler handler, String owner) {
        this.model = model;
        this.handler = handler;
        this.owner = owner;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    private List<ImageView> topTokens() {
        return Arrays.asList(firstCommonGoalCardTopTokenImageView, secondCommonGoalCardTopTokenImageView);
    }

    private List<ImageView> commonGoalCards() {
        return Arrays.asList(firstCommonGoalCardImageView, secondCommonGoalCardImageView);
    }

    private List<Button> enemyButtons() {
        return Arrays.asList(enemy1Button, enemy2Button, enemy3Button);
    }


    /**
     * Called when the game is created. It initializes the GUI elements and starts the game.
     */
    @Override
    public void onGameCreated() {
        if (model == null)
            return;

        render();
    }


    /**
     * Updates the game model and refreshes the GUI elements based on the updated model.
     *
     * @param game The updated game model.
     */
    @Override
    public void modelUpdate(Game game) {
        this.model = game;
        render();
    }


    @Override
    public void render() {
        if (model == null) {
            return;
        }

    }


    /**
     * Handles the game selection reply and updates the GUI elements based on the result.
     *
     * @param turnResult The result of the tile selection operation.
     */
    @Override
    public void onGameSelectionReply(TypedResult<TileSelectionSuccess, TileSelectionFailures> turnResult) {
        switch (turnResult) {
            case TypedResult.Failure<TileSelectionSuccess, TileSelectionFailures> failure -> {
                switch (failure.error()) {
                    case WRONG_GAME_PHASE -> {
                        playerErrorLabel.setOpacity(1);
                        playerErrorLabel.setText("Error, wrong game phase");
                    }
                    case UNAUTHORIZED_SELECTION -> {
                        playerErrorLabel.setOpacity(1);
                        playerErrorLabel.setText("Error, unauthorized selection");
                    }
                    case UNAUTHORIZED_PLAYER -> {
                        playerErrorLabel.setOpacity(1);
                        playerErrorLabel.setText("Error, player not authorized");
                    }
                }
            }
            case TypedResult.Success<TileSelectionSuccess, TileSelectionFailures> success -> playerErrorLabel.setOpacity(0);
        }
    }

    /**
     * Handles the game insertion reply and updates the GUI elements based on the result.
     *
     * @param turnResult The result of the bookshelf insertion operation.
     */
    @Override
    public void onGameInsertionReply(TypedResult<TileInsertionSuccess, BookshelfInsertionFailure> turnResult) {
        switch (turnResult) {
            case TypedResult.Failure<TileInsertionSuccess, BookshelfInsertionFailure> failure -> {
                switch (failure.error()) {
                    case WRONG_SELECTION -> {
                        playerErrorLabel.setVisible(true);
                        playerErrorLabel.setText("Error, wrong selection");
                    }

                    case ILLEGAL_COLUMN -> {
                        playerErrorLabel.setVisible(true);
                        playerErrorLabel.setText("Error, column out of bounds");
                    }

                    case TOO_MANY_TILES -> {
                        playerErrorLabel.setVisible(true);
                        playerErrorLabel.setText("Error, too many tiles selected");
                    }

                    case NO_FIT -> {
                        playerErrorLabel.setVisible(true);
                        playerErrorLabel.setText("Error, selected tiles can't fit in this columns");
                    }

                    case WRONG_PLAYER -> {
                        playerErrorLabel.setVisible(true);
                        playerErrorLabel.setText("Error, unauthorized action from non active player");
                    }

                    case WRONG_GAME_PHASE -> {
                        playerErrorLabel.setVisible(true);
                        playerErrorLabel.setText("Error, wrong game phase");
                    }
                }
            }
            case TypedResult.Success<TileInsertionSuccess, BookshelfInsertionFailure> success ->
                    playerErrorLabel.setOpacity(0);
        }
    }
}