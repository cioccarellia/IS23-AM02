package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.bookshelf.Bookshelf;
import it.polimi.ingsw.model.cards.common.CommonGoalCardIdentifier;
import it.polimi.ingsw.model.cards.personal.PersonalGoalCard;
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

    /**
     * Tokens acquired by the current user.
     */
    private List<Token> acquiredTokens;

    /**
     * Identifiers of all the {@link it.polimi.ingsw.model.cards.common.CommonGoalCardIdentifier}s that
     * have been acquired by the user during the game (i.e. for which the user has received any common goal card tokens).
     * */
    private List<CommonGoalCardIdentifier> achievedCommonGoalCards;

    private final int tokenCounter = 0;

    public PlayerSession(String username, PlayerNumber playerNumber, PersonalGoalCard personalGoalCard) {
        this.username = username;
        this.playerNumber = playerNumber;
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


    public List<Token> getAcquiredTokens() {
        return acquiredTokens;
    }

    /**
     * Registers a token as acquired by the user
     * */
    public void addAcquiredToken(Token newlyAcquiredToken) {
        acquiredTokens.add(newlyAcquiredToken);
    }

    /**
     * Calculates all the points associated to a specific token given by Token Enumeration
     */
    public int calculateCurrentPoint() {
        return acquiredTokens.stream().mapToInt(Token::getPoints).sum();
    }
}
