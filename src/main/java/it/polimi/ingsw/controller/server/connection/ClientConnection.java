package it.polimi.ingsw.controller.server.connection;

import it.polimi.ingsw.app.model.AggregatedPlayerInfo;
import it.polimi.ingsw.controller.server.connection.stash.ProtocolStash;
import it.polimi.ingsw.controller.server.connection.stash.StashFactory;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.controller.server.result.types.GameConnectionSuccess;
import it.polimi.ingsw.controller.server.result.types.GameCreationSuccess;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.services.ClientService;

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
    public void onAcceptConnectionAndFinalizeUsername(String string, Game game) {
        service().onAcceptConnectionAndFinalizeUsername(string, game);
    }

    @Override
    public void onServerStatusUpdateEvent(ServerStatus status, List<AggregatedPlayerInfo> playerInfo) {
        service().onServerStatusUpdateEvent(status, playerInfo);
    }

    @Override
    public void onGameCreationReply(TypedResult<GameCreationSuccess, GameCreationError> result) {
        service().onGameCreationReply(result);
    }

    @Override
    public void onGameConnectionReply(TypedResult<GameConnectionSuccess, GameConnectionError> result) {
        service().onGameConnectionReply(result);
    }

    @Override
    public void onGameStartedEvent() {
        service().onGameStartedEvent();
    }

    @Override
    public void onModelUpdateEvent(Game game) {
        service().onModelUpdateEvent(game);
    }

    @Override
    public void onGameSelectionTurnEvent(SingleResult<TileSelectionFailures> turnResult) {
        service().onGameSelectionTurnEvent(turnResult);
    }

    @Override
    public void onGameInsertionTurnEvent(SingleResult<BookshelfInsertionFailure> turnResult) {
        service().onGameInsertionTurnEvent(turnResult);
    }

    @Override
    public void onPlayerConnectionStatusUpdateEvent(List<AggregatedPlayerInfo> usernames) {
        service().onPlayerConnectionStatusUpdateEvent(usernames);
    }

    @Override
    public void onGameEndedEvent() {

    }
}