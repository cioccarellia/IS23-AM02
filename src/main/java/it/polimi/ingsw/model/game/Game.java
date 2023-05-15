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
import it.polimi.ingsw.model.player.selection.PlayerTileSelection;
import it.polimi.ingsw.utils.CollectionUtils;
import it.polimi.ingsw.utils.model.CoordinatesHelper;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static it.polimi.ingsw.model.game.GameStatus.*;
import static it.polimi.ingsw.model.game.goal.Token.FULL_SHELF_TOKEN;
import static it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase.*;

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
     * @param username The username for the given player
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

    public PlayerSession getPlayerSession(String username) {
        if (!sessions.isPresent(username)) {
            throw new IllegalStateException("Username not found in sessions");
        }

        return sessions.getByUsername(username);
    }

    public int getPlayerNumber() {
        return sessions.size();
    }

    public SessionManager getSessions() {
        return sessions;
    }

    public List<CommonGoalCardStatus> getCommonGoalCardsStatus() {
        return commonGoalCardStatuses;
    }


    @Override
    public void onGameStarted() {
        logger.info("onGameStarted()");
        setGameStatus(RUNNING);

        if (sessions.size() != mode.maxPlayerAmount()) {
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

    public GameMode getGameMode() {
        return mode;
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

    public Board getBoard() {
        return board;
    }

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


    private boolean areAllCoordinatesPresent(@NotNull Collection<Coordinate> coordinates) {
        return coordinates.stream().allMatch(it -> board.getTileAt(it).isPresent());
    }


    /**
     * Select Tiles from the board and set them inside current player session
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
        getCurrentPlayer().setPlayerTileSelection(tileSelection);
        getCurrentPlayer().setPlayerCurrentGamePhase(INSERTING);
    }

    /**
     * Insert selected Tiles inside bookshelf after got sure there's empty space in a given column
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
     * Updates acquired tokens by current player
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

        getCurrentPlayer().setPlayerCurrentGamePhase(IDLE);
    }


    /**
     * Updates currentPlayer from current player to next player
     */
    @Override
    public void onNextTurn(String nextPlayerUsername) {
        // assume the username is correct
        assert sessions.isPresent(nextPlayerUsername);

        currentPlayerNumber = sessions.getByUsername(nextPlayerUsername).getPlayerNumber();
        getCurrentPlayer().setPlayerCurrentGamePhase(SELECTING);
    }

    @Override
    public List<Pair<PlayerNumber, Integer>> onGameEnded() {
        List<Pair<PlayerNumber, Integer>> playersScore = new ArrayList<>();

        for (int i = 0, points = 0; i < sessions.getNumberMap().keySet().size(); i++, points = 0) {
            points += sessions.getNumberMap().get(i).calculateCurrentPoints();
            points += sessions.getNumberMap().get(i).calculatePersonalGoalCardPoints(getCurrentPlayer());

            GroupFinder groupFinder = new GroupFinder(getCurrentPlayer().getBookshelf().getShelfMatrix());
            List<Group> groups = groupFinder.computeGroupPartition();
            List<Integer> groupsSize = groups.stream().map(group -> groups.size()).toList();

            for (int j = 0; j < groups.size(); j++) {
                switch (groupsSize.get(i)) {
                    case 0, 1, 2:
                        continue;
                    case 3:
                        points += 2;
                    case 4:
                        points += 3;
                    case 5:
                        points += 5;
                    case 6:
                        points += 8;
                    default:
                        points += 8;
                }
            }

            playersScore.add(new Pair<>(sessions.getNumberMap().get(i).getPlayerNumber(), points));
        }
        return playersScore;
    }

    public PlayerSession getSessionFor(String username) {
        return sessions.getByUsername(username);
    }

    @TestOnly
    public Map<PlayerNumber, PlayerSession> getPlayerNumberMap() {
        return sessions.getNumberMap();
    }
}
