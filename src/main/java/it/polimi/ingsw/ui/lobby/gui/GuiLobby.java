package it.polimi.ingsw.ui.lobby.gui;

import it.polimi.ingsw.ui.lobby.LobbyViewEventHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

@SuppressWarnings("FieldCanBeLocal")
public class GuiLobby extends Application {

    private final LobbyViewEventHandler handler;

    public GuiLobby(LobbyViewEventHandler handler) {
        this.handler = handler;
    }

    @Override
    public void start(Stage lobbyStage) throws Exception {
        lobbyStage.setMaximized(true);
        lobbyStage.setFullScreen(false);
        lobbyStage.setFullScreenExitHint("");


        // Load root layout from fxml file.

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/loginScene.fxml"));
        Parent rootLayout = null;

        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            //AppClient.LOGGER.severe(e.getMessage());
            System.exit(1);
        }


        lobbyStage.setScene(rootLayout.getScene());
        lobbyStage.setTitle("LOGIN PAGE");
        lobbyStage.show();

    }
}
