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
import it.polimi.ingsw.model.game.CellInfo;
import it.polimi.ingsw.model.game.score.PlayerScore;
import it.polimi.ingsw.model.player.PlayerSession;
import it.polimi.ingsw.model.player.selection.PlayerTileSelection;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static it.polimi.ingsw.model.game.GameStatus.ENDED;
import static it.polimi.ingsw.model.game.GameStatus.LAST_ROUND;
import static it.polimi.ingsw.model.game.goal.Token.FULL_SHELF_TOKEN;
import static it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase.INSERTING;
import static it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase.SELECTING;
import static it.polimi.ingsw.ui.game.gui.renders.FinalRankingRender.renderRanking;
import static it.polimi.ingsw.ui.game.gui.renders.SelectedTilesRender.renderSelectedTiles;
import static it.polimi.ingsw.ui.game.gui.renders.SelectedTilesRender.renderSelectedTilesWhenOnlyOne;
import static it.polimi.ingsw.ui.game.gui.utils.GuiGameControllerUtils.*;


/**
 * The GuiGameControllerV2 class is responsible for managing the graphical user interface (GUI)
 * for the game. It handles user interactions, updates the GUI elements based on the game model,
 * and communicates with the game logic through the GameViewEventHandler interface.
 */
public class GuiGameController implements GameGateway, Initializable, Renderable {

    private static final Logger logger = LoggerFactory.getLogger(GuiGameController.class);
    private static final int dimension = BoardConfiguration.getInstance().getDimension();

    // region %%%%%%%%%%%%%%% FXML variables %%%%%%%%%%%%%%%%%%%
    // region board section
    @FXML
    public GridPane boardGridPane;
    @FXML
    public ImageView endGameTokenImageView;
    @FXML
    public Button boardSelectionButton;
    // endregion board section

    // region owner section
    @FXML
    public GridPane ownerBookshelfGridPane;
    @FXML
    public ImageView startingOwnerChair;
    @FXML
    public Label ownerUsernameLabel;
    @FXML
    public Label ownerCurrentPointsLabel;
    @FXML
    public ImageView personalGoalCardImageView;
    @FXML
    public ImageView ownerFirstTokenImageView;
    @FXML
    public ImageView ownerSecondTokenImageView;
    @FXML
    public ImageView ownerThirdTokenImageView;
    @FXML
    public Button bookshelfInsertionButton;
    // insertion
    @FXML
    public VBox insertionVBox;
    @FXML
    public HBox selectedTilesHBox;
    @FXML
    public ImageView firstSelectedTile;
    @FXML
    public ImageView secondSelectedTile;
    @FXML
    public ImageView thirdSelectedTile;
    @FXML
    public HBox selectedTilesNumberedLabelsHBox;
    @FXML
    public Label firstSelectedTilesNumberedLabel;
    @FXML
    public Label secondSelectedTilesNumberedLabel;
    @FXML
    public Label thirdSelectedTilesNumberedLabel;
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
    // endregion owner section

    // region enemy(ies) section
    @FXML
    public GridPane enemyBookshelfGridPane;
    @FXML
    public ImageView startingEnemyChair;
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
    @FXML
    public ImageView enemyFirstTokenImageView;
    @FXML
    public ImageView enemySecondTokenImageView;
    @FXML
    public ImageView enemyThirdTokenImageView;
    // endregion enemy(ies) section

    // region status and common goal card section
    @FXML
    public ImageView firstCommonGoalCardImageView;
    @FXML
    public ImageView secondCommonGoalCardImageView;
    @FXML
    public ImageView firstCommonGoalCardTopTokenImageView;
    @FXML
    public ImageView secondCommonGoalCardTopTokenImageView;
    @FXML
    public Label firstCommonGoalCardDescriptionLabel;
    @FXML
    public Label secondCommonGoalCardDescriptionLabel;
    @FXML
    public Label statusLabel;
    @FXML
    public Label errorLabel;
    // endregion status and common goal card section

    // region chat section
    @FXML
    public Button sendMessageButton;
    @FXML
    public Button quitButton;
    @FXML
    public TextArea chatTextField;
    @FXML
    public ComboBox<String> chatSelectorComboBox;
    @FXML
    public ListView<String> chatPane;
    // endregion chat section

    // endregion %%%%%%%%%%%%%%% FXML variables %%%%%%%%%%%%%%%%%%%

    // region %%%%%%%%%%%%%%% Controller variables %%%%%%%%%%%%%%%%%%%
    // region Constant game variables & references
    private GameViewEventHandler handler;
    private String owner;
    // endregion Constant game variables & references

    // region Model data
    private GameModel model;
    // endregion Model data

    // region Chat data
    private List<ChatTextMessage> messages;
    // endregion Chat data

    // region Controller data
    private boolean hasInitializedUi = false;
    private String currentlySelectedUsername = null;
    // endregion Controller data

    // region Useful Player Sessions
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

    // endregion Useful Player Sessions

    // endregion %%%%%%%%%%%%%%% Controller variables %%%%%%%%%%%%%%%%%%%

    // region %%%%%%%%%%%%%%% Status Variables - Selection %%%%%%%%%%%%%%%%%%%
    private final List<CellInfo> selectedCoordinatesAndValuesList = new ArrayList<>();
    // endregion %%%%%%%%%%%%%%% Status Variables - Selection %%%%%%%%%%%%%%%%%%%

    // region %%%%%%%%%%%%%%% Status Variables - Insertion %%%%%%%%%%%%%%%%%%%
    private int currentlySelectedColumn;
    private final List<Tile> playerOrderedTilesToBeInserted = new ArrayList<>();
    // endregion %%%%%%%%%%%% Status Variables - Insertion %%%%%%%%%%%%%%%%%%%

    // region %%%%%%%%%%%%%%% Iterator %%%%%%%%%%%%%%%%%%%
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

        private List<ImageView> selectedOwnerTilesImages() {
            return Arrays.asList(firstSelectedTile, secondSelectedTile, thirdSelectedTile);
        }

        private List<Label> selectedOwnerTilesLabels() {
            return Arrays.asList(firstSelectedTilesNumberedLabel, secondSelectedTilesNumberedLabel, thirdSelectedTilesNumberedLabel);
        }
    }
    // endregion %%%%%%%%%%%%%%% Iterator %%%%%%%%%%%%%%%%%%%

    // region %%%%%%%%%%%%%%% Initialization %%%%%%%%%%%%%%%%%%%

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
        setBookshelfInsertionButtonClickListener();

        // selection listeners
        setBoardSelectionButtonClickListener();
        setBoardImageViewsTileClickListener();

        // chat listeners
        setSendMessageButtonClickListener();

        // enemy buttons listeners
        setEnemyButtonClickListeners();

        // resets text
        resetSelectionLabelsAndImages();

        // autofollow
        setAutoFollowListener();
    }

    // endregion %%%%%%%%%%%%%%% Initialization %%%%%%%%%%%%%%%%%%%

    // region %%%%%%%%%%%%%%% Game creation %%%%%%%%%%%%%%%%%%%

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

        // owner current points
        PlayerScore ownerPlayerScore = model.getRankings().stream().filter(r -> r.username().equals(owner)).findAny().get();
        ownerCurrentPointsLabel.setText("Your points: " + ownerPlayerScore.total());

        // owner starting player chair
        if (ownerSession().getUsername().equals(startingPlayerSession.getUsername())) {
            UiUtils.visible(startingOwnerChair);
        } else {
            UiUtils.invisible(startingOwnerChair);
        }

        // set selected tiles as empty
        renderSelectedTiles(iter.selectedOwnerTilesImages(), selectedCoordinatesAndValuesList);

        // we set the currently selected enemy as the first one in the list
        currentlySelectedUsername = enemyList.get(0);

        // set ComboBox items for chat
        ChatRender.renderChatComboItems(enemyList, chatSelectorComboBox, model.getGameMode());

        hasInitializedUi = true;

        render();
        renderEnemySection();
    }

    // endregion %%%%%%%%%%%%%%% Game creation %%%%%%%%%%%%%%%%%%%

    // region %%%%%%%%%%%%%%% Updates %%%%%%%%%%%%%%%%%%%

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

    // endregion %%%%%%%%%%%%%%% Updates %%%%%%%%%%%%%%%%%%%

    // region %%%%%%%%%%%%%%% Renders %%%%%%%%%%%%%%%%%%%
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

                // owner section update
                endTurnRender();

                // enemy section update
                renderEnemySection();

                // current player settings
                switch (currentPlayerSession().getPlayerCurrentGamePhase()) {
                    case IDLE -> {
                    }
                    case SELECTING -> {
                        statusLabel.setText("Selection for @" + currentPlayerSession().getUsername());
                    }
                    case INSERTING -> {
                        statusLabel.setText("Insertion for @" + currentPlayerSession().getUsername());
                    }
                }

                // owner actions
                switch (ownerSession().getPlayerCurrentGamePhase()) {
                    case IDLE -> {
                        resetSelectionLabelsAndImages();

                        // all tiles return to null darkening effect
                        disableDarkeningEffectForAllTiles(boardGridPane, model.getBoard());
                    }
                    case SELECTING -> {
                        statusLabel.setText("Select up to 3 tiles from board");
                    }
                    case INSERTING -> {
                        statusLabel.setText("Insert selected tiles in the bookshelf");

                        logger.warn(model.toString());
                        logger.warn(ownerSession().toString());
                        List<CellInfo> selectedTiles = ownerSession().getPlayerTileSelection().getSelection().stream().toList();

                        if (selectedTiles.size() == 1) {
                            // if only one tile is selected, it is automatically selected as first tile
                            renderSelectedTilesWhenOnlyOne(firstSelectedTile, firstSelectedTilesNumberedLabel, selectedTiles, playerOrderedTilesToBeInserted);
                        } else {
                            renderSelectedTiles(iter.selectedOwnerTilesImages(), selectedTiles);
                        }
                    }
                }

                // managing the visibility and invisibility of everything needed in selecting and inserting phases
                autoUpdateVisibility();

                checkAndApplyBoardDarkeningState();
            }
            case ENDED -> {
                gameEnded();
            }
            case STANDBY -> {
                gameStandby();
            }
        }
    }

    public void renderTokens() {
        // owner's tokens
        tokenUpdate(ownerSession(), iter.ownerObtainedTokens());

        // end game token
        boolean hasSomeoneFinished = !model.getSessions().playerSessions().stream().map(player -> player.noMoreTurns).filter(flag -> flag).toList().isEmpty();

        if (hasSomeoneFinished && model.getGameStatus() == LAST_ROUND && model.getGameStatus() == ENDED) {
            TokenRender.renderToken(endGameTokenImageView, null);
        }

        // common goal cards token update
        topTokenUpdate(model, iter.topTokens());
    }

    public void renderEnemySection() {
        if (autoFollowCheckBox.isSelected() && !currentPlayerSession().getUsername().equals(ownerSession().getUsername())) {
            currentlySelectedUsername = currentPlayerSession().getUsername();
        }

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
        PlayerScore enemyPlayerScore = model.getRankings().stream().filter(r -> r.username().equals(currentlySelectedUsername)).findAny().get();
        EnemyStatusLabelRender.renderEnemyStatusLabel(enemyStatusLabel, selectedEnemySession(), enemyPlayerScore.total());
    }

    public void endTurnRender() {
        // owner's bookshelf
        BookshelfRender.renderBookshelf(ownerBookshelfGridPane, ownerSession().getBookshelf());

        // token update
        renderTokens();

        // points update
        PlayerScore ownerPlayerScore = model.getRankings().stream().filter(r -> r.username().equals(owner)).findAny().get();
        ownerCurrentPointsLabel.setText("Your points: " + ownerPlayerScore.total());

        // check if owner has obtained common goal card token
        checkAchievedCommonGoalCards(ownerSession(), model, iter.commonGoalCardsDescriptions());
    }

    // endregion %%%%%%%%%%%%%%% Renders %%%%%%%%%%%%%%%%%%%

    // region %%%%%%%%%%%%%%% Selection phase %%%%%%%%%%%%%%%%%%%

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private void onBoardTileClickHandler(Coordinate selectedCoordinate, Optional<Tile> optionalTileInSelectedCoordinate, ImageView currentImageView) {
        if (ownerSession().getPlayerCurrentGamePhase() != SELECTING) {
            // the owner is stupid and clicks random tiles
            return;
        }

        if (optionalTileInSelectedCoordinate.isEmpty()) {
            // a blank tile has been selected
            return;
        }

        autoUpdateVisibility();

        Set<Coordinate> selectedCoordinatesSet = selectedCoordinatesAndValuesList
                .stream()
                .map(CellInfo::coordinate)
                .collect(Collectors.toSet());

        Tile tile = optionalTileInSelectedCoordinate.get();

        // checks if c was already present in theo set, in that case it is removed
        // if (selectedCoordinates != null) {
        if (selectedCoordinatesSet.contains(selectedCoordinate)) {
            selectedCoordinatesAndValuesList.remove(new CellInfo(selectedCoordinate, tile));
            selectedCoordinatesSet.remove(selectedCoordinate);
            currentImageView.setEffect(null);

            // darken all the tiles that are still valid after the deselection
            checkAndApplyBoardDarkeningState();

            // removes from insertionVBox, no check needed
            renderSelectedTiles(iter.selectedOwnerTilesImages(), selectedCoordinatesAndValuesList);
        } else {
            selectedCoordinatesAndValuesList.add(new CellInfo(selectedCoordinate, tile));
            selectedCoordinatesSet.add(selectedCoordinate);


            if (model.isSelectionValid(selectedCoordinatesSet)) {
                enableDarkeningEffect(currentImageView);

                // darken all the tiles that are not valid anymore after the selection
                checkAndApplyBoardDarkeningState();

                // adds them to insertionVBox
                renderSelectedTiles(iter.selectedOwnerTilesImages(), selectedCoordinatesAndValuesList);
            } else {
                selectedCoordinatesAndValuesList.remove(new CellInfo(selectedCoordinate, tile));
                selectedCoordinatesSet.remove(selectedCoordinate);
                statusLabel.setText("Selection error");
                errorLabel.setText("Player " + ownerSession().getUsername() + " you can't select this tile.");
            }
        }
    }

    public void onSelectionButtonClicked() {
        autoUpdateVisibility();
        Set<Coordinate> selectedCoordinatesSet = selectedCoordinatesAndValuesList.stream().map(CellInfo::coordinate).collect(Collectors.toSet());

        // if selection is right (between 1 and 3 and acceptable tiles) sends coordinates to handler
        if (model.isSelectionValid(selectedCoordinatesSet)) {
            handler.onViewSelection(selectedCoordinatesSet);
        } else {
            errorLabel.setText("The tiles you selected are not valid.");
        }
    }

    // endregion %%%%%%%%%%%%%%% Selection phase %%%%%%%%%%%%%%%%%%%

    // region %%%%%%%%%%%%%%% Insertion phase %%%%%%%%%%%%%%%%%%%

    public void onInsertionButtonClicked() {
        autoUpdateVisibility();

        if (playerOrderedTilesToBeInserted.size() == selectedCoordinatesAndValuesList.size() && currentlySelectedColumn >= 0 && currentlySelectedColumn < 5) {
            handler.onViewInsertion(currentlySelectedColumn, playerOrderedTilesToBeInserted);
            playerOrderedTilesToBeInserted.clear();
            selectedCoordinatesAndValuesList.clear();
        } else {
            errorLabel.setText("You have to select all the tiles.");
        }
    }

    private void onSelectedTilesClickHandler(int index, ImageView selectedTileImageView, Label selectedTextLabel) {
        logger.info("onSelectedTilesClickHandler(index={}, selectedTileImageView={})", index, selectedTileImageView);
        List<Tile> playerSelectedTiles = model.getPlayerSession(owner).getPlayerTileSelection().getSelectedTiles().stream().toList();

        // " ", 1, 2 or 3
        String selectionLabelText = iter.selectedOwnerTilesLabels().get(index).getText();

        if (selectionLabelText.equals(" ")) {
            // if it still has the default value of X
            // remove tile darkened effect
            selectedTileImageView.setEffect(null);

            // add the tile to the orderedTiles list
            playerOrderedTilesToBeInserted.add(playerSelectedTiles.get(index));

            // set the label to the current orderedTiles size
            selectedTextLabel.setText(String.valueOf(playerOrderedTilesToBeInserted.size()));
        } else {
            // if it didn't have the default value, it means it was already selected
            // change all the other labels
            int currentLabelValue = Integer.parseInt(selectionLabelText);

            for (int i = 0; i < iter.selectedOwnerTilesLabels().size(); i++) {
                String otherLabelValue = iter.selectedOwnerTilesLabels().get(i).getText();

                if (i != index && !otherLabelValue.equals(" ") && Integer.parseInt(otherLabelValue) > currentLabelValue) {
                    String newText = String.valueOf(Integer.parseInt(otherLabelValue) - 1);
                    iter.selectedOwnerTilesLabels().get(i).setText(newText);
                }
            }

            // remove the tile from the orderedTiles list
            playerOrderedTilesToBeInserted.remove(playerSelectedTiles.get(index));

            // set the darkened effect to the ImageView
            enableDarkeningEffect(selectedTileImageView);

            // make the label invisible and set it to 0 again
            iter.selectedOwnerTilesLabels().get(index).setText(" ");
        }
    }

    void resetSelectionLabelsAndImages() {
        iter.selectedOwnerTilesLabels().forEach(it -> it.setText(" "));
        iter.selectedOwnerTilesImages().forEach(it -> it.setImage(null));
    }

    // endregion %%%%%%%%%%%%%%% Insertion phase %%%%%%%%%%%%%%%%%%%

    // region %%%%%%%%%%%%%%% Game ended %%%%%%%%%%%%%%%%%%%

    public void gameEnded() {
        renderEnemySection();
        endTurnRender();
        resetSelectionLabelsAndImages();
        disableDarkeningEffectForAllTiles(boardGridPane, model.getBoard());

        renderRanking(model.getRankings());
    }

    // endregion %%%%%%%%%%%%%%% Game ended %%%%%%%%%%%%%%%%%%%

    // region %%%%%%%%%%%%%%% Game standby %%%%%%%%%%%%%%%%%%%
    public void gameStandby() {

    }
    // endregion %%%%%%%%%%%%%%% Game standby %%%%%%%%%%%%%%%%%%%

    // region %%%%%%%%%%%%%%% Chat %%%%%%%%%%%%%%%%%%%

    private void onChatTextSend(String senderUsername, MessageRecipient recipient, String text) {
        handler.onViewSendMessage(senderUsername, recipient, text);
        chatTextField.clear();
    }

    // endregion %%%%%%%%%%%%%%% Chat %%%%%%%%%%%%%%%%%%%

    // region %%%%%%%%%%%%%%% Utils %%%%%%%%%%%%%%%%%%%

    public void autoUpdateVisibility() {
        switch (ownerSession().getPlayerCurrentGamePhase()) {
            case IDLE -> {
                UiUtils.invisible(insertionVBox, boardSelectionButton, bookshelfInsertionButton, selectedTilesNumberedLabelsHBox);
            }
            case SELECTING -> {
                UiUtils.visible(boardSelectionButton, selectedTilesHBox, insertionVBox);
                UiUtils.invisible(radioButtonHBox, selectedTilesNumberedLabelsHBox, bookshelfInsertionButton);
            }
            case INSERTING -> {
                UiUtils.visible(bookshelfInsertionButton, radioButtonHBox, selectedTilesNumberedLabelsHBox, selectedTilesHBox);
                UiUtils.invisible(boardSelectionButton);
            }
        }
    }

    public void checkAndApplyBoardDarkeningState() {
        if (ownerSession().getPlayerCurrentGamePhase() == SELECTING) {
            Set<Coordinate> selectedCoordinatesSet = selectedCoordinatesAndValuesList.stream().map(CellInfo::coordinate).collect(Collectors.toSet());
            // non-selectable tiles darken
            makeNonSelectableTilesDark(boardGridPane, model, selectedCoordinatesSet);
        }
    }
    // endregion %%%%%%%%%%%%%%% Utils %%%%%%%%%%%%%%%%%%%

    // region %%%%%%%%%%%%%%%%%%% Replies %%%%%%%%%%%%%%%%%%%

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
                    case WRONG_GAME_PHASE -> errorLabel.setText("Error, wrong game phase");
                    case UNAUTHORIZED_SELECTION -> errorLabel.setText("Error, unauthorized selection");
                    case UNAUTHORIZED_PLAYER -> errorLabel.setText("Error, player not authorized");
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
                switch (failure.error()) {
                    case WRONG_SELECTION -> errorLabel.setText("Error, illegal selection");
                    case ILLEGAL_COLUMN -> errorLabel.setText("Error, column out of bounds");
                    case TOO_MANY_TILES -> errorLabel.setText("Error, too many tiles selected");
                    case NO_FIT -> errorLabel.setText("Error, the selected tiles can't fit in this columns");
                    case WRONG_PLAYER -> errorLabel.setText("Error, unauthorized action from non active player");
                    case WRONG_GAME_PHASE -> errorLabel.setText("Error, wrong game phase");
                }
            }
            case TypedResult.Success<TileInsertionSuccess, BookshelfInsertionFailure> success -> {
                var data = success.value();
                modelUpdate(data.model());
            }
        }
    }

    // endregion %%%%%%%%%%%%%%%%%%% Replies %%%%%%%%%%%%%%%%%%%

    // region %%%%%%%%%%%%%%%%%%% ClickListeners %%%%%%%%%%%%%%%%%%%
    public void setRadioButtonsClickListeners() {
        for (int i = 0; i < iter.columnRadioButtons().size(); i++) {
            final int finalizedIndex = i;
            RadioButton columnRadioButton = iter.columnRadioButtons().get(finalizedIndex);
            columnRadioButton.setOnMouseClicked(mouseEvent -> currentlySelectedColumn = finalizedIndex);
        }
    }

    public void setBookshelfInsertionButtonClickListener() {
        bookshelfInsertionButton.setOnMouseClicked(mouseEvent -> {
                    if (model.getPlayerSession(owner).getPlayerCurrentGamePhase() == INSERTING) {
                        onInsertionButtonClicked();
                    }
                }
        );
    }

    public void setBoardSelectionButtonClickListener() {
        boardSelectionButton.setOnMouseClicked(mouseEvent -> {
                    if (model.getPlayerSession(owner).getPlayerCurrentGamePhase() == SELECTING) {
                        onSelectionButtonClicked();
                    }
                }
        );
    }

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

    @FXML
    public void onFirstSelectedOwnerTile() {
        logger.warn("CLICK ON 1");

        PlayerTileSelection playerTileSelection = model.getCurrentPlayerSession().getPlayerTileSelection();
        if (playerTileSelection != null) {
            onSelectedTilesClickHandler(0, firstSelectedTile, firstSelectedTilesNumberedLabel);
        }
    }

    @FXML
    public void onSecondSelectedOwnerTile() {
        logger.warn("CLICK ON 2");

        PlayerTileSelection playerTileSelection = model.getCurrentPlayerSession().getPlayerTileSelection();
        if (playerTileSelection != null && playerTileSelection.getSelectedTiles().size() >= 2) {
            onSelectedTilesClickHandler(1, secondSelectedTile, secondSelectedTilesNumberedLabel);
        }
    }

    @FXML
    public void onThirdSelectedOwnerTile() {
        logger.warn("CLICK ON 3");

        PlayerTileSelection playerTileSelection = model.getCurrentPlayerSession().getPlayerTileSelection();
        if (playerTileSelection != null && playerTileSelection.getSelectedTiles().size() == 3) {
            onSelectedTilesClickHandler(2, thirdSelectedTile, thirdSelectedTilesNumberedLabel);
        }
    }

    public MessageRecipient parseRecipientFromComboBox() {
        String currentlySelectedEntry = chatSelectorComboBox.getValue();

        if (currentlySelectedEntry.equals("Everyone")) {
            return new MessageRecipient.Broadcast();
        } else {
            return new MessageRecipient.Direct(currentlySelectedEntry);
        }
    }

    public void setSendMessageButtonClickListener() {
        sendMessageButton.setOnMouseClicked(mouseEvent -> {
            if (!chatTextField.getText().isEmpty()) {
                onChatTextSend(owner, parseRecipientFromComboBox(), chatTextField.getText());
            }
        });
    }

    public void setEnemyButtonClickListeners() {
        enemySelect1Button.setOnMouseClicked(mouseEvent -> {
            if (autoFollowCheckBox.isSelected()) {
                // removes auto follow
                autoFollowCheckBox.setSelected(false);
            }

            currentlySelectedUsername = enemySelect1Button.getText();
            renderEnemySection();
        });

        enemySelect2Button.setOnMouseClicked(mouseEvent -> {
            if (autoFollowCheckBox.isSelected()) {
                // removes auto follow
                autoFollowCheckBox.setSelected(false);
            }

            currentlySelectedUsername = enemySelect2Button.getText();
            renderEnemySection();
        });

        enemySelect3Button.setOnMouseClicked(mouseEvent -> {
            if (autoFollowCheckBox.isSelected()) {
                // removes auto follow
                autoFollowCheckBox.setSelected(false);
            }

            currentlySelectedUsername = enemySelect3Button.getText();
            renderEnemySection();
        });
    }

    public void setAutoFollowListener() {
        autoFollowCheckBox.setOnMouseClicked(mouseEvent -> {
            renderEnemySection();
        });
    }
    // endregion %%%%%%%%%%%%%%%%%%% ClickListeners %%%%%%%%%%%%%%%%%%%
}