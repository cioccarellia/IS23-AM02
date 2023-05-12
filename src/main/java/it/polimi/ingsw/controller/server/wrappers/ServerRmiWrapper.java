package it.polimi.ingsw.controller.server.wrappers;

import it.polimi.ingsw.app.server.ClientConnectionsManager;
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
import it.polimi.ingsw.net.rmi.ClientService;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

/**
 * RMI wrapper
 */
public class ServerRmiWrapper extends ServerWrapper implements ServerService {

    private final ServerService server;

    private final ClientConnectionsManager connectionsManager;

    public ServerRmiWrapper(ServerService server, ClientConnectionsManager connectionsManager) throws RemoteException {
        this.server = server;
        this.connectionsManager = connectionsManager;
    }

    @Override
    public ServerService serverService() {
        return server;
    }

    @Override
    public ClientProtocol protocol() {
        return ClientProtocol.RMI;
    }


    @Override
    public void synchronizeConnectionLayer(String username, ClientService service) {
        try {
            connectionsManager.get(username).getStash().setClientService(service);
            server.synchronizeConnectionLayer(username, service);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ServerStatus serverStatusRequest() {
        try {
            return server.serverStatusRequest();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SingleResult<GameStartError> gameStartRequest(GameMode mode, String username, ClientProtocol protocol) {
        try {
            return server.gameStartRequest(mode, username, protocol);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SingleResult<GameConnectionError> gameConnectionRequest(String username, ClientProtocol protocol) {
        try {
            return server.gameConnectionRequest(username, protocol);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SingleResult<TileSelectionFailures> gameSelectionTurnResponse(String username, Set<Coordinate> selection) {
        try {
            return server.gameSelectionTurnResponse(username, selection);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SingleResult<BookshelfInsertionFailure> gameInsertionTurnResponse(String username, List<Tile> tiles, int column) {
        try {
            return server.gameInsertionTurnResponse(username, tiles, column);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void keepAlive(String player) {
        try {
            server.keepAlive(player);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

}
