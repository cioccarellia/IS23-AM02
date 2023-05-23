package it.polimi.ingsw.controller.server.router;

import it.polimi.ingsw.app.model.AggregatedPlayerInfo;
import it.polimi.ingsw.app.server.ClientConnectionsManager;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.services.ClientService;

import java.util.List;
import java.util.function.Consumer;

/**
 * Mock instance specifically aimed at broadcasting capabilities
 */
public class BroadcastClientService implements ClientService {

    private final ClientConnectionsManager support;

    public BroadcastClientService(ClientConnectionsManager support) {
        this.support = support;
    }

    private void forward(Consumer<ClientService> method) {
        support.values().forEach(method);
    }

    @Override
    public void onAcceptConnectionAndFinalizeUsername(String string, Game game) {
        forward(source -> source.onAcceptConnectionAndFinalizeUsername(string, game));
    }

    @Override
    public void onServerStatusUpdateEvent(ServerStatus status, List<AggregatedPlayerInfo> playerInfo) {
        forward(source -> source.onServerStatusUpdateEvent(status, playerInfo));
    }

    @Override
    public void onGameCreationReply(SingleResult<GameCreationError> result) {
        forward(source -> source.onGameCreationReply(result));
    }

    @Override
    public void onGameConnectionReply(SingleResult<GameConnectionError> result) {
        forward(source -> source.onGameConnectionReply(result));
    }

    @Override
    public void onGameStartedEvent() {
        forward(ClientService::onGameStartedEvent);
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
    public void onPlayerConnectionStatusUpdateEvent(List<AggregatedPlayerInfo> usernames) {
        forward(source -> source.onPlayerConnectionStatusUpdateEvent(usernames));

    }

    @Override
    public void onGameEndedEvent() {
        forward(ClientService::onGameEndedEvent);
    }
}
