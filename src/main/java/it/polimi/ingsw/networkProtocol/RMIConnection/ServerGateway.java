package it.polimi.ingsw.networkProtocol.RMIConnection;

import it.polimi.ingsw.controller.ServerStatus;
import it.polimi.ingsw.controller.result.SingleResult;
import it.polimi.ingsw.controller.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.result.failures.SignUpRequest;
import it.polimi.ingsw.controller.result.failures.StatusError;
import it.polimi.ingsw.controller.result.failures.TileSelectionFailures;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.game.GameMode;

import java.rmi.Remote;
import java.util.List;
import java.util.Set;

public interface ServerGateway extends Remote {

    String NAME = "ServerGateway";

    // Initialization
    ServerStatus serverStatusRequest();

    SingleResult<StatusError> gameStartedRequest(GameMode mode, String username, ClientProtocol protocol);

    SingleResult<SignUpRequest> gameConnectionRequest(String username, ClientProtocol protocol);

    // SingleResult<StatusError> gameTeardownRequest();


    // Running
    SingleResult<TileSelectionFailures> gameSelectionTurnResponse(String username, Set<Coordinate> selection);

    SingleResult<BookshelfInsertionFailure> gameInsertionTurnResponse(String username, List<Tile> tiles, int column);


    //Connection - Disconnection
    void keepAlive(String player);
}
