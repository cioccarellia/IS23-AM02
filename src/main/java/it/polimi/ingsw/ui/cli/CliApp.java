package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.UiGateway;
import it.polimi.ingsw.ui.ViewEventHandler;
import it.polimi.ingsw.ui.cli.printer.BoardPrinter;

public class CliApp implements UiGateway {

    private Game model;
    final private ViewEventHandler handler;
    private boolean hasReceivedInitialModel = false;

    public CliApp(ViewEventHandler handler) {
        this.handler = handler;
    }

    @Override
    public int onGameStarted() {
        return 0;
    }


    @Override
    public int modelUpdate(Game game) {
        model = game;
        return 0;
    }


    private void printGameModel() {
        Console.out("Board:");
        BoardPrinter.print(model.getBoard());
    }


    @Override
    public int gameSelection() {
        return 0;
    }


    @Override
    public int gameInsertion() {
        return 0;
    }


    @Override
    public int onGameEnded() {
        return 0;
    }
}
