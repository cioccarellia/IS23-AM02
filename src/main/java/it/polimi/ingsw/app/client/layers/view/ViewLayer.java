package it.polimi.ingsw.app.client.layers.view;

import it.polimi.ingsw.ui.UiGateway;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;

public class ViewLayer {

    public static void scheduleUiExecutionThread(@NotNull UiGateway ui, @NotNull ExecutorService service) {
        service.execute(ui);
    }
}
