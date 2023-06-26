package it.polimi.ingsw.ui.commons.gui;

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
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.chat.ChatTextMessage;
import it.polimi.ingsw.ui.game.GameGateway;
import it.polimi.ingsw.ui.game.GameUiConstants;
import it.polimi.ingsw.ui.game.GameViewEventHandler;
import it.polimi.ingsw.ui.game.gui.GuiGameController;
import it.polimi.ingsw.ui.lobby.LobbyGateway;
import it.polimi.ingsw.ui.lobby.LobbyUiConstants;
import it.polimi.ingsw.ui.lobby.LobbyViewEventHandler;
import it.polimi.ingsw.ui.lobby.gui.GuiLobbyController;
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

import static it.polimi.ingsw.ui.game.GameUiConstants.APP_HEIGHT;
import static it.polimi.ingsw.ui.game.GameUiConstants.APP_WIDTH;
import static it.polimi.ingsw.ui.lobby.LobbyUiConstants.LOBBY_HEIGHT;
import static it.polimi.ingsw.ui.lobby.LobbyUiConstants.LOBBY_WIDTH;

/**
 * The RunnableGuiLobby class is responsible for launching and managing the graphical user interface (GUI) for the lobby.
 * It extends the JavaFX Application class and implements the LobbyGateway interface.
 * It provides methods for initializing the lobby view event handler, starting the lobby stage, and handling server status updates,
 * server creation replies, server connection replies, and terminating the lobby.
 */
public class GuiApp extends Application implements LobbyGateway, GameGateway {

    private static final Logger logger = LoggerFactory.getLogger(GuiApp.class);

    private GuiLobbyController lobbyController;
    private GuiGameController gameController;

    // Lifecycle
    private static AppLifecycle lifecycle;

    // Handlers
    private static LobbyViewEventHandler lobbyHandler;
    private static GameViewEventHandler gameHandler;

    // Injected model data
    private static GameModel model;
    private static String owner;


    public static void initLifecycle(AppLifecycle _appLifecycle) {
        lifecycle = _appLifecycle;
    }

    /**
     * Initializes the lobby view event handler.
     *
     * @param _handler The lobby view event handler.
     */
    public static void initLobbyHandler(LobbyViewEventHandler _handler) {
        lobbyHandler = _handler;
    }


    @Override
    public void start(Stage lobbyStage) throws Exception {
        setupLobbyStage(lobbyStage);
    }


    /**
     * Starts the lobby stage and loads the lobby GUI from the FXML file.
     *
     * @param lobbyStage The stage for the lobby GUI.
     * @throws Exception if an error occurs while loading the lobby XML.
     */
    public void setupLobbyStage(Stage lobbyStage) throws Exception {
        URL xmlURL = getClass().getResource(LobbyUiConstants.LOBBY_FXML_PATH);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(xmlURL);

        Parent rootLayout;

        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            logger.error("Error while loading lobby XML", e);
            throw new IllegalStateException();
        }


        lobbyController = loader.getController();
        lobbyController.injectEventHandler(lobbyHandler);

        Scene loadedScene = new Scene(rootLayout, LOBBY_WIDTH, LOBBY_HEIGHT, false);

        lobbyStage.setScene(loadedScene);
        lobbyStage.setResizable(false);

        lobbyStage.setTitle("Lobby");
        lobbyStage.getIcons().add(new Image("img/publisher_material/publisher.png"));
        lobbyStage.show();

        // notify the rest of the app
        AppManager.setAppInstance(this);
        lifecycle.onLobbyUiReady(this);
    }


    /**
     * Initializes the model, handler, and owner for the GUI game.
     *
     * @param _model The game model.
     * @param _owner The owner of the game.
     */
    public static void injectGameModelPostLogin(GameModel _model, GameViewEventHandler _gameHandler, String _owner) {
        model = _model;
        owner = _owner;
        gameHandler = _gameHandler;
    }

    public void setupPrimaryStage(Stage newStage) throws Exception {
        URL xmlURL = getClass().getResource(GameUiConstants.GAME_FXML_PATH);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(xmlURL);

        Parent rootLayout;

        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            logger.error("Error while loading game XML", e);
            throw new IllegalStateException();
        }

        gameController = loader.getController();
        gameController.injectModelData(model, gameHandler, owner);

        Scene loadedScene = new Scene(rootLayout, APP_WIDTH, APP_HEIGHT, false, SceneAntialiasing.BALANCED);

        newStage.setScene(loadedScene);
        newStage.setResizable(false);

        newStage.setTitle("MyShelfie");
        newStage.show();

        // Platform.runLater(() -> {
        //     gameController.modelUpdate(model);
        // });

        lifecycle.onGameUiReady(this);
    }


    /**
     * Notifies the lobby controller of a server status update.
     *
     * @param status     The updated server status.
     * @param playerInfo The list of player information.
     */
    @Override
    public void onServerStatusUpdate(ServerStatus status, List<PlayerInfo> playerInfo) {
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

    @Override
    public void inop() {
        Platform.runLater(() -> lobbyController.inop());
    }


    /**
     * Notifies the game controller that the game has been created.
     */
    @Override
    public void onGameCreated() {
        Platform.runLater(() -> {
            gameController.onGameCreated();
        });
    }
    /**
     * Notifies the game controller that the game has been created.
     */
    @Override
    public void onGameServerStatusUpdate(ServerStatus status, List<PlayerInfo> playerInfo) {
        Platform.runLater(() -> {
            gameController.onGameServerStatusUpdate(status, playerInfo);
        });
    }

    /**
     * Updates the game model in the game controller.
     *
     * @param game The updated game model.
     */
    @Override
    public void modelUpdate(GameModel game) {
        Platform.runLater(() -> {
            gameController.modelUpdate(game);
        });
    }

    /**
     * Notifies the game controller about the result of a tile selection in the game.
     *
     * @param turnResult The result of the tile selection.
     */
    @Override
    public void onGameSelectionReply(TypedResult<TileSelectionSuccess, TileSelectionFailures> turnResult) {
        Platform.runLater(() -> {
            gameController.onGameSelectionReply(turnResult);
        });
    }

    /**
     * Notifies the game controller about the result of a bookshelf insertion in the game.
     *
     * @param turnResult The result of the bookshelf insertion.
     */
    @Override
    public void onGameInsertionReply(TypedResult<TileInsertionSuccess, BookshelfInsertionFailure> turnResult) {
        Platform.runLater(() -> {
            gameController.onGameInsertionReply(turnResult);
        });
    }

    @Override
    public void chatModelUpdate(List<ChatTextMessage> messages) {
        Platform.runLater(() -> {
            gameController.chatModelUpdate(messages);
        });
    }

    @Override
    public void onGameEnded() {
        Platform.runLater(() -> {
            gameController.onGameEnded();
        });
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
