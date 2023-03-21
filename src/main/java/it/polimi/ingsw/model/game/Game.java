package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import it.polimi.ingsw.model.cards.personal.PersonalGoalCard;
import it.polimi.ingsw.model.game.extractors.CommonGoalCardExtractor;
import it.polimi.ingsw.model.game.extractors.PersonalGoalCardExtractor;
import it.polimi.ingsw.model.game.extractors.TileExtractor;
import it.polimi.ingsw.model.player.PlayerNumber;
import it.polimi.ingsw.model.player.PlayerSession;
import it.polimi.ingsw.utils.ListUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model class representing an instance of a game.
 */
public class Game {

    private final GameMode mode;
    private final Board board = new Board();
    private final Map<PlayerNumber, PlayerSession> playersMap = new HashMap<>();
    private final TileExtractor tileExtractor = new TileExtractor();
    private final CommonGoalCardExtractor commonGoalCardExtractor = new CommonGoalCardExtractor();
    private final PersonalGoalCardExtractor personalGoalCardExtractor = new PersonalGoalCardExtractor();
    private final GameStatus status;
    private PlayerNumber startingPlayer;
    private PlayerNumber currentPlayer;

    /**
     * Holds the current statuses for the common goal cards.
     */
    private List<CommonGoalCardStatus> commonGoalCardStatuses;

    public Game(GameMode _mode) {
        status = GameStatus.INITIALIZATION;
        mode = _mode;
    }


    public void onGameStart() {
        // Common goal card initialization
        CommonGoalCard card1 = commonGoalCardExtractor.extract();
        CommonGoalCard card2 = commonGoalCardExtractor.extract();

        CommonGoalCardStatus cardStatus1 = new CommonGoalCardStatus(card1, mode);
        CommonGoalCardStatus cardStatus2 = new CommonGoalCardStatus(card2, mode);

        commonGoalCardStatuses.add(cardStatus1);
        commonGoalCardStatuses.add(cardStatus2);


        // Random first-player extraction
        startingPlayer = ListUtils.extractRandomElement(playersMap.values()).getPlayerNumber();
        currentPlayer = startingPlayer;


        // refill board
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
