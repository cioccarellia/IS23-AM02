package it.polimi.ingsw.ui.lobby.gui;

import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.types.GameConnectionSuccess;
import it.polimi.ingsw.controller.server.result.types.GameCreationSuccess;
import it.polimi.ingsw.ui.lobby.LobbyGateway;
import it.polimi.ingsw.ui.lobby.LobbyViewEventHandler;
import javafx.application.Application;
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

@SuppressWarnings("FieldCanBeLocal")
public class RunnableGuiLobby extends Application implements LobbyGateway {

    private final URL fxmlURL = getClass().getResource("/fxml/lobby/logIn.fxml");

    private static final Logger logger = LoggerFactory.getLogger(RunnableGuiLobby.class);


    private GuiLobbyController lobbyController;
    private LobbyViewEventHandler handler;


    public static void main(String[] args) {
        Application.launch(args);
    }


    public void initHandler(LobbyViewEventHandler handler) {
        this.handler = handler;
    }
    

    @Override
    public void start(Stage lobbyStage) throws Exception {
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
        lobbyController.init(handler);

        Scene loadedScene = new Scene(rootLayout, 600, 400, false, SceneAntialiasing.BALANCED);

        lobbyStage.setScene(loadedScene);


        lobbyStage.setTitle("Login page");
        lobbyStage.getIcons().add(new Image("img/publisher_material/publisher.png"));
        lobbyStage.show();
    }

    @Override
    public void onServerStatusUpdate(ServerStatus status, List<PlayerInfo> playerInfo) {
        lobbyController.onServerStatusUpdate(status, playerInfo);
    }

    @Override
    public void onServerCreationReply(TypedResult<GameCreationSuccess, GameCreationError> result) {
        lobbyController.onServerCreationReply(result);
    }

    @Override
    public void onServerConnectionReply(TypedResult<GameConnectionSuccess, GameConnectionError> result) {
        lobbyController.onServerConnectionReply(result);
    }

    @Override
    public void kill() {
        lobbyController.kill();
    }
}
