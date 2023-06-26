package it.polimi.ingsw.controller.server.wrappers;

import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.network.tcp.TcpConnectionHandler;
import it.polimi.ingsw.network.tcp.messages.Message;
import it.polimi.ingsw.network.tcp.messages.request.*;
import it.polimi.ingsw.services.ServerService;
import org.jetbrains.annotations.NotNull;

import java.rmi.RemoteException;

public class ServerTcpWrapper extends ServerWrapper {

    private final ServerService controller;

    public ServerTcpWrapper(ServerService controller) {
        this.controller = controller;
    }

    @Override
    public ServerService exposeServerService() {
        return controller;
    }

    @Override
    public ClientProtocol protocol() {
        return ClientProtocol.TCP;
    }

    public void mapRequestToControllerMethodCall(@NotNull final Message incomingMessage, TcpConnectionHandler handler) {
        // visitor pattern
        try {
            switch (incomingMessage) {
                case ServerStatusRequest s -> controller.serverStatusRequest(handler);
                case GameCreationRequest s ->
                        controller.gameStartRequest(s.getUsername(), s.getMode(), s.getProtocol(), handler);
                case GameConnectionRequest s ->
                        controller.gameConnectionRequest(s.getUsername(), s.getProtocol(), handler);
                case GameSelectionTurnRequest s ->
                        controller.gameSelectionTurnResponse(s.getUsername(), s.getSelection());
                case GameInsertionTurnRequest s -> {
                    controller.gameInsertionTurnResponse(s.getUsername(), s.getTiles(), s.getColumn());
                }
                case ChatTextMessageRequest text -> {
                    controller.sendTextMessage(text.senderUsername(), text.recipient(), text.text());
                }
                case KeepAlive r -> controller.keepAlive(r.getUsername());
                case QuitRequest r -> controller.quitRequest(r.getUsername());
                case null, default -> throw new IllegalArgumentException("Message type not handled");
            }
        } catch (RemoteException e) {
            // Impossible condition: on TCP instance we get an RMI error.
            System.exit(-123);
            throw new RuntimeException(e);
        }
    }
}