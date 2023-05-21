package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.model.game.Game;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class RunnableGui extends Application {
    Game game;
    @FXML
    private ImageView Player1Tile23;

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


        //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("index.fxml")));
        primaryStage.setScene(rootLayout.getScene());
        primaryStage.setTitle("My shelfie");
        primaryStage.show();


        // mouse event, selezione dalla board di una tile
        Player1Tile23.setOnMouseClicked(mouseEvent -> {
            Integer col = GridPane.getColumnIndex(Player1Tile23);
            Integer row = GridPane.getRowIndex(Player1Tile23);
        });

    }

    @FXML
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}
