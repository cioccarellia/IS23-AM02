package it.polimi.ingsw.ui.gui;


import it.polimi.ingsw.controller.server.GameController;
import it.polimi.ingsw.ui.UiGateway;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public abstract class GuiApp extends Application implements UiGateway {


    @FXML
    GameController controller = new GameController();

    public static void main(String[] args) {
        launch(args);
    }

    /*public void Join(ActionEvent actionEvent) {
        controller.gameConnectionRequest();
    }*/

    ;
}

