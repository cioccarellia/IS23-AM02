package it.polimi.ingsw.networkProtocol.RMIConnection;

import java.rmi.Remote;

public interface ClientGateway extends Remote {

    //FIXME temporary typization

    String NAME = "ClientGateway";

    // Initialization
    int serverStatusResponse();

    int serverStatusUpdateEvent();

    int gameStartedEvent();


    // Running
    int modelUpdateEvent();

    int gamePhaseEvent();

    int gameEndedEvent();


    //Connection - Disconnection
    int playerConnectionStatusUpdateEvent();

    int gameStandbyEvent();

    int gameResumedEvent();

    //int gameEndedEvent();
}