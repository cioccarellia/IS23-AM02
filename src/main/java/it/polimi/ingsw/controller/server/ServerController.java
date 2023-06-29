package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.app.server.ClientConnectionsManager;
import it.polimi.ingsw.app.server.storage.StorageManager;
import it.polimi.ingsw.controller.server.async.AsyncExecutor;
import it.polimi.ingsw.controller.server.connection.PeriodicConnectionAwareComponent;
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
import it.polimi.ingsw.controller.server.router.Router;
import it.polimi.ingsw.controller.server.validator.Validator;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.model.ChatModel;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.chat.MessageRecipient;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.game.GameStatus;
import it.polimi.ingsw.model.player.PlayerNumber;
import it.polimi.ingsw.model.player.PlayerSession;
import it.polimi.ingsw.network.tcp.TcpConnectionHandler;
import it.polimi.ingsw.services.ClientService;
import it.polimi.ingsw.services.ServerService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static it.polimi.ingsw.controller.server.connection.ConnectionStatus.DISCONNECTED;
import static it.polimi.ingsw.controller.server.connection.ConnectionStatus.OPEN;
import static it.polimi.ingsw.controller.server.model.ServerStatus.*;
import static it.polimi.ingsw.model.game.GameStatus.ENDED;
import static it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase.INSERTING;
import static it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase.SELECTING;

/**
 * Server-side controller
 */
public class ServerController implements ServerService, PeriodicConnectionAwareComponent {

    private static final Logger logger = LoggerFactory.getLogger(ServerController.class);

    /**
     * Keeps a map associating a username (unique identifier for a player)
     * to the specific details of its connection to the server.
     */
    private final ClientConnectionsManager connectionsManager;

    /**
     * Executor service for saving data to the persistent storage
     */
    private final ExecutorService persistenceExecutor = Executors.newSingleThreadExecutor();

    /**
     * Executor service for running remote calls / network response tasks
     */
    private final AsyncExecutor asyncExecutor = AsyncExecutor.newCachedThreadPool();

    /**
     * Handles record creation/deletion/reading
     */
    private final StorageManager storageManager;

    /**
     * Routes asynchronous client responses to their destination
     */
    private final Router router;

    /**
     * Game model, containing full game information and data.
     */
    private GameModel gameModel;

    /**
     * Chat model, containing all chat messages.
     */
    private ChatModel chatModel = new ChatModel();

    /**
     * Max number of players for current game mode, when selected
     */
    private int maxPlayerAmount;

    /**
     * Current server general status, independent from game logic
     */
    private ServerStatus serverStatus = NO_GAME_STARTED;



    public ServerController(ClientConnectionsManager connectionsManager, StorageManager storageManager) {
        this.connectionsManager = connectionsManager;
        this.storageManager = storageManager;

        router = new Router(connectionsManager);
    }


    @Override
    public ClientConnectionsManager getConnectionsManager() {
        return connectionsManager;
    }


    private List<PlayerInfo> packagePlayerInfo() {
        return connectionsManager.values()
                .stream()
                .map(user -> new PlayerInfo(user.getUsername(), user.getStatus(), user.isHost()))
                .toList();
    }

    private void saveModelToPersistentStorage() {
        storageManager.save(connectionsManager.getUsernames(), gameModel);
    }

    private void deletePersistentModelForGame() {
        storageManager.delete(connectionsManager.getUsernames());
    }


    /**
     * Connects a username and a remote service {@link ClientService} to allow asynchronous
     * client-specific communication between client and server.
     *
     * @apiNote to be called only when the user connection/creation request has been accepted.
     */
    private synchronized void synchronizeConnectionLayer(String username, @NotNull ClientService service) throws RemoteException {
        // connection stash service for callbacks
        connectionsManager.get(username).getStash().setClientConnectionService(service);
        asyncExecutor.async(() -> {
            service.onAcceptConnectionAndFinalizeUsername(username);
        });

        // en-route parameters
        switch (service) {
            case TcpConnectionHandler handler -> {
                handler.setUsername(username);
            }
            case Proxy controller -> {
                // no-op, proxy is stateless
            }
            default -> throw new IllegalStateException("Unexpected client service value: " + service);
        }
    }



    /**
     * Returns the server information for each player regarding its connection status
     */
    @Override
    public void serverStatusRequest(ClientService remoteService) throws RemoteException {
        asyncExecutor.async(() -> {
            remoteService.onServerStatusUpdateEvent(serverStatus, packagePlayerInfo());
        });
    }

    @Override
    public synchronized void gameStartRequest(String username, GameMode mode, ClientProtocol protocol, ClientService remoteService) throws RemoteException {
        logger.info("gameStartedRequest, mode={}, username={}, protocol={}", mode, username, protocol);

        if (serverStatus == GAME_RUNNING) {
            logger.warn("returning failure from gameStartRequest(): {}", GameCreationError.GAME_ALREADY_RUNNING);

            asyncExecutor.async(() -> {
                remoteService.onGameCreationReply(new TypedResult.Failure<>(GameCreationError.GAME_ALREADY_RUNNING));
            });
            return;
        }

        if (serverStatus == GAME_INITIALIZING) {
            logger.warn("returning failure from gameStartRequest(): {}", GameCreationError.GAME_ALREADY_INITIALIZING);

            asyncExecutor.async(() -> {
                remoteService.onGameCreationReply(new TypedResult.Failure<>(GameCreationError.GAME_ALREADY_INITIALIZING));
            });
            return;
        }


        if (!Validator.isValidUsername(username)) {
            logger.warn("returning failure from gameStartRequest(): {}", GameCreationError.INVALID_USERNAME);

            asyncExecutor.async(() -> {
                remoteService.onGameCreationReply(new TypedResult.Failure<>(GameCreationError.INVALID_USERNAME));
            });
            return;
        }


        // accepting request and setting up the game model
        gameModel = new GameModel(mode);
        gameModel.addPlayer(username);
        maxPlayerAmount = mode.maxPlayerAmount();

        // update the server status
        serverStatus = GAME_INITIALIZING;

        // synchronize client, sending permanent username to client and saving remote service to stash for callbacks
        connectionsManager.add(username, protocol, true, OPEN, remoteService);
        synchronizeConnectionLayer(username, remoteService);


        // notify the
        logger.info("returning success from gameStartRequest()");

        // return success to the caller
        asyncExecutor.async(() -> {
            router.route(username).onGameCreationReply(new TypedResult.Success<>(new GameCreationSuccess(username, packagePlayerInfo())));
        });

        // route the new status to everybody
        asyncExecutor.async(() -> {
            router.broadcastExcluding(username).onServerStatusUpdateEvent(serverStatus, packagePlayerInfo());
        });
    }



    // Creates a connection between client and server
    @Override
    public synchronized void gameConnectionRequest(String username, ClientProtocol protocol, ClientService remoteService) throws RemoteException {
        logger.info("gameConnectionRequest(username={}, protocol={}, remoteService={})", username, protocol, remoteService);

        assert connectionsManager.size() <= maxPlayerAmount;

        if (!Validator.isValidUsername(username)) {
            logger.warn("returning failure from gameConnectionRequest(): {}", GameConnectionError.INVALID_USERNAME);

            asyncExecutor.async(() -> {
                remoteService.onGameConnectionReply(new TypedResult.Failure<>(GameConnectionError.INVALID_USERNAME));
            });
            return;
        }

        if (serverStatus == GAME_RUNNING) {
            logger.warn("returning failure from gameConnectionRequest(): {}", GameConnectionError.GAME_ALREADY_STARTED);

            // anonymous routing
            asyncExecutor.async(() -> {
                remoteService.onGameConnectionReply(new TypedResult.Failure<>(GameConnectionError.GAME_ALREADY_STARTED));
            });
            return;
        }

        if (serverStatus == NO_GAME_STARTED) {
            logger.warn("returning failure from gameConnectionRequest(): {}", GameConnectionError.NO_GAME_TO_JOIN);

            // anonymous routing
            asyncExecutor.async(() -> {
                remoteService.onGameConnectionReply(new TypedResult.Failure<>(GameConnectionError.NO_GAME_TO_JOIN));
            });
            return;
        }

        if (connectionsManager.size() == maxPlayerAmount) {
            logger.warn("returning failure from gameConnectionRequest(): {}", GameConnectionError.MAX_PLAYER_AMOUNT_EACHED);

            // anonymous routing
            asyncExecutor.async(() -> {
                remoteService.onGameConnectionReply(new TypedResult.Failure<>(GameConnectionError.MAX_PLAYER_AMOUNT_EACHED));
            });
            return;
        }

        if (connectionsManager.containsUsername(username)) {
            logger.warn("returning failure from gameConnectionRequest(): {}", GameConnectionError.USERNAME_ALREADY_IN_USE);

            // anonymous routing
            asyncExecutor.async(() -> {
                remoteService.onGameConnectionReply(new TypedResult.Failure<>(GameConnectionError.USERNAME_ALREADY_IN_USE));
            });
            return;
        }

        if (gameModel.getGameStatus() == ENDED) {
            logger.warn("returning failure from gameConnectionRequest(): {}", GameConnectionError.GAME_ALREADY_ENDED);

            // anonymous routing
            asyncExecutor.async(() -> {
                remoteService.onGameConnectionReply(new TypedResult.Failure<>(GameConnectionError.GAME_ALREADY_ENDED));
            });
            return;
        }


        // add player to game
        gameModel.addPlayer(username);

        // whether the conditions for a new game are met at the time of executing this call
        boolean shouldStartGame = maxPlayerAmount == gameModel.getPlayerCount();

        if (shouldStartGame) {
            // conditions for starting game are met
            serverStatus = GAME_RUNNING;

            // tell model to start its active game phase
            gameModel.onGameStarted();
        }


        // synchronize client, sending permanent username to client and saving remote service to stash for callbacks
        connectionsManager.add(username, protocol, false, OPEN, remoteService);
        synchronizeConnectionLayer(username, remoteService);


        // route a success response to the caller, since it connected successfully
        asyncExecutor.async(() -> {
            router.route(username).onGameConnectionReply(new TypedResult.Success<>(new GameConnectionSuccess(username, packagePlayerInfo())));
        });

        // route the new status to everybody, giving the details for the new connected player
        asyncExecutor.async(() -> {
            router.broadcastExcluding(username).onServerStatusUpdateEvent(serverStatus, packagePlayerInfo());
        });


        if (shouldStartGame) {
            // broadcast the game model with all users to everybody, only if the game has started.
            // This will display the main game UI


            // bonus: we discard and load a file-saved model if the usernames are recognized to be part of a past game
            GameModel matchingGame = storageManager.load(connectionsManager.getUsernames());

            if (matchingGame != null) {
                // we override the game
                gameModel = matchingGame;
            } else {
                // no game found, proceed with empty
            }

            asyncExecutor.async(() -> {
                router.broadcast().onGameStartedEvent(gameModel);
            });
        }


        // log an interaction for the requiring user
        asyncExecutor.async(() -> {
            connectionsManager.registerInteraction(username);
        });
    }


    // Game logic
    public boolean isUsernameActivePlayer(@NotNull String username) {
        return username.equals(gameModel.getCurrentPlayerSession().getUsername());
    }

    public boolean shouldStandbyGame() {
        return connectionsManager.values().stream().filter(player -> player.getStatus() == DISCONNECTED).count() >= maxPlayerAmount - 1;
    }


    @Override
    public synchronized void gameSelectionTurnResponse(String username, Set<Coordinate> selection) throws RemoteException {
        logger.info("gameSelectionTurnResponse(username={}, selection={})", username, selection);
        connectionsManager.registerInteraction(username);

        if (!isUsernameActivePlayer(username)) {
            logger.warn("returning failure from gameSelectionTurnResponse(): {}", TileSelectionFailures.UNAUTHORIZED_PLAYER);

            asyncExecutor.async(() -> {
                router.route(username).onGameSelectionTurnEvent(new TypedResult.Failure<>(TileSelectionFailures.UNAUTHORIZED_PLAYER));
            });
            return;
        }

        if (gameModel.getCurrentPlayerSession().getPlayerCurrentGamePhase() != SELECTING) {
            logger.warn("returning failure from gameSelectionTurnResponse(): {}", TileSelectionFailures.WRONG_GAME_PHASE);

            asyncExecutor.async(() -> {
                router.route(username).onGameSelectionTurnEvent(new TypedResult.Failure<>(TileSelectionFailures.WRONG_GAME_PHASE));
            });
            return;
        }

        if (!gameModel.isSelectionValid(selection)) {
            logger.warn("returning failure from gameSelectionTurnResponse(): {}", TileSelectionFailures.UNAUTHORIZED_SELECTION);

            asyncExecutor.async(() -> {
                router.route(username).onGameSelectionTurnEvent(new TypedResult.Failure<>(TileSelectionFailures.UNAUTHORIZED_SELECTION));
            });
            return;
        }

        gameModel.onPlayerSelectionPhase(selection);


        // send insertion success result to caller
        asyncExecutor.async(() -> {
            router.route(username).onGameSelectionTurnEvent(new TypedResult.Success<>(new TileSelectionSuccess(gameModel)));
        });

        // and send model update to everybody else
        asyncExecutor.async(() -> {
            router.broadcastExcluding(username).onModelUpdateEvent(gameModel);
        });

        // saves updated game config to file
        persistenceExecutor.submit(this::saveModelToPersistentStorage);
    }


    @Override
    public synchronized void gameInsertionTurnResponse(String username, List<Tile> tiles, int column) throws RemoteException {
        logger.info("gameInsertionTurnResponse(username={}, tiles={}, column={})", username, tiles, column);
        connectionsManager.registerInteraction(username);

        if (!isUsernameActivePlayer(username)) {
            logger.warn("returning failure from gameInsertionTurnResponse(): {}", BookshelfInsertionFailure.WRONG_PLAYER);

            asyncExecutor.async(() -> {
                router.route(username).onGameInsertionTurnEvent(new TypedResult.Failure<>(BookshelfInsertionFailure.WRONG_PLAYER));
            });
            return;
        }

        if (gameModel.getCurrentPlayerSession().getPlayerCurrentGamePhase() != INSERTING) {
            logger.warn("returning failure from gameInsertionTurnResponse(): {}", BookshelfInsertionFailure.WRONG_GAME_PHASE);

            asyncExecutor.async(() -> {
                router.route(username).onGameInsertionTurnEvent(new TypedResult.Failure<>(BookshelfInsertionFailure.WRONG_GAME_PHASE));
            });
            return;
        }

        if (!gameModel.getCurrentPlayerSession().getPlayerTileSelection().selectionEquals(tiles)) {
            logger.warn("returning failure from gameInsertionTurnResponse(): {}", BookshelfInsertionFailure.WRONG_SELECTION);

            asyncExecutor.async(() -> {
                router.route(username).onGameInsertionTurnEvent(new TypedResult.Failure<>(BookshelfInsertionFailure.WRONG_SELECTION));
            });
            return;
        }

        if (column < 0 || column >= BookshelfConfiguration.getInstance().cols()) {
            logger.warn("returning failure from gameInsertionTurnResponse(): {}", BookshelfInsertionFailure.ILLEGAL_COLUMN);

            asyncExecutor.async(() -> {
                router.route(username).onGameInsertionTurnEvent(new TypedResult.Failure<>(BookshelfInsertionFailure.ILLEGAL_COLUMN));
            });
            return;
        }

        if (tiles.size() > LogicConfiguration.getInstance().maxSelectionSize()) {
            logger.warn("returning failure from gameInsertionTurnResponse(): {}", BookshelfInsertionFailure.TOO_MANY_TILES);

            asyncExecutor.async(() -> {
                router.route(username).onGameInsertionTurnEvent(new TypedResult.Failure<>(BookshelfInsertionFailure.TOO_MANY_TILES));
            });
            return;
        }

        if (!gameModel.getCurrentPlayerSession().getBookshelf().canFit(column, tiles.size())) {
            logger.warn("returning failure from gameInsertionTurnResponse(): {}", BookshelfInsertionFailure.NO_FIT);

            asyncExecutor.async(() -> {
                router.route(username).onGameInsertionTurnEvent(new TypedResult.Failure<>(BookshelfInsertionFailure.NO_FIT));
            });
            return;
        }

        gameModel.onPlayerInsertionPhase(column, tiles);
        gameModel.onPlayerTurnEnding();

        PlayerSession nextPlayer = processNextPlayer();

        gameModel.onNextTurn(nextPlayer.getUsername());


        // send insertion success result to caller
        asyncExecutor.async(() -> {
            router.route(username).onGameInsertionTurnEvent(new TypedResult.Success<>(new TileInsertionSuccess(gameModel)));
        });

        // and send model update to everybody else
        asyncExecutor.async(() -> {
            router.broadcastExcluding(username).onModelUpdateEvent(gameModel);
        });

        // saves updates game config to file
        persistenceExecutor.submit(this::saveModelToPersistentStorage);
    }

    @Override
    public void quitRequest(String username) throws RemoteException {
        logger.info("quitRequest(username={})", username);
        connectionsManager.registerInteraction(username);

        serverStatus = GAME_OVER;
        gameModel.onGameEnded();

        asyncExecutor.async(() -> {
            router.broadcast().onGameEndedEvent();
        });

        persistenceExecutor.submit(this::deletePersistentModelForGame);
    }


    /**
     * Determines which player should move next
     */
    private synchronized PlayerSession processNextPlayer() {
        PlayerNumber currentPlayerNumber = gameModel.getCurrentPlayerSession().getPlayerNumber();
        PlayerNumber next = next_rec(currentPlayerNumber);

        return gameModel.getPlayerSession(next);
    }

    private synchronized PlayerNumber next_rec(@NotNull PlayerNumber current) {
        PlayerNumber naturalNextNumber = current.next(gameModel.getGameMode());
        PlayerSession next = gameModel.getPlayerSession(naturalNextNumber);

        if (connectionsManager.isClientDisconnected(next.getUsername())) {
            return next_rec(next.getPlayerNumber());
        } else {
            return next.getPlayerNumber();
        }
    }


    @Override
    public synchronized void sendTextMessage(String sendingUsername, MessageRecipient recipient, String text) throws RemoteException {
        switch (recipient) {
            case MessageRecipient.Broadcast broadcast -> {
                // add to model
                chatModel.addMessage(sendingUsername, recipient, text.trim());

                // dispatch
                for (String username : connectionsManager.getUsernames()) {
                    asyncExecutor.async(() -> {
                        router.route(username).onChatModelUpdate(
                                chatModel.getMessagesFor(username)
                        );
                    });
                }
            }
            case MessageRecipient.Direct directRecipient -> {
                if (sendingUsername.equals(directRecipient.username())) {
                    // can't DM yourself
                    logger.warn("dropping message(sendingUsername={}, recipient={}, text={}), sent message to self", sendingUsername, recipient, text);
                    return;
                }

                if (!connectionsManager.containsUsername(directRecipient.username())) {
                    // the given username doesn't exist
                    logger.warn("dropping message(sendingUsername={}, recipient={}, text={}), recipient non-existent", sendingUsername, recipient, text);
                    return;
                }

                // add to model
                chatModel.addMessage(sendingUsername, recipient, text.trim());

                // dispatch

                asyncExecutor.async(() -> {
                    router.route(sendingUsername).onChatModelUpdate(
                            chatModel.getMessagesFor(sendingUsername)
                    );
                });

                String recipientUsername = directRecipient.username();

                asyncExecutor.async(() -> {
                    router.route(recipientUsername).onChatModelUpdate(
                            chatModel.getMessagesFor(recipientUsername)
                    );
                });
            }
        }
    }


    @Override
    public synchronized void onConnectionChange() {
        if (connectionsManager.isAnyClientClosed()) {
            serverStatus = GAME_OVER;
            gameModel.onGameEnded();

            asyncExecutor.async(() -> {
                router.broadcastExcluding(
                        connectionsManager.getDisconnectedOrClosedClientUsernames()
                ).onServerStatusUpdateEvent(serverStatus, packagePlayerInfo());
            });

            persistenceExecutor.submit(this::deletePersistentModelForGame);
            return;
        }

        // no clients are closed, we continue
        if (connectionsManager.isAnyClientDisconnected()) {
            // Server Status Update
            // there are clients disconnected

            asyncExecutor.async(() -> {
                router.broadcastExcluding(
                        connectionsManager.getDisconnectedClientUsernames()
                ).onServerStatusUpdateEvent(serverStatus, packagePlayerInfo());
            });


            if (gameModel != null && gameModel.getGameStatus() != GameStatus.INITIALIZATION) {
                // Model update: there is an active player, and the game is started/last round/standby
                // we have to check if the current player is disconnected.
                // if it is, and we are not in standby, we need to move forward
                String currentPlayerUsername = gameModel.getCurrentPlayerSession().getUsername();

                if (connectionsManager.getDisconnectedClientUsernames().contains(currentPlayerUsername)) {
                    // force-forward turn, rollback to pre-selection model
                    PlayerSession nextPlayer = processNextPlayer();
                    gameModel.onForcedNextTurn(nextPlayer.getUsername());
                }


                boolean shouldStandby = shouldStandbyGame();

                if (shouldStandby) {
                    // player(s) disconnected and game standby
                    boolean hasModelStatusChanged = gameModel.onStandby();

                    if (hasModelStatusChanged) {

                        asyncExecutor.async(() -> {
                            router.broadcastExcluding(
                                    connectionsManager.getDisconnectedClientUsernames()
                            ).onModelUpdateEvent(gameModel);
                        });
                    }
                } else {
                    // player(s) disconnected but game running
                    boolean hasModelStatusChanged = gameModel.onResume();

                    if (hasModelStatusChanged) {
                        asyncExecutor.async(() -> {
                            router.broadcastExcluding(
                                    connectionsManager.getDisconnectedClientUsernames()
                            ).onModelUpdateEvent(gameModel);
                        });
                    }
                }
            }
        } else {
            // players all connected
            boolean hasModelStatusChanged = gameModel.onResume();

            if (hasModelStatusChanged) {
                asyncExecutor.async(() -> {
                    router.broadcastExcluding(
                            connectionsManager.getDisconnectedClientUsernames()
                    ).onModelUpdateEvent(gameModel);
                });
            }

            // all clients are back online, resend model and server status. Maybe make appropriate request
            asyncExecutor.async(() -> {
                router.broadcast().onServerStatusUpdateEvent(serverStatus, packagePlayerInfo());
            });
        }
    }



    @Override
    public void keepAlive(String username) {
        logger.info("keepAlive(username={})", username);

        if (connectionsManager.containsUsername(username)) {
            connectionsManager.registerInteraction(username);
        } else {
            logger.warn("Wrong keep alive username: {}", username);
        }
    }
}