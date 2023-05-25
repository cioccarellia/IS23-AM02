package it.polimi.ingsw.ui.lobby.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class LobbyController extends Application implements {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage lobbyStage) throws IOException {
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
        primaryStage.setTitle("My shelfie");
        primaryStage.show();

    }
}
