package it.polimi.ingsw.ui.game.gui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class SceneManager {
    private static Scene actualScene;
    private static GuiIndexController actualController;

    public static void changeScene(GuiIndexController controller, String pathToFXML) {
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
    public static GuiIndexController getActualController() {
        return actualController;
    }

}
