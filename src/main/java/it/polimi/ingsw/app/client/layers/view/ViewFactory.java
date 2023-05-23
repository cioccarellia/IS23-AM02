package it.polimi.ingsw.app.client.layers.view;

import it.polimi.ingsw.launcher.parameters.ClientUiMode;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.GameViewEventHandler;
import it.polimi.ingsw.ui.UiGateway;
import it.polimi.ingsw.ui.cli.CliApp;
import it.polimi.ingsw.ui.gui.GuiApp;
import it.polimi.ingsw.ui.lobby.LobbyGateway;
import it.polimi.ingsw.ui.lobby.LobbyViewEventHandler;
import it.polimi.ingsw.ui.lobby.cli.CliLobby;
import it.polimi.ingsw.ui.lobby.cli.GuiLobby;
import org.jetbrains.annotations.NotNull;

public class ViewFactory {
    public static @NotNull UiGateway createGameUi(final @NotNull ClientUiMode mode, final Game model, final GameViewEventHandler handler, final String owner) {
        switch (mode) {
            case CLI -> {
                return new CliApp(model, handler, owner);
            }
            case GUI -> {
                return new GuiApp(model, handler, owner);
            }
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        }
    }

    public static @NotNull LobbyGateway createLobbyUi(final @NotNull ClientUiMode mode, final LobbyViewEventHandler handler) {
        switch (mode) {
            case CLI -> {
                return new CliLobby(handler);
            }
            case GUI -> {
                return new GuiLobby(handler);
            }
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        }
    }
}
