package it.polimi.ingsw.controller.server.result.types;

import it.polimi.ingsw.app.model.PlayerInfo;

import java.util.List;

public record GameCreationSuccess(
        String username,
        List<PlayerInfo> playerInfo
) implements RequestType {
}
