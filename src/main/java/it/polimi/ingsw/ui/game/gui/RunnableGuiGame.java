package it.polimi.ingsw.ui.game.gui;

import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.ui.game.GameGateway;
import it.polimi.ingsw.ui.game.GameViewEventHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javafx.scene.SceneAntialiasing;

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

    public void initModel(Game model, GameViewEventHandler handler, String owner) {
        this.model = model;
        this.handler = handler;
        this.owner = owner;
    }

    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(fxmlURL);

        model = new Game(GameMode.GAME_MODE_2_PLAYERS);
        model.addPlayer("Fornaciari");
        model.addPlayer("Reghenzani");
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

        primaryStage.setTitle("LOGIN PAGE");
        primaryStage.show();

        gameController.modelUpdate(model);
    }

    @Override
    public void onGameCreated() {

    }

    @Override
    public void modelUpdate(Game game) {

    }

    @Override
    public void onGameSelectionReply(SingleResult<TileSelectionFailures> turnResult) {

    }

    @Override
    public void onGameInsertionReply(SingleResult<BookshelfInsertionFailure> turnResult) {

    }
}
