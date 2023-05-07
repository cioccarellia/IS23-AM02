package it.polimi.ingsw.controller.server.wrappers;

import it.polimi.ingsw.controller.server.ServerService;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameStartError;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.networkProtocol.tcp.messages.Message;
import it.polimi.ingsw.networkProtocol.tcp.messages.request.*;
import it.polimi.ingsw.networkProtocol.tcp.messages.request.replies.*;

import java.rmi.RemoteException;

public class ServerTCPWrapper {

    private final ServerService server;

    public ServerTCPWrapper(ServerService server) throws RemoteException {
        this.server = server;
    }


    public Message receiveAndReturnMessage(final Message incomingMessage) {

        switch (incomingMessage) {
            case ServerStatusRequest serverStatusRequest -> {
                ServerStatus status = server.serverStatusRequest();
                return new ServerStatusRequestReply(status);
            }
            case GameStartRequest s -> {
                SingleResult<GameStartError> result = server.gameStartRequest(s.getMode(), s.getUsername(), s.getProtocol());

                return new GameStartRequestReply(result);
            }
            case GameConnectionRequest s -> {
                SingleResult<GameConnectionError> result = server.gameConnectionRequest(s.getUsername(), s.getProtocol());

                return new GameConnectionRequestReply(result);
            }
            case GameSelectionTurnRequest s -> {
                SingleResult<TileSelectionFailures> result = server.gameSelectionTurnResponse(s.getUsername(), s.getSelection());

                return new GameSelectionTurnRequestReply(result);
            }
            case GameInsertionTurnRequest s -> {
                SingleResult<BookshelfInsertionFailure> result = server.gameInsertionTurnResponse(s.getUsername(), s.getTiles(), s.getColumn());

                return new GameInsertionTurnRequestReply(result);
            }
            case KeepAliveRequest r -> {
                server.keepAlive(r.getUsername());
                return new KeepAliveReply();
            }
            case null, default -> throw new IllegalArgumentException("Message type not handled");
        }

    }


}
