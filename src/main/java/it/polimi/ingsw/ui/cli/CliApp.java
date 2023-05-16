package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.PlayerNumber;
import it.polimi.ingsw.ui.UiGateway;
import it.polimi.ingsw.ui.ViewEventHandler;
import it.polimi.ingsw.ui.cli.parser.ColumnParser;
import it.polimi.ingsw.ui.cli.parser.CoordinatesParser;
import it.polimi.ingsw.ui.cli.parser.PlayerTilesOrderInsertionParser;
import it.polimi.ingsw.ui.cli.printer.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static it.polimi.ingsw.model.game.GameMode.GAME_MODE_4_PLAYERS;
import static it.polimi.ingsw.model.game.GameStatus.ENDED;
import static it.polimi.ingsw.utils.model.TurnHelper.getNextPlayerNumber;

public class CliApp implements UiGateway {

    public Game model;
    private ViewEventHandler handler;

    public CliApp() {
    }

    public static void main(String[] args) {
        CliApp app = new CliApp();
        Game game = new Game(GAME_MODE_4_PLAYERS);

        game.addPlayer("Alberto");
        game.addPlayer("Cookie");
        game.addPlayer("Giulia");
        game.addPlayer("Marco");

        app.modelUpdate(game);
        app.onGameStarted();

        while (game.getGameStatus() != ENDED) {
            app.gameSelection();
            app.modelUpdate(game);

            app.gameInsertion();
            app.modelUpdate(game);

            app.gameNextTurn();
            app.modelUpdate(game);
        }

        app.onGameEnded();
    }

    /**
     * Calls model's onGameStarted and notify user that the game is running
     */
    @Override
    public void onGameStarted() {
        model.onGameStarted();
        Console.out("Game has started, Enjoy the game and good luck!\n");
    }

    /**
     * Updates model's instance in order to show users an updated model every turn
     *
     * @param game model is passed to the function on order to update always the same model
     */
    @Override
    public void modelUpdate(Game game) {
        this.model = game;
    }

    /**
     * Shows users' Bookshelves, updates Board, Common goal cards and Tokens, First Player and Current Player, Private
     * goal card
     */
    public void printGameModel() {
        Console.out("Board: \n");

        BoardPrinter.print(model.getBoard());

        Console.out("First common goal card:\n");

        CommonGoalCardsPrinter.print(model.getCommonGoalCardsStatus().get(0));
        Console.out("\n");

        Console.out("Second common goal card:\n");
        CommonGoalCardsPrinter.print(model.getCommonGoalCardsStatus().get(1));

        Console.out("\nPersonal goal card for player: \n" +
                model.getSessions().getByNumber(model.getCurrentPlayer().getPlayerNumber()).getUsername() + ":\n");
        Console.flush();
        //TODO printing the current player's name is temporary, once fixed remove
        //TODO we need to print the player's card for whom is asking for their private goal card, not the current player. Others can't see other player's card
        PersonalGoalCardPrinter.print(model.getCurrentPlayer().getPersonalGoalCard());
        Console.out("\n");

        PlayerNumber player = model.getCurrentPlayer().getPlayerNumber();

        for (int i = 0; i < model.getPlayerNumber(); i++) {
            Console.out("\nBookshelf for player: " +
                    model.getSessions().getByNumber(player).getUsername() + ":\n");

            BookshelfPrinter.print(model.getPlayerSession(model.getSessions().getByNumber(player)
                    .getUsername()).getBookshelf());

            player = getNextPlayerNumber(player, model.getGameMode());
        }

        Console.out("\nThe first player is: " +
                model.getSessions().getByNumber(model.getStartingPlayerNumber()).getUsername() + "\n");

        Console.out("\nThe current player is: " + model.getCurrentPlayer().getUsername() + "\n");
        Console.out("\n");
        PlayersListPrinter.print(model);

    }

    /**
     * Calls model's onPlayerSelectionPhase in order have the current player selecting up to three tiles.
     */
    @Override
    public void gameSelection() {
        printGameModel();
        Set<Coordinate> validCoordinates = CoordinatesParser.scan(model);
        model.onPlayerSelectionPhase(validCoordinates);

    }

    /**
     * Inserts selected tiles inside current player's own Bookshelf, in the column they chose.
     */
    @Override
    public void gameInsertion() {
        int tilesSize = model.getCurrentPlayer().getPlayerTileSelection().getSelectedTiles().size();
        int column = ColumnParser.scan(model.getGameMatrix(), tilesSize);
        List<Tile> orderedTiles = new ArrayList<>();
        if (tilesSize > 1) {
            orderedTiles = PlayerTilesOrderInsertionParser
                    .scan(model.getCurrentPlayer().getPlayerTileSelection().getSelectedTiles());
        }
        model.onPlayerInsertionPhase(column, orderedTiles);

        model.onPlayerCheckingPhase();
    }

    public void gameNextTurn() {
        model.onNextTurn(model.getSessions().getByNumber(getNextPlayerNumber(model.getCurrentPlayer().getPlayerNumber(),
                model.getGameMode())).getUsername());
    }

    /**
     * Shows ranking and announces the winner.
     */
    @Override
    public void onGameEnded() {
        Console.out("""
                The game has ended. Congratulations to the winner!
                Here's the player's ranking with their points:
                """);

        List<Pair<PlayerNumber, Integer>> playersRanking = model.onGameEnded();

        playersRanking.forEach(System.out::println);
    }

    public void setHandler(ViewEventHandler handler) {
        this.handler = handler;
    }

}
