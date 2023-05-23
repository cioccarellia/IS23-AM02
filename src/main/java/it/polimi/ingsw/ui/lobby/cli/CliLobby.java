package it.polimi.ingsw.ui.lobby.cli;

import it.polimi.ingsw.app.model.AggregatedPlayerInfo;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.ui.lobby.LobbyGateway;
import it.polimi.ingsw.ui.lobby.LobbyViewEventHandler;

import java.util.List;

public class CliLobby implements LobbyGateway {

    private final LobbyViewEventHandler handler;

    public CliLobby(LobbyViewEventHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onServerStatusUpdate(ServerStatus status, List<AggregatedPlayerInfo> playerInfo) {

    }

    @Override
    public void onServerCreationReply(SingleResult<GameCreationError> result) {
        switch (result) {

            case SingleResult.Failure<GameCreationError> failure -> {

            }
            case SingleResult.Success<GameCreationError> success -> {

            }
        }
    }

    @Override
    public void onServerConnectionReply(SingleResult<GameConnectionError> result) {

    }
}
