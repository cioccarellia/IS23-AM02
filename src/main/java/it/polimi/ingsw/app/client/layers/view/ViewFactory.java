package it.polimi.ingsw.app.client.layers.view;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.launcher.parameters.ClientUiMode;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.ui.commons.gui.AppManager;
import it.polimi.ingsw.ui.commons.gui.GuiApp;
import it.polimi.ingsw.ui.game.GameGateway;
import it.polimi.ingsw.ui.game.cli.CliApp;
import it.polimi.ingsw.ui.lobby.cli.CliLobby;
import javafx.application.Platform;
import javafx.stage.Stage;
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
     * Creates the UI view for the lobby based on the client UI mode.
     *
     * @param mode       The client UI mode.
     * @param controller The lobby view event handler.
     */
    public static void createLobbyUiAsync(final @NotNull ClientUiMode mode, final ClientController controller, ExecutorService executorService) {
        switch (mode) {
            case CLI -> {
                logger.info("Starting CLI lobby");

                CliLobby lobby = new CliLobby(controller);
                controller.onLobbyUiReady(lobby);
            }
            case GUI -> {
                logger.info("Starting GUI lobby");
                GuiApp.initLobbyHandler(controller);
                GuiApp.initLifecycle(controller);

                executorService.submit(() -> {
                    logger.info("createLobbyUiAsync(): Starting JavaFX stage");
                    GuiApp.main(new String[]{});
                });

            }
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        }
    }


    /**
     * Creates the UI view for the game based on the client UI mode.
     *
     * @param mode       The client UI mode.
     * @param model      The game model.
     * @param controller The game view event handler.
     * @param owner      The owner of the game.
     */
    public static void createGameUiAsync(final @NotNull ClientUiMode mode, final GameModel model, final ClientController controller, final String owner, ExecutorService executorService) {
        switch (mode) {
            case CLI -> {
                logger.info("createGameUiAsync(): Starting CLI game");

                executorService.submit(() -> {
                    logger.info("createGameUiAsync(): Starting CLI game on dedicated thread");
                    GameGateway game = new CliApp(model, controller, owner);

                    logger.info("createGameUiAsync(): CLI started, calling controller.onGameUiReady()");
                    controller.onGameUiReady(game);
                });
            }
            case GUI -> {
                logger.info("Starting GUI game");
                GuiApp.injectGameModelPostLogin(model, controller, owner);

                Platform.runLater(() -> {
                    try {
                        logger.info("createGameUiAsync(): Starting JavaFX stage");
                        AppManager.getAppInstance().setupPrimaryStage(new Stage());
                    } catch (Exception e) {
                        System.out.println("Error starting JavaFX application");
                        e.printStackTrace();
                        System.exit(-1);
                        throw new RuntimeException(e);
                    }
                });
            }
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        }
    }
}
