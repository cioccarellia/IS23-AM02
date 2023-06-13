package it.polimi.ingsw.ui.game.gui;


import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.controller.server.result.types.TileInsertionSuccess;
import it.polimi.ingsw.controller.server.result.types.TileSelectionSuccess;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.chat.ChatTextMessage;
import it.polimi.ingsw.model.chat.MessageRecipient;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
import it.polimi.ingsw.model.player.PlayerSession;
import it.polimi.ingsw.ui.Renderable;
import it.polimi.ingsw.ui.game.GameGateway;
import it.polimi.ingsw.ui.game.GameViewEventHandler;
import it.polimi.ingsw.ui.game.gui.renders.*;
import it.polimi.ingsw.utils.javafx.PaneViewUtil;
import it.polimi.ingsw.utils.javafx.UiUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.TestOnly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.sql.Timestamp;
import java.util.*;

import static it.polimi.ingsw.model.game.GameStatus.LAST_ROUND;
import static it.polimi.ingsw.model.game.goal.Token.FULL_SHELF_TOKEN;
import static it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase.INSERTING;
import static it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase.SELECTING;
import static it.polimi.ingsw.ui.game.gui.utils.GuiGameControllerUtils.*;


/**
 * The GuiGameControllerV2 class is responsible for managing the graphical user interface (GUI)
 * for the game. It handles user interactions, updates the GUI elements based on the game model,
 * and communicates with the game logic through the GameViewEventHandler interface.
 */
public class GuiGameController implements GameGateway, Initializable, Renderable {

    private static final Logger logger = LoggerFactory.getLogger(GuiGameController.class);

    private static final int maxSelectionSize = LogicConfiguration.getInstance().maxSelectionSize();
    private static final int commonGoalCardsAmount = LogicConfiguration.getInstance().commonGoalCardAmount();

    private static final int dimension = BoardConfiguration.getInstance().getDimension();

    // region Main Layer

    // GridPanes
    @FXML
    public GridPane boardGridPane;
    @FXML
    public GridPane ownerBookshelfGridPane;
    @FXML
    public GridPane enemyBookshelfGridPane;

    // Starting player chair ImageView (owner and enemy)
    @FXML
    public ImageView startingEnemyChair;
    @FXML
    public ImageView startingOwnerChair;

    // Tokens ImageViews
    @FXML
    public ImageView firstCommonGoalCardTopTokenImageView;
    @FXML
    public ImageView secondCommonGoalCardTopTokenImageView;
    @FXML
    public ImageView endGameTokenImageView;

    // Owner Username Label
    @FXML
    public Label ownerUsernameLabel;


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
    public Button boardSelectionButton;
    @FXML
    public Button bookshelfInsertionButton;


    // STATUS LABELS
    @FXML
    public Label statusTitleLabel;
    @FXML
    public Label statusSubtitleLabel;
    @FXML
    public Label firstCommonGoalCardDescriptionLabel;
    @FXML
    public Label secondCommonGoalCardDescriptionLabel;

    // INSERTION VBox
    @FXML
    public VBox insertionVBox;

    // RadioButtons and HBox for column selection
    @FXML
    public RadioButton columnSelection1RadioButton;
    @FXML
    public RadioButton columnSelection2RadioButton;
    @FXML
    public RadioButton columnSelection3RadioButton;
    @FXML
    public RadioButton columnSelection4RadioButton;
    @FXML
    public RadioButton columnSelection5RadioButton;
    @FXML
    public HBox radioButtonHBox;

    // ImageViews and HBox for selected tiles
    @FXML
    public GridPane selectedTilesGridPane;
    @FXML
    public ImageView firstSelectedTile;
    @FXML
    public ImageView secondSelectedTile;
    @FXML
    public ImageView thirdSelectedTile;

    // Labels and HBox for selected tiles
    @FXML
    public HBox selectedTilesNumberedLabelsHBox;
    @FXML
    public Label firstSelectedTilesNumberedLabel;
    @FXML
    public Label secondSelectedTilesNumberedLabel;
    @FXML
    public Label thirdSelectedTilesNumberedLabel;


    // CHAT
    @FXML
    public TextArea chatTextField;
    @FXML
    public MenuButton chatSelectorMenuButton;
    @FXML
    public ListView<String> chatPane;
    @FXML
    public MenuItem player1ChatWith;
    @FXML
    public MenuItem player2ChatWith;
    @FXML
    public MenuItem player3ChatWith;
    @FXML
    public MenuItem chatWithEveryone;


    // end region Main Layer


    // Constant game variables
    private GameViewEventHandler handler;
    private String owner;

    // Model data
    private GameModel model;
    private int currentlySelectedColumn;
    private List<Tile> orderedTiles = new ArrayList<>();
    private Set<Coordinate> selectedCoordinates = new HashSet<>();

    // chat data
    private MessageRecipient chatWithSelectedName;

    private List<String> chatEnemyList;
    private List<ChatTextMessage> messages;


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

        private List<RadioButton> columnRadioButtons() {
            return Arrays.asList(columnSelection1RadioButton, columnSelection2RadioButton,
                    columnSelection3RadioButton, columnSelection4RadioButton, columnSelection5RadioButton);
        }

        private List<ImageView> selectedTiles() {
            return Arrays.asList(firstSelectedTile, secondSelectedTile, thirdSelectedTile);
        }

        private List<Label> selectedTilesLabels() {
            return Arrays.asList(firstSelectedTilesNumberedLabel, secondSelectedTilesNumberedLabel, thirdSelectedTilesNumberedLabel);
        }

        private List<MenuItem> chatWithMenuItem() {
            return Arrays.asList(player1ChatWith, player2ChatWith, player3ChatWith);
        }
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
    public void injectModelData(GameModel model, GameViewEventHandler handler, String owner) {
        this.model = model;
        this.handler = handler;
        this.owner = owner;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // JavaFX app initialization
        // insertion listeners
        setRadioButtonsClickListeners();
        setImageViewClickListeners();
        setBookshelfInsertionButtonClickListener();

        // selection listeners
        setBoardSelectionButtonClickListener();
        setBoardImageViewsTileClickListener();

        // chat listeners
        setChatWithSelectingListeners();
        setSendMessageButtonListener();

        // enemy buttons listeners
        setEnemyButtonClickListeners();
    }

    // useful player sessions
    private PlayerSession currentPlayerSession() {
        return model.getCurrentPlayerSession();
    }

    private PlayerSession ownerSession() {
        return model.getPlayerSession(owner);
    }

    private PlayerSession startingPlayerSession() {
        return model.getStartingPlayerSession();
    }

    private PlayerSession selectedEnemySession() {
        return model.getPlayerSession(currentlySelectedUsername);
    }


    // game creation

    /**
     * Called when the game is created.
     */
    @Override
    public void onGameCreated() {
        if (model == null) {
            throw new IllegalStateException("JavaFX app launched without valid model");
        }

        PlayerSession startingPlayerSession = model.getStartingPlayerSession();


        // common goal cards and their description and their token
        commonGoalCardsOnCreation(model, iter.commonGoalCards(), iter.commonGoalCardsDescriptions(), iter.topTokens());

        // end game token
        TokenRender.renderToken(endGameTokenImageView, FULL_SHELF_TOKEN);

        // personal goal card
        PersonalGoalCardRender.renderPersonalGoalCard(personalGoalCardImageView, ownerSession().getPersonalGoalCard());

        // enemy buttons
        List<String> enemyList = model.getPlayersUsernameListExcluding(owner);
        for (int i = 0; i < enemyList.size(); i++) {
            iter.enemyButtons().get(i).setText(enemyList.get(i));
            iter.enemyButtons().get(i).setVisible(true);
        }

        // owner username label
        ownerUsernameLabel.setText("Hi " + ownerSession().getUsername());

        // owner starting player chair
        if (ownerSession().getUsername().equals(startingPlayerSession.getUsername())) {
            UiUtils.visible(startingOwnerChair);
        } else {
            UiUtils.invisible(startingOwnerChair);
        }

        // set selected tiles as empty
        setSelectedTilesFromCoordinates(iter.selectedTiles(), selectedCoordinates, model);


        currentlySelectedUsername = enemyList.get(0);


        hasInitializedUi = true;

        //chat initialization
        chatEnemyList = model.getPlayersUsernameListExcluding(owner);

        for (int i = 0; i < chatEnemyList.size(); i++) {
            String enemyName = chatEnemyList.get(i);
            ChatRender.renderChatMenuItemOnSelection(iter.chatWithMenuItem().get(i), enemyName);
        }

        render();
        renderEnemySection();
    }


    // model update

    /**
     * Updates the game model and refreshes the GUI elements based on the updated model.
     *
     * @param game The updated game model.
     */
    @Override
    public void modelUpdate(GameModel game) {
        this.model = game;
        render();
    }

    @Override
    public void chatModelUpdate(List<ChatTextMessage> messages) {
        this.messages = messages;
        renderChat();
    }
    @TestOnly
    public List<ChatTextMessage> getMessages(){
        return messages;
    }
    @TestOnly
    public void chatModelUpdateTest(List<ChatTextMessage> messages){
        this.messages=messages;
    }

    // renders
    public void renderChat() {
        //Updating chat ListView
        ObservableList<String> observableMessage = FXCollections.observableArrayList(messages.stream().map(ChatTextMessage::toString).toList());

        chatPane.setItems(observableMessage);
    }

    @Override
    public void render() {
        if (model == null || !hasInitializedUi) {
            return;
        }

        switch (model.getGameStatus()) {
            case RUNNING, LAST_ROUND -> {
                // board
                BoardRender.renderBoard(boardGridPane, model.getBoard());

                // owner's bookshelf
                BookshelfRender.renderBookshelf(ownerBookshelfGridPane, ownerSession().getBookshelf());

                // tokens update
                // owner's tokens
                tokenUpdate(ownerSession(), iter.ownerObtainedTokens());

                // end game token
                boolean hasSomeoneFinished = !model.getSessions().playerSessions().stream().map(player -> player.noMoreTurns).filter(flag -> flag).toList().isEmpty();

                if (hasSomeoneFinished && model.getGameStatus() == LAST_ROUND) {
                    TokenRender.renderToken(endGameTokenImageView, null);
                }

                // check if owner has obtained common goal card token
                checkAchievedCommonGoalCards(ownerSession(), model, iter.commonGoalCardsDescriptions());

                // common goal cards token update
                topTokenUpdate(model, iter.topTokens());

                // statusTitleLabel and statusSubtitleLabel
                //switch (currentPlayerSession.getPlayerCurrentGamePhase()) { //todo not updating
                //    case IDLE -> {
                //    }
                //    case SELECTING -> {
                //        statusTitleLabel.setText("Selection");
                //        statusSubtitleLabel.setText("Player @" + currentPlayerSession.getUsername()
                //                + " is selecting their tiles.");
                //    }
                //    case INSERTING -> {
                //        statusTitleLabel.setText("Insertion");
                //        statusSubtitleLabel.setText("Player @" + currentPlayerSession.getUsername()
                //                + " is inserting the selected tiles in their bookshelf.");
                //    }
                //}

                // managing the visibility and invisibility of everything needed in selecting and inserting phases
                manageVisibilityForGamePhases();

                checkAndApplyBoardDarkeningState();
            }
            case ENDED -> {
                //gameEnded();
            }
            case STANDBY -> {
                //gameStandby();
            }
        }
    }

    public void renderEnemySection() {
        // selected enemy's bookshelf
        BookshelfRender.renderBookshelf(enemyBookshelfGridPane, selectedEnemySession().getBookshelf());

        // enemy username label
        enemyUsernameLabel.setText("@" + currentlySelectedUsername);

        // enemy starting player chair
        if (selectedEnemySession().getUsername().equals(startingPlayerSession().getUsername())) {
            UiUtils.visible(startingEnemyChair);
        } else {
            UiUtils.invisible(startingEnemyChair);
        }

        // enemy's token
        tokenUpdate(selectedEnemySession(), iter.enemyObtainedTokens());

        // enemy label (enemyStatusLabel)
        EnemyStatusLabelRender.renderEnemyStatusLabel(enemyStatusLabel, selectedEnemySession());
    }

    // utils
    public void manageVisibilityForGamePhases() {
        switch (ownerSession().getPlayerCurrentGamePhase()) {
            case IDLE -> UiUtils.invisible(insertionVBox, boardSelectionButton, bookshelfInsertionButton);
            case SELECTING -> {
                UiUtils.visible(boardSelectionButton, selectedTilesGridPane, insertionVBox);
                UiUtils.invisible(radioButtonHBox, selectedTilesNumberedLabelsHBox, bookshelfInsertionButton);
            }
            case INSERTING -> {
                UiUtils.visible(bookshelfInsertionButton, radioButtonHBox, selectedTilesNumberedLabelsHBox, selectedTilesGridPane);
                UiUtils.invisible(boardSelectionButton);
            }
        }
    }

    public void checkAndApplyBoardDarkeningState() {
        if (ownerSession().getPlayerCurrentGamePhase() == SELECTING) {
            // non-selectable tiles darken
            makeNonSelectableTilesDark(boardGridPane, model, selectedCoordinates);
        }
    }

    // game selection and related listeners
    public void gameSelection() {
        manageVisibilityForGamePhases();

        // if selection is right (between 1 and 3 and acceptable tiles) sends coordinates to handler
        if (model.isSelectionValid(selectedCoordinates)) {
            handler.onViewSelection(selectedCoordinates);
        } else {
            statusTitleLabel.setText("Selection error");
            statusSubtitleLabel.setText("Player " + ownerSession().getUsername() + " the tiles you selected are not valid.");
        }
        setSelectedTilesFromCoordinates(iter.selectedTiles(), selectedCoordinates, model);
    }

    @FXML
    public void setBoardSelectionButtonClickListener() {
        boardSelectionButton.setOnMouseClicked(mouseEvent -> {
                    if (model.getPlayerSession(owner).getPlayerCurrentGamePhase() == SELECTING) {
                        gameSelection();
                    }
                }
        );
    }

    @FXML
    public void setBoardImageViewsTileClickListener() {
        Node[][] gridPaneNodes = PaneViewUtil.matrixify(boardGridPane, dimension, dimension);

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (gridPaneNodes[i][j] == null) {
                    continue;
                }

                Node currentImage = gridPaneNodes[i][j];
                ImageView matchingImageView = (ImageView) gridPaneNodes[i][j];

                currentImage.setOnMouseClicked(mouseEvent -> {
                    Node k = (Node) mouseEvent.getSource();
                    String positionField = (String) k.getUserData();
                    Coordinate c = Coordinate.parse(positionField);

                    Optional<Tile> matchingTile = model.getBoard().getTileAt(c);
                    onBoardTileClickHandler(c, matchingTile, matchingImageView);
                });
            }
        }
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private void onBoardTileClickHandler(Coordinate coordinate, Optional<Tile> tile, ImageView currentImageView) {
        if (ownerSession().getPlayerCurrentGamePhase() != SELECTING) {
            return;
        }

        if (tile.isEmpty()) {
            return;
        }

        //todo sometimes when adding a tile, it adds it to the end sometimes to the beginning

        manageVisibilityForGamePhases();

        // checks if c was already present in the set, in that case it is removed
        // if (selectedCoordinates != null) {
        if (selectedCoordinates.contains(coordinate)) {
            selectedCoordinates.remove(coordinate);
            currentImageView.setEffect(null);

            // darken all the tiles that are still valid after the deselection
            checkAndApplyBoardDarkeningState();

            // removes from insertionVBox
            setSelectedTilesFromCoordinates(iter.selectedTiles(), selectedCoordinates, model);
        } else {
            selectedCoordinates.add(coordinate);

            if (model.isSelectionValid(selectedCoordinates)) {
                enableDarkeningEffect(currentImageView);

                // darken all the tiles that are not valid anymore after the selection
                checkAndApplyBoardDarkeningState();

                // adds them to insertionVBox
                setSelectedTilesFromCoordinates(iter.selectedTiles(), selectedCoordinates, model);
            } else {
                selectedCoordinates.remove(coordinate);
                statusTitleLabel.setText("Selection error");
                statusSubtitleLabel.setText("Player " + ownerSession().getUsername() + " you can't select this tile.");
            }
        }
    }


    // game insertion and related listeners
    public void gameInsertion() {
        manageVisibilityForGamePhases();

        if (orderedTiles != null
                && orderedTiles.size() == selectedCoordinates.size()
                && currentlySelectedColumn >= 0 && currentlySelectedColumn < 5) {
            handler.onViewInsertion(currentlySelectedColumn, orderedTiles);
            orderedTiles.clear();
            selectedCoordinates.clear();
        } else {
            statusTitleLabel.setText("Insertion error");
            statusSubtitleLabel.setText("Player " + ownerSession().getUsername() + ", you have to select all the tiles.");
        }
    }

    @FXML
    public void setBookshelfInsertionButtonClickListener() {
        bookshelfInsertionButton.setOnMouseClicked(mouseEvent -> {
                    if (model.getPlayerSession(owner).getPlayerCurrentGamePhase() == INSERTING) {
                        gameInsertion();
                    }
                }
        );
    }

    @FXML
    public void setRadioButtonsClickListeners() {
        for (int i = 0; i < iter.columnRadioButtons().size(); i++) {
            final int finalizedIndex = i;
            RadioButton columnRadioButton = iter.columnRadioButtons().get(finalizedIndex);
            columnRadioButton.setOnMouseClicked(mouseEvent -> currentlySelectedColumn = finalizedIndex + 1);
        }
    }

    @FXML
    public void setImageViewClickListeners() {
        if (model == null)
            return;

        Node[][] gridPaneNodes = PaneViewUtil.matrixify(selectedTilesGridPane, 1, maxSelectionSize);

        for (int j = 0; j < maxSelectionSize; j++) {
            if (gridPaneNodes[0][j] == null) {
                continue;
            }

            Node currentImage = gridPaneNodes[0][j];
            ImageView matchingImageView = (ImageView) gridPaneNodes[0][j];

            currentImage.setOnMouseClicked(mouseEvent -> {
                Node k = (Node) mouseEvent.getSource();
                int positionField = Integer.parseInt(String.valueOf(k.getUserData()));

                onSelectedTilesClickHandler(positionField, matchingImageView);
            });
        }
    }

    private void onSelectedTilesClickHandler(int positionField, ImageView selectedTileImageView) {
        List<Tile> playerSelectedTiles = model.getPlayerSession(owner).getPlayerTileSelection().getSelectedTiles();

        if (Integer.parseInt(iter.selectedTilesLabels().get(positionField).getText()) == 0) {
            // if it still has the default value of 0

            // remove tile darkened effect and make label visible
            selectedTileImageView.setEffect(null);
            UiUtils.visible(iter.selectedTilesLabels().get(positionField));

            // add the tile to the orderedTiles list
            orderedTiles.add(playerSelectedTiles.get(positionField));

            // set the label to the current orderedTiles size
            iter.selectedTilesLabels().get(positionField).setText(String.valueOf(orderedTiles.size()));
        } else {
            // if it didn't have the default value, it means it's being deselected

            // change all the other labels
            int currentLabelValue = Integer.parseInt(iter.selectedTilesLabels().get(positionField).getText());

            for (int i = 0; i < iter.selectedTilesLabels().size(); i++) {
                int otherLabelValue = Integer.parseInt(iter.selectedTilesLabels().get(i).getText());

                if (i != positionField && otherLabelValue > currentLabelValue) {
                    iter.selectedTilesLabels().get(i).setText(String.valueOf(otherLabelValue - 1));
                }
            }

            // remove the tile from the orderedTiles list
            orderedTiles.remove(playerSelectedTiles.get(0));

            // set the darkened effect to the ImageView
            enableDarkeningEffect(selectedTileImageView);

            // make the label invisible and set it to 0 again
            UiUtils.invisible(iter.selectedTilesLabels().get(positionField));
            iter.selectedTilesLabels().get(positionField).setText("0");
        }
    }


    // enemy buttons listener
    @FXML
    public void setEnemyButtonClickListeners() {
        enemySelect1Button.setOnMouseClicked(mouseEvent -> {
            currentlySelectedUsername = enemySelect1Button.getText();
            renderEnemySection();
        });

        enemySelect2Button.setOnMouseClicked(mouseEvent -> {
            currentlySelectedUsername = enemySelect2Button.getText();
            renderEnemySection();
        });

        enemySelect3Button.setOnMouseClicked(mouseEvent -> {
            currentlySelectedUsername = enemySelect3Button.getText();
            renderEnemySection();
        });
    }


    // chat
    private void addToChat(ChatTextMessage chatTextMessage) {
        chatPane.getItems().add(chatTextMessage.toString());

        handler.onViewSendMessage(chatTextMessage);
        chatTextField.clear();
    }

    @FXML
    public void setChatWithSelectingListeners() {
        player1ChatWith.setOnAction(mouseEvent -> {
            chatWithSelectedName = new MessageRecipient.Direct(chatEnemyList.get(0));
            chatSelectorMenuButton.setText(chatEnemyList.get(0));
        });

        player2ChatWith.setOnAction(mouseEvent -> {
            chatWithSelectedName = new MessageRecipient.Direct(chatEnemyList.get(1));
            chatSelectorMenuButton.setText(chatEnemyList.get(1));
        });

        player3ChatWith.setOnAction(mouseEvent -> {
            chatWithSelectedName = new MessageRecipient.Direct(chatEnemyList.get(2));
            chatSelectorMenuButton.setText(chatEnemyList.get(2));
        });

        chatWithEveryone.setOnAction(mouseEvent -> {
            chatWithSelectedName = new MessageRecipient.Broadcast();
            chatSelectorMenuButton.setText("Everyone");
        });

    }

    @FXML
    public void setSendMessageButtonListener() {
        sendMessageButton.setOnMouseClicked(mouseEvent -> {
            Timestamp time = new Timestamp(System.currentTimeMillis());

            if (!chatTextField.getText().isEmpty()) {
                ChatTextMessage textMessage = new ChatTextMessage(owner, chatWithSelectedName, chatTextField.getText(), time);
                addToChat(textMessage);
            }
        });
    }


    // replies

    /**
     * Handles the game selection reply and updates the GUI elements based on the result.
     *
     * @param turnResult The result of the tile selection operation.
     */
    @Override
    public void onGameSelectionReply(TypedResult<TileSelectionSuccess, TileSelectionFailures> turnResult) {
        switch (turnResult) {
            case TypedResult.Failure<TileSelectionSuccess, TileSelectionFailures> failure -> {
                statusTitleLabel.setText("Selection error");
                switch (failure.error()) {
                    case WRONG_GAME_PHASE -> statusSubtitleLabel.setText("Error, wrong game phase");
                    case UNAUTHORIZED_SELECTION -> statusSubtitleLabel.setText("Error, unauthorized selection");
                    case UNAUTHORIZED_PLAYER -> statusSubtitleLabel.setText("Error, player not authorized");
                }
            }
            case TypedResult.Success<TileSelectionSuccess, TileSelectionFailures> success -> {
                var data = success.value();
                modelUpdate(data.model());
            }
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
                statusTitleLabel.setText("Insertion error");
                switch (failure.error()) {
                    case WRONG_SELECTION -> statusSubtitleLabel.setText("Error, illegal selection");
                    case ILLEGAL_COLUMN -> statusSubtitleLabel.setText("Error, column out of bounds");
                    case TOO_MANY_TILES -> statusSubtitleLabel.setText("Error, too many tiles selected");
                    case NO_FIT -> statusSubtitleLabel.setText("Error, the selected tiles can't fit in this columns");
                    case WRONG_PLAYER ->
                            statusSubtitleLabel.setText("Error, unauthorized action from non active player");
                    case WRONG_GAME_PHASE -> statusSubtitleLabel.setText("Error, wrong game phase");
                }
            }
            case TypedResult.Success<TileInsertionSuccess, BookshelfInsertionFailure> success -> {
                var data = success.value();
                modelUpdate(data.model());
            }
        }
    }
}