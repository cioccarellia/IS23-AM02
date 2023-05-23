package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.ui.UiGateway;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class SceneManager extends Application{
    private static Scene actualScene;
    private static GuiApp actualController;

    public static void changeScene(GuiApp controller, String pathToFXML) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("/fxml/" + pathToFXML));
            fxmlLoader.setController(controller);
            actualController = controller;
            Parent parent = fxmlLoader.load();
            actualScene.setRoot(parent);
            Platform.runLater(() -> actualScene.getWindow().sizeToScene());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void start(Stage stage){

    }

    /**
     * Returns the actual scene
     *
     * @return actual scene
     */
    public static Scene getActualScene() {
        return actualScene;
    }

    /**
     * Returns the actual controller
     *
     * @return actual controller
     */
    public static GuiApp getActualController() {
        return actualController;
    }

}
