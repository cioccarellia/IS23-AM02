package it.polimi.ingsw.app.client.layers.view;

import it.polimi.ingsw.ui.game.GameGateway;
import it.polimi.ingsw.ui.lobby.LobbyGateway;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;

public class ViewLayer {

    public static void scheduleGameExecutionThread(@NotNull GameGateway ui, @NotNull ExecutorService service) {
        service.execute(ui);
    }

    public static void scheduleLobbyExecutionThread(@NotNull LobbyGateway ui, @NotNull ExecutorService service) {
        service.submit(ui);
    }
}
