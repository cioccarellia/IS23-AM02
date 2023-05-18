package it.polimi.ingsw.ui.gui;


import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.ui.UiGateway;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;


public class GuiApp extends Application implements UiGateway {
    @FXML
    private Image Player1Tile23;
    @FXML
    private Image Player1Tile01;
    @FXML
    private Image Player1Tile02;
    @FXML
    private Image Player1Tile03;
    @FXML
    private Image Player1Tile04;
    @FXML
    private Image Player1Tile10;
    @FXML
    private GridPane gridPane;



    @FXML
    public void start(Stage primaryStage) throws IOException {
        Game game = new Game(GameMode.GAME_MODE_4_PLAYERS);
        game.addPlayer("Cookie");
        game.addPlayer("Alberto");
        game.addPlayer("Marco");
        game.addPlayer("Giulia");

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Index.fxml")));
        primaryStage.setScene(root.getScene());
        primaryStage.show();
        gridPane.

    }

    @FXML
    public void stop() {
        //Guimanager.getInstance().closeConnection();
        System.exit(0);
    }


    @Override
    public void onGameStarted() {

    }

    @Override
    public void modelUpdate(Game game) {

    }

    @Override
    public void gameSelection() {

    }

    @Override
    public void gameInsertion() {

    }

    @Override
    public void onGameEnded() {

    }
}