package it.polimi.ingsw.controller.client.gateways;

import com.google.gson.JsonParseException;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameStartError;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.net.rmi.ClientService;
import it.polimi.ingsw.net.tcp.messages.Message;
import it.polimi.ingsw.net.tcp.messages.request.*;
import it.polimi.ingsw.net.tcp.messages.request.replies.*;
import it.polimi.ingsw.utils.json.Parsers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

public class TcpClientGateway extends ClientGateway {

    private static final Logger logger = LoggerFactory.getLogger(TcpClientGateway.class);

    final private Socket socket;
    final private PrintWriter socketOut;
    final private BufferedReader socketIn;


    public TcpClientGateway(String serverHost, int serverTcpPort) {
        try {
            socket = new Socket(serverHost, serverTcpPort);
        } catch (UnknownHostException e) {
            logger.error("Unknown host serverHost={}", serverHost);
            throw new IllegalArgumentException("Wrong serverHost/port combination", e);
        } catch (IOException e) {
            logger.error("Can not acquire I/O to start client gateway, probably TcpServer not yet started [tried connecting to serverHost={}, serverPort={}]", serverHost, serverTcpPort);
            throw new IllegalArgumentException("Impossible to acquire I/O for connection to server", e);
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

    public void close() throws IOException {
        socketIn.close();
        socketOut.close();
        socket.close();
    }


    @Override
    public void synchronizeConnectionLayer(String username, ClientService service) throws RemoteException {
        // INOP, TCP does not need initial
        keepAlive(username);
    }

    private <I extends Request, O extends Reply> O sendRequestAndAwaitReply(Message request, Class<I> inputType, Class<O> outputType) {
        // serializes to JSON the message content
        String serializedJsonRequest = Parsers.marshaledGson().toJson(request, inputType);

        // sends the message bytes on TCP
        socketOut.println(serializedJsonRequest);
        socketOut.flush();

        String serializedReply;
        O messageResponse;

        try {
            while (true) {
                //noinspection BlockingMethodInNonBlockingContext
                if ((serializedReply = socketIn.readLine()) == null) {
                    try {
                        messageResponse = Parsers.marshaledGson().fromJson(serializedReply, outputType);
                        break;
                    } catch (JsonParseException e) {
                        logger.error("Parsing exception", e);
                    } catch (RuntimeException rex) {
                        logger.error("RuntimeException while deserializing class {} -> {}", inputType, outputType);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // De-serializes response back into its expected response type

        // returns
        return messageResponse;
    }

    @Override
    public ServerStatus serverStatusRequest() {
        ServerStatusRequest message = new ServerStatusRequest();

        ServerStatusRequestReply reply = sendRequestAndAwaitReply(message, ServerStatusRequest.class, ServerStatusRequestReply.class);

        return reply != null ? reply.getStatus() : null;
    }

    @Override
    public SingleResult<GameStartError> gameStartRequest(GameMode mode, String username, ClientProtocol protocol) {
        GameStartRequest message = new GameStartRequest(mode, username, protocol);

        GameStartRequestReply reply = sendRequestAndAwaitReply(message, GameStartRequest.class, GameStartRequestReply.class);

        return reply != null ? reply.getStatus() : null;
    }

    @Override
    public SingleResult<GameConnectionError> gameConnectionRequest(String username, ClientProtocol protocol) {
        GameConnectionRequest message = new GameConnectionRequest(username, protocol);

        GameConnectionRequestReply reply = sendRequestAndAwaitReply(message, GameConnectionRequest.class, GameConnectionRequestReply.class);

        return reply != null ? reply.getStatus() : null;
    }

    @Override
    public SingleResult<TileSelectionFailures> gameSelectionTurnResponse(String username, Set<Coordinate> selection) {
        GameSelectionTurnRequest message = new GameSelectionTurnRequest(username, selection);

        GameSelectionTurnRequestReply reply = sendRequestAndAwaitReply(message, GameSelectionTurnRequest.class, GameSelectionTurnRequestReply.class);

        return reply.getTurnResult();
    }

    @Override
    public SingleResult<BookshelfInsertionFailure> gameInsertionTurnResponse(String username, List<Tile> tiles, int column) {
        GameInsertionTurnRequest message = new GameInsertionTurnRequest(username, tiles, column);

        GameInsertionTurnRequestReply reply = sendRequestAndAwaitReply(message, GameInsertionTurnRequest.class, GameInsertionTurnRequestReply.class);

        return reply.getTurnResult();
    }

    @Override
    public void keepAlive(String player) {
        KeepAlive keepAliveMessage = new KeepAlive(player);

        KeepAliveReply reply = sendRequestAndAwaitReply(keepAliveMessage, KeepAlive.class, KeepAliveReply.class);
    }
}
