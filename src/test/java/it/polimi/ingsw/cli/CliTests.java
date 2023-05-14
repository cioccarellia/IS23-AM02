package it.polimi.ingsw.cli;


import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.ViewEventHandler;
import it.polimi.ingsw.ui.cli.CliApp;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.beans.EventHandler;
import java.io.*;

import static it.polimi.ingsw.model.game.GameMode.GAME_MODE_2_PLAYERS;
import static it.polimi.ingsw.model.game.GameMode.GAME_MODE_4_PLAYERS;

public class CliTests {

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

    @Test
    @DisplayName("Verify the correct function of gameSelection method, positively")
    @Disabled
    public void gameSelectionTest_Positively() throws UnsupportedEncodingException {

        CliApp cliApp = new CliApp((ViewEventHandler) eventHandler);

        Game game = new Game(GAME_MODE_4_PLAYERS);

        game.addPlayer("alberto");
        game.addPlayer("marco");
        game.addPlayer("andrea");
        game.addPlayer("giulia");
        game.onGameStarted();

        cliApp.modelUpdate(game);

        cliApp.printGameModel();
        //TODO capire come passare CoordinateParser.scan() i dati in input che dovrebbe passargli l'utente
        cliApp.gameSelection();
    }


    @Test
    @DisplayName("Verify the correct function of gameInsertion method, positively")
    @Disabled
    public void gameInsertionTest_Positively() {
        CliApp cliApp = new CliApp((ViewEventHandler) eventHandler);

        Game game = new Game(GAME_MODE_4_PLAYERS);

        game.addPlayer("alberto");
        game.addPlayer("marco");
        game.addPlayer("andrea");
        game.addPlayer("giulia");
        game.onGameStarted();

        cliApp.modelUpdate(game);

        cliApp.printGameModel();

        //TODO capire come passare CoordinateParser.scan() i dati in input che dovrebbe passargli l'utente
        cliApp.gameSelection();
        //TODO stesso problema, ma con ColumnParser.scan() e PlayerTilesOrderInsertionParser.scan()
        cliApp.gameInsertion();
    }
}
