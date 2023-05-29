package it.polimi.ingsw.ui.game.gui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class SceneManager {
    private static Scene actualScene;
    private static GuiGameController actualController;


    public static void changeScene(GuiGameController controller, String pathToFXML) {
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

    /**
     * Returns the actual guiHelper
     *
     * @return actual guiHelper
     */
    public static Scene getActualScene() {
        return actualScene;
    }

    /**
     * Returns the actual controller
     *
     * @return actual controller
     */
    public static GuiGameController getActualController() {
        return actualController;
    }

}
