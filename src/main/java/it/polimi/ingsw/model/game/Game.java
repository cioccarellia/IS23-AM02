package it.polimi.ingsw.model.game;

import it.polimi.ingsw.controller.ControlInterface;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import it.polimi.ingsw.model.cards.personal.PersonalGoalCard;
import it.polimi.ingsw.model.game.extractors.CommonGoalCardExtractor;
import it.polimi.ingsw.model.game.extractors.PersonalGoalCardExtractor;
import it.polimi.ingsw.model.game.extractors.TileExtractor;
import it.polimi.ingsw.model.player.PlayerNumber;
import it.polimi.ingsw.model.player.PlayerSession;
import it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase;
import it.polimi.ingsw.model.player.selection.PlayerTileSelection;
import it.polimi.ingsw.utils.CollectionUtils;
import it.polimi.ingsw.utils.model.CoordinatesHelper;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Model class representing an instance of a game.
 */
public class Game implements ControlInterface {

    // game status
    private final GameMode mode;
    private final Board board = new Board();
    private final Map<PlayerNumber, PlayerSession> playersMap = new HashMap<>();
    // extractors
    private final TileExtractor tileExtractor = new TileExtractor();
    private final CommonGoalCardExtractor commonGoalCardExtractor = new CommonGoalCardExtractor();
    private final PersonalGoalCardExtractor personalGoalCardExtractor = new PersonalGoalCardExtractor();
    private GameStatus status;

    // current state
    private PlayerNumber startingPlayerNumber;
    private PlayerNumber currentPlayerNumber;
    /**
     * Holds the current statuses for the common goal cards.\
     */
    private List<CommonGoalCardStatus> commonGoalCardStatuses;

    public Game(GameMode _mode) {
        status = GameStatus.INITIALIZATION;
        mode = _mode;
    }

    @Override
    public void onGameStarted() {
        // Common goal card initialization
        CommonGoalCard card1 = commonGoalCardExtractor.extract();
        CommonGoalCard card2 = commonGoalCardExtractor.extract();

        CommonGoalCardStatus cardStatus1 = new CommonGoalCardStatus(card1, mode);
        CommonGoalCardStatus cardStatus2 = new CommonGoalCardStatus(card2, mode);

        commonGoalCardStatuses.add(cardStatus1);
        commonGoalCardStatuses.add(cardStatus2);


        // Random first-player extraction (coincise)
        startingPlayerNumber = CollectionUtils.extractRandomElement(playersMap.values()).getPlayerNumber();
        currentPlayerNumber = startingPlayerNumber;


        // (re)fill board
        int emptyBoardCells = board.countEmptyCells(mode);
        List<Tile> extractedTiles = tileExtractor.extract(emptyBoardCells);

        board.fill(extractedTiles, mode);
    }


    /**
     *
     */
    public void addPlayer(String username) {
        PersonalGoalCard randomPersonalGoalCard = personalGoalCardExtractor.extract();
        PlayerNumber newPlayerNumber = PlayerNumber.fromInt(playersMap.size() + 1);

        PlayerSession newSession = new PlayerSession(username, newPlayerNumber, randomPersonalGoalCard);

        playersMap.put(newPlayerNumber, newSession);
    }

    public Optional<PlayerSession> getPlayer(String username) {
        return playersMap.values().stream().filter(it -> Objects.equals(it.getUsername(), username)).findFirst();
    }

    public GameStatus getGameStatus() {
        return this.status;
    }

    public boolean isSelectionValid(@NotNull Set<Coordinate> coordinates) {
        boolean isSelectionAmountValid = coordinates.size() <= 3;
        boolean isEdgeConditionSatisfied = coordinates.stream().allMatch(coordinate -> board.countFreeEdges(coordinate) > 0);
        boolean areCoordinatesInStraightLine = CoordinatesHelper.areCoordinatesInStraightLine(coordinates.stream().toList());

        return isSelectionAmountValid && isEdgeConditionSatisfied && areCoordinatesInStraightLine;
    }

    public PlayerSession getCurrentPlayer() {
        return playersMap.get(currentPlayerNumber);
    }

    public List<PlayerSession> getPlayerSessions() {
        return playersMap.values().stream().toList();
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
     * @param username
     */
    public void playerHasNoMoreTurns(String username) {
        playerHasNoMoreTurns(getPlayer(username).get().getPlayerNumber());
    }

    /**
     * Sets the player's, identified by the given player number, attribute noMoreTurns as true
     * @param number
     */
    private void playerHasNoMoreTurns(PlayerNumber number) {
        playersMap.get(number).noMoreTurns = true;
    }


    /**
     * Select Tiles from the board and set them inside current player session
     *
     * @param coordinates
     */
    @Override
    public void onPlayerSelectionPhase(Set<Coordinate> coordinates) {
        List<Coordinate> orderedCoordinates = coordinates.stream().toList();

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
        while(player != currentPlayerNumber){
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
        assert getPlayer(nextPlayerUsername).isPresent();

        currentPlayerNumber = getPlayer(nextPlayerUsername).get().getPlayerNumber();
        getCurrentPlayer().setPlayerCurrentGamePhase(PlayerCurrentGamePhase.SELECTING);
    }

    @Override
    public void onGameEnded() {

    }
}
