package it.polimi.ingsw.app.client.layers.view;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.launcher.parameters.ClientUiMode;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.game.GameGateway;
import it.polimi.ingsw.ui.game.cli.CliApp;
import it.polimi.ingsw.ui.game.gui.RunnableGuiGame;
import it.polimi.ingsw.ui.lobby.cli.CliLobby;
import it.polimi.ingsw.ui.lobby.gui.RunnableGuiLobby;
import javafx.application.Platform;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

/**
 * Factory class for creating the UI views for the game and lobby.
 */
public class ViewFactory {

    private static final Logger logger = LoggerFactory.getLogger(ViewFactory.class);


    /**
     * Creates the UI view for the game based on the client UI mode.
     *
     * @param mode       The client UI mode.
     * @param model      The game model.
     * @param controller The game view event handler.
     * @param owner      The owner of the game.
     * @return The game UI view.
     */
    public static void createGameUiAsync(final @NotNull ClientUiMode mode, final Game model, final ClientController controller, final String owner, ExecutorService executorService) {
        switch (mode) {
            case CLI -> {
                logger.info("createGameUiAsync(): Starting CLI game");

                //executorService.submit(() -> {
                logger.info("createGameUiAsync(): Starting CLI game on dedicated thread");
                GameGateway game = new CliApp(model, controller, owner);

                logger.info("createGameUiAsync(): CLI started, calling controller.onGameUiReady()");
                controller.onGameUiReady(game);
                //});
            }
            case GUI -> {
                logger.info("Starting GUI game");
                RunnableGuiGame.initModel(model, controller, owner);
                RunnableGuiGame.initLifecycle(controller);

                // executorService.submit(() -> {
                Platform.runLater(() -> {
                    logger.info("Starting GUI game on dedicated thread");
                    RunnableGuiGame.main(new String[]{});
                });

            }
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        }
    }

    /**
     * Creates the UI view for the lobby based on the client UI mode.
     *
     * @param mode       The client UI mode.
     * @param controller The lobby view event handler.
     * @return The lobby UI view.
     */
    public static void createLobbyUiAsync(final @NotNull ClientUiMode mode, final ClientController controller, ExecutorService executorService) {
        switch (mode) {
            case CLI -> {
                logger.info("Starting CLI lobby");

                //executorService.submit(() -> {
                CliLobby lobby = new CliLobby(controller);
                controller.onLobbyUiReady(lobby);
                //});
            }
            case GUI -> {
                logger.info("Starting GUI lobby");
                RunnableGuiLobby.initHandler(controller);
                RunnableGuiLobby.initLifecycle(controller);

                executorService.submit(() -> {
                    logger.info("Starting GUI lobby on dedicated thread");
                    RunnableGuiLobby.main(new String[]{});
                });

            }
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        }
    }
}
