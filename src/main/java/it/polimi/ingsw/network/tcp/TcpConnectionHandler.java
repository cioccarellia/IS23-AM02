package it.polimi.ingsw.network.tcp;

import it.polimi.ingsw.app.model.PlayerInfo;
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
import it.polimi.ingsw.controller.server.wrappers.ServerTcpWrapper;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.chat.ChatTextMessage;
import it.polimi.ingsw.network.tcp.messages.Message;
import it.polimi.ingsw.network.tcp.messages.replies.GameConnectionRequestReply;
import it.polimi.ingsw.network.tcp.messages.replies.GameCreationRequestReply;
import it.polimi.ingsw.network.tcp.messages.replies.ServerStatusRequestReply;
import it.polimi.ingsw.network.tcp.messages.response.*;
import it.polimi.ingsw.network.tcp.messages.system.SocketSystem;
import it.polimi.ingsw.services.ClientService;
import it.polimi.ingsw.utils.json.Parsers;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

public class TcpConnectionHandler implements Runnable, ClientService, Closeable {

    private static final Logger logger = LoggerFactory.getLogger(TcpConnectionHandler.class);

    /**
     * Socket for given {@code TcpConnectionHandler}
     * */
    private final Socket socket;
    final private PrintWriter socketOut;
    final private BufferedReader socketIn;

    /**
     * Server controller wrapper for abstract {@link it.polimi.ingsw.services.ServerService}
     */
    private final ServerTcpWrapper wrapper;


    /**
     * Specifies whether the current {@code ClientGateway} is active and listening to the socket.
     */
    private boolean isActivelyListeningOnSocket = true;

    /**
     * Used to intercept and associate a username with a socket connection
     */
    private String username = null;

    public TcpConnectionHandler(@NotNull Socket socket, ServerTcpWrapper wrapper) {
        this.socket = socket;
        this.wrapper = wrapper;

        try {
            socketOut = new PrintWriter(socket.getOutputStream(), true);
            socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            logger.error("I/O error while acquiring socket", e);
            throw new RuntimeException(e);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActivelyListeningOnSocket() {
        return isActivelyListeningOnSocket;
    }

    public void setActivelyListeningOnSocket(boolean activelyListeningOnSocket) {
        isActivelyListeningOnSocket = activelyListeningOnSocket;
    }

    @Override
    public void run() {
        logger.info("Starting TcpConnectionHandler for socket={}", socket);
        try (
                Scanner in = new Scanner(socketIn)
        ) {
            logger.info("Actively monitoring socket={}", socket);
            while (isActivelyListeningOnSocket) {
                if (in.hasNextLine()) {
                    // receive serialized message
                    String serializedJsonRequest = in.nextLine();

                    logger.info("Received a new line from socket, line={}, ={}", serializedJsonRequest, socket);

                    // de-serialize message from JSON to Message
                    Message controllerRequest = Parsers.marshaledGson().fromJson(serializedJsonRequest, Message.class);

                    logger.info("Deserialized request into message={}", controllerRequest);


                    // sends the message to the controller (converting it into the appropriate function call)
                    // for the controller to processes it
                    wrapper.mapRequestToControllerMethodCall(controllerRequest, TcpConnectionHandler.this);
                }
            }
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                logger.error("Error closing socket: socket={}, message={}", socket, e.getMessage());
            }
        }
    }

    @Override
    public void close() throws IOException {
        socketIn.close();
        socketOut.close();
        socket.close();
    }

    @Override
    public void onAcceptConnectionAndFinalizeUsername(String string) {
        ConnectionAcceptanceEvent reply = new ConnectionAcceptanceEvent(string);

        SocketSystem.sendAsync(socketOut, reply, ConnectionAcceptanceEvent.class);
    }

    @Override
    public void onServerStatusUpdateEvent(ServerStatus status, List<PlayerInfo> playerInfo) {
        ServerStatusRequestReply reply = new ServerStatusRequestReply(status, playerInfo);

        SocketSystem.sendAsync(socketOut, reply, ServerStatusRequestReply.class);
    }

    @Override
    public void onGameCreationReply(TypedResult<GameCreationSuccess, GameCreationError> result) {
        GameCreationRequestReply reply = new GameCreationRequestReply(result);

        SocketSystem.sendAsync(socketOut, reply, GameCreationRequestReply.class);
    }

    @Override
    public void onGameConnectionReply(TypedResult<GameConnectionSuccess, GameConnectionError> result) {
        GameConnectionRequestReply reply = new GameConnectionRequestReply(result);

        SocketSystem.sendAsync(socketOut, reply, GameConnectionRequestReply.class);
    }

    @Override
    public void onGameStartedEvent(GameModel game) {
        GameStartedEvent event = new GameStartedEvent(game);

        SocketSystem.sendAsync(socketOut, event, GameStartedEvent.class);
    }

    @Override
    public void onModelUpdateEvent(GameModel game) {
        ModelUpdateEvent event = new ModelUpdateEvent(game);

        SocketSystem.sendAsync(socketOut, event, ModelUpdateEvent.class);
    }

    @Override
    public void onGameSelectionTurnEvent(TypedResult<TileSelectionSuccess, TileSelectionFailures> turnResult) {
        GameSelectionTurnResponse event = new GameSelectionTurnResponse(turnResult);

        SocketSystem.sendAsync(socketOut, event, GameSelectionTurnResponse.class);

    }

    @Override
    public void onGameInsertionTurnEvent(TypedResult<TileInsertionSuccess, BookshelfInsertionFailure> turnResult) {
        GameInsertionTurnResponse event = new GameInsertionTurnResponse(turnResult);

        SocketSystem.sendAsync(socketOut, event, GameInsertionTurnResponse.class);
    }

    @Override
    public void onPlayerConnectionStatusUpdateEvent(ServerStatus status, List<PlayerInfo> playerInfo) {
        ServerStatusRequestReply event = new ServerStatusRequestReply(status, playerInfo);

        SocketSystem.sendAsync(socketOut, event, ServerStatusRequestReply.class);
    }

    @Override
    public void onChatModelUpdate(List<ChatTextMessage> messages) throws RemoteException {
        ChatModelUpdateEvent event = new ChatModelUpdateEvent(messages);

        SocketSystem.sendAsync(socketOut, event, ChatModelUpdateEvent.class);
    }

    @Override
    public void onGameEndedEvent() {
        GameEndedEventResponse event = new GameEndedEventResponse();

        SocketSystem.sendAsync(socketOut, event, GameEndedEventResponse.class);
    }
}
