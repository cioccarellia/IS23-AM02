package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Coordinates;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import it.polimi.ingsw.model.cards.personal.PersonalGoalCard;
import it.polimi.ingsw.model.game.extractors.CommonGoalCardExtractor;
import it.polimi.ingsw.model.game.extractors.PersonalGoalCardExtractor;
import it.polimi.ingsw.model.game.extractors.TileExtractor;
import it.polimi.ingsw.model.player.PlayerNumber;
import it.polimi.ingsw.model.player.PlayerSession;
import it.polimi.ingsw.model.player.action.PlayerCurrentAction;
import it.polimi.ingsw.model.player.selection.PlayerTileSelection;
import it.polimi.ingsw.utils.ListUtils;
import it.polimi.ingsw.controller.ControlInterface;

import java.util.*;

//FIXME make the class abstract(?)
/**
 * Model class representing an instance of a game.
 */
public class Game implements ControlInterface {

    private final GameMode mode;
    private final Board board = new Board();
    private final GameStatus status;
    private final Map<PlayerNumber, PlayerSession> playersMap = new HashMap<>();
    private PlayerNumber startingPlayer;
    private PlayerNumber currentPlayer;

    private final TileExtractor tileExtractor = new TileExtractor();
    private final CommonGoalCardExtractor commonGoalCardExtractor = new CommonGoalCardExtractor();
    private final PersonalGoalCardExtractor personalGoalCardExtractor = new PersonalGoalCardExtractor();


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





    @Override
    public void onPlayerQuit(){

    }

    /**
     * Select Tiles from the board and set them inside current player session
     * @param username
     * @param coordinates
     */
    @Override
    public void onPLayerTileSelection(String username, Set<Coordinates> coordinates) {
        assert getCurrentPlayer().getUsername().equals(username);
        for (int i = 0; i < coordinates.size(); i++) {
            assert board.countFreeEdges(coordinates.stream().toList().get(i)) != 0;
            getCurrentPlayer().setPlayerCurrentAction(PlayerCurrentAction.SELECTING);
            Set<Tile> currentTileSet = new HashSet<>();
            PlayerTileSelection playerSelection = new PlayerTileSelection();
            //FIXME get sure about using get method on the next line
            currentTileSet.add((board.getTileAt((coordinates.stream().toList().get(i))).get()));
            playerSelection.setSelectedTiles(currentTileSet);
            getCurrentPlayer().setPlayerTileSelection(playerSelection);
        }
    }

    /**
     * Insert selected Tiles inside bookshelf after got sure there's empty space in a given column
     * @param username
     * @param column
     * @param tiles
     */
    @Override
    public void onPlayerBookshelfTileInsertion(String username, int column, List<Tile> tiles){
        for(int i=0; i< tiles.size();i++){
            assert getCurrentPlayer().getUsername().equals(username);
            assert getCurrentPlayer().getBookshelf().canFit(column, tiles.size());
            getCurrentPlayer().setPlayerCurrentAction(PlayerCurrentAction.INSERTING);
            getCurrentPlayer().getBookshelf().insert(column,tiles);
        //FIXME understand if removing Tiles from board could be useful
        //board.removeTileAt(coordinates.stream().toList().get(i));
        }

    }

    /**
     * update acquired token by current player
     * @param username
     * @param token
     */
    @Override
    public void onPlayerTokenUpdate(String username, Token token){
        assert getCurrentPlayer().getUsername().equals(username);
        getCurrentPlayer().addAcquiredToken(token);
    }
    //FIXME think about an optimized algorithm
    /**
     * update currentPlayer from current player to next player
     */
    @Override
    public void onTurnChange(){
        switch (currentPlayer) {
            case PLAYER_1 -> {
                currentPlayer=PlayerNumber.PLAYER_2;
            }
            case PLAYER_2 -> {
                currentPlayer=PlayerNumber.PLAYER_3;
            }
            case PLAYER_3 -> {
                currentPlayer=PlayerNumber.PLAYER_4;
            }
            case PLAYER_4 -> {
                currentPlayer=PlayerNumber.PLAYER_1;
            }
        }

    }

    @Override
    public void onLastTurn(){

    }

    @Override
    public void onGameEnded(){

    }
}
