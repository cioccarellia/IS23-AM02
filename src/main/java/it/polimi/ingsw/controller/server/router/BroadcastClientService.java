package it.polimi.ingsw.controller.server.router;

import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.app.server.ClientConnectionsManager;
import it.polimi.ingsw.controller.server.async.AsyncExecutor;
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
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.chat.ChatTextMessage;
import it.polimi.ingsw.services.ClientService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Mock instance tuned with broadcasting capabilities, forwarding all the methods
 * called on it to its support
 */
public class BroadcastClientService implements ClientService {

    /**
     * Support {@link ClientConnectionsManager}, to forward the method sources to
     */
    private final ClientConnectionsManager support;

    /**
     * List of clients which won't be included in the forward operation
     */
    private List<String> ignoredSources = new ArrayList<>();

    /**
     * External executor for running the given commands in
     */
    @Nullable
    AsyncExecutor externalExecutor;

    boolean isUsingExternalExecutor;

    public BroadcastClientService(ClientConnectionsManager support) {
        this.support = support;
        isUsingExternalExecutor = false;
    }


    public BroadcastClientService(ClientConnectionsManager support, @NotNull AsyncExecutor executor) {
        this.support = support;
        this.externalExecutor = executor;
        isUsingExternalExecutor = true;
    }

    public BroadcastClientService(ClientConnectionsManager support, List<String> ignoredSources) {
        this.support = support;
        this.ignoredSources = ignoredSources;
        isUsingExternalExecutor = false;
    }

    public BroadcastClientService(ClientConnectionsManager support, List<String> ignoredSources, @NotNull AsyncExecutor executor) {
        this.support = support;
        this.ignoredSources = ignoredSources;
        this.externalExecutor = executor;
        isUsingExternalExecutor = true;
    }

    /**
     * Forwards a method call to a remote service ({@link ClientService})
     */
    private void forward(Consumer<ClientService> remoteMethodCall) {
        support.values().forEach(clientConnection -> {
            if (!ignoredSources.contains(clientConnection.getUsername())) {
                // allowed destination
                if (isUsingExternalExecutor) {
                    assert externalExecutor != null;
                    externalExecutor.async(() -> remoteMethodCall.accept(clientConnection));
                } else {
                    remoteMethodCall.accept(clientConnection);
                }
            }
        });
    }

    @Override
    public void onAcceptConnectionAndFinalizeUsername(String string) {
        forward(source -> {
            try {
                source.onAcceptConnectionAndFinalizeUsername(string);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void onServerStatusUpdateEvent(ServerStatus status, List<PlayerInfo> playerInfo) {
        forward(source -> {
            try {
                source.onServerStatusUpdateEvent(status, playerInfo);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void onGameCreationReply(TypedResult<GameCreationSuccess, GameCreationError> result) {
        forward(source -> {
            try {
                source.onGameCreationReply(result);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void onGameConnectionReply(TypedResult<GameConnectionSuccess, GameConnectionError> result) {
        forward(source -> {
            try {
                source.onGameConnectionReply(result);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void onGameStartedEvent(GameModel game) {
        forward(source -> {
            try {
                source.onGameStartedEvent(game);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void onModelUpdateEvent(GameModel game) {
        forward(source -> {
            try {
                source.onModelUpdateEvent(game);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void onGameSelectionTurnEvent(TypedResult<TileSelectionSuccess, TileSelectionFailures> turnResult) {
        forward(source -> {
            try {
                source.onGameSelectionTurnEvent(turnResult);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void onGameInsertionTurnEvent(TypedResult<TileInsertionSuccess, BookshelfInsertionFailure> turnResult) {
        forward(source -> {
            try {
                source.onGameInsertionTurnEvent(turnResult);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void onPlayerConnectionStatusUpdateEvent(ServerStatus status, List<PlayerInfo> usernames) {
        forward(source -> {
            try {
                source.onPlayerConnectionStatusUpdateEvent(status, usernames);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

    }

    @Override
    public void onGameEndedEvent() {
        forward(clientService -> {
            try {
                clientService.onGameEndedEvent();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void onChatModelUpdate(List<ChatTextMessage> messages) throws RemoteException {
        forward(clientService -> {
            try {
                clientService.onChatModelUpdate(messages);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
