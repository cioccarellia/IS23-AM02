package it.polimi.ingsw.cli;


import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.ViewEventHandler;
import it.polimi.ingsw.ui.cli.CliApp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.beans.EventHandler;

import static it.polimi.ingsw.model.game.GameMode.GAME_MODE_2_PLAYERS;

public class cliTests {

    private EventHandler eventHandler;

    @Test
    @DisplayName("Verify the correct function of PrintGameModel method, positively")
    public void printGameModelTest_Positively() {
        CliApp cliApp = new CliApp((ViewEventHandler) eventHandler);

        Game game = new Game(GAME_MODE_2_PLAYERS);
        game.addPlayer("alberto");
        game.addPlayer("marco");
        game.onGameStarted();

        cliApp.modelUpdate(game);
        cliApp.printGameModel();
    }
}
