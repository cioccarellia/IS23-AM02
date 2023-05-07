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

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Set;


public class RMIGateway implements ServerService {

    final private ServerService stub;

    public RMIGateway(String serverIp, int serverPort) {
        Registry registry;

        try {
            registry = LocateRegistry.getRegistry(serverIp, serverPort);
            stub = (ServerService) registry.lookup(ServerService.NAME);
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public ServerStatus serverStatusRequest() {
        return stub.serverStatusRequest();
    }

    @Override
    public SingleResult<GameStartError> gameStartRequest(GameMode mode, String username, ClientProtocol protocol) {
        return stub.gameStartRequest(mode, username, protocol);
    }

    @Override
    public SingleResult<GameConnectionError> gameConnectionRequest(String username, ClientProtocol protocol) {
        return stub.gameConnectionRequest(username, protocol);
    }

    @Override
    public SingleResult<TileSelectionFailures> gameSelectionTurnResponse(String username, Set<Coordinate> selection) {
        return stub.gameSelectionTurnResponse(username, selection);
    }

    @Override
    public SingleResult<BookshelfInsertionFailure> gameInsertionTurnResponse(String username, List<Tile> tiles, int column) {
        return stub.gameInsertionTurnResponse(username, tiles, column);
    }

    @Override
    public void keepAlive(String player) {
        stub.keepAlive(player);
    }
}
