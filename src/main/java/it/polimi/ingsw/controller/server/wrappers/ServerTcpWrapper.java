package it.polimi.ingsw.controller.server.wrappers;

import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.net.tcp.messages.Message;
import it.polimi.ingsw.net.tcp.messages.request.*;
import it.polimi.ingsw.services.ServerService;

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

    public void convertMessageToControllerMethodCall(final Message incomingMessage) {
        // visitor pattern
        try {
            switch (incomingMessage) {
                case ServerStatusRequest serverStatusRequest -> {
                    controller.serverStatusRequest();
                }
                case GameStartRequest s -> {
                    controller.gameStartRequest(s.getUsername(), s.getMode(), s.getProtocol());
                }
                case GameConnectionRequest s -> {
                    controller.gameConnectionRequest(s.getUsername(), s.getProtocol());
                }
                case GameSelectionTurnRequest s -> {
                    controller.gameSelectionTurnResponse(s.getUsername(), s.getSelection());
                }
                case GameInsertionTurnRequest s -> {
                    controller.gameInsertionTurnResponse(s.getUsername(), s.getTiles(), s.getColumn());
                }
                case KeepAlive r -> {
                    controller.keepAlive(r.getUsername());
                }
                case null, default -> throw new IllegalArgumentException("Message type not handled");
            }
        } catch (RemoteException e) {
            // Impossible condition: on TCP instance we get an RMI error.
            System.exit(-123);
            throw new RuntimeException(e);
        }
    }
}