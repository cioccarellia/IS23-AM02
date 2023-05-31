package it.polimi.ingsw.ui.game.gui;

import it.polimi.ingsw.controller.client.lifecycle.AppLifecycle;
import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.controller.server.result.types.TileInsertionSuccess;
import it.polimi.ingsw.controller.server.result.types.TileSelectionSuccess;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.game.GameGateway;
import it.polimi.ingsw.ui.game.GameViewEventHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

/**
 * The RunnableGuiGame class represents the entry point for running the graphical user interface (GUI) version of the game.
 * It extends the JavaFX Application class and implements the GameGateway interface.
 */
public class RunnableGuiGame extends Application implements GameGateway {

    private final URL fxmlURL = getClass().getResource("/fxml/game/index.fxml");

    private static final Logger logger = LoggerFactory.getLogger(RunnableGuiGame.class);

    private GuiGameController gameController;

    private static Game model;
    private static String owner;

    private static AppLifecycle lifecycle;
    private static GameViewEventHandler handler;

    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * Initializes the model, handler, and owner for the GUI game.
     *
     * @param _model   The game model.
     * @param _handler The game view event handler.
     * @param _owner   The owner of the game.
     */
    public static void initModel(Game _model, GameViewEventHandler _handler, String _owner) {
        model = _model;
        handler = _handler;
        owner = _owner;
    }

    public static void initLifecycle(AppLifecycle _appLifecycle) {
        lifecycle = _appLifecycle;
    }

    /**
     * Starts the GUI game by loading the FXML file and initializing the game controller.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    public void start(Stage primaryStage) throws IOException {
        try {
            logger.info("RunnableGuiGame.start(), model={}", model.toString());
            logger.info("RunnableGuiGame.start(), handler={}", handler.toString());
            logger.info("RunnableGuiGame.start(), this={} ", this);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(fxmlURL);

            Parent rootLayout;

            try {
                rootLayout = loader.load();
            } catch (IOException e) {
                logger.error("Error while loading game XML", e);
                throw new IllegalStateException();
            }

            gameController = loader.getController();
            gameController.initModel(model, handler, owner);

            Scene loadedScene = new Scene(rootLayout, 1018, 809, false, SceneAntialiasing.BALANCED);

            primaryStage.setScene(loadedScene);

            primaryStage.setTitle("My shelfie: the game");
            primaryStage.getIcons().add(new Image("img/publisher_material/title_2000x2000px.png"));
            primaryStage.show();

            Platform.runLater(() -> gameController.modelUpdate(model));

            lifecycle.onGameUiReady(this);
        } catch (Exception e) {
            logger.error("Exception in RunnableGuiGame.start()", e);
        }
    }

    /**
     * Notifies the game controller that the game has been created.
     */
    @Override
    public void onGameCreated() {
        gameController.onGameCreated();
    }

    /**
     * Updates the game model in the game controller.
     *
     * @param game The updated game model.
     */
    @Override
    public void modelUpdate(Game game) {
        gameController.modelUpdate(game);
    }

    /**
     * Notifies the game controller about the result of a tile selection in the game.
     *
     * @param turnResult The result of the tile selection.
     */
    @Override
    public void onGameSelectionReply(TypedResult<TileSelectionSuccess, TileSelectionFailures> turnResult) {
        gameController.onGameSelectionReply(turnResult);
    }

    /**
     * Notifies the game controller about the result of a bookshelf insertion in the game.
     *
     * @param turnResult The result of the bookshelf insertion.
     */
    @Override
    public void onGameInsertionReply(TypedResult<TileInsertionSuccess, BookshelfInsertionFailure> turnResult) {
        gameController.onGameInsertionReply(turnResult);
    }
}
