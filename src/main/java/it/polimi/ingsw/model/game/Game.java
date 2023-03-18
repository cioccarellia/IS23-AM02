package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.game.extractors.CommonGoalCardExtractor;
import it.polimi.ingsw.model.game.extractors.PersonalGoalCardExtractor;
import it.polimi.ingsw.model.game.extractors.TileExtractor;
import it.polimi.ingsw.model.player.PlayerNumber;
import it.polimi.ingsw.model.player.PlayerSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Game {

    private final GameMode mode;
    private GameStatus status;

    private final Board board = new Board();

    private final Map<PlayerNumber, PlayerSession> playersMap = new HashMap<>();

    private PlayerNumber startingPlayer;
    private PlayerNumber currentPlayer;

    private List<CommonGoalCardStatus> commonGoalCardStatuses;

    private final TileExtractor tileExtractor = new TileExtractor();
    private final CommonGoalCardExtractor commonGoalCardExtractor = new CommonGoalCardExtractor();
    private final PersonalGoalCardExtractor personalGoalCardExtractor = new PersonalGoalCardExtractor();

    public Game(GameMode _mode) {
        status = GameStatus.INITIALIZATION;
        mode = _mode;
    }

    public void addPlayer(String username) {
        // PersonalGoalCard
        // PlayerSession x = new PlayerSession(username, ,);
        // FIXME
        // players.put(x);
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
