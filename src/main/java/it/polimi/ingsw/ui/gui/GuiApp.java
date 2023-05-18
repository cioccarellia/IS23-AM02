package it.polimi.ingsw.ui.gui;


import it.polimi.ingsw.app.client.AppClient;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.UiGateway;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.input.KeyCombination;
import javafx.application.Platform;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static it.polimi.ingsw.model.game.GameMode.GAME_MODE_4_PLAYERS;


public class GuiApp extends Application implements UiGateway {
    private final int dimension = BoardConfiguration.getInstance().getDimension();
    private Game game;

    @FXML
    public void start(Stage primaryStage) throws IOException {
        game = new Game(GAME_MODE_4_PLAYERS);
        game.addPlayer("Cookie");
        game.addPlayer("Alberto");
        game.addPlayer("Marco");
        game.addPlayer("Giulia");

        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(false);
        primaryStage.setFullScreenExitHint("");


        // Load root layout from fxml file.
       /*
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Index.fxml"));
        Parent rootLayout = null;

        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            AppClient.LOGGER.severe(e.getMessage());
            System.exit(1);
        }

        */

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Index.fxml")));
        primaryStage.setScene(root.getScene());
        primaryStage.setTitle("My shelfie");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.show();



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


    @Override
    public void onGameStarted() {

    }

    @Override
    public void modelUpdate(Game game) {
        GridPane matrix = new GridPane();
        Tile tile;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                tile = game.getGameMatrix()[i][j];

                switch (tile) {
                    case BOOK -> {
                        matrix.add(createImageMatrix("resources/img/tiles/book1.1.png"), i, j);
                    }
                    case CAT -> {
                        matrix.add(createImageMatrix("resources/img/tiles/cat1.1.png"), i, j);
                    }
                    case GAME -> {
                        matrix.add(createImageMatrix("resources/img/tiles/game1.1.png"), i, j);
                    }
                    case TROPHY -> {
                        matrix.add(createImageMatrix("resources/img/tiles/trophy1.1.png"), i, j);
                    }
                    case PLANT -> {
                        matrix.add(createImageMatrix("resources/img/tiles/plant1.1.png"), i, j);
                    }
                    case FRAME -> {
                        matrix.add(createImageMatrix("resources/img/tiles/frame1.1.png"), i, j);
                    }

                }

            }
        }
    }

    public ImageView createImageMatrix(String name) {
        ImageView book = new ImageView();
        book.setImage(new Image(name));

        return book;
    }

    public Set<Coordinate> getSelectedCoordinates(Node tileNode) {

        Set<Coordinate> selectedCoordinate = new HashSet<>();
        Coordinate coordinate;

        Integer col = GridPane.getColumnIndex(tileNode);
        Integer row = GridPane.getRowIndex(tileNode);

        coordinate = new Coordinate(row, col);

        selectedCoordinate.add(coordinate);
        return selectedCoordinate;
    }

    @Override
    public void gameSelection() {
         //game.onPlayerSelectionPhase(getSelectedCoordinates(Player1Tile23.));

    }

    @Override
    public void gameInsertion() {

    }

    @Override
    public void onGameEnded() {

    }


}