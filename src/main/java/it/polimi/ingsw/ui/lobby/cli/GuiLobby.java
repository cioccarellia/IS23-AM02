package it.polimi.ingsw.ui.lobby.cli;

import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.types.GameConnectionSuccess;
import it.polimi.ingsw.controller.server.result.types.GameCreationSuccess;
import it.polimi.ingsw.ui.lobby.LobbyGateway;
import it.polimi.ingsw.ui.lobby.LobbyViewEventHandler;

import java.util.List;

public class GuiLobby implements LobbyGateway {

    private final LobbyViewEventHandler handler;

    public GuiLobby(LobbyViewEventHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onServerStatusUpdate(ServerStatus status, List<PlayerInfo> playerInfo) {

    }

    @Override
    public void onServerCreationReply(TypedResult<GameCreationSuccess, GameCreationError> result) {

    }

    @Override
    public void onServerConnectionReply(TypedResult<GameConnectionSuccess, GameConnectionError> result) {

    }

    @Override
    public void run() {

    }
}
