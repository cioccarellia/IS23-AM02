package it.polimi.ingsw.controller.server.router;

import it.polimi.ingsw.app.server.ClientConnectionsManager;
import it.polimi.ingsw.controller.server.connection.ConnectionStatus;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.services.ClientService;
import javafx.util.Pair;

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
    public void injectUsername(String string) {
        forward(source -> source.injectUsername(string));
    }

    @Override
    public void serverStatusUpdateEvent(ServerStatus status, List<Pair<String, ConnectionStatus>> playerInfo) {
        forward(source -> source.serverStatusUpdateEvent(status, playerInfo));
    }

    @Override
    public void gameCreationReply(SingleResult<GameCreationError> result) {
        forward(source -> source.gameCreationReply(result));
    }

    @Override
    public void gameConnectionReply(SingleResult<GameConnectionError> result) {
        forward(source -> source.gameConnectionReply(result));
    }

    @Override
    public void gameStartedEvent() {
        forward(ClientService::gameStartedEvent);
    }

    @Override
    public void modelUpdateEvent(Game game) {
        forward(source -> source.modelUpdateEvent(game));
    }

    @Override
    public void gameSelectionTurnEvent(SingleResult<TileSelectionFailures> turnResult) {
        forward(source -> source.gameSelectionTurnEvent(turnResult));
    }

    @Override
    public void gameInsertionTurnEvent(SingleResult<BookshelfInsertionFailure> turnResult) {
        forward(source -> source.gameInsertionTurnEvent(turnResult));
    }

    @Override
    public void playerConnectionStatusUpdateEvent(List<Pair<String, ConnectionStatus>> usernames) {
        forward(source -> source.playerConnectionStatusUpdateEvent(usernames));

    }

    @Override
    public void gameEndedEvent() {
        forward(ClientService::gameEndedEvent);
    }
}
