package it.polimi.ingsw.controller.server.wrappers;

import it.polimi.ingsw.controller.server.ServerService;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameStartError;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.net.tcp.messages.Message;
import it.polimi.ingsw.net.tcp.messages.request.*;
import it.polimi.ingsw.net.tcp.messages.request.replies.*;

import java.rmi.RemoteException;

public class ServerTcpWrapper extends ServerWrapper {

    private final ServerService controller;

    public ServerTcpWrapper(ServerService controller) {
        this.controller = controller;
    }

    @Override
    public ServerService serverService() {
        return controller;
    }

    @Override
    public ClientProtocol protocol() {
        return ClientProtocol.TCP;
    }

    public Message receiveAndReturnMessage(final Message incomingMessage) {
        // visitor pattern
        try {
            switch (incomingMessage) {
                case ServerStatusRequest serverStatusRequest -> {
                    ServerStatus status = controller.serverStatusRequest();
                    return new ServerStatusRequestReply(status);
                }
                case GameStartRequest s -> {
                    SingleResult<GameStartError> result = controller.gameStartRequest(s.getMode(), s.getUsername(), s.getProtocol());

                    return new GameStartRequestReply(result);
                }
                case GameConnectionRequest s -> {
                    SingleResult<GameConnectionError> result = controller.gameConnectionRequest(s.getUsername(), s.getProtocol());

                    return new GameConnectionRequestReply(result);
                }
                case GameSelectionTurnRequest s -> {
                    SingleResult<TileSelectionFailures> result = controller.gameSelectionTurnResponse(s.getUsername(), s.getSelection());

                    return new GameSelectionTurnRequestReply(result);
                }
                case GameInsertionTurnRequest s -> {
                    SingleResult<BookshelfInsertionFailure> result = controller.gameInsertionTurnResponse(s.getUsername(), s.getTiles(), s.getColumn());

                    return new GameInsertionTurnRequestReply(result);
                }
                case KeepAlive r -> {
                    controller.keepAlive(r.getUsername());
                    return new KeepAliveReply();
                }
                case null, default -> throw new IllegalArgumentException("Message type not handled");
            }
        } catch (RemoteException e) {
            // Impossible condition: on TCP instance we get an RMI error.
            System.exit(-1);
            throw new RuntimeException(e);
        }
    }
}