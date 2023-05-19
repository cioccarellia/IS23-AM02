package it.polimi.ingsw.controller.client.gateways;

import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.services.ClientService;
import it.polimi.ingsw.services.ServerService;
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
 */
public class RmiClientGateway extends ClientGateway {

    private static final Logger logger = LoggerFactory.getLogger(RmiClientGateway.class);

    /**
     * RMI stub object to forward the methods on
     * */
    final private ServerService rmiServerStub;

    public RmiClientGateway(String serverHost, int serverRmiPort) {
        Registry registry;

        try {
            registry = LocateRegistry.getRegistry(serverHost, serverRmiPort);
            rmiServerStub = (ServerService) registry.lookup(ServerService.NAME);
        } catch (RemoteException | NotBoundException e) {
            logger.error("error while looking up the registry info for ServerService, serverHost={}, rmiPort={}", serverHost, serverRmiPort);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void gameStartRequest(String username, GameMode mode, ClientProtocol protocol, ClientService remoteService) {
        try {
            rmiServerStub.gameStartRequest(username, mode, protocol, remoteService);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void gameConnectionRequest(String username, ClientProtocol protocol, ClientService remoteService) {
        try {
            rmiServerStub.gameConnectionRequest(username, protocol, remoteService);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void gameSelectionTurnResponse(String username, Set<Coordinate> selection) {
        try {
            rmiServerStub.gameSelectionTurnResponse(username, selection);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void gameInsertionTurnResponse(String username, List<Tile> tiles, int column) {
        try {
            rmiServerStub.gameInsertionTurnResponse(username, tiles, column);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void keepAlive(String username) {
        try {
            rmiServerStub.keepAlive(username);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
