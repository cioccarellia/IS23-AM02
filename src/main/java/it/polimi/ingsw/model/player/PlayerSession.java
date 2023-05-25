package it.polimi.ingsw.model.player;

import it.polimi.ingsw.groupfinder.Group;
import it.polimi.ingsw.groupfinder.GroupFinder;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.bookshelf.Bookshelf;
import it.polimi.ingsw.model.cards.common.CommonGoalCardIdentifier;
import it.polimi.ingsw.model.cards.personal.PersonalGoalCard;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.model.game.goal.Token;
import it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase;
import it.polimi.ingsw.model.player.selection.PlayerTileSelection;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent the game session for a given user.
 */
public class PlayerSession {

    private final static int rows = BookshelfConfiguration.getInstance().rows();
    private final static int cols = BookshelfConfiguration.getInstance().cols();

    private final String username;
    private final PlayerNumber playerNumber;
    private final PersonalGoalCard personalGoalCard;
    private final Bookshelf bookshelf = new Bookshelf();
    /**
     * Identifiers of all the {@link it.polimi.ingsw.model.cards.common.CommonGoalCardIdentifier}s that
     * have been acquired by the user during the game (i.e. for which the user has received any common goal card token).
     */
    private final List<CommonGoalCardIdentifier> achievedCommonGoalCards = new ArrayList<>();
    public boolean noMoreTurns = false;
    private PlayerCurrentGamePhase playerCurrentGamePhase;
    // If the player is after the selection phase, its selection is saved here
    private PlayerTileSelection playerTileSelection;
    /**
     * Tokens acquired by the current user during gameplay.
     */
    private final List<Token> acquiredTokens = new ArrayList<>();


    public PlayerSession(String username, PlayerNumber playerNumber, PersonalGoalCard personalGoalCard) {
        this.username = username;
        this.playerNumber = playerNumber;
        this.personalGoalCard = personalGoalCard;
    }


    // getters
    public String getUsername() {
        return username;
    }

    public PlayerNumber getPlayerNumber() {
        return playerNumber;
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

    public PlayerTileSelection getPlayerTileSelection() {
        return playerTileSelection;
    }

    public PlayerCurrentGamePhase getPlayerCurrentGamePhase() {
        return playerCurrentGamePhase;
    }

    public List<CommonGoalCardIdentifier> getAchievedCommonGoalCards() {
        return achievedCommonGoalCards;
    }

    // setters
    public void setPlayerTileSelection(PlayerTileSelection selection) {
        playerTileSelection = selection;
    }

    public void setPlayerCurrentGamePhase(PlayerCurrentGamePhase phase) {
        playerCurrentGamePhase = phase;
    }


    // utils

    /**
     * Registers a token as acquired by the user
     */
    public void addAcquiredToken(Token newlyAcquiredToken) {
        acquiredTokens.add(newlyAcquiredToken);
    }

    /**
     * Calculates all the points associated to a specific token given by Token Enumeration
     */
    public int calculateCurrentTokenPoints() {
        return acquiredTokens.stream().mapToInt(Token::getPoints).sum();
    }

    public int calculateCurrentPersonalGoalCardPoints() {
        Tile[][] bookshelf = getBookshelf().getShelfMatrix();
        Tile[][] personalGoalCard = getPersonalGoalCard().getShelfPointMatrix();

        int points = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (personalGoalCard[i][j] == bookshelf[i][j]) {
                    points++;
                }
            }
        }

        switch (points) {
            case 0 -> {
                return 0;
            }
            case 1 -> {
                return 1;
            }
            case 2 -> {
                return 2;
            }
            case 3 -> {
                return 4;
            }
            case 4 -> {
                return 6;
            }
            case 5 -> {
                return 9;
            }
            case 6 -> {
                return 12;
            }
            default -> throw new IllegalArgumentException("Expected a number between 0 and 6.");
        }
    }

    public int calculateBookshelfGroupPoints() {
        int points = 0;

        GroupFinder groupFinder = new GroupFinder(getBookshelf().getShelfMatrix());
        List<Group> groups = groupFinder.computeGroupPartition();

        for (Group g : groups) {
            switch (g.size()) {
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

        return points;
    }
}
