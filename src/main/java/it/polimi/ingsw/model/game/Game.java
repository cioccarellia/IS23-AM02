package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import it.polimi.ingsw.model.cards.common.CommonGoalCardFunctionContainer;
import it.polimi.ingsw.model.game.extractors.CommonGoalCardExtractor;
import it.polimi.ingsw.model.game.extractors.TileExtractor;
import it.polimi.ingsw.model.player.PlayerNumber;
import it.polimi.ingsw.model.player.PlayerSession;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class Game {

    private final GameMode mode;
    private GameStatus status;

    private Board board = new Board();

    private Map<PlayerNumber, PlayerSession> playersMap;

    private PlayerNumber startingPlayer;
    private PlayerNumber currentPlayer;

    private List<CommonGoalCardStatus> commonGoalCardStatuses;


    private TileExtractor tileExtractor = new TileExtractor();

    public Game(GameMode mode) {
        status = GameStatus.INITIALIZATION;
        this.mode = mode;


    }

    public void addPlayer(String username) {
        // PersonalGoalCard
        // PlayerSession x = new PlayerSession(username, ,);
        // FIXME
        // players.add(x);
    }

    public GameStatus getGameStatus() {
        return this.status;
    }

    public GameMode getGameMode() {
        return mode;
    }

    public List<PlayerSession> getPlayers() {
        return playersMap.values().stream().toList();
    }

    public PlayerSession getCurrentPlayer() {
        return playersMap.get(currentPlayer);
    }


    public PlayerNumber getStartingPlayer() {
        return startingPlayer;
    }


    public List<CommonGoalCardStatus> getCommonGoalCards() {
        return commonGoalCardStatuses;
    }

}
