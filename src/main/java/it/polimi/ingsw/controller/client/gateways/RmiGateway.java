package it.polimi.ingsw.controller.client.gateways;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Set;

/**
 * Client side class used as communication interface to talk to the server
 * */
public class RmiGateway extends Gateway {

    protected static final Logger logger = LoggerFactory.getLogger(RmiGateway.class);

    final private ServerService rmiServerStub;

    public RmiGateway(String serverHost, int serverPort) {
        Registry registry;

        try {
            registry = LocateRegistry.getRegistry(serverHost, serverPort);
            rmiServerStub = (ServerService) registry.lookup(ServerService.NAME);
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public ServerStatus serverStatusRequest() {
        try {
            return rmiServerStub.serverStatusRequest();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SingleResult<GameStartError> gameStartRequest(GameMode mode, String username, ClientProtocol protocol) {
        try {
            return rmiServerStub.gameStartRequest(mode, username, protocol);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SingleResult<GameConnectionError> gameConnectionRequest(String username, ClientProtocol protocol) {
        try {
            return rmiServerStub.gameConnectionRequest(username, protocol);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SingleResult<TileSelectionFailures> gameSelectionTurnResponse(String username, Set<Coordinate> selection) {
        try {
            return rmiServerStub.gameSelectionTurnResponse(username, selection);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SingleResult<BookshelfInsertionFailure> gameInsertionTurnResponse(String username, List<Tile> tiles, int column) {
        try {
            return rmiServerStub.gameInsertionTurnResponse(username, tiles, column);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void keepAlive(String player) {
        try {
            rmiServerStub.keepAlive(player);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
