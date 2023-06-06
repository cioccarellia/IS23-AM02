package it.polimi.ingsw.ui.game.gui;


import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.controller.server.result.types.TileInsertionSuccess;
import it.polimi.ingsw.controller.server.result.types.TileSelectionSuccess;
import it.polimi.ingsw.model.chat.ChatTextMessage;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.PlayerSession;
import it.polimi.ingsw.ui.Renderable;
import it.polimi.ingsw.ui.game.GameGateway;
import it.polimi.ingsw.ui.game.GameViewEventHandler;
import it.polimi.ingsw.ui.game.gui.renders.BoardRender;
import it.polimi.ingsw.ui.game.gui.renders.BookshelfRender;
import it.polimi.ingsw.ui.game.gui.renders.CommonGoalCardRender;
import it.polimi.ingsw.ui.game.gui.renders.PersonalGoalCardRender;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


/**
 * The GuiGameControllerV2 class is responsible for managing the graphical user interface (GUI)
 * for the game. It handles user interactions, updates the GUI elements based on the game model,
 * and communicates with the game logic through the GameViewEventHandler interface.
 */
public class GuiGameController implements GameGateway, Initializable, Renderable {

    private static final Logger logger = LoggerFactory.getLogger(GuiGameController.class);

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

    // OWNER TOKENS
    @FXML
    public ImageView ownerFirstTokenImageView;
    @FXML
    public ImageView ownerSecondTokenImageView;
    @FXML
    public ImageView ownerThirdTokenImageView;


    // Enemy Buttons
    @FXML
    public Label enemyStatusLabel;
    @FXML
    public Label enemyUsernameLabel;
    @FXML
    public Button enemySelect1Button;
    @FXML
    public Button enemySelect2Button;
    @FXML
    public Button enemySelect3Button;
    @FXML
    public CheckBox autoFollowCheckBox;

    // ENEMY TOKENS
    @FXML
    public ImageView enemyFirstTokenImageView;
    @FXML
    public ImageView enemySecondTokenImageView;
    @FXML
    public ImageView enemyThirdTokenImageView;


    // BUTTONS
    @FXML
    public Button quitButton;
    @FXML
    public Button sendMessageButton;
    @FXML
    public Button boardButton;
    @FXML
    public Button bookshelfButton;


    // STATUS LABELS
    @FXML
    public Label statusTitleLabel;
    @FXML
    public Label statusSubtitleLabel;
    @FXML
    public Label firstCommonGoalCardDescriptionLabel;
    @FXML
    public Label secondCommonGoalCardDescriptionLabel;


    // Radio buttons for column selection<
    /*
    @FXML
    public RadioButton columnSelection1RadioButton;
    @FXML
    public RadioButton columnSelection2RadioButton;
    @FXML
    public RadioButton columnSelection3RadioButton;
    @FXML
    public RadioButton columnSelection4RadioButton;
    @FXML
    public RadioButton columnSelection5RadioButton;*/


    // CHAT
    @FXML
    public TextField chatTextField;
    @FXML
    public MenuButton chatSelectorMenuButton;
    @FXML
    public ListView<ChatTextMessage> chatMessagesListView;
    // end region Main Layer


    // Constant game variables
    private GameViewEventHandler handler;
    private String owner;

    // Model data
    private Game model;


    DynamicIterator iter = new DynamicIterator();

    public class DynamicIterator {
        private List<ImageView> topTokens() {
            return Arrays.asList(firstCommonGoalCardTopTokenImageView, secondCommonGoalCardTopTokenImageView);
        }

        private List<ImageView> commonGoalCards() {
            return Arrays.asList(firstCommonGoalCardImageView, secondCommonGoalCardImageView);
        }

        private List<Label> commonGoalCardsDescriptions() {
            return Arrays.asList(firstCommonGoalCardDescriptionLabel, secondCommonGoalCardDescriptionLabel);
        }

        private List<Button> enemyButtons() {
            return Arrays.asList(enemySelect1Button, enemySelect2Button, enemySelect3Button);
        }

        private List<ImageView> enemyObtainedTokens() {
            return Arrays.asList(enemyFirstTokenImageView, enemySecondTokenImageView, enemyThirdTokenImageView);
        }

        private List<ImageView> ownerObtainedTokens() {
            return Arrays.asList(ownerFirstTokenImageView, ownerSecondTokenImageView, ownerThirdTokenImageView);
        }

        /*
        private List<RadioButton> columnRadioButtons() {
            return Arrays.asList(columnSelection1RadioButton, columnSelection2RadioButton, columnSelection3RadioButton, columnSelection4RadioButton, columnSelection5RadioButton);
        }
        */
    }


    private boolean hasInitializedUi = false;
    private String currentlySelectedUsername = null;


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
        // JavaFX app initialization
    }


    /**
     * Called when the game is created.
     */
    @Override
    public void onGameCreated() {
        if (model == null) {
            throw new IllegalStateException();
        }

        PlayerSession ownerSession = model.getSessions().getByUsername(owner);
        PlayerSession currentPlayerSession = model.getCurrentPlayerSession();


        CommonGoalCardRender.renderCommonGoalCard(firstCommonGoalCardImageView, model.getCommonGoalCards().get(0).getCommonGoalCard());
        CommonGoalCardRender.renderCommonGoalCard(secondCommonGoalCardImageView, model.getCommonGoalCards().get(1).getCommonGoalCard());
        PersonalGoalCardRender.renderPersonalGoalCard(personalGoalCardImageView, ownerSession.getPersonalGoalCard());


        List<String> enemyList = model.getPlayersUsernameListExcluding(owner);

        for (int i = 0; i < enemyList.size(); i++) {
            iter.enemyButtons().get(i).setText(enemyList.get(i));
            iter.enemyButtons().get(i).setVisible(true);
        }


        currentlySelectedUsername = enemyList.get(0);


        hasInitializedUi = true;
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
        if (model == null || !hasInitializedUi) {
            return;
        }

        PlayerSession ownerSession = model.getSessions().getByUsername(owner);
        PlayerSession currentPlayerSession = model.getCurrentPlayerSession();
        PlayerSession enemySession = model.getSessionFor(currentlySelectedUsername);


        // board
        BoardRender.renderBoard(boardGridPane, model.getBoard());

        // owner's bookshelf
        BookshelfRender.renderBookshelf(ownerBookshelfGridPane, ownerSession.getBookshelf());

        // selected enemy's bookshelf
        BookshelfRender.renderBookshelf(enemyBookshelfGridPane, enemySession.getBookshelf());

        // enemy username label
        enemyUsernameLabel.setText(currentlySelectedUsername);

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
                        statusSubtitleLabel.setVisible(true);
                        statusSubtitleLabel.setText("Error, wrong game phase");
                    }
                    case UNAUTHORIZED_SELECTION -> {
                        statusSubtitleLabel.setVisible(true);
                        statusSubtitleLabel.setText("Error, unauthorized selection");
                    }
                    case UNAUTHORIZED_PLAYER -> {
                        statusSubtitleLabel.setVisible(true);
                        statusSubtitleLabel.setText("Error, player not authorized");
                    }
                }
            }
            case TypedResult.Success<TileSelectionSuccess, TileSelectionFailures> success ->
                    statusSubtitleLabel.setVisible(false);
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
                        statusSubtitleLabel.setVisible(true);
                        statusSubtitleLabel.setText("Error, illegal selection");
                    }

                    case ILLEGAL_COLUMN -> {
                        statusSubtitleLabel.setVisible(true);
                        statusSubtitleLabel.setText("Error, column out of bounds");
                    }

                    case TOO_MANY_TILES -> {
                        statusSubtitleLabel.setVisible(true);
                        statusSubtitleLabel.setText("Error, too many tiles selected");
                    }

                    case NO_FIT -> {
                        statusSubtitleLabel.setVisible(true);
                        statusSubtitleLabel.setText("Error, the selected tiles can't fit in this columns");
                    }

                    case WRONG_PLAYER -> {
                        statusSubtitleLabel.setVisible(true);
                        statusSubtitleLabel.setText("Error, unauthorized action from non active player");
                    }

                    case WRONG_GAME_PHASE -> {
                        statusSubtitleLabel.setVisible(true);
                        statusSubtitleLabel.setText("Error, wrong game phase");
                    }
                }
            }
            case TypedResult.Success<TileInsertionSuccess, BookshelfInsertionFailure> success ->
                    statusSubtitleLabel.setVisible(false);
        }
    }


    @FXML
    public void enemy1BookshelfButtonClick() {
        __enemyBookshelfButtonClick(enemySelect1Button.getText());
    }

    @FXML
    public void enemy2BookshelfButtonClick() {
        __enemyBookshelfButtonClick(enemySelect2Button.getText());
    }

    @FXML
    public void enemy3BookshelfButtonClick() {
        __enemyBookshelfButtonClick(enemySelect3Button.getText());
    }

    private void __enemyBookshelfButtonClick(String username) {
        currentlySelectedUsername = username;
        modelUpdate(model);
    }
}