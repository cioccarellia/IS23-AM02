package it.polimi.ingsw.controller.server.connection;

import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.controller.server.connection.stash.ProtocolStash;
import it.polimi.ingsw.controller.server.connection.stash.StashFactory;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.controller.server.result.types.GameConnectionSuccess;
import it.polimi.ingsw.controller.server.result.types.GameCreationSuccess;
import it.polimi.ingsw.controller.server.result.types.TileInsertionSuccess;
import it.polimi.ingsw.controller.server.result.types.TileSelectionSuccess;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.chat.ChatTextMessage;
import it.polimi.ingsw.services.ClientService;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Class representing a virtual connection with a client app.
 * It contains protocol-specific data, as well as general functions and
 * utilities to handle communication and internal representation
 * for that specific client.
 */
public class ClientConnection implements ClientService {

    /**
     * The username associated with the current connection.
     */
    private final String username;

    /**
     * Protocol used throughout the connection to communicate with the client.
     */
    private final ClientProtocol proto;

    /**
     * Status of the connection with the client, has three possible values.
     *
     * @see ConnectionStatus
     */
    private ConnectionStatus status;

    /**
     * Keeps track of the last timestamp where a message/call from the client
     * has been sent the server-side interface.
     */
    private Date lastSeen;

    private final boolean isHost;

    private final ProtocolStash stash;

    public ClientConnection(String username, ClientProtocol proto, boolean isHost, ConnectionStatus status, ClientService remoteService) {
        this.username = username;
        this.proto = proto;
        this.isHost = isHost;
        this.status = status;

        // last seen is now
        this.lastSeen = Calendar.getInstance().getTime();

        // setting up the stash
        stash = StashFactory.create(proto);
        stash.setClientConnectionService(remoteService);
    }


    public String getUsername() {
        return username;
    }

    public ConnectionStatus getStatus() {
        return status;
    }

    public void setStatus(ConnectionStatus status) {
        this.status = status;
    }


    public ClientProtocol getProto() {
        return proto;
    }

    public ProtocolStash getStash() {
        return stash;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }

    public boolean isHost() {
        return isHost;
    }

    /**
     * Gets the current client remote reference to forward remote method calls to.
     */
    private ClientService service() {
        return stash.getClientConnectionService();
    }


    @Override
    public void onAcceptConnectionAndFinalizeUsername(String string) {
        try {
            service().onAcceptConnectionAndFinalizeUsername(string);
        } catch (RemoteException e) {
            System.exit(-1);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onServerStatusUpdateEvent(ServerStatus status, List<PlayerInfo> playerInfo) {
        try {
            service().onServerStatusUpdateEvent(status, playerInfo);
        } catch (RemoteException e) {
            System.exit(-1);
            // connection refused from host, remove, flag as dead and propagate
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onGameCreationReply(TypedResult<GameCreationSuccess, GameCreationError> result) {
        try {
            service().onGameCreationReply(result);
        } catch (RemoteException e) {
            System.exit(-1);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onGameConnectionReply(TypedResult<GameConnectionSuccess, GameConnectionError> result) {
        try {
            service().onGameConnectionReply(result);
        } catch (RemoteException e) {
            System.exit(-1);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onGameStartedEvent(GameModel game) {
        try {
            service().onGameStartedEvent(game);
        } catch (RemoteException e) {
            System.exit(-1);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onModelUpdateEvent(GameModel game) {
        try {
            service().onModelUpdateEvent(game);
        } catch (RemoteException e) {
            System.exit(-1);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onGameSelectionTurnEvent(TypedResult<TileSelectionSuccess, TileSelectionFailures> turnResult) {
        try {
            service().onGameSelectionTurnEvent(turnResult);
        } catch (RemoteException e) {
            System.exit(-1);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onGameInsertionTurnEvent(TypedResult<TileInsertionSuccess, BookshelfInsertionFailure> turnResult) {
        try {
            service().onGameInsertionTurnEvent(turnResult);
        } catch (RemoteException e) {
            System.exit(-1);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onPlayerConnectionStatusUpdateEvent(ServerStatus status, List<PlayerInfo> usernames) {
        try {
            service().onPlayerConnectionStatusUpdateEvent(status, usernames);
        } catch (RemoteException e) {
            System.exit(-1);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onGameEndedEvent() {
        try {
            service().onGameEndedEvent();
        } catch (RemoteException e) {
            System.exit(-1);
            throw new RuntimeException(e);
        }
    }


    @Override
    public void onChatModelUpdate(List<ChatTextMessage> messages) throws RemoteException {
        try {
            service().onChatModelUpdate(messages);
        } catch (RemoteException e) {
            System.exit(-1);
            throw new RuntimeException(e);
        }
    }


}