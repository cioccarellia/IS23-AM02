package it.polimi.ingsw.networkProtocol.RMIConnection;

import it.polimi.ingsw.controller.result.SingleResult;
import it.polimi.ingsw.controller.result.failures.SignUpRequest;
import it.polimi.ingsw.controller.result.failures.StatusError;

import java.rmi.Remote;

public interface Callable extends Remote {
    //FIXME io qui estenderei EventControl e chiamerei tutti i metodi di gestione gioco dal client
    //FIXME il client è quindi collegato ad un controller nel server che cambia i parametri al model
    //FIXME non capisco inoltre cosa sia remote e dove viene implementato, sarebbe forse meglio chiamare dal server EventControl
    SingleResult<SignUpRequest> onPlayerSignUpRequest(String username);
    public SingleResult<StatusError> onPlayerConnection(String username);
}
