package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.cards.common.CommonGoalCardIdentifier;
import it.polimi.ingsw.utils.model.TurnHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static it.polimi.ingsw.model.board.Tile.*;
import static it.polimi.ingsw.model.game.GameMode.GAME_MODE_2_PLAYERS;
import static it.polimi.ingsw.model.game.GameMode.GAME_MODE_4_PLAYERS;
import static it.polimi.ingsw.model.game.goal.Token.COMMON_GOAL_TOKEN_4_POINTS;
import static it.polimi.ingsw.model.game.goal.Token.COMMON_GOAL_TOKEN_8_POINTS;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameOnPlayerCheckingPhaseTest implements GameTester {
    @Test
    @DisplayName("verify the function OnPlayerCheckingPhase, positively")
    public void test_OnPlayerCheckingPhase_positively_1() {
        Game game = new Game(GAME_MODE_4_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);
        game.addPlayer(PLAYER_C);
        game.addPlayer(PLAYER_D);

        game.onGameStarted();

        Coordinate c1 = new Coordinate(1, 3);
        Coordinate c2 = new Coordinate(1, 4);

        Set<Coordinate> selection = Set.of(c1, c2);

        if (game.isSelectionValid(selection)) {
            Tile[][] matrix = game.getGameMatrix();

            List<Tile> tiles = List.of(
                    matrix[c1.x()][c1.y()],
                    matrix[c2.x()][c2.y()]
            );
            game.onPlayerSelectionPhase(selection);

            game.onPlayerInsertionPhase(1, tiles);
            game.onPlayerCheckingPhase();

            assertFalse(game.getCurrentPlayer().getBookshelf().isFull());
            assertTrue(game.getCurrentPlayer().getAcquiredTokens().isEmpty());
        }
    }

    @Test
    @DisplayName("verify the function OnPlayerCheckingPhase, positively")
    public void test_OnPlayerCheckingPhase_positively_2() {
        Game game = new Game(GAME_MODE_2_PLAYERS);
        Tile[][] shelfMatrix = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {PLANT, null, PLANT, null, null},
                {GAME, PLANT, PLANT, null, null},
                {PLANT, TROPHY, PLANT, null, null}
        };

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        game.onGameStarted();

        game.getCurrentPlayer().getBookshelf().fillUpBookShelf(shelfMatrix);

        game.onPlayerCheckingPhase();

        if (game.getCommonGoalCards().stream().map(status -> status.getCommonGoalCard().getId()).toList().contains(CommonGoalCardIdentifier.X_TILES))
            assertTrue(game.getCurrentPlayer().getAcquiredTokens().contains(COMMON_GOAL_TOKEN_8_POINTS));
        else
            assertTrue(game.getCurrentPlayer().getAcquiredTokens().isEmpty());

        String y = game.getSessions().getByNumber(TurnHelper.getNextPlayerNumber(game.getCurrentPlayer().getPlayerNumber(), game.getGameMode())).getUsername();
        game.onNextTurn(y);

        game.getCurrentPlayer().getBookshelf().fillUpBookShelf(shelfMatrix);

        game.onPlayerCheckingPhase();

        if (game.getCommonGoalCards().stream().map(status -> status.getCommonGoalCard().getId()).toList().contains(CommonGoalCardIdentifier.X_TILES))
            assertTrue(game.getCurrentPlayer().getAcquiredTokens().contains(COMMON_GOAL_TOKEN_4_POINTS));
        else
            assertTrue(game.getCurrentPlayer().getAcquiredTokens().isEmpty());

    }


}