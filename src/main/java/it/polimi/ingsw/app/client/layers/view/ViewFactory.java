package it.polimi.ingsw.app.client.layers.view;

import it.polimi.ingsw.launcher.parameters.ClientUiMode;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.UiGateway;
import it.polimi.ingsw.ui.ViewEventHandler;
import it.polimi.ingsw.ui.cli.CliApp;
import it.polimi.ingsw.ui.gui.GuiApp;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ViewFactory {
    public static @NotNull UiGateway create(final @NotNull ClientUiMode mode, final Game model, final ViewEventHandler handler) {
        switch (mode) {
            case CLI -> {
                return new CliApp(model, handler);
            }
            case GUI -> {
                return new GuiApp(model, handler);
            }
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        }
    }
}
