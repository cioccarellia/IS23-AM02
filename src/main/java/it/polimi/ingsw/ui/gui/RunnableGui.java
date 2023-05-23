package it.polimi.ingsw.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class RunnableGui extends Application {
    @FXML
    public Button quitButton;

    public void start(Stage primaryStage) throws IOException {

        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(false);
        primaryStage.setFullScreenExitHint("");


        // Load root layout from fxml file.

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/index.fxml"));
        Parent rootLayout = null;

        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            //AppClient.LOGGER.severe(e.getMessage());
            System.exit(1);
        }


        primaryStage.setScene(rootLayout.getScene());
        primaryStage.setTitle("My shelfie");
        primaryStage.show();

    }

    @FXML
    public void stop() {
        quitButton.setOnMouseClicked(mouseEvent -> {
            Platform.exit();
            System.exit(0);
        });
    }
}
