package it.polimi.ingsw.app.client.layers.view;

import it.polimi.ingsw.launcher.parameters.ClientUiMode;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.game.GameGateway;
import it.polimi.ingsw.ui.game.GameViewEventHandler;
import it.polimi.ingsw.ui.game.cli.CliApp;
import it.polimi.ingsw.ui.game.gui.GuiGameController;
import it.polimi.ingsw.ui.game.gui.RunnableGuiGame;
import it.polimi.ingsw.ui.lobby.LobbyGateway;
import it.polimi.ingsw.ui.lobby.LobbyViewEventHandler;
import it.polimi.ingsw.ui.lobby.cli.CliLobby;
import it.polimi.ingsw.ui.lobby.gui.RunnableGuiLobby;
import javafx.application.Application;
import javafx.application.Platform;
import org.jetbrains.annotations.NotNull;

/**
 * Factory class for creating the UI views for the game and lobby.
 */
public class ViewFactory {
    /**
     * Creates the UI view for the game based on the client UI mode.
     *
     * @param mode    The client UI mode.
     * @param model   The game model.
     * @param handler The game view event handler.
     * @param owner   The owner of the game.
     * @return The game UI view.
     */
    public static @NotNull GameGateway createGameUi(final @NotNull ClientUiMode mode, final Game model, final GameViewEventHandler handler, final String owner) {
        switch (mode) {
            case CLI -> {
                return new CliApp(model, handler, owner);
            }
            case GUI -> {
                var gui = new RunnableGuiGame();
                gui.initModel(model, handler, owner);

                Platform.runLater(() -> {
                    Application.launch(RunnableGuiGame.class, "");
                });
                return gui;

            }
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        }
    }

    /**
     * Creates the UI view for the lobby based on the client UI mode.
     *
     * @param mode    The client UI mode.
     * @param handler The lobby view event handler.
     * @return The lobby UI view.
     */
    public static @NotNull LobbyGateway createLobbyUi(final @NotNull ClientUiMode mode, final LobbyViewEventHandler handler) {
        switch (mode) {
            case CLI -> {
                return new CliLobby(handler);
            }
            case GUI -> {
                RunnableGuiLobby lobby = new RunnableGuiLobby();
                lobby.initHandler(handler);

                Platform.runLater(() -> {
                    Application.launch(RunnableGuiLobby.class, "");
                });
                return lobby;
            }
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        }
    }
}
