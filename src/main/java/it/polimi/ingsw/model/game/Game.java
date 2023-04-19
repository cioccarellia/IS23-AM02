package it.polimi.ingsw.model.game;

import it.polimi.ingsw.controller.EventControl;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import it.polimi.ingsw.model.cards.personal.PersonalGoalCard;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
import it.polimi.ingsw.model.game.extractors.CommonGoalCardExtractor;
import it.polimi.ingsw.model.game.extractors.PersonalGoalCardExtractor;
import it.polimi.ingsw.model.game.extractors.TileExtractor;
import it.polimi.ingsw.model.game.goal.CommonGoalCardStatus;
import it.polimi.ingsw.model.game.goal.Token;
import it.polimi.ingsw.model.game.session.SessionManager;
import it.polimi.ingsw.model.player.PlayerNumber;
import it.polimi.ingsw.model.player.PlayerSession;
import it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase;
import it.polimi.ingsw.model.player.selection.PlayerTileSelection;
import it.polimi.ingsw.utils.CollectionUtils;
import it.polimi.ingsw.utils.model.CoordinatesHelper;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Model class representing an instance of a game.
 */
public class Game implements EventControl {

    // Game logger
    private static final Logger logger = LoggerFactory.getLogger(Game.class);
    /**
     * Basic game related data
     */
    private final GameMode mode;
    private final Board board = new Board();

    /**
     * Maps a player to its {@link PlayerSession}
     */
    private final SessionManager sessions;

    /**
     * Random, stateful extractors for the game
     */
    private final TileExtractor tileExtractor = new TileExtractor();
    private final CommonGoalCardExtractor commonGoalCardExtractor = new CommonGoalCardExtractor();
    private final PersonalGoalCardExtractor personalGoalCardExtractor = new PersonalGoalCardExtractor();

    /**
     * Holder class for the common goal cards
     * Holds the current statuses for the common goal cards.
     */
    private final List<CommonGoalCardStatus> commonGoalCardStatuses = new ArrayList<>();

    /**
     * External game configuration parameters
     */
    private final LogicConfiguration config = LogicConfiguration.getInstance();
    private GameStatus status = GameStatus.INITIALIZATION;

    /**
     * Markers for the current state of the game (needed for turn logic)
     */
    private PlayerNumber startingPlayerNumber, currentPlayerNumber;

    public Game(GameMode _mode) {
        mode = _mode;
        sessions = new SessionManager(mode);

        logger.info("Game initialized");
    }


    /**
     * Inserts a player into the game
     *
     * @param username The username for the given player
     * @throws IllegalStateException
     */
    public void addPlayer(String username) {
        if (status != GameStatus.INITIALIZATION) {
            throw new IllegalStateException("Impossible to add a player: current game phase (%s) not in INITIALIZATION".formatted(status));
        }

        if (sessions.size() == mode.playerCount()) {
            throw new IllegalStateException("Impossible to add a player: the game is full (having %d players for %s mode)".formatted(sessions.size(), mode));
        }

        // Creating new player session
        PlayerNumber newPlayerNumber = PlayerNumber.fromInt(sessions.size() + 1);
        PersonalGoalCard randomPersonalGoalCard = personalGoalCardExtractor.extract();
        PlayerSession newSession = new PlayerSession(username, newPlayerNumber, randomPersonalGoalCard);

        // Adding session
        sessions.put(newSession);

        logger.info("addPlayer(username={}): player added", username);
    }

    public PlayerSession getPlayerSession(String username) {
        if (!sessions.isPresent(username)) {
            throw new IllegalStateException("Username not found in sessions");
        }

        return sessions.getByUsername(username);
    }


    @Override
    public void onGameStarted() {
        logger.info("onGameStarted()");
        setGameStatus(GameStatus.RUNNING);

        if (sessions.size() != mode.playerCount()) {
            throw new IllegalStateException("Expected number of players (%d) differs from the actual number of players in game (%d)".formatted(mode.playerCount(), sessions.size()));
        }

        // Common goal card initialization
        for (int cardNumber = 1; cardNumber <= config.commonGoalCardAmount(); cardNumber++) {
            CommonGoalCard card = commonGoalCardExtractor.extract();
            CommonGoalCardStatus cardStatus = new CommonGoalCardStatus(card, mode);
            commonGoalCardStatuses.add(cardStatus);
        }


        // Random first-player extraction (concise)
        startingPlayerNumber = CollectionUtils.extractRandomElement(sessions).getPlayerNumber();
        currentPlayerNumber = startingPlayerNumber;


        // (re)fill board
        onRefill();

        // Set first state
        getCurrentPlayer().setPlayerCurrentGamePhase(PlayerCurrentGamePhase.SELECTING);
    }


    public void onRefill() {
        int emptyBoardCells = board.countEmptyCells(mode);
        List<Tile> extractedTiles = tileExtractor.extractAmount(emptyBoardCells);

        board.fill(extractedTiles, mode);
    }

    public GameStatus getGameStatus() {
        return status;
    }

    public void setGameStatus(GameStatus status) {
        this.status = status;
    }

    public boolean isSelectionValid(@NotNull Set<Coordinate> coordinates) {
        boolean areCoordinatesReferencingValidTiles = areAllCoordinatesPresent(coordinates);
        boolean isSelectionAmountValid = coordinates.size() <= config.maxSelectionSize();
        boolean isEdgeConditionSatisfied = coordinates.stream().allMatch(coordinate -> board.countFreeEdges(coordinate) > 0);
        boolean areCoordinatesInStraightLine = CoordinatesHelper.areCoordinatesInStraightLine(coordinates.stream().toList());

        return areCoordinatesReferencingValidTiles && isSelectionAmountValid && isEdgeConditionSatisfied && areCoordinatesInStraightLine;
    }

    public PlayerSession getCurrentPlayer() {
        return sessions.getByNumber(currentPlayerNumber);
    }

    public PlayerNumber getStartingPlayerNumber() {
        return startingPlayerNumber;
    }

    public List<CommonGoalCardStatus> getCommonGoalCards() {
        return commonGoalCardStatuses;
    }

    public Tile[][] getGameMatrix() {
        return board.getTileMatrix();
    }

    /**
     * Sets the player's, identified by the given username, attribute noMoreTurns as true
     *
     * @param username
     */

    public void playerHasNoMoreTurns(String username) {
        assert sessions.isPresent(username);
        playerHasNoMoreTurns(sessions.getByUsername(username).getPlayerNumber());
    }

    /**
     * Sets the player's, identified by the given player number, attribute noMoreTurns as true
     *
     * @param number
     */
    public void playerHasNoMoreTurns(PlayerNumber number) {
        sessions.getByNumber(number).noMoreTurns = true;
    }


    private boolean areAllCoordinatesPresent(@NotNull Collection<Coordinate> coordinates) {
        return coordinates.stream().allMatch(it -> board.getTileAt(it).isPresent());
    }


    /**
     * Select Tiles from the board and set them inside current player session
     *
     * @param coordinates
     */
    @Override
    public void onPlayerSelectionPhase(Set<Coordinate> coordinates) {
        if (!isSelectionValid(coordinates)) {
            throw new IllegalStateException("Coordinates are not valid");
        }

        // we assume the selection is valid
        List<Pair<Coordinate, Tile>> coordinatesAndValues = coordinates
                .stream()
                .map(it -> new Pair<>(it, board.getTileAt(it).get()))
                .toList();

        var tileSelection = new PlayerTileSelection(coordinatesAndValues);

        // update model accordingly
        getCurrentPlayer().setPlayerTileSelection(tileSelection);
        getCurrentPlayer().setPlayerCurrentGamePhase(PlayerCurrentGamePhase.INSERTING);
    }

    /**
     * Insert selected Tiles inside bookshelf after got sure there's empty space in a given column
     *
     * @param column
     * @param tiles
     */
    @Override
    public void onPlayerInsertionPhase(int column, List<Tile> tiles) {
        //FIXME PLayers could choose which tile goes where inside the same column, in this function the arguments are just col and tiles

        // we assume tiles have been checked and match
        getCurrentPlayer().getBookshelf().insert(column, tiles);
        getCurrentPlayer().setPlayerCurrentGamePhase(PlayerCurrentGamePhase.CHECKING);
    }

    /**
     * Sets noMoreTurns has true for all the players between the first player and the first that filled his bookshelf;
     * if this two players are the same person, it simply puts his attribute has true
     */
    private void setFlags() {
        // using currentPlayerNumber and startingPlayerNumber;
        // playersMap.get(number).noMoreTurns = true;
        PlayerNumber player = startingPlayerNumber;
        while (player != currentPlayerNumber) {
            playerHasNoMoreTurns(player);
            player.next(mode);
        }

        playerHasNoMoreTurns(player);
    }

    /**
     * Updates acquired tokens by current player
     */
    @Override
    public void onPlayerCheckingPhase() {
        // full bookshelf test
        boolean isBookshelfFull = getCurrentPlayer().getBookshelf().isFull();

        if (isBookshelfFull && status == GameStatus.RUNNING) {
            getCurrentPlayer().addAcquiredToken(Token.FULL_SHELF_TOKEN);
            status = GameStatus.LAST_ROUND;

            setFlags();
        }

        if (status == GameStatus.LAST_ROUND) {
            playerHasNoMoreTurns(currentPlayerNumber);
        }


        // common goal card testing
        for (CommonGoalCardStatus cardStatus : commonGoalCardStatuses) {
            CommonGoalCard card = cardStatus.getCommonGoalCard();
            Tile[][] shelf = getCurrentPlayer().getBookshelf().getShelfMatrix();

            boolean isCardConditionVerified = card.matches(shelf);

            boolean hasAlreadyAcquiredToken = getCurrentPlayer()
                    .getAchievedCommonGoalCards()
                    .contains(card.getId());

            if (isCardConditionVerified && !hasAlreadyAcquiredToken) {
                Optional<Token> pendingToken = cardStatus.acquireAndRemoveTopToken();
                pendingToken.ifPresent(token -> getCurrentPlayer().getAcquiredTokens().add(token));
            }
        }

        getCurrentPlayer().setPlayerCurrentGamePhase(PlayerCurrentGamePhase.IDLE);
    }


    /**
     * Updates currentPlayer from current player to next player
     */
    @Override
    public void onNextTurn(String nextPlayerUsername) {
        // assume the username is correct
        assert sessions.isPresent(nextPlayerUsername);

        currentPlayerNumber = sessions.getByUsername(nextPlayerUsername).getPlayerNumber();
        getCurrentPlayer().setPlayerCurrentGamePhase(PlayerCurrentGamePhase.SELECTING);
    }

    @Override
    public void onGameEnded() {

    }

    public PlayerSession getSessionFor(String username) {
        return sessions.getByUsername(username);
    }

    @TestOnly
    public Map<PlayerNumber, PlayerSession> getPlayerNumberMap() {
        return sessions.getNumberMap();
    }
}
