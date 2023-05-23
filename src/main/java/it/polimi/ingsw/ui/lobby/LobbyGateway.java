package it.polimi.ingsw.ui.lobby;

import it.polimi.ingsw.app.model.AggregatedPlayerInfo;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.types.GameConnectionSuccess;
import it.polimi.ingsw.controller.server.result.types.GameCreationSuccess;

import java.util.List;

public interface LobbyGateway extends Runnable {

    void onServerStatusUpdate(ServerStatus status, List<AggregatedPlayerInfo> playerInfo);

    void onServerCreationReply(TypedResult<GameCreationSuccess, GameCreationError> result);

    void onServerConnectionReply(TypedResult<GameConnectionSuccess, GameConnectionError> result);

    void confirmOwner(String owner);

}
