package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.board.cell.CellPattern;
import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import it.polimi.ingsw.model.cards.personal.PersonalGoalCard;
import it.polimi.ingsw.model.game.extractors.CommonGoalCardExtractor;
import it.polimi.ingsw.model.game.extractors.PersonalGoalCardExtractor;
import it.polimi.ingsw.model.game.extractors.TileExtractor;
import it.polimi.ingsw.model.player.PlayerNumber;
import it.polimi.ingsw.model.player.PlayerSession;
import it.polimi.ingsw.utils.ListUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model class representing an instance of a game.
 * */
public class Game {

    private final GameMode mode;
    private GameStatus status;

    private final Board board = new Board();

    private final Map<PlayerNumber, PlayerSession> playersMap = new HashMap<>();

    private PlayerNumber startingPlayer;
    private PlayerNumber currentPlayer;

    /**
     * Holds the current statuses for the common goal cards.
     *
     * */
    private List<CommonGoalCardStatus> commonGoalCardStatuses;

    private final TileExtractor tileExtractor = new TileExtractor();
    private final CommonGoalCardExtractor commonGoalCardExtractor = new CommonGoalCardExtractor();
    private final PersonalGoalCardExtractor personalGoalCardExtractor = new PersonalGoalCardExtractor();

    public Game(GameMode _mode) {
        status = GameStatus.INITIALIZATION;
        mode = _mode;
    }


    public void onGameStart() {
        // Common goal card initialization
        CommonGoalCard card1 = commonGoalCardExtractor.extract();
        CommonGoalCard card2 = commonGoalCardExtractor.extract();

        CommonGoalCardStatus cardStatus1 = new CommonGoalCardStatus(card1, Arrays.asList(Token.COMMON_GOAL_TOKEN_8_POINTS, Token.COMMON_GOAL_TOKEN_6_POINTS));
        CommonGoalCardStatus cardStatus2 = new CommonGoalCardStatus(card2, Arrays.asList(Token.COMMON_GOAL_TOKEN_8_POINTS, Token.COMMON_GOAL_TOKEN_6_POINTS));

        commonGoalCardStatuses.add(cardStatus1);
        commonGoalCardStatuses.add(cardStatus2);



        // Random first-player extraction
        startingPlayer = ListUtils.extractRandomElement(playersMap.values()).getPlayerNumber();
        currentPlayer = startingPlayer;



        // refill board
        CellPattern pattern = CellPattern.FOUR_DOTS;

        int emptyBoardCells = board.countEmptyCells(pattern);
        List<Tile> tiles = tileExtractor.extract(emptyBoardCells);

        board.fill(tiles, pattern);
    }

    /**
     *
     * */
    public void addPlayer(String username) {
        PersonalGoalCard randomPersonalGoalCard = personalGoalCardExtractor.extract();
        PlayerNumber newPlayerNumber = PlayerNumber.fromInt(playersMap.size() + 1);

        PlayerSession newSession = new PlayerSession(username, newPlayerNumber, randomPersonalGoalCard);

        playersMap.put(newPlayerNumber, newSession);
    }

    public GameStatus getGameStatus() {
        return this.status;
    }

    public GameMode getGameMode() {
        return mode;
    }

    public PlayerSession getCurrentPlayer() {
        return playersMap.get(currentPlayer);
    }

    public List<PlayerSession> getPlayerSessions() {
        return playersMap.values().stream().toList();
    }

    public PlayerNumber getStartingPlayer() {
        return startingPlayer;
    }

    public List<CommonGoalCardStatus> getCommonGoalCards() {
        return commonGoalCardStatuses;
    }

}
