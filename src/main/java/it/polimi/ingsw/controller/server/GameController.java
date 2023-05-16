package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.app.server.ClientConnectionsManager;
import it.polimi.ingsw.controller.server.connection.ConnectionStatus;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameStartError;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.game.GameStatus;
import it.polimi.ingsw.model.game.session.SessionManager;
import it.polimi.ingsw.model.player.PlayerNumber;
import it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase;
import it.polimi.ingsw.net.rmi.ClientService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

import static it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase.SELECTING;

public class GameController implements ServerService {

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    /**
     * Keeps a map associating a username (unique identifier for a player)
     * to the specific details of its connection to the server.
     */
    private final ClientConnectionsManager connectionsManager;

    /**
     * Game instance
     */
    private Game game;

    private int maxPlayerAmount;

    private ServerStatus serverStatus = ServerStatus.NO_GAME_STARTED;

    private SessionManager sessions;

    public GameController() {
        this.connectionsManager = new ClientConnectionsManager();}

    public GameController(ClientConnectionsManager connectionsManager) {
        this.connectionsManager = connectionsManager;
    }

    @Override
    public void synchronizeConnectionLayer(String username, ClientService service) throws RemoteException {

    }

    @Override
    public ServerStatus serverStatusRequest() {
        return serverStatus;
    }

    @Override
    public SingleResult<GameStartError> gameStartRequest(GameMode mode, String username, ClientProtocol protocol) {
        logger.info("gameStartedRequest, mode={}, username={}, protocol={}", mode, username, protocol);

        if (serverStatus == ServerStatus.NO_GAME_STARTED) {
            game = new Game(mode);
            sessions= new SessionManager(game.getGameMode());
            maxPlayerAmount = mode.maxPlayerAmount();

            serverStatus = ServerStatus.GAME_INITIALIZING;

            connectionsManager.add(username, protocol, ConnectionStatus.OPEN);
            game.addPlayer(username);

            logger.info("returning success from gameStartRequest()");
            return new SingleResult.Success<>();
        } else {
            logger.warn("returning failure from gameStartRequest()");
            return new SingleResult.Failure<>(GameStartError.GAME_ALREADY_STARTED);
        }
    }


    // Creates a connection between client and server
    @Override
    public SingleResult<GameConnectionError> gameConnectionRequest(String username, ClientProtocol protocol) {
        logger.info("gameConnectionRequest(username={}, protocol={})", username, protocol);

        assert connectionsManager.size() <= maxPlayerAmount;

        if (serverStatus == ServerStatus.GAME_RUNNING) {
            return new SingleResult.Failure<>(GameConnectionError.GAME_ALREADY_STARTED);
        }

        if (connectionsManager.size() == maxPlayerAmount) {
            return new SingleResult.Failure<>(GameConnectionError.MAX_PLAYER_REACHED);
        }

        if (connectionsManager.containsUsername(username)) {
            return new SingleResult.Failure<>(GameConnectionError.USERNAME_ALREADY_IN_USE);
        }

        if (game.getGameStatus() == GameStatus.ENDED) {
            return new SingleResult.Failure<>(GameConnectionError.GAME_ALREADY_ENDED);
        }


        // if successful & can start game
        if (maxPlayerAmount == connectionsManager.size() + 1) {
            serverStatus = ServerStatus.GAME_RUNNING;
        }

        connectionsManager.add(username, protocol, ConnectionStatus.OPEN);
        game.addPlayer(username);

        return new SingleResult.Success<>();
    }


    // Game logic
    public boolean isUsernameActivePlayer(@NotNull String username) {
        return username.equals(game.getCurrentPlayer().getUsername());
    }

    public boolean shouldStandbyGame() {
        return connectionsManager.values().stream().filter(player -> player.getStatus() == ConnectionStatus.DISCONNECTED).count() >= maxPlayerAmount - 1;
    }


    @Override
    public SingleResult<TileSelectionFailures> gameSelectionTurnResponse(String username, Set<Coordinate> selection) {
        logger.info("gameSelectionTurnResponse(username={}, selection={})", username, selection);

        if (isUsernameActivePlayer(username)) {
            return new SingleResult.Failure<>(TileSelectionFailures.UNAUTHORIZED_PLAYER);
        }

        if (game.getCurrentPlayer().getPlayerCurrentGamePhase() != PlayerCurrentGamePhase.SELECTING) {
            return new SingleResult.Failure<>(TileSelectionFailures.UNAUTHORIZED_ACTION);
        }

        if (!game.isSelectionValid(selection)) {
            return new SingleResult.Failure<>(TileSelectionFailures.UNAUTHORIZED_SELECTION);
        }

        game.onPlayerSelectionPhase(selection);
        return new SingleResult.Success<>();
    }


    @Override
    public SingleResult<BookshelfInsertionFailure> gameInsertionTurnResponse(String username, List<Tile> tiles, int column) {
        logger.info("onPlayerBookshelfTileInsertionRequest(username={}, tiles={}, column={})", username, tiles, column);

        if (isUsernameActivePlayer(username)) {
            return new SingleResult.Failure<>(BookshelfInsertionFailure.WRONG_PLAYER);
        }

        if (game.getCurrentPlayer().getPlayerCurrentGamePhase() != PlayerCurrentGamePhase.INSERTING) {
            return new SingleResult.Failure<>(BookshelfInsertionFailure.WRONG_STATUS);
        }

        if (!game.getCurrentPlayer().getPlayerTileSelection().selectionEquals(tiles)) {
            return new SingleResult.Failure<>(BookshelfInsertionFailure.WRONG_SELECTION);
        }

        if (column < 0 || column >= BookshelfConfiguration.getInstance().cols()) {
            return new SingleResult.Failure<>(BookshelfInsertionFailure.ILLEGAL_COLUMN);
        }

        if (tiles.size() > LogicConfiguration.getInstance().maxSelectionSize()) {
            return new SingleResult.Failure<>(BookshelfInsertionFailure.TOO_MANY_TILES);
        }

        if (!game.getCurrentPlayer().getBookshelf().canFit(column, tiles.size())) {
            return new SingleResult.Failure<>(BookshelfInsertionFailure.NO_FIT);
        }

        game.onPlayerInsertionPhase(column, tiles);

        onPlayerCheckingRequest();
        return new SingleResult.Success<>();
    }

    public void onPlayerCheckingRequest() {
        logger.info("onPlayerCheckingRequest()");
        game.onPlayerCheckingPhase();
    }

    @Override
    public void keepAlive(String username) {
        logger.info("keepAlive(username={})", username);
        if (connectionsManager.containsUsername(username)) {
            connectionsManager.setConnectionStatus(username, ConnectionStatus.OPEN);
        } else {
            logger.warn("Wrong keep alive username");
        }
    }

    public void onNextTurn(PlayerNumber nextPlayerNumber) {
        // assume the username is correct
        assert sessions.isPresent(nextPlayerNumber);
        PlayerNumber currentPlayerNumber = game.getCurrentPlayer().getPlayerNumber();

        currentPlayerNumber = sessions.getByNumber(nextPlayerNumber).getPlayerNumber();
        game.getCurrentPlayer().setPlayerCurrentGamePhase(SELECTING);
    }
}