package it.polimi.ingsw.ui.game.cli;

import com.github.tomaslanger.chalk.Chalk;
import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.controller.server.result.types.TileInsertionSuccess;
import it.polimi.ingsw.controller.server.result.types.TileSelectionSuccess;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.chat.ChatTextMessage;
import it.polimi.ingsw.model.chat.MessageRecipient;
import it.polimi.ingsw.model.game.score.PlayerScore;
import it.polimi.ingsw.model.player.PlayerSession;
import it.polimi.ingsw.ui.Renderable;
import it.polimi.ingsw.ui.game.GameGateway;
import it.polimi.ingsw.ui.game.GameViewEventHandler;
import it.polimi.ingsw.ui.game.cli.console.AsyncStreamReader;
import it.polimi.ingsw.ui.game.cli.parser.ChatMessageParser;
import it.polimi.ingsw.ui.game.cli.parser.ColumnParser;
import it.polimi.ingsw.ui.game.cli.parser.CoordinatesParser;
import it.polimi.ingsw.ui.game.cli.parser.PlayerTilesOrderInsertionParser;
import it.polimi.ingsw.ui.game.cli.printer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class CliApp implements GameGateway, Renderable {

    private static final Logger logger = LoggerFactory.getLogger(CliApp.class);

    /**
     * Keeps a reference to the game model
     */
    private GameModel model;
    private ServerStatus status;
    private List<PlayerInfo> playerInfo;

    private List<ChatTextMessage> messages = new ArrayList<>();

    private final GameViewEventHandler handler;

    private final String owner;

    ExecutorService chatCommandStreamReader = Executors.newFixedThreadPool(5);

    public CliApp(GameModel model, GameViewEventHandler handler, String owner) {
        this.model = model;
        this.handler = handler;
        this.owner = owner;

        chatCommandStreamReader.execute(this::startChatStdinReader);
    }

    Future<String> currentChatCommand;

    public void startChatStdinReader() {
        while (true) {
            currentChatCommand = chatCommandStreamReader.submit(new AsyncStreamReader());

            String input = null;
            try {
                input = currentChatCommand.get();
            } catch (CancellationException e) {
                // stop blocking input
                logger.info("Intercepted keyboard input on idle, input=[" + input + "]");
                break;
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }

            if (input == null) {
                continue;
            }

            logger.info("Got message " + input);

            String text = ChatMessageParser.parseMessageText(input.trim());
            MessageRecipient recipient = ChatMessageParser.parseMessageRecipient(input.trim());

            if (text == null || recipient == null) {
                logger.warn("Got a malformed message=\"" + input + "\", can't parse recipient and/or text");
            } else {
                handler.onViewSendMessage(owner, recipient, text);
            }
        }
    }


    /**
     * Calls model's onGameStarted and notify user that the game is running
     */
    @Override
    public void onGameCreated() {
        if (model == null) {
            return;
        }

        Console.out("Hi @" + owner + "! Game has started, Enjoy the game and good luck!\n");

        render();
    }

    /**
     * Updates model's instance in order to show users an updated model every turn
     *
     * @param game model is passed to the function on order to update always the same model
     */
    @Override
    public void modelUpdate(GameModel game) {
        this.model = game;
        render();
    }

    @Override
    public void onGameServerStatusUpdate(ServerStatus status, List<PlayerInfo> playerInfo) {
        this.status = status;
        this.playerInfo = playerInfo;
    }


    /**
     * Shows users' Bookshelves, updates Board, Common goal cards and Tokens, First Player and Current Player, Private
     * goal card
     */
    @Override
    public void render() {
        if (model == null) {
            Console.out(Chalk.on("Void model.").bgRed().toString());
            return;
        }

        Console.printnl(100);

        int messagesToShow = Math.min(messages.size(), 10);

        if (messagesToShow > 0) {
            Console.out("Latest " + messagesToShow + " messages:\n");
            ChatPrinter.printLastNMessages(messages, owner, messagesToShow);
        }

        switch (model.getGameStatus()) {
            case RUNNING, LAST_ROUND -> {
                PlayerSession currentPlayer = model.getCurrentPlayerSession();
                boolean isOwnerTurn = currentPlayer.getUsername().equals(owner);
                Console.outln();

                BoardPrinter.print(model.getBoard());
                CommonGoalCardsPrinter.print(model.getCommonGoalCards());
                Console.printnl(2);

                PersonalGoalCardPrinter.print(model.getSessions().getByUsername(owner).getPersonalGoalCard());
                Console.outln();

                BookshelvesPrinter.print(model);
                Console.outln();

                if (isOwnerTurn) {
                    switch (currentPlayer.getPlayerCurrentGamePhase()) {
                        case IDLE -> {
                            logger.warn("Current user in idle state, model={},", model);
                        }
                        case SELECTING -> {
                            gameSelection();
                        }
                        case INSERTING -> {
                            gameInsertion();
                        }
                    }
                } else {
                    Console.out("@" + currentPlayer.getUsername() + " is " + currentPlayer.getPlayerCurrentGamePhase().toString().toLowerCase());
                    Console.outln();
                }
            }
            case ENDED -> onGameEnded();
            case STANDBY -> onGameStandby();
        }


    }

    /**
     * To be invoked when it's the player turn to select
     */
    public void gameSelection() {
        currentChatCommand.cancel(true);

        Set<Coordinate> validCoordinates = CoordinatesParser.scan(model);
        handler.onViewSelection(validCoordinates);

        chatCommandStreamReader.execute(this::startChatStdinReader);
    }

    /**
     * To be invoked when it's the player turn to insert
     */
    public void gameInsertion() {
        currentChatCommand.cancel(true);
        List<Tile> selectedTiles = model.getCurrentPlayerSession().getPlayerTileSelection().getSelectedTiles();


        int tilesSize = selectedTiles.size();

        int column = ColumnParser.scan(model.getCurrentPlayerSession().getBookshelf().getShelfMatrix(), selectedTiles);

        List<Tile> orderedTiles;

        if (tilesSize > 1) {
            orderedTiles = PlayerTilesOrderInsertionParser.scan(selectedTiles);
        } else {
            orderedTiles = selectedTiles;
        }
        handler.onViewInsertion(column, orderedTiles);


        chatCommandStreamReader.execute(this::startChatStdinReader);
    }


    @Override
    public void onGameSelectionReply(TypedResult<TileSelectionSuccess, TileSelectionFailures> turnResult) {
        switch (turnResult) {
            case TypedResult.Failure<TileSelectionSuccess, TileSelectionFailures> failure -> {
                switch (failure.error()) {
                    case WRONG_GAME_PHASE, UNAUTHORIZED_PLAYER, UNAUTHORIZED_SELECTION:
                        break;
                }
            }
            case TypedResult.Success<TileSelectionSuccess, TileSelectionFailures> success -> {
                // implicit model update
                modelUpdate(success.value().model());
            }
        }
    }

    @Override
    public void onGameInsertionReply(TypedResult<TileInsertionSuccess, BookshelfInsertionFailure> turnResult) {
        switch (turnResult) {
            case TypedResult.Failure<TileInsertionSuccess, BookshelfInsertionFailure> failure -> {

            }
            case TypedResult.Success<TileInsertionSuccess, BookshelfInsertionFailure> success -> {
                // implicit modelupdate
                modelUpdate(success.value().model());
            }
        }
    }


    @Override
    public void chatModelUpdate(List<ChatTextMessage> messages) {
        this.messages = messages;

        ChatPrinter.printChatLastMessage(messages, owner);
    }

    /**
     * Shows ranking and announces the winner.
     */
    @Override
    public void onGameEnded() {
        Console.out("""
                The game has ended.
                Here's the player's ranking with their points:
                """);
        List<PlayerScore> playersRanking = model.getRankings();
        Console.out("Player:          Token points:          Personal goal card points:          Bookshelf points:\n");
        Console.out("\n");
        for (int i = 0; i < playersRanking.size(); i++) {

            Console.out(playersRanking.get(i).username());
            cliFormatiPrinter.printSpaceAfterUsername(playersRanking.get(i).username().length());

            Console.out(playersRanking.get(i).getTokenPoints());
            cliFormatiPrinter.printSpaceAfterTokenPoints(playersRanking.get(i).getTokenPoints());

            Console.out(playersRanking.get(i).getPersonalGoalCardsPoints());
            cliFormatiPrinter.printSpaceAfterPersonalGoalCardPoints(playersRanking.get(i).getPersonalGoalCardsPoints());

            Console.out(playersRanking.get(i).getBookshelfPoints());
            cliFormatiPrinter.printSpaceAfterBookshelfPoints(playersRanking.get(i).getBookshelfPoints());
            Console.out("\n");
        }

        //playersRanking.forEach(System.out::println);
    }

    private void onGameStandby() {
        Console.out("Game standby.\n");
    }
}
