package it.polimi.ingsw.ui.lobby.gui;

import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.controller.client.lifecycle.AppLifecycle;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.controller.server.result.types.GameConnectionSuccess;
import it.polimi.ingsw.controller.server.result.types.GameCreationSuccess;
import it.polimi.ingsw.controller.server.result.types.TileInsertionSuccess;
import it.polimi.ingsw.controller.server.result.types.TileSelectionSuccess;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.game.GameGateway;
import it.polimi.ingsw.ui.game.GameViewEventHandler;
import it.polimi.ingsw.ui.game.guiv2.GuiGameControllerV2;
import it.polimi.ingsw.ui.lobby.LobbyGateway;
import it.polimi.ingsw.ui.lobby.LobbyViewEventHandler;
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
import java.util.List;

/**
 * The RunnableGuiLobby class is responsible for launching and managing the graphical user interface (GUI) for the lobby.
 * It extends the JavaFX Application class and implements the LobbyGateway interface.
 * It provides methods for initializing the lobby view event handler, starting the lobby stage, and handling server status updates,
 * server creation replies, server connection replies, and terminating the lobby.
 */
public class RunnableGuiLobby extends Application implements LobbyGateway, GameGateway {

    private final URL fxmlURL = getClass().getResource("/fxml/lobby/login_stage.fxml");

    private static final Logger logger = LoggerFactory.getLogger(RunnableGuiLobby.class);

    private GuiLobbyController lobbyController;
    private GuiGameControllerV2 gameController;

    public static void main(String[] args) {
        Application.launch(args);
    }


    private static AppLifecycle lifecycle;
    private static LobbyViewEventHandler loginHandler;

    /**
     * Initializes the lobby view event handler.
     *
     * @param _handler The lobby view event handler.
     */
    public static void initHandler(LobbyViewEventHandler _handler) {
        loginHandler = _handler;
    }


    public static void initLifecycle(AppLifecycle _appLifecycle) {
        lifecycle = _appLifecycle;
    }


    /**
     * Starts the lobby stage and loads the lobby GUI from the FXML file.
     *
     * @param lobbyStage The stage for the lobby GUI.
     * @throws Exception if an error occurs while loading the lobby XML.
     */
    @Override
    public void start(Stage lobbyStage) throws Exception {

        try {
            logger.info("RunnableGuiLobby.start(), handler={}", loginHandler.toString());
            logger.info("RunnableGuiLobby.start(), this={} ", this);

            FXMLLoader loader = new FXMLLoader(fxmlURL);
            loader.setLocation(fxmlURL);

            Parent rootLayout;

            try {
                rootLayout = loader.load();
            } catch (IOException e) {
                logger.error("Error while loading lobby XML", e);
                throw new IllegalStateException();
            }


            lobbyController = loader.getController();
            lobbyController.injectEventHandler(loginHandler);

            Scene loadedScene = new Scene(rootLayout, 800, 600, false);

            lobbyStage.setScene(loadedScene);
            lobbyStage.setResizable(false);

            lobbyStage.setTitle("Login page");
            lobbyStage.getIcons().add(new Image("img/publisher_material/publisher.png"));
            lobbyStage.show();

            lifecycle.onLobbyUiReady((RunnableGuiLobby) this, this);
        } catch (Exception e) {
            logger.error("Exception in RunnableGuiLobby.start()", e);
        }
    }


    private static GameViewEventHandler gameHandler;
    private static Game model;
    private static String owner;

    private final URL fxmlURLGame = getClass().getResource("/fxml/gamev2/index_stage.fxml");

    /**
     * Initializes the model, handler, and owner for the GUI game.
     *
     * @param _model   The game model.
     * @param _owner   The owner of the game.
     */
    public static void injectGameModelPostLogin(Game _model, GameViewEventHandler _gameHandler, String _owner) {
        model = _model;
        owner = _owner;
        gameHandler = _gameHandler;
    }

    public void startPrimaryStage() throws Exception {
        Stage newStage = new Stage();

        try {
            logger.info("RunnableGuiGameV2.start(), model={}", model.toString());
            logger.info("RunnableGuiGameV2.start(), handler={}", loginHandler.toString());
            logger.info("RunnableGuiGameV2.start(), this={} ", this);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(fxmlURLGame);

            Parent rootLayout;

            try {
                rootLayout = loader.load();
            } catch (IOException e) {
                logger.error("Error while loading game XML", e);
                throw new IllegalStateException();
            }

            gameController = loader.getController();
            gameController.injectModelData(model, gameHandler, owner);

            Scene loadedScene = new Scene(rootLayout, 1100, 809, false, SceneAntialiasing.BALANCED);

            newStage.setScene(loadedScene);

            newStage.setTitle("MyShelfie");
            newStage.getIcons().add(new Image("img/publisher_material/title_2000x2000px.png"));
            newStage.showAndWait();

            Platform.runLater(() -> gameController.modelUpdate(model));

            lifecycle.onGameUiReady(this);
        } catch (Exception e) {
            logger.error("Exception in RunnableGuiGameV2.start()", e);
        }
    }



    /**
     * Notifies the lobby controller of a server status update.
     *
     * @param status     The updated server status.
     * @param playerInfo The list of player information.
     */
    @Override
    public void onServerStatusUpdate(ServerStatus status, List<PlayerInfo> playerInfo) {
        logger.info("onServerStatusUpdate, handler={}", loginHandler.toString());
        logger.info("onServerStatusUpdate, this={} ", this.toString());

        Platform.runLater(() -> lobbyController.onServerStatusUpdate(status, playerInfo));
    }

    /**
     * Notifies the lobby controller of a server creation reply.
     *
     * @param result The typed result containing either a game creation success or a game creation error.
     */
    @Override
    public void onServerCreationReply(TypedResult<GameCreationSuccess, GameCreationError> result) {
        Platform.runLater(() -> lobbyController.onServerCreationReply(result));
    }

    /**
     * Notifies the lobby controller of a server connection reply.
     *
     * @param result The typed result containing either a game connection success or a game connection error.
     */
    @Override
    public void onServerConnectionReply(TypedResult<GameConnectionSuccess, GameConnectionError> result) {
        Platform.runLater(() -> lobbyController.onServerConnectionReply(result));
    }

    /**
     * Terminates the lobby by notifying the lobby controller.
     */
    @Override
    public void kill() {
        // Platform.exit();
        // Platform.runLater(() -> {
        //     lobbyController.kill();
        //     Platform.exit();
        // });
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
