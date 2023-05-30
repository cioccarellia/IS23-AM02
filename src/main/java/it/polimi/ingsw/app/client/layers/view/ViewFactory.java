package it.polimi.ingsw.app.client.layers.view;

import it.polimi.ingsw.launcher.parameters.ClientUiMode;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.game.GameGateway;
import it.polimi.ingsw.ui.game.GameViewEventHandler;
import it.polimi.ingsw.ui.game.cli.CliApp;
import it.polimi.ingsw.ui.game.gui.RunnableGuiGame;
import it.polimi.ingsw.ui.lobby.LobbyGateway;
import it.polimi.ingsw.ui.lobby.LobbyViewEventHandler;
import it.polimi.ingsw.ui.lobby.cli.CliLobby;
import it.polimi.ingsw.ui.lobby.gui.RunnableGuiLobby;
import javafx.application.Application;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Factory class for creating the UI views for the game and lobby.
 */
public class ViewFactory {

    private static final Logger logger = LoggerFactory.getLogger(ViewFactory.class);


    /**
     * Creates the UI view for the game based on the client UI mode.
     *
     * @param mode    The client UI mode.
     * @param model   The game model.
     * @param handler The game view event handler.
     * @param owner   The owner of the game.
     * @return The game UI view.
     */
    public static @NotNull GameGateway createGameUi(final @NotNull ClientUiMode mode, final Game model, final GameViewEventHandler handler, final String owner, ExecutorService executorService) {
        switch (mode) {
            case CLI -> {
                logger.info("Starting CLI game");
                return new CliApp(model, handler, owner);
            }
            case GUI -> {
                logger.info("Starting GUI game");
                var gui = new RunnableGuiGame();
                gui.initModel(model, handler, owner);

                executorService.submit(() -> {
                    logger.info("Starting GUI game on dedicated thread");
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
    public static @NotNull LobbyGateway createLobbyUi(final @NotNull ClientUiMode mode, final LobbyViewEventHandler handler, ExecutorService executorService) {
        switch (mode) {
            case CLI -> {
                logger.info("Starting CLI lobby");
                return new CliLobby(handler);
            }
            case GUI -> {
                logger.info("Starting GUI lobby");
                RunnableGuiLobby lobby = new RunnableGuiLobby();
                lobby.initHandler(handler);

                executorService.submit(() -> {
                    logger.info("Starting GUI lobby on dedicated thread");
                    Application.launch(RunnableGuiLobby.class, "");
                });

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                return lobby;
            }
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        }
    }
}
