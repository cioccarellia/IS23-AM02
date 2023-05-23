package it.polimi.ingsw.controller.server.router;

import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.app.server.ClientConnectionsManager;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.controller.server.result.types.GameConnectionSuccess;
import it.polimi.ingsw.controller.server.result.types.GameCreationSuccess;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.services.ClientService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Mock instance specifically aimed at broadcasting capabilities
 */
public class BroadcastClientService implements ClientService {

    /**
     * Support to forward the method sources to
     */
    private final ClientConnectionsManager support;

    /**
     * List of clients which won't be forwarded
     */
    private List<String> ignoredSources = new ArrayList<>();

    public BroadcastClientService(ClientConnectionsManager support) {
        this.support = support;
    }

    public BroadcastClientService(ClientConnectionsManager support, List<String> ignoredSources) {
        this.support = support;
        this.ignoredSources = ignoredSources;
    }

    /**
     * Forwards a method call to a remote service ({@link ClientService})
     */
    private void forward(Consumer<ClientService> remoteMethodCall) {
        support.values().forEach(clientConnection -> {
            if (!ignoredSources.contains(clientConnection.getUsername())) {
                remoteMethodCall.accept(clientConnection);
            }
        });
    }

    @Override
    public void onAcceptConnectionAndFinalizeUsername(String string, Game game) {
        forward(source -> source.onAcceptConnectionAndFinalizeUsername(string, game));
    }

    @Override
    public void onServerStatusUpdateEvent(ServerStatus status, List<PlayerInfo> playerInfo) {
        forward(source -> source.onServerStatusUpdateEvent(status, playerInfo));
    }

    @Override
    public void onGameCreationReply(TypedResult<GameCreationSuccess, GameCreationError> result) {
        forward(source -> source.onGameCreationReply(result));
    }

    @Override
    public void onGameConnectionReply(TypedResult<GameConnectionSuccess, GameConnectionError> result) {
        forward(source -> source.onGameConnectionReply(result));
    }

    @Override
    public void onGameStartedEvent(Game game) {
        forward(source -> source.onGameStartedEvent(game));
    }

    @Override
    public void onModelUpdateEvent(Game game) {
        forward(source -> source.onModelUpdateEvent(game));
    }

    @Override
    public void onGameSelectionTurnEvent(SingleResult<TileSelectionFailures> turnResult) {
        forward(source -> source.onGameSelectionTurnEvent(turnResult));
    }

    @Override
    public void onGameInsertionTurnEvent(SingleResult<BookshelfInsertionFailure> turnResult) {
        forward(source -> source.onGameInsertionTurnEvent(turnResult));
    }

    @Override
    public void onPlayerConnectionStatusUpdateEvent(List<PlayerInfo> usernames) {
        forward(source -> source.onPlayerConnectionStatusUpdateEvent(usernames));

    }

    @Override
    public void onGameEndedEvent() {
        forward(ClientService::onGameEndedEvent);
    }
}
