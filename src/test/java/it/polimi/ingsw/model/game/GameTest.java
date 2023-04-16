package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.HardcodedBoardConstants;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.board.cell.Cell;
import it.polimi.ingsw.model.cards.personal.PersonalGoalCard;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class GameTest {
    private static final int dimension = BoardConfiguration.getInstance().getDimension();


    @Test
    @DisplayName("Verify player adding positively")
    public void test_game_positively() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        final String PLAYER_A = "PlayerA", PLAYER_B = "PlayerB";

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        assertEquals(2, game.getPlayerNumberMap().size());

        assertNotNull(game.getPlayerSession(PLAYER_A));
        assertNotNull(game.getPlayerSession(PLAYER_B));

        assertEquals(PLAYER_A, game.getPlayerSession(PLAYER_A).getUsername());
        assertEquals(PLAYER_B, game.getPlayerSession(PLAYER_B).getUsername());
    }


    @Test
    @DisplayName("Verify common goal card initialization")
    public void test_on_game_started_1_positively() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer("A");
        game.addPlayer("B");

        game.onGameStarted();

        // list must be composed by 2 commongoalcards
        assertEquals(2, game.getCommonGoalCards().size());
    }

    @Test
    @DisplayName("Verify personal goal card initialization")
    public void test_on_game_started_2_positively() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer("A");
        game.addPlayer("B");

        game.onGameStarted();

        // all the player must have a personal goal card
        assertNotNull(game.getPlayerSession("A").getPersonalGoalCard());
        assertNotNull(game.getPlayerSession("B").getPersonalGoalCard());

        // verificare che la personal goal card esista effettivamente
        //assertEquals(game.getPlayerSession("A").getPersonalGoalCard(), )
    }

    @Test
    @DisplayName("verify starting player and current player initialization")
    public void test_on_game_started_3_positively() {
        Game game = new Game(GameMode.GAME_MODE_3_PLAYERS);

        final String PLAYER_A = "PlayerA", PLAYER_B = "PlayerB", PLAYER_C = "PlayerC";

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);
        game.addPlayer(PLAYER_C);

        game.onGameStarted();

        assertEquals(3, game.getPlayerNumberMap().size());

        // verify random first-player extraction
        assertTrue(game.getPlayerNumberMap().containsKey(game.getStartingPlayerNumber()));
        assertTrue(game.getPlayerNumberMap().containsValue(game.getCurrentPlayer()));
    }

    @Test
    @DisplayName("verify the correct (re)fill of the board, positively")
    public void test_on_game_started_4_positively()
    {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer("A");
        game.addPlayer("B");

        game.onGameStarted();
        Board testingBoard=new Board();
        Cell[][] testingCell=testingBoard.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                testingCell[i][j].setContent(game.getGameMatrix()[i][j]);
            }
        }

        //verify if the board is full
        assertEquals(0, testingBoard.countEmptyCells(GameMode.GAME_MODE_2_PLAYERS));
    }

    @Test
    @DisplayName("verify the function isSelectionValid, positively")
    @Disabled
    //FIXME isSelectionValid thorw a UnsupportedOperationException
    public void test_is_selection_valid_1_positively()
    {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer("A");
        game.addPlayer("B");

        game.onGameStarted();

        Coordinate coords1 = new Coordinate(3,1);
        Coordinate coords2 = new Coordinate(4,1);
        Set<Coordinate> testingCoordinates = Set.of(coords1,coords2);

        assertTrue(game.isSelectionValid(testingCoordinates));
    }

    @Test
    @DisplayName("verify the function isSelectionValid, positively")
    @Disabled
    //FIXME isSelectionValid thorw a UnsupportedOperationException
    public void test_is_selection_valid_2_positively()
    {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer("A");
        game.addPlayer("B");

        game.onGameStarted();

        Coordinate coords1 = new Coordinate(1,5);
        Coordinate coords2 = new Coordinate(1,4);
        Set<Coordinate> testingCoordinates = Set.of(coords1,coords2);

        assertTrue(game.isSelectionValid(testingCoordinates));
    }
    @Test
    @DisplayName("verify the function isSelectionValid, negatively")
    @Disabled
    //FIXME
    public void test_is_selection_valid_1_negatively()
    {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer("A");
        game.addPlayer("B");

        game.onGameStarted();

        Coordinate coords1 = new Coordinate(7,1);
        Coordinate coords2 = new Coordinate(7,2);
        Set<Coordinate> testingCoordinates = Set.of(coords1,coords2);

        assertFalse(game.isSelectionValid(testingCoordinates));
    }

    @Test
    @DisplayName("verify the function isSelectionValid, negatively")
    @Disabled
    //FIXME
    public void test_is_selection_valid_2_negatively()
    {
        Game game = new Game(GameMode.GAME_MODE_3_PLAYERS);

        game.addPlayer("A");
        game.addPlayer("B");
        game.addPlayer("C");

        game.onGameStarted();

        Coordinate coords1 = new Coordinate(1,4);
        Coordinate coords2 = new Coordinate(1,5);
        Coordinate coords3 = new Coordinate(1,6);

        Set<Coordinate> testingCoordinates = Set.of(coords1,coords2,coords3);

        assertFalse(game.isSelectionValid(testingCoordinates));
    }

    @Test
    @DisplayName("verify the function onPlayerTileSelection, positively")
    @Disabled
    public void test_on_player_tile_selection_1_positively()
    {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer("A");
        game.addPlayer("B");

        game.onGameStarted();

        Coordinate coords1 = new Coordinate(7,3);
        Coordinate coords2 = new Coordinate(7,4);

        Set<Coordinate> testingCoordinates = Set.of(coords1,coords2);

        game.onPlayerSelectionPhase(testingCoordinates);
        List<Tile> testingtileslist = List.of(game.getGameMatrix()[coords1.x()][coords1.y()],
                                              game.getGameMatrix()[coords2.x()][coords2.y()]);

        assertTrue(game.getCurrentPlayer().getPlayerTileSelection().selectionEquals(testingtileslist));

    }

    @Test
    @DisplayName("verify the function onPlayerTileSelection, negatively")
    @Disabled
    public void test_on_player_tile_selection_1_negatively()
    {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer("A");
        game.addPlayer("B");

        game.onGameStarted();

        Coordinate coords1 = new Coordinate(5,1);
        Coordinate coords2 = new Coordinate(5,2);

        Set<Coordinate> testingCoordinates = Set.of(coords1,coords2);

        game.onPlayerSelectionPhase(testingCoordinates);
        List<Tile> testingtileslist = List.of(game.getGameMatrix()[coords1.x()][coords1.y()],
                                              game.getGameMatrix()[coords2.x()][coords2.y()]);

        assertFalse(game.getCurrentPlayer().getPlayerTileSelection().selectionEquals(testingtileslist));

    }

}
