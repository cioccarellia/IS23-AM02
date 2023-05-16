package it.polimi.ingsw.controller.client.gateways;

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
import org.jetbrains.annotations.Nullable;
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

    final private Socket echoSocket;
    final private PrintWriter out;
    final private BufferedReader in;


    public TcpClientGateway(String serverHost, int serverTcpPort) {
        try {
            echoSocket = new Socket(serverHost, serverTcpPort);
        } catch (UnknownHostException e) {
            logger.error("Unknown host serverHost={}", serverHost);
            throw new IllegalArgumentException("Wrong serverHost/port combination", e);
        } catch (IOException e) {
            logger.error("Can not acquire I/O to start client gateway, probably TcpServer not yet started [tried connecting to serverHost={}, serverPort={}]", serverHost, serverTcpPort);
            throw new IllegalArgumentException("Impossible to acquire I/O for connection to server", e);
        }

        logger.info("Started socket for TcpClientGateway, socket={}", echoSocket);

        try {
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (IOException e) {
            logger.error("I/O error while acquiring socket", e);
            throw new RuntimeException(e);
        }
    }

    public void close() throws IOException {
        in.close();
        out.close();
        echoSocket.close();
    }


    @Override
    public void synchronizeConnectionLayer(String username, ClientService service) throws RemoteException {
        // INOP, TCP does not need initial
        keepAlive(username);
    }

    @Nullable private <I extends Request, O extends Reply> O sendMessageAndAwaitReply(Message request, Class<I> inputType, Class<O> outputType) {
        // serializes to JSON the message content
        String serializedJsonRequest = Parsers.marshaledGson().toJson(request, inputType);

        // sends the message bytes on TCP
        out.println(serializedJsonRequest);
        out.flush();


        String serializedResponse;

        try {
            // fixme reads response from network
            while ((serializedResponse = in.readLine()) == null) {
                serializedResponse = in.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (serializedResponse == null) {
            return null;
        }

        // De-serializes response back into its expected response type
        O messageResponse = Parsers.marshaledGson().fromJson(serializedResponse, outputType);

        // returns
        return messageResponse;
    }

    @Override
    public ServerStatus serverStatusRequest() {
        ServerStatusRequest message = new ServerStatusRequest();

        ServerStatusRequestReply reply = sendMessageAndAwaitReply(message, ServerStatusRequest.class, ServerStatusRequestReply.class);

        return reply == null ? null : reply.getStatus();
    }

    @Override
    public SingleResult<GameStartError> gameStartRequest(GameMode mode, String username, ClientProtocol protocol) {
        GameStartRequest message = new GameStartRequest(mode, username, protocol);

        GameStartRequestReply reply = sendMessageAndAwaitReply(message, GameStartRequest.class, GameStartRequestReply.class);

        return reply.getStatus();
    }

    @Override
    public SingleResult<GameConnectionError> gameConnectionRequest(String username, ClientProtocol protocol) {
        GameConnectionRequest message = new GameConnectionRequest(username, protocol);

        GameConnectionRequestReply reply = sendMessageAndAwaitReply(message, GameConnectionRequest.class, GameConnectionRequestReply.class);

        return reply.getStatus();
    }

    @Override
    public SingleResult<TileSelectionFailures> gameSelectionTurnResponse(String username, Set<Coordinate> selection) {
        GameSelectionTurnRequest message = new GameSelectionTurnRequest(username, selection);

        GameSelectionTurnRequestReply reply = sendMessageAndAwaitReply(message, GameSelectionTurnRequest.class, GameSelectionTurnRequestReply.class);

        return reply.getTurnResult();
    }

    @Override
    public SingleResult<BookshelfInsertionFailure> gameInsertionTurnResponse(String username, List<Tile> tiles, int column) {
        GameInsertionTurnRequest message = new GameInsertionTurnRequest(username, tiles, column);

        GameInsertionTurnRequestReply reply = sendMessageAndAwaitReply(message, GameInsertionTurnRequest.class, GameInsertionTurnRequestReply.class);

        return reply.getTurnResult();
    }

    @Override
    public void keepAlive(String player) {
        KeepAlive keepAliveMessage = new KeepAlive(player);

        KeepAliveReply reply = sendMessageAndAwaitReply(keepAliveMessage, KeepAlive.class, KeepAliveReply.class);
    }
}
