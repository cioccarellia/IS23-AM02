package it.polimi.ingsw.ui.gui;


import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.ui.UiGateway;
import javafx.application.Application;
import javafx.fxml.FXML;

public abstract class GuiApp extends Application implements UiGateway {


    @FXML
    ServerController controller = new ServerController();

    public static void main(String[] args) {
        launch(args);
    }

    /*public void Join(ActionEvent actionEvent) {
        controller.gameConnectionRequest();
    }*/

    ;
}

