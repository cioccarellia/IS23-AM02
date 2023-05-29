package it.polimi.ingsw.ui.game.gui;

import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameMode;
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

    private Game model;
    private GameViewEventHandler handler;
    private String owner;

    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * Initializes the model, handler, and owner for the GUI game.
     *
     * @param model   The game model.
     * @param handler The game view event handler.
     * @param owner   The owner of the game.
     */
    public void initModel(Game model, GameViewEventHandler handler, String owner) {
        this.model = model;
        this.handler = handler;
        this.owner = owner;
    }

    /**
     * Starts the GUI game by loading the FXML file and initializing the game controller.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(fxmlURL);

        model = new Game(GameMode.GAME_MODE_3_PLAYERS);
        model.addPlayer("Fornaciari");
        model.addPlayer("Reghenzani");
        model.addPlayer("Margara");
        model.onGameStarted();
        owner = "Fornaciari";

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
    public void onGameSelectionReply(SingleResult<TileSelectionFailures> turnResult) {
        gameController.onGameSelectionReply(turnResult);
    }

    /**
     * Notifies the game controller about the result of a bookshelf insertion in the game.
     *
     * @param turnResult The result of the bookshelf insertion.
     */
    @Override
    public void onGameInsertionReply(SingleResult<BookshelfInsertionFailure> turnResult) {
        gameController.onGameInsertionReply(turnResult);
    }
}
