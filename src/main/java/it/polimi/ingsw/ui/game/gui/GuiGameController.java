package it.polimi.ingsw.ui.game.gui;


import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.controller.server.result.types.TileInsertionSuccess;
import it.polimi.ingsw.controller.server.result.types.TileSelectionSuccess;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.chat.ChatTextMessage;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
import it.polimi.ingsw.model.game.CellInfo;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.PlayerSession;
import it.polimi.ingsw.ui.Renderable;
import it.polimi.ingsw.ui.game.GameGateway;
import it.polimi.ingsw.ui.game.GameViewEventHandler;
import it.polimi.ingsw.ui.game.gui.renders.BoardRender;
import it.polimi.ingsw.ui.game.gui.renders.BookshelfRender;
import it.polimi.ingsw.ui.game.gui.renders.PersonalGoalCardRender;
import it.polimi.ingsw.ui.game.gui.renders.TokenRender;
import it.polimi.ingsw.utils.javafx.PaneViewUtil;
import it.polimi.ingsw.utils.javafx.UiUtils;
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
    public HBox selectedTilesHBox;
    @FXML
    public ImageView firstSelectedTile;
    @FXML
    public ImageView secondSelectedTile;
    @FXML
    public ImageView thirdSelectedTile;

    // Labels and HBox for selected tiles
    @FXML
    public HBox selectedTilesNumberedLabels;
    @FXML
    public Label firstSelectedTilesNumberedLabel;
    @FXML
    public Label secondSelectedTilesNumberedLabel;
    @FXML
    public Label thirdSelectedTilesNumberedLabel;


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

    private int currentlySelectedColumn;

    private List<Tile> orderedTiles;

    private Set<Coordinate> selectedCoordinates = new HashSet<>();


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
        setRadioButtonsClickListeners();
        setImageViewClickListeners();
        setEnemyButtonClickListeners();
        setBoardSelectionButtonClickListener();
        setBookshelfInsertionButtonClickListener();
        setBoardImageViewsTileClickListener();
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
        PlayerSession startingPlayerSession = model.getStartingPlayerSession();


        // common goal cards and their description and their token
        commonGoalCardsOnCreation(model, iter.commonGoalCards(), iter.commonGoalCardsDescriptions(), iter.topTokens());

        // end game token
        TokenRender.renderToken(endGameTokenImageView, FULL_SHELF_TOKEN);

        // personal goal card
        PersonalGoalCardRender.renderPersonalGoalCard(personalGoalCardImageView, ownerSession.getPersonalGoalCard());

        // enemy buttons
        List<String> enemyList = model.getPlayersUsernameListExcluding(owner);

        for (int i = 0; i < enemyList.size(); i++) {
            iter.enemyButtons().get(i).setText(enemyList.get(i));
            iter.enemyButtons().get(i).setVisible(true);
        }

        // owner username label
        ownerUsernameLabel.setText("Hi " + ownerSession.getUsername());

        // owner starting player chair
        if (ownerSession.getUsername().equals(startingPlayerSession.getUsername())) {
            UiUtils.visible(startingOwnerChair);
        }


        currentlySelectedUsername = enemyList.get(0);


        hasInitializedUi = true;

        // enemyStatusLabel

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

        switch (model.getGameStatus()) {
            case RUNNING, LAST_ROUND -> {
                PlayerSession ownerSession = model.getSessions().getByUsername(owner);
                PlayerSession currentPlayerSession = model.getCurrentPlayerSession();
                PlayerSession enemySession = model.getSessionFor(currentlySelectedUsername);
                PlayerSession startingPlayerSession = model.getStartingPlayerSession();


                // board
                BoardRender.renderBoard(boardGridPane, model.getBoard());

                // owner's bookshelf
                BookshelfRender.renderBookshelf(ownerBookshelfGridPane, ownerSession.getBookshelf());

                // selected enemy's bookshelf
                BookshelfRender.renderBookshelf(enemyBookshelfGridPane, enemySession.getBookshelf());

                // enemy username label
                enemyUsernameLabel.setText("@" + currentlySelectedUsername);

                // enemy starting player chair
                if (enemySession.getUsername().equals(startingPlayerSession.getUsername())) {
                    UiUtils.visible(startingEnemyChair);
                }

                // tokens update
                // owner's tokens
                tokenUpdate(ownerSession, iter.ownerObtainedTokens());

                // enemy's token
                tokenUpdate(enemySession, iter.enemyObtainedTokens());

                // end game token
                boolean hasSomeoneFinished = !model.getSessions().playerSessions().stream().map(player -> player.noMoreTurns).filter(flag -> flag).toList().isEmpty();

                if (hasSomeoneFinished && model.getGameStatus() == LAST_ROUND) {
                    TokenRender.renderToken(endGameTokenImageView, null);
                }

                // check if owner has obtained common goal card token
                checkAchievedCommonGoalCards(ownerSession, model, iter.commonGoalCardsDescriptions());

                // common goal cards token update
                topTokenUpdate(model, iter.topTokens());


                // enemy label (enemyStatusLabel)

                // set selected tiles
                setSelectedTiles(iter.selectedTiles(), selectedCoordinates, model);


                // statusTitleLabel and statusSubtitleLabel
                //switch (currentPlayerSession.getPlayerCurrentGamePhase()) { //todo non si sta aggiornando
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
                manageVisibilityInGamePhases(ownerSession);

                manageBoardDarkening();
            }
            case ENDED -> {
                //gameEnded();
            }
            case STANDBY -> {
                //gameStandby();
            }
        }
    }

    public void manageVisibilityInGamePhases(PlayerSession ownerSession) {
        switch (ownerSession.getPlayerCurrentGamePhase()) {
            case IDLE -> {
                UiUtils.invisible(insertionVBox, boardSelectionButton, bookshelfInsertionButton);
            }
            case SELECTING -> {
                UiUtils.visible(boardSelectionButton, selectedTilesHBox);
            }
            case INSERTING -> {
                UiUtils.visible(insertionVBox, bookshelfInsertionButton);
                UiUtils.invisible(boardSelectionButton);
            }
        }
    }

    public void manageBoardDarkening() {
        PlayerSession ownerSession = model.getPlayerSession(owner);

        if (ownerSession.getPlayerCurrentGamePhase() == SELECTING) {
            // non-selectable tiles darken
            Set<Coordinate> currentCoordinates;
            if (ownerSession.getPlayerTileSelection() != null) {
                currentCoordinates = ownerSession.getPlayerTileSelection().getSelection().stream().map(CellInfo::coordinate).collect(Collectors.toSet());
            } else {
                currentCoordinates = selectedCoordinates;
            }
            makeNonSelectableTilesDark(boardGridPane, model, currentCoordinates);
        }
    }

    public void gameSelection() {
        PlayerSession ownerSession = model.getPlayerSession(owner);
        manageVisibilityInGamePhases(ownerSession);

        // while selection is not right (between 1 and 3 and acceptable tiles) shows error
        while (!model.isSelectionValid(selectedCoordinates)) {
            statusTitleLabel.setText("Selection error");
            statusSubtitleLabel.setText("Player " + ownerSession.getUsername() + " the tiles you selected are not valid.");
        }

        setSelectedTiles(iter.selectedTiles(), selectedCoordinates, model);

        handler.onViewSelection(selectedCoordinates);
    }

    public void gameInsertion() {
        PlayerSession ownerSession = model.getPlayerSession(owner);
        manageVisibilityInGamePhases(ownerSession);

        while (orderedTiles.size() != ownerSession.getPlayerTileSelection().getSelectedTiles().size() && (currentlySelectedColumn < 0 || currentlySelectedColumn > 5)) {
            statusTitleLabel.setText("Insertion error");
            statusSubtitleLabel.setText("Player " + ownerSession.getUsername() + ", you have to select all the tiles.");
        }

        handler.onViewInsertion(currentlySelectedColumn, orderedTiles);
        orderedTiles.clear();
        selectedCoordinates.clear();
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
                statusTitleLabel.setText("Selection error");
                switch (failure.error()) {
                    case WRONG_GAME_PHASE -> {
                        statusSubtitleLabel.setText("Error, wrong game phase");
                    }
                    case UNAUTHORIZED_SELECTION -> {
                        statusSubtitleLabel.setText("Error, unauthorized selection");
                    }
                    case UNAUTHORIZED_PLAYER -> {
                        statusSubtitleLabel.setText("Error, player not authorized");
                    }
                }
            }
            case TypedResult.Success<TileSelectionSuccess, TileSelectionFailures> success -> {
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
                    case WRONG_SELECTION -> {
                        statusSubtitleLabel.setText("Error, illegal selection");
                    }

                    case ILLEGAL_COLUMN -> {
                        statusSubtitleLabel.setText("Error, column out of bounds");
                    }

                    case TOO_MANY_TILES -> {
                        statusSubtitleLabel.setText("Error, too many tiles selected");
                    }

                    case NO_FIT -> {
                        statusSubtitleLabel.setText("Error, the selected tiles can't fit in this columns");
                    }

                    case WRONG_PLAYER -> {
                        statusSubtitleLabel.setText("Error, unauthorized action from non active player");
                    }

                    case WRONG_GAME_PHASE -> {
                        statusSubtitleLabel.setText("Error, wrong game phase");
                    }
                }
            }
            case TypedResult.Success<TileInsertionSuccess, BookshelfInsertionFailure> success -> {
            }

        }
    }


    public void setBookshelfInsertionButtonClickListener() {
        bookshelfInsertionButton.setOnMouseClicked(mouseEvent -> {
                    if (model.getPlayerSession(owner).getPlayerCurrentGamePhase() == INSERTING) {
                        gameInsertion();
                    }
                }
        );
    }

    public void setBoardSelectionButtonClickListener() {
        boardSelectionButton.setOnMouseClicked(mouseEvent -> {
                    if (model.getPlayerSession(owner).getPlayerCurrentGamePhase() == SELECTING) {
                        gameSelection();
                    }
                }
        );
    }

    private void setRadioButtonsClickListeners() {
        columnSelection1RadioButton.setOnMouseClicked(mouseEvent -> currentlySelectedColumn = 0);
        columnSelection2RadioButton.setOnMouseClicked(mouseEvent -> currentlySelectedColumn = 1);
        columnSelection3RadioButton.setOnMouseClicked(mouseEvent -> currentlySelectedColumn = 2);
        columnSelection4RadioButton.setOnMouseClicked(mouseEvent -> currentlySelectedColumn = 3);
        columnSelection5RadioButton.setOnMouseClicked(mouseEvent -> currentlySelectedColumn = 4);
    }

    private void setImageViewClickListeners() {
        if (model == null)
            return;

        List<Tile> playerSelectedTiles = model.getPlayerSession(owner).getPlayerTileSelection().getSelectedTiles();

        firstSelectedTile.setOnMouseClicked(mouseEvent -> {
            manageTileSelectionForInsertion(firstSelectedTilesNumberedLabel, firstSelectedTile, orderedTiles, playerSelectedTiles, iter.selectedTilesLabels(), 0);
        });

        secondSelectedTile.setOnMouseClicked(mouseEvent -> {
            manageTileSelectionForInsertion(secondSelectedTilesNumberedLabel, secondSelectedTile, orderedTiles, playerSelectedTiles, iter.selectedTilesLabels(), 1);
        });

        thirdSelectedTile.setOnMouseClicked(mouseEvent -> {
            manageTileSelectionForInsertion(thirdSelectedTilesNumberedLabel, thirdSelectedTile, orderedTiles, playerSelectedTiles, iter.selectedTilesLabels(), 2);
        });
    }


    private void setEnemyButtonClickListeners() {
        enemySelect1Button.setOnMouseClicked(mouseEvent -> {
            currentlySelectedUsername = enemySelect1Button.getText();
            render();
        });

        enemySelect2Button.setOnMouseClicked(mouseEvent -> {
            currentlySelectedUsername = enemySelect2Button.getText();
            render();
        });

        enemySelect3Button.setOnMouseClicked(mouseEvent -> {
            currentlySelectedUsername = enemySelect3Button.getText();
            render();
        });
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
                    boardClickHandler(c, matchingTile, matchingImageView);
                });
            }
        }
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private void boardClickHandler(Coordinate coordinate, Optional<Tile> t, ImageView currentImageView) {
        PlayerSession ownerSession = model.getSessionFor(owner);

        if (ownerSession.getPlayerCurrentGamePhase() != SELECTING) {
            return;
        }

        if (t.isEmpty()) {
            return;
        }

        manageVisibilityInGamePhases(ownerSession);

        // checks if c was already present in the set, in that case it is removed
        //if (selectedCoordinates != null) {
        if (selectedCoordinates.contains(coordinate)) {
            selectedCoordinates.remove(coordinate);
            currentImageView.setEffect(null);
            // removes from insertionVBox
            setSelectedTiles(iter.selectedTiles(), selectedCoordinates, model);
        } else {
            selectedCoordinates.add(coordinate);

            if (model.isSelectionValid(selectedCoordinates)) {
                setDarkeningEffect(currentImageView);

                // darken all the tiles that are not valid after this selection
                manageBoardDarkening();
                //adds them to insertionVBox
                setSelectedTiles(iter.selectedTiles(), selectedCoordinates, model);
            } else {
                selectedCoordinates.remove(coordinate);
                statusTitleLabel.setText("Selection error");
                statusSubtitleLabel.setText("Player " + ownerSession.getUsername() + " you can't select this tile.");
            }
        }
    }
}