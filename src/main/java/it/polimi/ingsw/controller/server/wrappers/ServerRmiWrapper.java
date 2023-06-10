package it.polimi.ingsw.controller.server.wrappers;

import it.polimi.ingsw.app.server.ClientConnectionsManager;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.chat.MessageRecipient;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.services.ClientService;
import it.polimi.ingsw.services.ServerService;

import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.util.List;
import java.util.Set;

/**
 * RMI wrapper for server controller, unboxing RMI protocol calls (coming from the client)
 * and forwarding them to the server controller
 */
public class ServerRmiWrapper extends ServerWrapper implements ServerService {

    private final ServerService controller;

    private final ClientConnectionsManager connectionsManager;

    public ServerRmiWrapper(ServerService controller, ClientConnectionsManager connectionsManager) throws RemoteException {
        this.controller = controller;
        this.connectionsManager = connectionsManager;
    }

    @Override
    public ServerService exposeServerService() {
        return controller;
    }

    @Override
    public ClientProtocol protocol() {
        return ClientProtocol.RMI;
    }


    /**
     * Returns the current thread's client's (underlying TCP socket's) hostname.
     * It is a unique identifier for clients (on LAN, without NAT/Proxies)
     */
    private String getCurrentClientHostname() {
        try {
            return RemoteServer.getClientHost();
        } catch (ServerNotActiveException e) {
            throw new IllegalStateException("Server is not alive", e);
        }
    }

    /**
     * Authentication function, verifying that the remote caller can only perform authenticated actions
     * (so tasks that are game-specific)
     */
    private boolean verifyAuthorization(String username) {
        String remoteClientActualHostname = getCurrentClientHostname();
        String correctClientHostname = connectionsManager.get(username).getStash().getClientHostname();
        return remoteClientActualHostname.equals(correctClientHostname);
    }

    @Override
    public void serverStatusRequest(ClientService remoteService) throws RemoteException {
        try {
            controller.serverStatusRequest(remoteService);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void gameStartRequest(String username, GameMode mode, ClientProtocol protocol, ClientService remoteService) {
        try {
            controller.gameStartRequest(username, mode, protocol, remoteService);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void gameConnectionRequest(String username, ClientProtocol protocol, ClientService remoteService) {
        try {
            controller.gameConnectionRequest(username, protocol, remoteService);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void gameSelectionTurnResponse(String username, Set<Coordinate> selection) {
        try {
            controller.gameSelectionTurnResponse(username, selection);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void gameInsertionTurnResponse(String username, List<Tile> tiles, int column) {
        try {
            controller.gameInsertionTurnResponse(username, tiles, column);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void keepAlive(String username) {
        try {
            controller.keepAlive(username);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendTextMessage(String sendingUsername, MessageRecipient recipient, String text) throws RemoteException {
        try {
            controller.sendTextMessage(sendingUsername, recipient, text);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
