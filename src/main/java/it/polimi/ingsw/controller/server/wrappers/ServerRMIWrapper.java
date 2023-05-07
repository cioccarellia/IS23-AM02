package it.polimi.ingsw.controller.server.wrappers;

import it.polimi.ingsw.controller.server.ServerService;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameStartError;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.game.GameMode;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Set;

/**
 * RMI wrapper
 * */
public class ServerRMIWrapper extends UnicastRemoteObject implements ServerService {

    private final ServerService server;

    public ServerRMIWrapper(ServerService server) throws RemoteException {
        this.server = server;
    }

    @Override
    public ServerStatus serverStatusRequest() {
        return server.serverStatusRequest();
    }

    @Override
    public SingleResult<GameStartError> gameStartRequest(GameMode mode, String username, ClientProtocol protocol) {
        return server.gameStartRequest(mode, username, protocol);
    }

    @Override
    public SingleResult<GameConnectionError> gameConnectionRequest(String username, ClientProtocol protocol) {
        return server.gameConnectionRequest(username, protocol);
    }

    @Override
    public SingleResult<TileSelectionFailures> gameSelectionTurnResponse(String username, Set<Coordinate> selection) {
        return server.gameSelectionTurnResponse(username, selection);
    }

    @Override
    public SingleResult<BookshelfInsertionFailure> gameInsertionTurnResponse(String username, List<Tile> tiles, int column) {
        return server.gameInsertionTurnResponse(username, tiles, column);
    }

    @Override
    public void keepAlive(String player) {
        server.keepAlive(player);
    }
}
