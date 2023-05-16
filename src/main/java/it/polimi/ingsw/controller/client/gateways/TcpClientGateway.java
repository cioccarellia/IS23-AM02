package it.polimi.ingsw.controller.client.gateways;

import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.net.tcp.messages.Message;
import it.polimi.ingsw.net.tcp.messages.request.*;
import it.polimi.ingsw.services.ClientService;
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

    private <I extends Request> void sendRequestAndAwaitReply(Message request, Class<I> inputType) {
        // serializes to JSON the message content
        String serializedJsonRequest = Parsers.marshaledGson().toJson(request, inputType);

        // sends the message bytes on TCP
        socketOut.println(serializedJsonRequest);
        socketOut.flush();
    }

    @Override
    public void serverStatusRequest() {
        ServerStatusRequest message = new ServerStatusRequest();

        sendRequestAndAwaitReply(message, ServerStatusRequest.class);
        // fixme return reply != null ? reply.getStatus() : null;
    }

    @Override
    public void gameStartRequest(String username, GameMode mode, ClientProtocol protocol) {
        GameStartRequest message = new GameStartRequest(mode, username, protocol);

        sendRequestAndAwaitReply(message, GameStartRequest.class);

        // fixme return reply.getStatus() == null ? new SingleResult.Success<>() : new SingleResult.Failure<>(reply.getStatus());
    }

    @Override
    public void gameConnectionRequest(String username, ClientProtocol protocol) {
        GameConnectionRequest message = new GameConnectionRequest(username, protocol);

        sendRequestAndAwaitReply(message, GameConnectionRequest.class);

        // fixme return reply != null ? reply.getStatus() : null;
    }

    @Override
    public void gameSelectionTurnResponse(String username, Set<Coordinate> selection) {
        GameSelectionTurnRequest message = new GameSelectionTurnRequest(username, selection);

        sendRequestAndAwaitReply(message, GameSelectionTurnRequest.class);

        // fixme return reply.getTurnResult();
    }

    @Override
    public void gameInsertionTurnResponse(String username, List<Tile> tiles, int column) {
        GameInsertionTurnRequest message = new GameInsertionTurnRequest(username, tiles, column);

        sendRequestAndAwaitReply(message, GameInsertionTurnRequest.class);

        // fixme return reply.getTurnResult();
    }

    @Override
    public void keepAlive(String username) {
        KeepAlive keepAliveMessage = new KeepAlive(username);

        sendRequestAndAwaitReply(keepAliveMessage, KeepAlive.class);
    }
}
