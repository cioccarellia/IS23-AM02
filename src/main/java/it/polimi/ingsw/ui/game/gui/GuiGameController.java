package it.polimi.ingsw.ui.game.gui;


import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.SingleResult.Failure;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.score.PlayerScore;
import it.polimi.ingsw.model.player.PlayerSession;
import it.polimi.ingsw.ui.game.GameGateway;
import it.polimi.ingsw.ui.game.GameViewEventHandler;
import it.polimi.ingsw.ui.game.gui.render.*;
import it.polimi.ingsw.ui.game.gui.utils.GuiResources;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 * The GuiGameController class is responsible for managing the graphical user interface (GUI)
 * for the game. It handles user interactions, updates the GUI elements based on the game model,
 * and communicates with the game logic through the GameViewEventHandler interface.
 */
public class GuiGameController implements GameGateway {

    private static final Logger logger = LoggerFactory.getLogger(GuiGameController.class);

    private static final int maxSelectionSize = LogicConfiguration.getInstance().maxSelectionSize();
    private static final int commonGoalCardsAmount = LogicConfiguration.getInstance().commonGoalCardAmount();


    private static final int col = 0;
    private final List<Tile> orderedTiles = new ArrayList<>();
    private final Set<Coordinate> selectedCoordinates = new HashSet<>();

    @FXML
    public GridPane board;
    @FXML
    public GridPane ownerBookshelf;
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
    public ImageView firstCommonGoalCardTopToken = new ImageView();
    @FXML
    public ImageView secondCommonGoalCardTopToken = new ImageView();
    @FXML
    public ImageView firstCommonGoalCard = new ImageView();
    @FXML
    public ImageView secondCommonGoalCard = new ImageView();
    @FXML
    public ImageView personalGoalCard = new ImageView();
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
    public ImageView insertionCommonGoalCard1 = new ImageView();
    @FXML
    public ImageView insertionCommonGoalCard2 = new ImageView();
    @FXML
    public ImageView insertionPersonalGoalCard = new ImageView();
    @FXML
    public Label Status;
    @FXML
    public Label CurrentPlayer;
    @FXML
    public Label insertionStatus;
    @FXML
    public GridPane insertionBookshelf;
    @FXML
    public ImageView tile1Selected = new ImageView();
    @FXML
    public ImageView tile2Selected = new ImageView();
    @FXML
    public ImageView tile3Selected = new ImageView();

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
    public void initModel(Game model, GameViewEventHandler handler, String owner) {
        this.model = model;
        this.handler = handler;
        this.owner = owner;
    }


    private List<ImageView> selectedTileList() {
        return Arrays.asList(tile1Selected, tile2Selected, tile3Selected);
    }

    private List<ImageView> insertionCommonGoalCard() {
        return Arrays.asList(insertionCommonGoalCard1, insertionCommonGoalCard2);
    }

    private List<GridPane> bookshelves() {
        return Arrays.asList(player1BookShelf, player2BookShelf, player3BookShelf, player4BookShelf);
    }

    private List<ImageView> topTokens() {
        return Arrays.asList(firstCommonGoalCardTopToken, secondCommonGoalCardTopToken);
    }

    private List<ImageView> commonGoalCards() {
        return Arrays.asList(firstCommonGoalCard, secondCommonGoalCard);
    }

    private List<Tab> playersButtons() {
        return Arrays.asList(player1Button, player2Button, player3Button, player4Button);
    }


    /**
     * Called when the game is created. It initializes the GUI elements and starts the game.
     */
    @Override
    public void onGameCreated() {
        if (model == null)
            return;

        processModel();
    }


    /**
     * Updates the game model and refreshes the GUI elements based on the updated model.
     *
     * @param game The updated game model.
     */
    @Override
    public void modelUpdate(Game game) {
        this.model = game;
        processModel();
    }


    public void processModel() {
        if (model == null) {
            return;
        }
        switch (model.getGameStatus()) {
            case RUNNING, LAST_ROUND -> {
                PlayerSession currentPlayer = model.getCurrentPlayerSession();

                boolean isOwnerTurn = currentPlayer.getUsername().equals(owner);

                //board
                BoardRender.renderBoard(model, board);


                //CGC
                for (int i = 0; i < commonGoalCardsAmount; i++) {
                    CommonGoalCard commonGoalCard = model.getCommonGoalCards().get(i).getCommonGoalCard();

                    //Image commonGoalCardImage = new Image(GuiResources.getCommonGC(commonGoalCard.getId()));

                    //commonGoalCards().get(i).setImage(commonGoalCardImage);

                    //commonGoalCards().get(i).setImage(CommonGoalCardsRender.generateCommonGoalCardImageView(commonGoalCard).getImage());
                    commonGoalCards().get(i).setImage(CommonGoalCardsRender.generateCommonGoalCardImageView(commonGoalCard));


                }

                //firstCommonGoalCard = CommonGoalCardsRender.generateCommonGoalCardImageView(model.getCommonGoalCards().get(0).getCommonGoalCard());
                //secondCommonGoalCard = CommonGoalCardsRender.generateCommonGoalCardImageView(model.getCommonGoalCards().get(1).getCommonGoalCard());


                //PGC
                personalGoalCard.setImage(new Image(GuiResources.getPersonalGC(model.getPlayerSession(owner).getPersonalGoalCard())));
                //personalGoalCard = PersonalGoalCardRender.generatePersonalGoalCardImageView(model, owner);

                //players' bookshelf update
                for (int i = 0; i < model.getGameMode().maxPlayerAmount(); i++) {
                    String username = model.getPlayersUsernameList().get(i);
                    BookshelfRender.regenerateBookshelfGridPane(model.getSessions().getByUsername(username).getBookshelf(), bookshelves().get(i));
                }

                //players buttons
                for (int i = 0; i < model.getSessions().size(); i++){
                    playersButtons().get(i).setText(model.getPlayersUsernameList().get(i));
                    //capire come svuotare i bottoni che non sono usati quando non ci sono 4 giocatori
                    // settiamoli come invisibili nel fxml e rendiamo visibili qui quelli che servono
                }

                //tokens
                endGameToken = EndGameTokenRender.generateEndTokenImageView();


                for (int i = 0; i < commonGoalCardsAmount; i++) {
                    topTokens().get(i).setImage(CommonGoalCardsTokenRender.generateTokenImageView(model.getCommonGoalCards().get(i)));
                }

                CurrentPlayer.setText(model.getCurrentPlayerSession().getUsername());

                if (isOwnerTurn) {
                    switch (currentPlayer.getPlayerCurrentGamePhase()) {
                        case IDLE -> {
                            //errore
                        }
                        case SELECTING -> {
                            gameSelection();
                        }
                        case INSERTING -> {
                            gameInsertion();
                        }
                    }
                }
            }
            case ENDED -> {
                onGameEnded();
            }
            case STANDBY -> {
                onGameStandby();
            }
        }
    }

    public void gameSelection() {
        onSelectingButtonClick();
    }

    public void gameInsertion() {
        for (int i = 0; i < commonGoalCardsAmount; i++) {
            CommonGoalCard commonGoalCard = model.getCommonGoalCards().get(i).getCommonGoalCard();
            insertionCommonGoalCard().get(i).setImage(CommonGoalCardsRender.generateCommonGoalCardImageView(commonGoalCard));
        }

        insertionPersonalGoalCard = PersonalGoalCardRender.generatePersonalGoalCardImageView(model, owner);


        BookshelfRender.regenerateBookshelfGridPane(model.getSessions().getByUsername(owner).getBookshelf(), insertionBookshelf);

        SceneManager.changeScene(SceneManager.getActualController(), "inserting.fxml");
    }


    /**
     * Handles the game selection reply and updates the GUI elements based on the result.
     *
     * @param turnResult The result of the tile selection operation.
     */
    @Override
    public void onGameSelectionReply(SingleResult<TileSelectionFailures> turnResult) {
        switch (turnResult) {
            case Failure<TileSelectionFailures> failure -> {
                switch (failure.error()) {
                    case WRONG_GAME_PHASE -> {
                        Status.setOpacity(1);
                        Status.setText("Error, wrong game phase");

                    }
                    case UNAUTHORIZED_SELECTION -> {
                        Status.setOpacity(1);
                        Status.setText("Error, unauthorized selection");

                    }
                    case UNAUTHORIZED_PLAYER -> {
                        Status.setOpacity(1);
                        Status.setText("Error, player not authorized");
                    }
                }
            }
            case SingleResult.Success<TileSelectionFailures> success -> Status.setOpacity(0);
        }
    }

    /**
     * Handles the game insertion reply and updates the GUI elements based on the result.
     *
     * @param turnResult The result of the bookshelf insertion operation.
     */
    @Override
    public void onGameInsertionReply(SingleResult<BookshelfInsertionFailure> turnResult) {
        switch (turnResult) {
            case Failure<BookshelfInsertionFailure> failure -> {
                switch (failure.error()) {
                    case WRONG_SELECTION -> {
                        insertionStatus.setOpacity(1);
                        insertionStatus.setText("Error, wrong selection");
                    }

                    case ILLEGAL_COLUMN -> {
                        insertionStatus.setOpacity(1);
                        insertionStatus.setText("Error, column out of bounds");
                    }

                    case TOO_MANY_TILES -> {
                        insertionStatus.setOpacity(1);
                        insertionStatus.setText("Error, too many tiles selected");
                    }

                    case NO_FIT -> {
                        insertionStatus.setOpacity(1);
                        insertionStatus.setText("Error, selected tiles can't fit in this columns");
                    }

                    case WRONG_PLAYER -> {
                        insertionStatus.setOpacity(1);
                        insertionStatus.setText("Error, unauthorized action from non active player");
                    }

                    case WRONG_GAME_PHASE -> {
                        insertionStatus.setOpacity(1);
                        insertionStatus.setText("Error, wrong game phase");
                    }
                }
            }
            case SingleResult.Success<BookshelfInsertionFailure> success -> insertionStatus.setOpacity(0);
        }


    }

    @FXML
    public void onSelectingButtonClick() {
        handler.onViewSelection(selectedCoordinates);
        //SceneManager.changeScene(SceneManager.getActualController(), "inserting.fxml");
    }

    public void onGameEnded() {
        //todo
        List<PlayerScore> playerRanking = model.getRankings();
        SceneManager.changeScene(SceneManager.getActualController(), "ranking.fxml");
    }

    public void onGameStandby() {
        //
    }


    public void setSelectedCoordinatesListener(MouseEvent mouseEvent) {
        Coordinate coordinate;

        Integer col = GridPane.getColumnIndex((Node) mouseEvent.getSource());

        Integer row = GridPane.getRowIndex((Node) mouseEvent.getSource());

        coordinate = new Coordinate(row, col);
        selectedCoordinates.add(coordinate);
    }
}