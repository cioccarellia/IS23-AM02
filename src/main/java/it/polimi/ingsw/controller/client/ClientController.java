package it.polimi.ingsw.controller.client;

import it.polimi.ingsw.networkProtocol.RMIConnection.ClientGateway;
import it.polimi.ingsw.ui.UiGateway;

public class ClientController implements ClientGateway {

    UiGateway ui;

    public ClientController(UiGateway _ui) {
        ui = _ui;
    }

    @Override
    public int serverStatusResponse() {
        return 0;
    }

    @Override
    public int serverStatusUpdateEvent() {
        return 0;
    }

    @Override
    public int gameStartedEvent() {
        return 0;
    }

    @Override
    public int modelUpdateEvent() {
        return 0;
    }

    @Override
    public int gamePhaseEvent() {
        return 0;
    }

    @Override
    public int gameEndedEvent() {
        return 0;
    }

    @Override
    public int playerConnectionStatusUpdateEvent() {
        return 0;
    }

    @Override
    public int gameStandbyEvent() {
        return 0;
    }

    @Override
    public int gameResumedEvent() {
        return 0;
    }
}
