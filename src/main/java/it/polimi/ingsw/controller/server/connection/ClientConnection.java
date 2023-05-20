package it.polimi.ingsw.controller.server.connection;

import it.polimi.ingsw.controller.server.connection.stash.ProtocolStash;
import it.polimi.ingsw.controller.server.connection.stash.StashFactory;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.services.ClientService;
import javafx.util.Pair;

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

    private final ProtocolStash stash;

    public ClientConnection(String username, ClientProtocol proto, ConnectionStatus status, ClientService remoteService) {
        this.username = username;
        this.proto = proto;
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


    /**
     * Gets the current client remote reference to forward remote method calls to.
     */
    private ClientService service() {
        return stash.getClientConnectionService();
    }


    @Override
    public void onAcceptConnectionAndFinalizeUsername(String string) {
        service().onAcceptConnectionAndFinalizeUsername(string);
    }

    @Override
    public void onServerStatusUpdateEvent(ServerStatus status, List<Pair<String, ConnectionStatus>> playerInfo) {
        service().onServerStatusUpdateEvent(status, playerInfo);
    }

    @Override
    public void onGameCreationReply(SingleResult<GameCreationError> result) {
        service().onGameCreationReply(result);
    }

    @Override
    public void onGameConnectionReply(SingleResult<GameConnectionError> result) {
        service().onGameConnectionReply(result);
    }

    @Override
    public void onGameStartedEvent() {
        service().onGameStartedEvent();
    }

    @Override
    public void modelUpdateEvent(Game game) {
        service().modelUpdateEvent(game);
    }

    @Override
    public void gameSelectionTurnEvent(SingleResult<TileSelectionFailures> turnResult) {
        service().gameSelectionTurnEvent(turnResult);
    }

    @Override
    public void gameInsertionTurnEvent(SingleResult<BookshelfInsertionFailure> turnResult) {
        service().gameInsertionTurnEvent(turnResult);
    }

    @Override
    public void playerConnectionStatusUpdateEvent(List<Pair<String, ConnectionStatus>> usernames) {
        service().playerConnectionStatusUpdateEvent(usernames);
    }

    @Override
    public void gameEndedEvent() {

    }
}