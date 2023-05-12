package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.UiGateway;
import it.polimi.ingsw.ui.ViewEventHandler;
import it.polimi.ingsw.ui.cli.printer.BoardPrinter;
import it.polimi.ingsw.ui.cli.printer.BookshelfPrinter;
import it.polimi.ingsw.ui.cli.printer.CommonGoalCardsPrinter;

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
        Console.out("Board: \n");
        BoardPrinter.print(model.getBoard());
        Console.out("Common goal cards: \n");
        //CommonGoalCardsPrinter.print(model.getCommonGoalCards().get(0).toString());
        //CommonGoalCardsPrinter.print(model.getCommonGoalCards().get(1).toString());
        Console.out("Bookshelf player 1: \n");

        Console.out("Bookshelf player 2: \n");

        Console.out("Bookshelf player 3: \n");

        Console.out("Bookshelf player 4: \n");

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
