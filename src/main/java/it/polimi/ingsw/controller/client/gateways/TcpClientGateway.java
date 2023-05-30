package it.polimi.ingsw.controller.client.gateways;

import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.network.tcp.messages.Message;
import it.polimi.ingsw.network.tcp.messages.replies.GameConnectionRequestReply;
import it.polimi.ingsw.network.tcp.messages.replies.GameCreationRequestReply;
import it.polimi.ingsw.network.tcp.messages.replies.ServerStatusRequestReply;
import it.polimi.ingsw.network.tcp.messages.request.*;
import it.polimi.ingsw.network.tcp.messages.response.*;
import it.polimi.ingsw.network.tcp.messages.system.SocketSystem;
import it.polimi.ingsw.services.ClientService;
import it.polimi.ingsw.utils.json.Parsers;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Client gateway for server communication (through {@link it.polimi.ingsw.services.ServerService}).
 */
public class TcpClientGateway extends ClientGateway implements Runnable, Closeable {

    private static final Logger logger = LoggerFactory.getLogger(TcpClientGateway.class);

    final private Socket socket;
    final private PrintWriter socketOut;
    final private BufferedReader socketIn;

    /**
     * Specifies whether the current {@code ClientGateway} is active and listening to the socket.
     */
    private boolean isActivelyListeningOnSocket = true;


    public TcpClientGateway(String serverHost, int serverTcpPort) {
        try {
            socket = new Socket(serverHost, serverTcpPort);
        } catch (UnknownHostException e) {
            logger.error("Unknown host serverHost={}", serverHost);
            throw new IllegalArgumentException("Wrong serverHost/port combination", e);
        } catch (IOException e) {
            logger.error("Can not acquire I/O to start client gateway, probably TcpServer not yet started [tried connecting to serverHost={}, serverPort={}]", serverHost, serverTcpPort);
            System.out.printf("No server started at the given address %s:%d", serverHost, serverTcpPort);
            System.exit(-1);
            throw new IllegalStateException();
        }

        logger.info("Started socket for TcpClientGateway, socket={}", socket);

        try {
            socketOut = new PrintWriter(socket.getOutputStream(), true);
            socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            logger.error("I/O error while acquiring socket", e);
            throw new RuntimeException(e);
        }
    }


    public boolean isActivelyListeningOnSocket() {
        return isActivelyListeningOnSocket;
    }

    public void setActivelyListeningOnSocket(boolean activelyListeningOnSocket) {
        isActivelyListeningOnSocket = activelyListeningOnSocket;
    }


    @Override
    public void run() {
        logger.info("Starting TcpClientGateway for socket={}", socket);
        Scanner in = new Scanner(socketIn);

        try {
            logger.info("Actively monitoring socket={}", socket);

            while (isActivelyListeningOnSocket) {
                if (in.hasNextLine()) {
                    // receive serialized message
                    String serializedJsonReply = in.nextLine();

                    logger.info("Received a new line from client socket, line={}, socket={}", serializedJsonReply, socket);

                    // de-serialize message from JSON to Message
                    Message controllerReply = Parsers.marshaledGson().fromJson(serializedJsonReply, Message.class);

                    logger.info("Deserialized event into reply={}", controllerReply);

                    // sends the message to the controller (converting it into the appropriate function call)
                    // for the controller to processes it
                    mapEventToControllerMethodCall(controllerReply);
                }
            }
        } finally {
            try {
                isActivelyListeningOnSocket = false;
                close();
            } catch (IOException e) {
                logger.error("Error closing socket: socket={}, message={}", socket, e.getMessage());
            }
        }
    }


    public void mapEventToControllerMethodCall(@NotNull final Message incomingMessage) {
        switch (incomingMessage) {
            case ConnectionAcceptanceEvent s -> controller.onAcceptConnectionAndFinalizeUsername(s.getUsername(), s.getModel());
            case GameConnectionRequestReply s -> controller.onGameConnectionReply(s.seal());
            case GameCreationRequestReply s -> controller.onGameCreationReply(s.seal());
            case ServerStatusRequestReply s -> controller.onServerStatusUpdateEvent(s.getStatus(), s.getPlayerInfo());
            case GameStartedEvent e -> controller.onGameStartedEvent(e.getGame());
            case ModelUpdateEvent e -> controller.onModelUpdateEvent(e.getGame());
            case GameSelectionTurnResponse r -> controller.onGameSelectionTurnEvent(r.seal());
            case GameInsertionTurnResponse r -> controller.onGameInsertionTurnEvent(r.seal());
            case null, default ->
                    throw new IllegalArgumentException("Message type not handled, message=" + incomingMessage);
        }
    }


    @Override
    public void close() throws IOException {
        socketIn.close();
        socketOut.close();
        socket.close();
    }

    @Override
    public void gameStartRequest(String username, GameMode mode, ClientProtocol protocol, ClientService remoteService) {
        GameCreationRequest message = new GameCreationRequest(mode, username, protocol);

        SocketSystem.sendAsync(socketOut, message, GameCreationRequest.class);

        // fixme return reply.getStatus() == null ? new SingleResult.Success<>() : new SingleResult.Failure<>(reply.getStatus());
    }

    @Override
    public void serverStatusRequest(ClientService remoteService) throws RemoteException {
        ServerStatusRequest message = new ServerStatusRequest();

        SocketSystem.sendAsync(socketOut, message, ServerStatusRequest.class);

    }

    @Override
    public void gameConnectionRequest(String username, ClientProtocol protocol, ClientService remoteService) {
        GameConnectionRequest message = new GameConnectionRequest(username, protocol);

        SocketSystem.sendAsync(socketOut, message, GameConnectionRequest.class);

        // fixme return reply != null ? reply.getStatus() : null;
    }

    @Override
    public void gameSelectionTurnResponse(String username, Set<Coordinate> selection) {
        GameSelectionTurnRequest message = new GameSelectionTurnRequest(username, selection);

        SocketSystem.sendAsync(socketOut, message, GameSelectionTurnRequest.class);

        // fixme return reply.getTurnResult();
    }

    @Override
    public void gameInsertionTurnResponse(String username, List<Tile> tiles, int column) {
        GameInsertionTurnRequest message = new GameInsertionTurnRequest(username, tiles, column);

        SocketSystem.sendAsync(socketOut, message, GameInsertionTurnRequest.class);

        // fixme return reply.getTurnResult();
    }

    @Override
    public void keepAlive(String username) {
        KeepAlive keepAliveMessage = new KeepAlive(username);

        SocketSystem.sendAsync(socketOut, keepAliveMessage, KeepAlive.class);
    }
}
