package it.polimi.ingsw.ui.lobby;

import it.polimi.ingsw.app.model.AggregatedPlayerInfo;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;

import java.util.List;

public interface LobbyGateway {

    void onServerStatusUpdate(ServerStatus status, List<AggregatedPlayerInfo> playerInfo);


    void onServerCreationReply(SingleResult<GameCreationError> result);

    void onServerConnectionReply(SingleResult<GameConnectionError> result);



}
