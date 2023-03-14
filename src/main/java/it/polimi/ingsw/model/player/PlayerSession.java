package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.bookshelf.Bookshelf;
import it.polimi.ingsw.model.cards.PersonalGoalCard;
import it.polimi.ingsw.model.game.Token;
import it.polimi.ingsw.model.player.action.PlayerCurrentAction;
import it.polimi.ingsw.model.player.selection.PlayerTileSelection;

import java.util.List;

/**
 * Represent the game session for a given user.
 */
public class PlayerSession {

    private final String username;
    private PlayerNumber playerNumber;
    private PlayerStatus playerStatus;
    private PlayerCurrentAction playerCurrentAction;
    private PlayerTileSelection playerTileSelection;
    private Bookshelf bookshelf;
    private final PersonalGoalCard personalGoalCard;
    private List<Token> acquiredTokens;
    private final int tokenCounter = 0;

    public PlayerSession(String username, PlayerNumber playerId, PersonalGoalCard personalGoalCard) {
        this.username = username;
        this.playerNumber = playerId;
        this.personalGoalCard = personalGoalCard;
    }

    public String getUsername() {
        return username;
    }

    public PlayerNumber getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(PlayerNumber number) {
        this.playerNumber = number;
    }

    public PersonalGoalCard getPersonalGoalCard() {
        return personalGoalCard;
    }


    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    /**
     * Registers a token as taken by the user
     * */
    public void addAcquiredToken(Token newlyAcquiredToken) {
        acquiredTokens.add(newlyAcquiredToken);
    }

    /**
     * calculates all the points associated to a specific token given by Token Enumeration
     */
    public int calculateCurrentPoint() {
        return acquiredTokens.stream().mapToInt(Token::getPoints).sum();
    }

    public List<Token> getAcquiredTokens() {
        return acquiredTokens;
    }
}
