package it.polimi.ingsw.ui.gui;


import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.ui.UiGateway;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Objects;

public abstract class GuiApp extends Application implements UiGateway {



    @FXML
    private TextField UsernameField;
    @FXML
    private MenuItem tcp;
    @FXML
    private MenuItem rmi;
    @FXML
    private MenuButton playerNumberSelection;
    @FXML
    private Label playerCounter;
    ServerController controller = new ServerController();

    @FXML
    public void start(Stage primaryStage) throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("logInScreen.fxml")));
        primaryStage.setScene(root.getScene());
        primaryStage.show();

    }
    @FXML
    public void joinAction(ActionEvent actionEvent) {

        if (rmi.getText().equals("rmi")) {

            controller.gameConnectionRequest(UsernameField.getText(), ClientProtocol.RMI);

            if (controller.isUsernameActivePlayer(UsernameField.getText())) {
                //TODO implement getPlayerList inside game controller
             //   playerCounter.setText("Online players:" + controller.);
            }

        } else if (tcp.getText().equals("tcp")) controller.gameConnectionRequest(UsernameField.getText(), ClientProtocol.TCP);

    }
}