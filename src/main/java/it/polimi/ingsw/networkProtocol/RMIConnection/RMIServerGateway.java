package it.polimi.ingsw.networkProtocol.RMIConnection;

import it.polimi.ingsw.controller.ServerStatus;
import it.polimi.ingsw.controller.result.SingleResult;
import it.polimi.ingsw.controller.result.failures.*;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.game.GameMode;

import java.util.List;
import java.util.Set;

public class RMIServerGateway implements ServerGateway {

    @Override
    public ServerStatus serverStatusRequest() {
        return null;
    }

    @Override
    public SingleResult<GameStartError> gameStartedRequest(GameMode mode, String username, ClientProtocol protocol) {
        return null;
    }

    @Override
    public SingleResult<GameConnectionError> gameConnectionRequest(String username, ClientProtocol protocol) {
        return null;
    }

    @Override
    public SingleResult<TileSelectionFailures> gameSelectionTurnResponse(String username, Set<Coordinate> selection) {
        return null;
    }

    @Override
    public SingleResult<BookshelfInsertionFailure> gameInsertionTurnResponse(String username, List<Tile> tiles, int column) {
        return null;
    }

    @Override
    public void keepAlive(String player) {

    }
}
