package it.polimi.ingsw.ui.game.gui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

/**
 * The SceneManager class is responsible for managing scene transitions in the GUI game interface.
 * It provides a static method to change scenes by loading the corresponding FXML file and setting the appropriate controller.
 * The class also keeps track of the actual scene and controller for easy access.
 */
public class SceneManager {
    private static Scene actualScene;
    private static GuiGameController actualController;

    /**
     * Changes the scene to the specified FXML file and sets the provided controller.
     *
     * @param controller The controller for the new scene.
     * @param pathToFXML The path to the FXML file representing the new scene.
     */
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
     * @return The actual scene.
     */
    public static Scene getActualScene() {
        return actualScene;
    }

    /**
     * @return The actual controller.
     */
    public static GuiGameController getActualController() {
        return actualController;
    }

}
