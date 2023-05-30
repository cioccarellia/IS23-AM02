package it.polimi.ingsw.model.game;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.ModelService;
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
import it.polimi.ingsw.model.game.score.PlayerScore;
import it.polimi.ingsw.model.game.score.ScoreBreakdown;
import it.polimi.ingsw.model.game.session.SessionManager;
import it.polimi.ingsw.model.player.PlayerNumber;
import it.polimi.ingsw.model.player.PlayerSession;
import it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase;
import it.polimi.ingsw.model.player.selection.PlayerTileSelection;
import it.polimi.ingsw.utils.CollectionUtils;
import it.polimi.ingsw.utils.model.CoordinatesHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static it.polimi.ingsw.model.game.GameStatus.*;
import static it.polimi.ingsw.model.game.goal.Token.FULL_SHELF_TOKEN;
import static it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase.*;
import static java.util.Comparator.comparing;

/**
 * Model class representing an instance of a game.
 */
public class Game implements ModelService {

    // Game logger
    @Expose(serialize = false)
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
    @Expose(serialize = false)
    private transient final TileExtractor tileExtractor = new TileExtractor();

    @Expose(serialize = false)
    private transient final CommonGoalCardExtractor commonGoalCardExtractor = new CommonGoalCardExtractor();

    @Expose(serialize = false)
    private transient final PersonalGoalCardExtractor personalGoalCardExtractor = new PersonalGoalCardExtractor();

    /**
     * Holder class for the common goal cards
     * Holds the current statuses for the common goal cards.
     */
    private final List<CommonGoalCardStatus> commonGoalCardStatuses = new ArrayList<>();

    /**
     * External game configuration parameters
     */
    @Expose(serialize = false)
    private transient final LogicConfiguration config = LogicConfiguration.getInstance();

    /**
     * Game Status
     */
    private GameStatus status = INITIALIZATION;

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
     * It sets the {@link GameStatus} to the one given
     *
     * @param status the given {@link GameStatus}
     */
    public void setGameStatus(GameStatus status) {
        this.status = status;
    }


    /**
     * @param username the username identifying the player
     * @return the session of the chosen player
     */
    public PlayerSession getPlayerSession(String username) {
        if (!sessions.isPresent(username)) {
            throw new IllegalStateException("Username not found in sessions");
        }

        return sessions.getByUsername(username);
    }


    public PlayerSession getPlayerSession(PlayerNumber number) {
        return sessions.getByNumber(number);
    }

    public PlayerSession getStartingPlayerSession() {
        return sessions.getByNumber(startingPlayerNumber);
    }

    public PlayerSession getCurrentPlayerSession() {
        return sessions.getByNumber(currentPlayerNumber);
    }

    /**
     * @return how many players are in the current game
     */
    public int getPlayerCount() {
        return sessions.size();
    }

    /**
     * @return the map of players' sessions
     */
    public SessionManager getSessions() {
        return sessions;
    }

    public List<String> getPlayersUsernameList() {
        return sessions
                .playerSessions()
                .stream()
                .map(PlayerSession::getUsername).toList();
    }

    /**
     * @return the {@link GameStatus}
     */
    public GameStatus getGameStatus() {
        return status;
    }

    /**
     * @return the {@link GameMode}
     */
    public GameMode getGameMode() {
        return mode;
    }

    /**
     * @return the {@link PlayerNumber} of the player that started the game
     */
    public PlayerNumber getStartingPlayerNumber() {
        return startingPlayerNumber;
    }

    /**
     * @return the list of {@link CommonGoalCardStatus} for this game
     */
    public List<CommonGoalCardStatus> getCommonGoalCards() {
        return commonGoalCardStatuses;
    }

    /**
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @return the matrix of the board
     */
    public Tile[][] getGameMatrix() {
        return board.getTileMatrix();
    }

    /**
     * @param username the username identifying the player
     * @return the {@link PlayerSession} of the given player
     */
    public PlayerSession getSessionFor(String username) {
        return sessions.getByUsername(username);
    }


    /**
     * @return the map of players
     */
    @TestOnly
    public Map<PlayerNumber, PlayerSession> getPlayerNumberMap() {
        return sessions.getNumberMap();
    }


    // utils


    /**
     * Removes the remaining isolated tiles from the board, puts them back in the extractor, checks how many empty cells
     * there, it then extracts the amount of tiles needed and fills the board with them.
     * Counts how many empty cells are left, it extracts the needed tiles and fills the board with them
     */
    public void onRefill() {
        Map<Tile, Integer> removedTiles = board.removeRemainingTiles();
        tileExtractor.putBackTiles(removedTiles);

        int emptyBoardCells = board.countEmptyCells(mode);
        // int emptyBoardCells = mode.maxCellAmount();
        List<Tile> extractedTiles = tileExtractor.extractAmount(emptyBoardCells);

        board.fill(extractedTiles, mode);
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
            flagNoMoreTurnsForPlayer(player);
            player = player.next(mode);
        }

        flagNoMoreTurnsForPlayer(player);
    }

    /**
     * Sets the player's, identified by the given username, attribute noMoreTurns as true
     *
     * @param username player's username
     */
    public void flagNoMoreTurnsForPlayer(String username) {
        assert sessions.isPresent(username);
        flagNoMoreTurnsForPlayer(sessions.getByUsername(username).getPlayerNumber());
    }

    /**
     * Sets the player's, identified by the given player number, attribute noMoreTurns as true
     *
     * @param number player's number
     */
    private void flagNoMoreTurnsForPlayer(PlayerNumber number) {
        sessions.getByNumber(number).noMoreTurns = true;
    }

    /**
     * @param coordinates the set of coordinates selected by the current player
     * @return if the selected coordinates are valid: checks if at the given coordinates there is indeed a tile present,
     * checks if the selection is no more than 3 and more than 0, checks if all the tiles at the given coordinates have
     * at least one free edge, checks if they in a straight line (vertically and horizontally)
     */
    public boolean isSelectionValid(@NotNull Set<Coordinate> coordinates) {
        boolean areCoordinatesReferencingValidTiles = areAllCoordinatesPresent(coordinates);
        boolean isSelectionAmountValid = coordinates.size() <= config.maxSelectionSize() && coordinates.size() > 0;
        boolean isEdgeConditionSatisfied = coordinates.stream().allMatch(coordinate -> board.countFreeEdges(coordinate) > 0);
        boolean areCoordinatesInStraightLine = CoordinatesHelper.areCoordinatesInStraightLine(coordinates.stream().toList());

        return areCoordinatesReferencingValidTiles && isSelectionAmountValid && isEdgeConditionSatisfied && areCoordinatesInStraightLine;
    }

    /**
     * @param coordinates the collection of coordinates that needs checking
     * @return if at the given coordinates there are indeed tiles present
     */
    private boolean areAllCoordinatesPresent(@NotNull Collection<Coordinate> coordinates) {
        return coordinates.stream().allMatch(it -> board.getTileAt(it).isPresent());
    }


    // game phases

    /**
     * Inserts a player into the game
     *
     * @param username the username for the given player
     */
    public void addPlayer(String username) {
        if (status != INITIALIZATION) {
            throw new IllegalStateException("Impossible to add a player: current game phase (%s) not in INITIALIZATION".formatted(status));
        }

        if (sessions.size() == mode.maxPlayerAmount()) {
            throw new IllegalStateException("Impossible to add a player: the game is full (having %d players for %s mode)".formatted(sessions.size(), mode));
        }

        // Creating new player session
        PlayerNumber newPlayerNumber = PlayerNumber.fromInt(getPlayerCount() + 1);
        PersonalGoalCard randomPersonalGoalCard = personalGoalCardExtractor.extract();
        PlayerSession newSession = new PlayerSession(username, newPlayerNumber, randomPersonalGoalCard);

        // Adding session
        sessions.put(newSession);

        logger.info("addPlayer(username={}): player added", username);
    }

    /**
     * Method called when the game starts: sets the game as {@link GameStatus} RUNNING, checks all the players are
     * present, extracts the two common goal cards, extracts a player to start the game, fills the boards with tiles and
     * sets the {@link PlayerCurrentGamePhase} as SELECTING
     */
    @Override
    public void onGameStarted() {
        logger.info("onGameStarted()");
        setGameStatus(RUNNING);

        if (getPlayerCount() != mode.maxPlayerAmount()) {
            throw new IllegalStateException("Expected number of players (%d) differs from the actual number of players in game (%d)".formatted(mode.maxPlayerAmount(), sessions.size()));
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
        getCurrentPlayerSession().setPlayerCurrentGamePhase(SELECTING);
    }

    /**
     * Fetches the {@link Tile}s from the board at the given coordinates and adds them to the current player's session
     *
     * @param coordinates set of the player's selected tiles' coordinates
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public void onPlayerSelectionPhase(Set<Coordinate> coordinates) {
        if (!isSelectionValid(coordinates)) {
            throw new IllegalStateException("Coordinates are not valid");
        }

        // we assume the selection is valid
        List<CellInfo> coordinatesAndValues = coordinates
                .stream()
                .peek(it -> {
                    assert board.getTileAt(it).isPresent();
                })
                .map(it -> new CellInfo(it, board.getTileAt(it).get())).toList();

        var tileSelection = new PlayerTileSelection(coordinatesAndValues);

        // update model accordingly
        coordinates.forEach(board::removeTileAt);

        getCurrentPlayerSession().setPlayerTileSelection(tileSelection);
        getCurrentPlayerSession().setPlayerCurrentGamePhase(INSERTING);
    }

    /**
     * Inserts the selected {@link Tile}s inside the player's bookshelf
     *
     * @param column the column in which the player wants to add the tiles
     * @param tiles  the tiles the players wants to insert in their bookshelf
     */
    @Override
    public void onPlayerInsertionPhase(int column, List<Tile> tiles) {

        // we assume tiles have been checked and match
        getCurrentPlayerSession().getBookshelf().insert(column, tiles);
        getCurrentPlayerSession().clearTileSelection();

        // need to check
        getCurrentPlayerSession().setPlayerCurrentGamePhase(IDLE);
    }

    /**
     * ˙
     * Updates the current player's tokens, if needed, checks if the board needs refilling and sets the player's status
     * as IDLE
     */
    @Override
    public void onPlayerTurnEnding() {
        // full bookshelf test
        boolean isBookshelfFull = getCurrentPlayerSession().getBookshelf().isFull();

        if (isBookshelfFull && status == RUNNING) {
            getCurrentPlayerSession().addAcquiredToken(FULL_SHELF_TOKEN);
            status = LAST_ROUND;

            setFlags();
        }

        // checks if it's the last round and sets this turn as the last for the player
        if (status == LAST_ROUND) {
            flagNoMoreTurnsForPlayer(currentPlayerNumber);
        }

        // common goal card testing
        for (CommonGoalCardStatus cardStatus : commonGoalCardStatuses) {
            CommonGoalCard card = cardStatus.getCommonGoalCard();
            Tile[][] shelf = getCurrentPlayerSession().getBookshelf().getShelfMatrix();

            boolean isCardConditionVerified = card.matches(shelf);

            boolean hasAlreadyAcquiredToken = getCurrentPlayerSession().getAchievedCommonGoalCards().contains(card.getId());

            boolean cardHasTokensLeft = !cardStatus.getCardTokens().isEmpty();

            if (isCardConditionVerified && !hasAlreadyAcquiredToken && cardHasTokensLeft) {
                Optional<Token> pendingToken = cardStatus.acquireAndRemoveTopToken();
                pendingToken.ifPresent(token -> getCurrentPlayerSession().getAcquiredTokens().add(token));
                getCurrentPlayerSession().getAchievedCommonGoalCards().add(card.getId());
            }
        }

        if (board.needsRefilling()) {
            onRefill();
        }

        getCurrentPlayerSession().setPlayerCurrentGamePhase(IDLE);
    }

    /**
     * Updates the current player by setting the next player's status as SELECTING
     */
    @Override
    public void onNextTurn(String nextPlayerUsername) {
        // checks if the flag noMoreTurns for the nextPlayer is already set as true, in this case the game ends
        if (sessions.getByUsername(nextPlayerUsername).noMoreTurns) {
            setGameStatus(ENDED);
        } else {
            // assume the username is correct
            assert sessions.isPresent(nextPlayerUsername);

            currentPlayerNumber = sessions.getByUsername(nextPlayerUsername).getPlayerNumber();
            getCurrentPlayerSession().setPlayerCurrentGamePhase(SELECTING);
        }
    }

    @Override
    public void onForcedNextTurn(String nextPlayer) {
        // conditioned rollback
        switch (getCurrentPlayerSession().getPlayerCurrentGamePhase()) {
            case IDLE, SELECTING -> // normal phase, no changes required, switching
                    onNextTurn(nextPlayer);
            case INSERTING -> {
                // rollback model to pre-selection stage and forward turns
                // todo
                List<CellInfo> savedCellInfos = getCurrentPlayerSession().getPlayerTileSelection().getSelection();

                for (CellInfo savedInfoForSingleCell : savedCellInfos) {
                    board.setTile(savedInfoForSingleCell);
                }

                getCurrentPlayerSession().clearTileSelection();
            }
        }
    }

    /**
     * Calculates points for all players by adding up their tokens (if they had any), the points from the personal goal
     * card and the points they get from groups of the same {@link Tile} in their bookshelf. It then adds player number
     * and their point in a list of pairs, to get the players' ranking.
     *
     * @return the players' ranking
     */
    public List<PlayerScore> getRankings() {
        List<PlayerScore> scores = new ArrayList<>();
        List<PlayerSession> players = sessions.values();

        for (PlayerSession player : players) {
            int tokenPoints = player.calculateCurrentTokenPoints();
            int personalGoalCardPoints = player.calculateCurrentPersonalGoalCardPoints();
            int bookshelfGroupPoints = player.calculateBookshelfGroupPoints();

            ScoreBreakdown breakdown = new ScoreBreakdown(tokenPoints, personalGoalCardPoints, bookshelfGroupPoints);
            scores.add(new PlayerScore(player.getUsername(), player.getAcquiredTokens(), breakdown));
        }

        return scores.stream().sorted(comparing(PlayerScore::total)).toList();
    }

    private GameStatus preStandByGameStatus;

    @Override
    public boolean onStandby() {
        if (status != STANDBY) {
            preStandByGameStatus = status;
            status = STANDBY;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onResume() {
        if (status == STANDBY) {
            status = preStandByGameStatus;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onGameEnded() {

    }
}
