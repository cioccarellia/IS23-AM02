package it.polimi.ingsw.app.client.layers.view;

import it.polimi.ingsw.launcher.parameters.ClientUiMode;
import it.polimi.ingsw.ui.UiGateway;
import it.polimi.ingsw.ui.cli.CliApp;
import it.polimi.ingsw.ui.gui.GuiApp;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ViewFactory {
    @Contract("_ -> new")
    public static @NotNull UiGateway create(@NotNull ClientUiMode mode) {
        switch (mode) {
            case CLI -> {
                return new CliApp();
            }
            case GUI -> {
                return new GuiApp();
            }
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        }
    }
}
