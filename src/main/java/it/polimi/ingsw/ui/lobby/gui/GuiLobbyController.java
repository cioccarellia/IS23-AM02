package it.polimi.ingsw.ui.lobby.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GuiLobbyController extends Application {

    @FXML
    public TextField usernameField;
    @FXML
    public Label loginStatus;
    @FXML
    public Button loginButton;

    String username = "admin";

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public void loginAction(ActionEvent actionEvent){

        if(usernameField.getText() != null){
            loginStatus.setText("login status: LOGGED IN");
        }
    }
}
