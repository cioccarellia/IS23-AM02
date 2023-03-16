package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.cards.CommonGoalCard;
import it.polimi.ingsw.model.player.PlayerNumber;
import it.polimi.ingsw.model.player.PlayerSession;
import javafx.util.Pair;

import java.util.List;


public class Game {
    private final GameMode mode;
    private GameStatus status;
    private Board board;
    private List<PlayerSession> players;
    private PlayerNumber startingPlayer;
    private PlayerNumber currentPlayer;
    private List<Pair<CommonGoalCard, Token>> commonGoalCards;
    private TileExtractor randomExtractor;

    public Game(GameMode mode) {
        this.mode = mode;
    }

    public void addPlayer(String username) {
        // PlayerSession x = new PlayerSession(username, ,);
        // FIXME
        // players.add(x);
    }

    public GameStatus getGameStatus() {
        return this.status;
    }

    public GameMode GetGameMode() {
        return mode;
    }

    public List<PlayerSession> getPlayers() {
        return players;
    }

    public PlayerSession getCurrentPlayer() {
        return players
                .stream()
                .filter(session -> session.getPlayerNumber() == currentPlayer)
                .findFirst()
                .get();
    }


    public PlayerNumber getStartingPlayer() {
        return startingPlayer;
    }

    /*
    public List<Pair<CommonGoalCard, Optional<Token>>> getCommonGoalCards() {
          return commonGoalCards
                  .stream()
                  .map(card -> new Pair(card.getKey(), Optional.of(card.getValue())))
                  .to();
    }
    */
}
