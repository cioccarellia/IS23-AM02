package it.polimi.ingsw.app.client.layers.view;

import it.polimi.ingsw.ui.game.GameGateway;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;

public class ViewLayer {

    public static void scheduleGameExecutionThread(@NotNull GameGateway ui, @NotNull ExecutorService service) {
        service.execute(ui);
    }
}
