package it.polimi.ingsw.model.game;

import it.polimi.ingsw.groupfinder.Group;
import it.polimi.ingsw.groupfinder.GroupFinder;
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
import java.util.stream.Collectors;

import static it.polimi.ingsw.model.game.GameStatus.*;
import static it.polimi.ingsw.model.game.goal.Token.FULL_SHELF_TOKEN;
import static it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase.*;
import static java.util.Comparator.comparing;

/**
 * Model class representing an instance of a game.
 */
public class Game implements ModelService {

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
        PlayerNumber newPlayerNumber = PlayerNumber.fromInt(sessions.size() + 1);
        PersonalGoalCard randomPersonalGoalCard = personalGoalCardExtractor.extract();
        PlayerSession newSession = new PlayerSession(username, newPlayerNumber, randomPersonalGoalCard);

        // Adding session
        sessions.put(newSession);

        logger.info("addPlayer(username={}): player added", username);
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

    /**
     * @return the list of all players' usernames
     */
    public List<String> getPlayersUsernameList() {
        return getSessions().playerSessions().stream().map(PlayerSession::getUsername).toList();
    }

    /**
     * @return how many players are in the current game
     */
    public int getPlayersCurrentAmount() {
        return sessions.size();
    }

    /**
     * @return the map of players' sessions
     */
    public SessionManager getSessions() {
        return sessions;
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

        if (getPlayersCurrentAmount() != mode.maxPlayerAmount()) {
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
        getCurrentPlayer().setPlayerCurrentGamePhase(SELECTING);
    }


    /**
     * Counts how many empty cells are left, it extracts the needed tiles and fills the board with them.
     */
    public void onRefill() {
        int emptyBoardCells = board.countEmptyCells(mode);
        List<Tile> extractedTiles = tileExtractor.extractAmount(emptyBoardCells);

        board.fill(extractedTiles, mode);
    }

    /**
     * @return the {@link GameStatus}
     */
    public GameStatus getGameStatus() {
        return status;
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
     * @return the {@link GameMode}
     */
    public GameMode getGameMode() {
        return mode;
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
     * @return the {@link PlayerSession} of the current player
     */
    public PlayerSession getCurrentPlayer() {
        return sessions.getByNumber(currentPlayerNumber);
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
     * Sets the player's, identified by the given username, attribute noMoreTurns as true
     *
     * @param username player's username
     */

    public void playerHasNoMoreTurns(String username) {
        assert sessions.isPresent(username);
        playerHasNoMoreTurns(sessions.getByUsername(username).getPlayerNumber());
    }

    /**
     * Sets the player's, identified by the given player number, attribute noMoreTurns as true
     *
     * @param number player's number
     */
    public void playerHasNoMoreTurns(PlayerNumber number) {
        sessions.getByNumber(number).noMoreTurns = true;
    }


    /**
     * @param coordinates the collection of coordinates that needs checking
     * @return if at the given coordinates there are indeed tiles present
     */
    private boolean areAllCoordinatesPresent(@NotNull Collection<Coordinate> coordinates) {
        return coordinates.stream().allMatch(it -> board.getTileAt(it).isPresent());
    }


    /**
     * Fetches the {@link Tile}s from the board at the given coordinates and adds them to the current player's session
     *
     * @param coordinates set of the player's selected tiles' coordinates
     */
    @Override
    public void onPlayerSelectionPhase(Set<Coordinate> coordinates) {
        if (!isSelectionValid(coordinates)) {
            throw new IllegalStateException("Coordinates are not valid");
        }

        // we assume the selection is valid
        List<Pair<Coordinate, Tile>> coordinatesAndValues = coordinates.stream().map(it -> new Pair<>(it, board.getTileAt(it).get())).toList();

        var tileSelection = new PlayerTileSelection(coordinatesAndValues);

        // update model accordingly
        coordinates.forEach(board::removeTileAt);

        getCurrentPlayer().setPlayerTileSelection(tileSelection);
        getCurrentPlayer().setPlayerCurrentGamePhase(INSERTING);
    }

    /**
     * Inserts the selected {@link Tile}s inside the player's bookshelf
     *
     * @param column the column in which the player wants to add the tiles
     * @param tiles  the tiles the players wants to insert in their bookshelf
     */
    @Override
    public void onPlayerInsertionPhase(int column, List<Tile> tiles) {
        //FIXME PLayers could choose which tile goes where inside the same column, in this function the arguments are just col and tiles

        // we assume tiles have been checked and match
        getCurrentPlayer().getBookshelf().insert(column, tiles);
        getCurrentPlayer().setPlayerCurrentGamePhase(CHECKING);
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
     * Updates the current player's tokens, if needed, checks if the board needs refilling and sets the player's status
     * as IDLE
     */
    @Override
    public void onPlayerCheckingPhase() {
        // full bookshelf test
        boolean isBookshelfFull = getCurrentPlayer().getBookshelf().isFull();

        if (isBookshelfFull && status == RUNNING) {
            getCurrentPlayer().addAcquiredToken(FULL_SHELF_TOKEN);
            status = LAST_ROUND;

            setFlags();
        }

        if (status == LAST_ROUND) {
            playerHasNoMoreTurns(currentPlayerNumber);
        }


        // common goal card testing
        for (CommonGoalCardStatus cardStatus : commonGoalCardStatuses) {
            CommonGoalCard card = cardStatus.getCommonGoalCard();
            Tile[][] shelf = getCurrentPlayer().getBookshelf().getShelfMatrix();

            boolean isCardConditionVerified = card.matches(shelf);

            boolean hasAlreadyAcquiredToken = getCurrentPlayer().getAchievedCommonGoalCards().contains(card.getId());

            if (isCardConditionVerified && !hasAlreadyAcquiredToken) {
                Optional<Token> pendingToken = cardStatus.acquireAndRemoveTopToken();
                pendingToken.ifPresent(token -> getCurrentPlayer().getAcquiredTokens().add(token));
            }
        }

        if (board.needsRefilling()) {
            onRefill();
        }

        getCurrentPlayer().setPlayerCurrentGamePhase(IDLE);
    }


    /**
     * Updates the current player by setting the next player's status as SELECTING
     */
    @Override
    public void onNextTurn(String nextPlayerUsername) {
        // assume the username is correct
        assert sessions.isPresent(nextPlayerUsername);

        currentPlayerNumber = sessions.getByUsername(nextPlayerUsername).getPlayerNumber();
        getCurrentPlayer().setPlayerCurrentGamePhase(SELECTING);
    }

    /**
     * Calculates points for all players by adding up their tokens (if they had any), the points from the personal goal
     * card and the points they get from groups of the same {@link Tile} in their bookshelf. It then adds player number
     * and their point in a list of pairs, to get the players' ranking.
     *
     * @return the players' ranking
     */
    @Override
    public List<Pair<PlayerNumber, Integer>> onGameEnded() {
        List<Pair<PlayerNumber, Integer>> playersScore = new ArrayList<>();

        List<PlayerSession> players = sessions.getNumberMap().values().stream().toList();
        for (int i = 0; i < players.size(); i++) {
            int points = 0;
            points += players.get(i).calculateCurrentPoints();
            points += players.get(i).calculatePersonalGoalCardPoints(getCurrentPlayer());

            GroupFinder groupFinder = new GroupFinder(getCurrentPlayer().getBookshelf().getShelfMatrix());
            List<Group> groups = groupFinder.computeGroupPartition();
            List<Integer> groupsSize = groups.stream().map(group -> groups.size()).toList();

            for (int j = 0; j < groups.size(); j++) {
                assert groupsSize.get(i) >= 0;
                switch (groupsSize.get(i)) {
                    case 0, 1, 2:
                        break;
                    case 3:
                        points += 2;
                        break;
                    case 4:
                        points += 3;
                        break;
                    case 5:
                        points += 5;
                        break;
                    case 6:
                        points += 8;
                        break;
                    default:
                        points += 8;
                        break;
                }
            }

            playersScore.add(new Pair<>(players.get(i).getPlayerNumber(), points));
        }

        return playersScore.stream().sorted(comparing(Pair::getValue)).collect(Collectors.toList());
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
}
