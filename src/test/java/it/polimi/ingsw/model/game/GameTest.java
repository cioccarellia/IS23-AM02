package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.board.cell.Cell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase.*;

import static org.junit.jupiter.api.Assertions.*;


public class GameTest implements GameTester {


    @Test
    @DisplayName("Verify player adding positively")
    public void test_game_positively() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        assertEquals(2, game.getPlayerNumberMap().size());

        assertNotNull(game.getPlayerSession(PLAYER_A));
        assertNotNull(game.getPlayerSession(PLAYER_B));

        assertEquals(PLAYER_A, game.getPlayerSession(PLAYER_A).getUsername());
        assertEquals(PLAYER_B, game.getPlayerSession(PLAYER_B).getUsername());
    }


    @Test
    @DisplayName("Verify common goal card initialization #1")
    public void test_onGameStarted_1_positively() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        game.onGameStarted();

        // list must be composed by 2 commongoalcards
        assertEquals(2, game.getCommonGoalCards().size());
    }

    @Test
    @DisplayName("Verify personal goal card initialization #2")
    public void test_onGameStarted_2_positively() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        game.onGameStarted();

        // all the player must have a personal goal card
        assertNotNull(game.getPlayerSession(PLAYER_A).getPersonalGoalCard());
        assertNotNull(game.getPlayerSession(PLAYER_B).getPersonalGoalCard());

        // verificare che la personal goal card esista effettivamente
        //assertEquals(game.getPlayerSession(PLAYER_A).getPersonalGoalCard(), )
    }

    @Test
    @DisplayName("verify starting player and current player initialization #3")
    public void test_onGameStarted_3_positively() {
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
    @DisplayName("verify the correct (re)fill of the board, positively #4")
    public void test_onGameStarted_4_positively() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        game.onGameStarted();
        Board testingBoard = new Board();
        Cell[][] testingCell = testingBoard.getCellMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                testingCell[i][j].setContent(game.getGameMatrix()[i][j]);
            }
        }

        //verify if the board is full
        assertEquals(0, testingBoard.countEmptyCells(GameMode.GAME_MODE_2_PLAYERS));
    }

    @Test
    @DisplayName("verify the function isSelectionValid, positively #1")
    public void test_isSelectionValid_1_positively() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        game.onGameStarted();

        Coordinate coords1 = new Coordinate(4, 1);
        Coordinate coords2 = new Coordinate(5, 1);
        Set<Coordinate> selction = Set.of(coords1, coords2);

        assertTrue(game.isSelectionValid(selction));
    }

    @Test
    @DisplayName("verify the function isSelectionValid, positively #2")
    public void test_isSelectionValid_2_positively() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        game.onGameStarted();

        Coordinate coords1 = new Coordinate(1, 5);
        Coordinate coords2 = new Coordinate(1, 4);
        Coordinate coords3 = new Coordinate(1, 3);
        Set<Coordinate> selction = Set.of(coords1, coords2, coords3);

        assertTrue(game.isSelectionValid(selction));
    }

    @Test
    @DisplayName("verify the function isSelectionValid, positively #3")
    public void test_isSelectionValid_3_positively() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        game.onGameStarted();

        Coordinate coords1 = new Coordinate(1, 5);
        Coordinate coords2 = new Coordinate(1, 4);
        Set<Coordinate> selction = Set.of(coords1, coords2);

        assertTrue(game.isSelectionValid(selction));
    }

    @Test
    @DisplayName("verify the function isSelectionValid, negatively #1")
    public void test_isSelectionValid_1_negatively() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        game.onGameStarted();

        Coordinate coords1 = new Coordinate(7, 1);
        Coordinate coords2 = new Coordinate(7, 4);
        Set<Coordinate> selction = Set.of(coords1, coords2);

        assertFalse(game.isSelectionValid(selction));
    }

    @Test
    @DisplayName("verify the function isSelectionValid, negatively #2")
    public void test_isSelectionValid_2_negatively() {
        Game game = new Game(GameMode.GAME_MODE_3_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);
        game.addPlayer(PLAYER_C);

        game.onGameStarted();

        Coordinate coords1 = new Coordinate(1, 4);
        Coordinate coords2 = new Coordinate(1, 5);
        Coordinate coords3 = new Coordinate(1, 8);

        Set<Coordinate> selction = Set.of(coords1, coords2, coords3);

        assertFalse(game.isSelectionValid(selction));
    }

    @Test
    @DisplayName("verify the function isSelectionValid, negatively #3")
    public void test_isSelectionValid_3_negatively() {
        Game game = new Game(GameMode.GAME_MODE_3_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);
        game.addPlayer(PLAYER_C);

        game.onGameStarted();

        Coordinate coords1 = new Coordinate(4, 2);
        Coordinate coords2 = new Coordinate(4, 3);
        Coordinate coords3 = new Coordinate(4, 4);

        Set<Coordinate> selection = Set.of(coords1, coords2, coords3);

        assertFalse(game.isSelectionValid(selection));
    }

    @Test
    @DisplayName("verify the function onPlayerSelectionPhase, positively #1")
    public void test_onPlayerSelectionPhase_1_positively() {
        Game game = new Game(GameMode.GAME_MODE_4_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);
        game.addPlayer(PLAYER_C);
        game.addPlayer(PLAYER_D);

        game.onGameStarted();

        Coordinate coords1 = new Coordinate(8, 5);
        Coordinate coords2 = new Coordinate(8, 4);

        Set<Coordinate> selction = Set.of(coords1, coords2);

        game.onPlayerSelectionPhase(selction);

        List<Tile> testingTiles = List.of(game.getGameMatrix()[coords1.x()][coords1.y()],
                game.getGameMatrix()[coords2.x()][coords2.y()]);

        assertTrue(game.getCurrentPlayer().getPlayerTileSelection().selectionEquals(testingTiles));
        assertEquals(INSERTING, game.getCurrentPlayer().getPlayerCurrentGamePhase());

    }

    @Test
    @DisplayName("Checks that calling onPlayerSelectionPhase(coordinates) actually saves the selection on" +
            "the user session upon successful execution")
    public void test_onPlayerSelectionPhase_assertSelectionSavesCoordinatesinUserSession() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        game.onGameStarted();

        Coordinate coords1 = new Coordinate(5, 1);
        Coordinate coords2 = new Coordinate(5, 2);

        Set<Coordinate> selction = Set.of(coords1, coords2);


        game.onPlayerSelectionPhase(selction);

        List<Tile> testingTiles = List.of(
                game.getGameMatrix()[coords1.x()][coords1.y()],
                game.getGameMatrix()[coords2.x()][coords2.y()]
        );

        assertTrue(game.getCurrentPlayer().getPlayerTileSelection().selectionEquals(testingTiles));

    }

    @Test
    @DisplayName("verify the function onPlayerSelectionPhase, coordinates are not valid exception")
    public void test_onPlayerSelectionPhase_coordinates_not_valid_exception(){
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        game.onGameStarted();

        Coordinate c1 = new Coordinate(0, 0);
        Coordinate c2 = new Coordinate(0, 1);

        Set<Coordinate> selection = Set.of(c1, c2);

        Exception exception = assertThrows(IllegalStateException.class, () -> game.onPlayerSelectionPhase(selection));

        String expectedMessage = "Coordinates are not valid";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    @DisplayName("verify the function onPlayerInsertionPhase, positively")
    public void test_onPlayerInsertionPhase_positively() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        game.onGameStarted();

        Coordinate c1 = new Coordinate(1, 3);
        Coordinate c2 = new Coordinate(1, 4);

        Set<Coordinate> selection = Set.of(c1, c2);

        game.onPlayerSelectionPhase(selection);


        Tile[][] matrix = game.getGameMatrix();

        List<Tile> testingTilesList = List.of(
                matrix[c1.x()][c1.y()],
                matrix[c2.x()][c2.y()]
        );

        game.onPlayerInsertionPhase(1, testingTilesList);

        assertEquals(matrix[c1.x()][c1.y()], testingTilesList.get(0));
        assertEquals(matrix[c2.x()][c2.y()], testingTilesList.get(1));
        assertEquals(CHECKING, game.getCurrentPlayer().getPlayerCurrentGamePhase());
    }

}